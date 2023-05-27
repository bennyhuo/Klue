//
// Created by benny on 2023/5/23.
// Copyright (c) 2023 orgName. All rights reserved.
//

import Foundation
import SampleBridge
import React

@objc
extension KlueModule: RCTBridgeModule {

    private static var isRegistered = false
       
    public static func __rct_export__170()-> UnsafeMutableRawPointer {
        ReactNativeBridge.shared.methodInfo()
    }

    public static func moduleName() -> String {
        "KlueModule"
    }

    public static func requiresMainQueueSetup() -> Bool {
        true
    }
    
    public static func register() {
        if !isRegistered {
            isRegistered = true
            RCTRegisterModule(KlueModule.self)
        }
    }
}
