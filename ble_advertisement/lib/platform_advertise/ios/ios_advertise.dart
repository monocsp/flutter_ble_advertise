import 'dart:developer';

import 'package:ble_advertisement/model/enum/native_channel_name.dart';
import 'package:flutter/services.dart';
import 'package:ble_advertisement/model/advertise_options/advertise_options.dart';
import 'package:ble_advertisement/repository/advertise_lib_abstract.dart';

class IOSAdvertise implements IBleAdvertise {
  final MethodChannel channel;
  IOSAdvertise({required this.channel});

  @override
  Future<bool> get isActivatedAdvertise async {
    try {
      return await channel
          .invokeMethod(AdvertiseMethodChannel.isActivatedAdvertise.name);
    } catch (e) {
      log('[BLE ADVERTISE in openBleSettingPage ERROR]  : $e');
      return false;
    }
  }

  @override
  Future<bool> get openBleSettingPage async {
    try {
      return await channel
          .invokeMethod(AdvertiseMethodChannel.openBleSettingPage.name);
    } catch (e) {
      log('[BLE ADVERTISE in openBleSettingPage ERROR]  : $e');
      return false;
    }
  }

  @override
  Future<bool> stopAdvertise() async {
    try {
      return await channel
          .invokeMethod(AdvertiseMethodChannel.stopAdvertise.name);
    } catch (e) {
      log('[BLE ADVERTISE in stopAdvertise ERROR] : $e');
      return false;
    }
  }

  @override
  Future<bool> get isAbleAdvertise async {
    try {
      return await channel
          .invokeMethod(AdvertiseMethodChannel.isAbleAdvertise.name);
    } catch (e) {
      log('[BLE ADVERTISE in isAbleAdvertise ERROR]  : $e');
      return false;
    }
  }

  @override
  Future<bool> startAdvertise(
      {required String uuid,
      required String bluetoothSetName,
      bool setIncludeDeviceName = true,
      AdvertiseOptions? advertiseOptions}) async {
    try {
      return await channel
          .invokeMethod(AdvertiseMethodChannel.startAdvertise.name);
    } catch (e) {
      log('[BLE ADVERTISE in startAdvertise ERROR] : $e');
      return false;
    }
  }
}
