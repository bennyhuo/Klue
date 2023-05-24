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
    
    @nonobjc
    private static let jsName = "".utf8CString
    
    @nonobjc
    private static let objcName = "callNativeValue:(NSString *)value resolve:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject".utf8CString
    
    private static let config = RCTMethodInfo(
        jsName: jsName.withUnsafeBufferPointer(\.baseAddress),
        objcName: objcName.withUnsafeBufferPointer(\.baseAddress),
        isSync: false
    )
    
    private static let configPointer = UnsafeMutablePointer<RCTMethodInfo>.allocate(capacity: 1)
    
    public static func __rct_export__170()-> UnsafePointer<RCTMethodInfo> {
        configPointer.initialize(to: config)
        return UnsafePointer(configPointer)
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
