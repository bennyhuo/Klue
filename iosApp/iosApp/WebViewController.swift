//
//  WebViewController.swift
//  iosApp
//
//  Created by benny on 2022/07/17.
//  Copyright © 2022 orgName. All rights reserved.
//

import UIKit
import WebKit
import SampleBridge

class WebViewController: UIViewController {

    @IBOutlet weak var urlTextField: UITextField!
    @IBOutlet weak var webView: WKWebView!
    @IBOutlet weak var reloadButton: UIButton!
    
    private func reloadWebView() {
        guard let urlText = urlTextField.text else {
            print("url input is nil.")
            return
        }
        
        guard let url = URL(string: urlText) else {
            print("invalid url.")
            return
        }

        let request = URLRequest(url: url)
        webView.load(request)
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        IosWebViewBridge(webView: webView).register(
            bridge: UtilsKt.bridge(UtilsImpl())
        )
        reloadWebView()
        
        reloadButton.addAction(UIAction { action in
            self.reloadWebView()
        }, for: .touchUpInside)
                
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
