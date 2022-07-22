package com.bennyhuo.klue.common.exception

class BridgeInvokeException(val bridgeName: String, val code: Int, val error: String) :
    Exception("Invoke $bridgeName error: $code, $error.")