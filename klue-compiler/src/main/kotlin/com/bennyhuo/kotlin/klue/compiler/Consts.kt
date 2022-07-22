package com.bennyhuo.kotlin.klue.compiler

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.MemberName

/**
 * Created by benny.
 */
val PROMISE_TYPE = ClassName.bestGuess("kotlin.js.Promise")
val KLUE_TYPE = ClassName.bestGuess("com.bennyhuo.klue.js.KlueJs")
val BRIDGE_TYPE = ClassName.bestGuess("com.bennyhuo.klue.common.Bridge")
val JSEXPORT_TYPE = ClassName.bestGuess("kotlin.js.JsExport")
val JSON_TYPE = ClassName.bestGuess("kotlinx.serialization.json.Json")
val DECODE_FROM_STRING = MemberName("kotlinx.serialization", "decodeFromString")
val ENCODE_TO_STRING = MemberName("kotlinx.serialization", "encodeToString")
val SERIALIZABLE = ClassName("kotlinx.serialization", "Serializable")
val SERIALIZABLE_NAME = "kotlinx.serialization.Serializable"