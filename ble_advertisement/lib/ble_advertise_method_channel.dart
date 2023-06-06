import 'dart:developer';

import 'package:ble_advertisement/ble_advertise_platform_interface.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

/// An implementation of [BleAdvertisePlatform] that uses method channels.
class MethodChannelBleAdvertise extends BleAdvertisePlatform {
  static const String _tag = "[Ble Advertise]";

  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel =
      const MethodChannel('com.monocsp.flutter_ble_advertisement_android');

  @override
  Future<bool?> checkBleAdvertiseSupported(
      {String? countryCode,
      required String partnerCode,
      required String serviceType}) async {
    final result = await methodChannel.invokeMethod('checkWallet', {
      'countryCode': countryCode,
      'partnerCode': partnerCode,
      'serviceType': serviceType
    });
    log("$_tag : Samsung Wallet supported? ${result ? "YES!" : "NO!"}");
    return result;
  }

  @override
  Future<bool?> addCardToBleAdvertise(
      {required String cardID,
      required String cData,
      required String clickURL}) async {
    final result = await methodChannel.invokeMethod('addCardToBleAdvertise',
            {"cardId": cardID, "cData": cData, "clickURL": clickURL}) ??
        false;
    log("$_tag : Open Samsung Wallet ${result ? "Success" : "Fail"}");
    return result;
  }

  @override
  Future<void> initialized(
      {String? countryCode,
      required String partnerCode,
      required String serviceType,
      required String impressionURL}) async {
    Map result = (await methodChannel.invokeMapMethod('initialized', {
          'countryCode': countryCode,
          'partnerCode': partnerCode,
          'serviceType': serviceType,
          'impressionURL': impressionURL
        })) ??
        {"walletSupported": false, "connectedImpressionUrl": false};

    log("$_tag : Samsung Wallet supported? ${(result["walletSupported"] ?? false) ? "YES!" : "NO!"}");
    log("$_tag : ${(result["connectedImpressionUrl"] ?? false) ? "Success to connection" : "Fail to connection"} ImpressUrl");
    return;
  }
}
