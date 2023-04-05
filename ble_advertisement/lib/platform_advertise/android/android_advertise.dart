import 'dart:developer';

import 'package:ble_advertisement/exceptions/advertise_exceptions.dart';
import 'package:ble_advertisement/model/advertise_options/advertise_options.dart';
import 'package:ble_advertisement/model/advertise_options/service_data.dart';

import 'package:ble_advertisement/model/enum/native_channel_name.dart';
import 'package:ble_advertisement/repository/advertise_lib_abstract.dart';
import 'package:flutter/services.dart';

class AndroidAdvertise implements IBleAdvertise {
  final MethodChannel channel;

  AndroidAdvertise({required this.channel});

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
      {String? serviceUuid,
      AdvertiseServiceData? advertiseServiceData,
      required String bluetoothSetName,
      bool setIncludeDeviceName = true,
      AdvertiseOptions? advertiseOptions}) async {
    assert(
        (serviceUuid?.isEmpty ?? true) && (advertiseServiceData == null),
        'MUST one setting [serviceUuid] or [advertiseServiceData]');

    if (serviceUuid?.isNotEmpty ?? false) {
      advertiseServiceData =
          AdvertiseServiceData(serviceUuid: serviceUuid!);
    }
    try {
      return await channel.invokeMethod(
          AdvertiseMethodChannel.startAdvertise.name,
          {
            "bluetoothSetName": bluetoothSetName,
            "setIncludeDeviceName": setIncludeDeviceName,
          }
            ..addAll(advertiseServiceData!.toMap)
            ..addAll(advertiseOptions?.toMap ?? {}));
    } catch (e) {
      log('[BLE ADVERTISE in startAdvertise ERROR] : $e');
      return false;
    }
  }
}
