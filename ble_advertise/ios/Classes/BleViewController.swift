// //
// //  ViewController.swift
// //  BruceGetQrBd
// //
// //  Created by Bruce Jy Lee on 2022/07/28.
// //

// import UIKit
// import AVFoundation
// import CoreBluetooth


// class BleViewController: UIViewController {
 
    
//     @IBOutlet weak var openButton: UIButton!
//     @IBOutlet weak var lockButton: UIButton!
//     @IBOutlet weak var startButton: UIButton!
 
//     @IBOutlet weak var messageLabel: UILabel!
//     @IBOutlet weak var readValueLabel: UILabel!
//     @IBOutlet weak var writeValueLabel: UILabel!
    
//     public var peer: PeerAdv!
//     public var console: Console?
    
//     override func viewDidLoad() {
//         super.viewDidLoad()
//         // Do any additional setup after loading the view.
//         self.peer = PeerAdv()
//         self.peer.delegate = self

//         console = Console(rawValue: 0xFD)
//         self.peer.broadcast(Console: console!)
//     }
    
//     override func viewWillDisappear(_ animated: Bool) {
//         super.viewWillDisappear(animated)
//         self.peer.broadcast(Console: .None)
//     }
    
//     public override func viewDidDisappear(_ animated: Bool) {
//         self.peer.stopBroadcasting()
//     }
    
//     public func bleadvertise() {
//         console = Console(rawValue: 0xFD)
//         self.peer.broadcast(Console: console!)
//     }
    
    
//     @IBAction func openButtonAction(_ sender: UIButton) {
//         // 5. Start advertising
//         console = Console(rawValue: 0xFD)
//         self.peer.broadcast(Console: console!)
//         writeValueLabel.text = serviceUUIDString
//  //       sender.isSelected = self.readerView.isRunning
//     }
    
//     @IBAction func lockButtonAction(_ sender: UIButton) {
//         console = Console(rawValue: 0xFE)
//         self.peer.broadcast(Console: console!)
//         writeValueLabel.text = serviceUUIDString
//  //       sender.isSelected = self.readerView.isRunning
//     }
    
// }

// // MARK: - PeerDelegate

// extension BleViewController: PeerAdvDelegate {
//     public func peerDidBecomeActive(peer: PeerAdv) {
//     }

//     public func peerDidBecomeInactive(peer: PeerAdv) {
//     }
// }

// extension Data {
//     struct HexEncodingOptions: OptionSet {
//         let rawValue: Int
//         static let upperCase = HexEncodingOptions(rawValue: 1 << 0)
//     }
    
//     func hexEncodedString(options: HexEncodingOptions = []) -> String {
//         let format = options.contains(.upperCase) ? "%02hhX" : "%02hhx"
//         return map { String(format: format, $0) }.joined()
//     }
// }
