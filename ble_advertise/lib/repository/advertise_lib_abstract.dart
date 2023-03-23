import 'dart:io';

import 'package:flutter/services.dart';
import 'package:tecsen_plugin/advertise_options.dart';
import 'package:tecsen_plugin/tecsen_plugin_method_channel.dart';

/// Ble Advertisement abstract class
///
/// Depending on whether the current platform is Android or iOS, different results are returned.
/// Therefore, declare a common method and override it for each platform accordingly.

abstract class IBleAdvertise {
  /// Start Advertise Method,
  ///
  /// setting the Ble uuid, [uuid], must be setting
  ///
  /// setting Ble name [bluetoothSetName], must be setting
  ///
  ///For Optional about Advertise
  ///

  Future<bool> startAdvertise(
      {required String uuid,
      required String bluetoothSetName,
      AdvertiseOptions? advertiseOptions});

  /// open Bluetooth Setting page
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

  ///Checked current device can advertise
  Future<bool> get isAbleAdvertise;
}
