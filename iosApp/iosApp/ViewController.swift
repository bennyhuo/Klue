//
//  ViewController.swift
//  KlueSample
//
//  Created by benny on 2022/07/17.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var reload: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        
        reload.addAction(UIAction { (action:UIAction) in
            print("Clicked!!")
        }, for: .touchUpInside)
    }


}

