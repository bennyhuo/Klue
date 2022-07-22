package com.bennyhuo.kotlin.klue.compiler

import com.google.devtools.ksp.innerArguments
import com.google.devtools.ksp.outerType
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSType

/**
 * Created by benny.
 */
fun Resolver.isSupportedParameterType(ksType: KSType): Int {
    return when (ksType) {
        builtIns.anyType,
        builtIns.numberType,
        builtIns.nothingType,
        builtIns.iterableType,
        builtIns.annotationType,
        builtIns.unitType,
        builtIns.arrayType -> {
            return -1
        }
        builtIns.byteType,
        builtIns.charType,
        builtIns.booleanType,
        builtIns.intType,
        builtIns.longType,
        builtIns.floatType,
        builtIns.doubleType,
        builtIns.stringType -> {
            return 1
        }
        else -> {
            if (ksType.outerType == builtIns.arrayType) {
                ksType.innerArguments.firstOrNull()?.type?.resolve()?.let {
                    isSupportedParameterType(it)
                } ?: -1
            } else {
                0
            }
        }
    }
}