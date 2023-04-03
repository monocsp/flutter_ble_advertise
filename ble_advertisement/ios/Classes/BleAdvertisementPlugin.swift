import Flutter
import UIKit
import CoreBluetooth

public class SwiftBlePlugin: NSObject, FlutterPlugin {
  
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "com.pcs.flutter_ble_advertisement_ios", binaryMessenger: registrar.messenger())
    let instance = SwiftBlePlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }
    let peerAdv = PeerAdv.shared
    var console: Console?



  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {

    switch call.method{
      case "advertise":
                DispatchQueue.main.async {
                    guard let args = call.arguments as? [String: String] else {result(FlutterError(code:call.method,message:"MISSING ARGUMENT",details:nil))
                     return
                     }
                    let key = args["UUID"]! //Device UUID
                    let localName = args["NAME"]! //name
                    let txpower = args["txpower"]!

                    self.console = Console(rawValue: 0x0202)
                    PeerAdv.shared.updateUUID(uuid: key)
                    PeerAdv.shared.updateLocalNameKey(name: localName)
                    PeerAdv.shared.broadcast(Console: self.console!)
                }
                DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) {
                    PeerAdv.shared.stopBroadcasting()
                }
                result(true)

    case "openBluetoothSetting" : //블루투스 설정화면
                //                  self?.receiveBatteryLevel(result: result)
                self.openBluetooth()
                result(true)

              default:
              result(FlutterMethodNotImplemented)
              
    }
    result("iOS " + UIDevice.current.systemVersion)
  }

func openBluetooth(){
        let myUrl = "http://www.google.com"
        if let url = URL(string: "App-Prefs:root=Bluetooth"), !url.absoluteString.isEmpty {
            UIApplication.shared.open(url, options: [:], completionHandler: nil)
        }
    }

}


//
//  PeerAdv.swift
//  Runner
//
//  Created by HoSeon Chu on 2022/09/06.
//

import CoreBluetooth
import Foundation

public let serviceUUIDString = "FB3367FD-09F8-4E08-9F6B-07E309FD2576"
//public let Push_Service_UUID = "ffffffff-ffff-ffff-5359-4e4350555348" //출근
//public var Push_Service_UUID = "ffffffff-ffff-ffff-5359-4e4345584954" //퇴근이 뭘까???
public var Push_Service_UUID = "12345678-ffff-ffff-5359-4e4345584954" //외출
public var Push_Service_Name: String = "Caps8"
///public let Push_Service_UUID = "0000b81d-0000-1000-8000-00805f9b34fb" //외출
                                   //4     2    2    2        6
// public var Push_Service_TxPowerLevel : String = 

public class PeerAdv: NSObject {
    
    //MARK: 싱글톤 선언 시작
    public static let shared: PeerAdv = PeerAdv()
    
    
    private var peripheral: CBPeripheralManager!
    private var central: CBCentralManager!
    public var delegate: PeerAdvDelegate?
    
    private var isPeripheralActive = false {
        didSet { self.delegateActivationIfNeeded(wasActive: oldValue) }
    }

    public var active: Bool {
        return self.isPeripheralActive
    }

    public override init() {
        super.init()
        self.peripheral = CBPeripheralManager(delegate: self, queue: nil)
    }

    // MARK: Broadcasting
    public func broadcast(Console: Console) {
        guard self.active else {
            NSLog("[WARNING] Peer is not active. Skip broadcasting.")
            return
        }
        let encoded = Console.encode()
        NSLog("Start broadcasting: \(encoded)")
        self.peripheral.stopAdvertising()
        self.peripheral.startAdvertising([
            CBAdvertisementDataLocalNameKey: Push_Service_Name,
            CBAdvertisementDataServiceUUIDsKey: [CBUUID(string: encoded)],
        ])
    }

    public func stopBroadcasting() {
        self.peripheral.stopAdvertising()
    }
    
    public func updateUUID(uuid: String) {
        Push_Service_UUID = uuid
    }

    public func updateLocalNameKey(name: String) {
        Push_Service_Name = name
    }
    
    //업데이트 요망
    public func updateBleTxPower(txpower: String{
        

    }
    
    // MARK: Active

    private func delegateActivationIfNeeded(wasActive: Bool) {
        if !wasActive && self.active {
            self.delegate?.peerDidBecomeActive(peer: self)
        } else if wasActive && !self.active {
            self.delegate?.peerDidBecomeInactive(peer: self)
        }
    }

}

public protocol PeerAdvDelegate: NSObjectProtocol {
    func peerDidBecomeActive(peer: PeerAdv)
    func peerDidBecomeInactive(peer: PeerAdv)
}

extension PeerAdv: CBPeripheralManagerDelegate {

    public func peripheralManagerDidUpdateState(_ peripheral: CBPeripheralManager) {
        switch peripheral.state {
        case .poweredOn:
            let serviceUUID = CBUUID(string: serviceUUIDString)
            let service = CBMutableService(type: serviceUUID, primary: true)
            self.peripheral.add(service)
            peripheralManager(peripheral: peripheral, didAddService: service, error: NSError(domain: "Error", code: 4))
        default:
            break
        }
    }
    
    public func peripheralManager(peripheral: CBPeripheralManager,
                                  didAddService service: CBService,
                                  error: NSError?) {
        self.isPeripheralActive = true
    }

    public func peripheralManagerDidStartAdvertising(peripheral: CBPeripheralManager, error: NSError?) {
//        self.log("[Peripheral] Send")
    }

}

public enum Console: Int {
    case Open = 0xFD
    case Lock = 0xFE
    case None = 0xFF
    case Test = 0x0101
    case Out = 0x0202

    public var hexValue: String {
        let hex = String(self.rawValue, radix: 16, uppercase: true)
        if self.rawValue < 0xA {
            return "0" + hex
        }
        return hex
    }

    public var text: String {
        switch self {
            case .Open: return "Open Door"
            case .Lock: return "Lock Door"
            case .None: return "Unknown"
            default: return String(self.rawValue)
        }
    }

    public static let allValues: [Console] = [
        .Open,
        .Lock
    ]
    
    public func encode() -> String {
        return Push_Service_UUID
    }
}


extension Console: CustomStringConvertible {

    public var description: String {
        return "<Console: \(self.text)>"
    }

}

