import 'dart:io';

import 'package:flutter/services.dart';
import 'package:tecsen_plugin/tecsen_plugin_method_channel.dart';

///Advertise implement

abstract class IBleAdvertise {
  /// Start Advertise Method,
  ///
  /// setting Ble name [bluetoothSetName], must be setting
  ///
  /// setting the Ble uuid, [uuid], must be setting
  ///
  ///For Optional Parameter
  ///
  /// setting Advertise TxPower [bleTxPower], default
  ///
  /// setting Advertise miliSeconds, [timeOutMiliSec] During this time, we will maintain the advertisement.default : 1000 miliSeconds
  ///
  /// setting Advertise Latency Mode, [advertiseMode], default [AdvertiseMode.balanced]

  Future<bool> startAdvertise(
      {required String bluetoothSetName,
      required String uuid,
      BleTXPower? bleTxPower,
      int? timeOutMiliSec,
      AdvertiseMode? advertiseMode});

  /// open Setting page
  ///
  /// Support os : Android, iOS
  ///
  /// Android device go to bluetooth setting page
  ///
  /// iOS go to bluetooth setting page
  Future<bool> openBleSettingPage();

  /// Stop Advertise
  ///
  /// still advertise, you can stop advertise using this method
  ///
  /// Nothing will happen if we don't advertise.
  Future<bool> stopAdvertise();

  ///Checked current device activated advertise
  Future<bool> get isActivatedAdvertise;
}

class TecsenLibrary {
  static final tecsenLibrary = Platform.isAndroid
      ? TecsenAndroidPlugin(const MethodChannel('tecsen_plugin_android'))
      : Platform.isIOS
          ? TecsenIoSPlugin(const MethodChannel('tecsen_plugin_ios'))
          : throw ("TECSEN PLATFORM EXCEPTION");

  static Future startAdvertise(
          {required String bluetoothSetName,
          required String uuid,
          TecsenTXPower? txPower,
          int? timeOutMiliSec,
          TecsenAdvertiseMode? advertiseMode}) =>
      tecsenLibrary.startAdvertise(
          bluetoothSetName: bluetoothSetName,
          uuid: uuid,
          txPower: txPower,
          timeOutMiliSec: timeOutMiliSec,
          advertiseMode: advertiseMode);
  static Future openBlePage() => tecsenLibrary.openBleSettingPage();
}
