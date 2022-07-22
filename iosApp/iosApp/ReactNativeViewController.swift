//
//  ReactNativeViewController.swift
//  iosApp
//
//  Created by benny on 2022/07/17.
//  Copyright © 2022 orgName. All rights reserved.
//

import UIKit
import React
import SampleBridge

class ReactNativeViewController: UIViewController, RCTBridgeDelegate {

    @IBOutlet weak var urlText: UITextField!
    @IBOutlet weak var reactNativeViewContainer: UIView!
    @IBOutlet weak var reloadButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        reload()
        
        reloadButton.addAction(UIAction {
            action in self.reload()
        }, for: .touchUpInside)
    }
    
    private func reload() {
        for subView in reactNativeViewContainer.subviews {
            subView.removeFromSuperview()
        }
        
        guard let jsCodeLocation = URL(string: "\(urlText.text ?? "")/index.bundle?platform=ios") else {
            print("invalid url")
            return
        }
        
        let reactNativeView = RCTRootView(
            bundleURL: jsCodeLocation,
            moduleName: "KlueSample",
            initialProperties: [:],
            launchOptions: nil
        )
        
        reactNativeView.backgroundColor = UIColor.white
        reactNativeView.frame = reactNativeViewContainer.bounds
        reactNativeView.autoresizingMask = [.flexibleWidth, .flexibleHeight]
        let klueModule = reactNativeView.bridge.module(forName: "KlueModule") as! KlueModule
        klueModule.getBridge().register(bridge: UtilsKt.bridge(UtilsImpl()))
        
        reactNativeViewContainer.addSubview(reactNativeView)
    }
    
    func sourceURL(for bridge: RCTBridge!) -> URL! {
        RCTBundleURLProvider.jsBundleURL(forBundleRoot: "index", packagerHost: urlText.text, enableDev: true, enableMinification: false)
    }

}
