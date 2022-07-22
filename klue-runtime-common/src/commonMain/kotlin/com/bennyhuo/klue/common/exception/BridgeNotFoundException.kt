package com.bennyhuo.klue.common.exception

class BridgeNotFoundException(val bridgeName: String) :
    Exception("Implementation of $bridgeName is not found.")