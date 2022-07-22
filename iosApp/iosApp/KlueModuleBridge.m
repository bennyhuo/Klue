//
//  CalendarManagerBridge.m
//  iosApp
//
//  Created by benny on 2022/07/18.
//  Copyright © 2022 orgName. All rights reserved.
//

#import <Foundation/Foundation.h>

#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_REMAP_MODULE(KlueModule, SampleBridgeKlueModule, NSObject)

RCT_EXTERN_METHOD(callNativeValue:(NSString *)value
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)

@end
