//
// Created by Benny Huo on 2023/5/22.
// Copyright (c) 2023 orgName. All rights reserved.
//

import Foundation

import Foundation
import React

@objc(SampleBridgeKlueModule2)
class SampleBridgeKlueModule2: NSObject, RCTBridgeModule {

    static func moduleName() -> String! {
        return "KlueModule"
    }

    static func requiresMainQueueSetup() -> Bool {
        return true
    }

    static let config = RCTMethodInfo(
            jsName: "",
            objcName: "callNativeValue:(NSString *)value resolve:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject",
            isSync: false
    )

    @objc
    static func __rct_export__170() -> RCTMethodInfo {
        config
    }
}