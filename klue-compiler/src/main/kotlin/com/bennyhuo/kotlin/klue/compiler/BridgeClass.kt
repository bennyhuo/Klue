package com.bennyhuo.kotlin.klue.compiler

import com.bennyhuo.klue.annotations.KlueBridge
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.processing.JsPlatformInfo
import com.google.devtools.ksp.processing.JvmPlatformInfo
import com.google.devtools.ksp.processing.NativePlatformInfo
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSTypeReference
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.STRING
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.addOriginatingKSFile
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo
import io.outfoxx.swiftpoet.DeclaredTypeName
import io.outfoxx.swiftpoet.ExtensionSpec
import io.outfoxx.swiftpoet.FunctionSpec
import io.outfoxx.swiftpoet.Modifier
import java.io.File

/**
 * Created by benny.
 */
class BridgeClass(
    private val environment: SymbolProcessorEnvironment,
    private val bridgeType: KSClassDeclaration,
    private val options: Options
) {

    val logger = environment.logger

    val name: String by lazy {
        bridgeType.getAnnotationsByType(KlueBridge::class).first().name.takeIf { it.isNotBlank() }
            ?: bridgeType.simpleName.asString()
    }

    fun generate(resolver: Resolver) {
        if (environment.platforms.size > 1) {
            logger.warn("Meta compiling with ${environment.platforms.joinToString { it.platformName }}")
        } else {
            when(environment.platforms.first()) {
                is JsPlatformInfo -> generateJsBridge(resolver)
                is JvmPlatformInfo -> generateNativeBridge(resolver)
                is NativePlatformInfo -> {
                    generateNativeBridge(resolver)
                    generateSwiftExtensions(resolver)
                }
            }
        }
    }

    private fun checkParameterType(resolver: Resolver, typeReference: KSTypeReference) {
        val type = typeReference.resolve()
        when (resolver.isSupportedParameterType(type)) {
            1 -> {

            }
            0 -> {
                if (type.annotations.find {
                        it.annotationType.resolve().declaration.qualifiedName?.asString() == SERIALIZABLE_NAME
                    } == null) {
                    logger.error("Unsupported parameter type: $type. Add @Serializable to fix this.", typeReference)
                }
            }
            else -> {
                logger.error("Unsupported parameter type: $type", typeReference)
            }
        }
    }

    private fun generateParameterClass(resolver: Resolver): Sequence<TypeSpec> {
        return bridgeType.getDeclaredFunctions().filter {
            it.parameters.isNotEmpty()
        }.map { function ->
            TypeSpec.classBuilder("${function.simpleName.asString().capitalize()}Parameter")
                .also { typeBuilder ->
                    val constructor = FunSpec.constructorBuilder()
                    function.parameters.forEachIndexed { index, ksValueParameter ->
                        checkParameterType(resolver, ksValueParameter.type)

                        typeBuilder.addProperty(
                            PropertySpec.builder("p${index}", ksValueParameter.type.toTypeName())
                                .initializer("p${index}")
                                .build()
                        )
                        constructor.addParameter("p${index}", ksValueParameter.type.toTypeName())
                    }
                    typeBuilder.primaryConstructor(constructor.build())
                }
                .addAnnotation(SERIALIZABLE)
                .build()
        }
    }

    private fun generateSwiftExtensions(resolver: Resolver) {
        logger.warn("Generate swift extensions: $name")

        File(options.wrapperSourceDir, "$name.swift").writer().use {
            val bridgeType = DeclaredTypeName.typeName("${options.frameworkName}.$name")

            io.outfoxx.swiftpoet.FileSpec.builder(name).addExtension(
                ExtensionSpec.builder(bridgeType)
                    .addFunction(
                        FunctionSpec.builder("bridge")
                            .addModifiers(Modifier.PUBLIC)
                            .returns(DeclaredTypeName.typeName(".${name}Bridge"))
                            .addStatement("${name}Bridge(target: self)")
                            .build()
                    ).build()

            ).build().writeTo(it)
        }
    }

    private fun generateNativeBridge(resolver: Resolver) {
        val fileSpecBuilder = FileSpec.builder(bridgeType.packageName.asString(), name)
        val classBuilder = TypeSpec.classBuilder("${name}Bridge")
            .addOriginatingKSFile(bridgeType.containingFile!!)
            .addProperty(
                PropertySpec.builder("target", bridgeType.toClassName(), KModifier.OVERRIDE)
                    .initializer("target").build()
            )
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameter("target", bridgeType.toClassName())
                    .build()
            ).addSuperinterface(BRIDGE_TYPE.parameterizedBy(bridgeType.toClassName()))
            .addFunction(
                FunSpec.builder("call")
                    .returns(STRING)
                    .addModifiers(KModifier.OVERRIDE)
                    .addParameter("functionName", String::class)
                    .addParameter("args", STRING)
                    .beginControlFlow("return when(functionName)")
                    .also { funBuilder ->
                        bridgeType.getDeclaredFunctions().forEach { function ->
                            funBuilder.beginControlFlow("%S ->", function.simpleName.asString())
                                .also {
                                    if (function.parameters.isNotEmpty()) {
                                        it.addStatement(
                                            "val parameters = %T.%M<${
                                                function.simpleName.asString().capitalize()
                                            }Parameter>(args)",
                                            JSON_TYPE,
                                            DECODE_FROM_STRING
                                        )
                                    }
                                }
                                .addStatement(
                                    "%T.%M(target.%L(${
                                        (0 until function.parameters.size).joinToString {
                                            "parameters.p$it"
                                        }
                                    }))",
                                    JSON_TYPE,
                                    ENCODE_TO_STRING,
                                    function.simpleName.asString()
                                ).endControlFlow()
                        }
                    }
                    .addStatement("else -> throw IllegalArgumentException(\"\$functionName is not supported.\")")
                    .endControlFlow()
                    .build()
            ).addProperty(
                PropertySpec.builder("name", String::class, KModifier.OVERRIDE)
                    .initializer("%S", name).build()
            )

        val bridgeFunction = FunSpec.builder("bridge")
            .receiver(bridgeType.toClassName())
            .addStatement("return ${name}Bridge(this)")

        generateParameterClass(resolver).forEach {
            classBuilder.addType(it)
        }

        fileSpecBuilder.addType(classBuilder.build())
            .addFunction(bridgeFunction.build())
            .build().writeTo(environment.codeGenerator, false)
    }

    private fun generateJsBridge(resolver: Resolver) {
        val fileSpecBuilder = FileSpec.builder("", name)
        val classBuilder = TypeSpec.objectBuilder(name)
            .addAnnotation(JSEXPORT_TYPE)
            .addOriginatingKSFile(bridgeType.containingFile!!)

        bridgeType.getDeclaredFunctions().map { function ->
            FunSpec.builder(function.simpleName.asString())
                .returns(PROMISE_TYPE.parameterizedBy(function.returnType!!.toTypeName()))
                .also { funBuilder ->
                    function.parameters.forEach {
                        funBuilder.addParameter(it.name!!.asString(), it.type.toTypeName())
                    }
                }.also { builder ->
                    if (function.parameters.isEmpty()) {
                        builder.addStatement(
                            "return %T.callNative(%S, %S, \"\")",
                            KLUE_TYPE,
                            name,
                            function.simpleName.asString()
                        )
                    } else {
                        builder.addStatement(
                            "return %T.callNative(%S, %S, %T.%M(${function.simpleName.asString().capitalize()}Parameter(${
                                function.parameters.joinToString {
                                    it.name!!.asString()
                                }
                            })))",
                            KLUE_TYPE,
                            name,
                            function.simpleName.asString(),
                            JSON_TYPE,
                            ENCODE_TO_STRING
                        )
                    }
                }
                .build()
        }.let {
            classBuilder.addFunctions(it.toList())
        }

        generateParameterClass(resolver).forEach {
            classBuilder.addType(it)
        }

        fileSpecBuilder.addType(classBuilder.build())
            .build().writeTo(environment.codeGenerator, false)
    }

}