package com.bennyhuo.kotlin.klue.compiler

import com.bennyhuo.klue.annotations.KlueBridge
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration

/**
 * Created by benny at 2021/6/20 19:02.
 */
class KlueSymbolProcessor(private val environment: SymbolProcessorEnvironment) :
    SymbolProcessor {
    private val logger = environment.logger

    init {
        logger.warn("Ksp options: ${environment.options}")
    }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        try {
            logger.warn("SymbolProcessor, ${KotlinVersion.CURRENT}")

            resolver.getSymbolsWithAnnotation(KlueBridge::class.qualifiedName!!)
                .filterIsInstance<KSClassDeclaration>()
                .onEach {
                    if (it.classKind != ClassKind.INTERFACE) {
                        logger.error("KlueBridge should be annotated on interfaces only. ", it)
                    }
                }.toSet()
                .forEach {
                    BridgeClass(environment, it).generate(resolver)
                }

        } catch (e: Exception) {
            logger.exception(e)
        }
        return emptyList()
    }
}