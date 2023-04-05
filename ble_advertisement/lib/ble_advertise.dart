import 'dart:io';

import 'package:flutter/services.dart';
import 'package:ble_advertisement/model/enum/native_channel_name.dart';
import 'package:ble_advertisement/platform_advertise/android/android_advertise.dart';
import 'package:ble_advertisement/platform_advertise/ios/ios_advertise.dart';
import 'package:ble_advertisement/exceptions/advertise_exceptions.dart';
import 'package:ble_advertisement/repository/advertise_lib_abstract.dart';

class BleAdvertise {
  /// Ble Advertise get instance.
  ///
  /// Set whether the advertising type is connectable
  static IBleAdvertise getInstance() {
    BleAdvertiseTargetPlatform platform = _setCurrentPlatform();
    switch (platform) {
      case BleAdvertiseTargetPlatform.android:
        return AndroidAdvertise(channel: MethodChannel(platform.channel));

      case BleAdvertiseTargetPlatform.iOS:
        return IOSAdvertise(channel: MethodChannel(platform.channel));

      default:
        throw NoSupportCurrentDevice(
            'DOES NOT SUPPORT CURRENT PLATFORM, Only Support Android or iOS ');
    }
  }

  static BleAdvertiseTargetPlatform _setCurrentPlatform() {
    if (Platform.isAndroid) return BleAdvertiseTargetPlatform.android;
    if (Platform.isIOS) return BleAdvertiseTargetPlatform.iOS;
    throw NoSupportCurrentDevice(
        'DOES NOT SUPPORT CURRENT PLATFORM, Only Support Android or iOS ');
  }
}
