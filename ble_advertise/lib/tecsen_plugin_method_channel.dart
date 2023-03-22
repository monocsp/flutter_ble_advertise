import 'dart:developer';
import 'dart:io';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:tecsen_plugin/tecsen_ble/iBleAdvertise.dart';

import 'tecsen_plugin_platform_interface.dart';

/// An implementation of [TecsenPluginPlatform] that uses method channels.
class TecsenAndroidPlugin extends TecsenPluginPlatform {
  TecsenAndroidPlugin(super.channel);

  ///AdvertiserToAndroid
  ///안드로이드에서 Tecsen device와 연결하기 위해서 사용하는 InvokeMethodChannel
  ///
  ///[bluetoothSetName]는 연결될 디바이스의 이름을 설정
  ///[uuid]는 연결할 때 요청하는 uuid

  @override
  Future<bool> startAdvertise(
      {required String bluetoothSetName,
      required String uuid,
      TecsenTXPower? txPower,
      int? timeOutMiliSec,
      TecsenAdvertiseMode? advertiseMode}) async {
    bool result = false;
    try {
      result = await channel.invokeMethod<bool?>('advertise', {
            "DATA": uuid,
            "NAME": bluetoothSetName,
            "TXPOWER": txPower?.toInteger ??
                TecsenTXPower.ADVERTISE_TX_POWER_MEDIUM.toInteger,
            "TIMEOUT": timeOutMiliSec ?? 1000,
            "ADVERTISEMODE": advertiseMode?.toInteger ??
                TecsenAdvertiseMode.BALANCED.toInteger
          }) ??
          false;
      log("HERE");
    } catch (e) {
      log("ANDROID START ADVERTISE ERROR : $e");
      result = false;
    }
    return result;
  }

  ///Advertise하는걸 종료하는 메소드채널
  ///
  ///안드로이드에서만 작동함
  Future<bool> distoryService() async {
    bool isSuccess = await channel.invokeMethod<bool?>(
          'distory',
        ) ??
        false;

    return isSuccess;
  }

  ///Advertise하는걸 종료하는 메소드채널
  ///
  ///안드로이드에서만 작동함
  Future<void> openSettingBluetoothPage() async {}

  @override
  Future<bool> openBleSettingPage() async {
    bool result = false;
    try {
      await channel.invokeMethod('openBluetoothSetting');
      result = true;
    } catch (e) {
      log("ANDROID OPEN BLE SETTING ERROR : $e");
      result = false;
    }
    return result;
  }
}

class TecsenIoSPlugin extends TecsenPluginPlatform {
  TecsenIoSPlugin(super.channel);

  @override
  Future<bool> startAdvertise(
      {required String bluetoothSetName,
      required String uuid,
      TecsenTXPower? txPower,
      int? timeOutMiliSec,
      TecsenAdvertiseMode? advertiseMode}) async {
    bool result = false;
    try {
      result = await channel
          .invokeMethod('advertise', {"UUID": uuid, "NAME": bluetoothSetName});
    } catch (e) {
      log("IOS START ADVERTISE ERROR : $e");
      result = false;
    }

    return result;
  }

  @override
  Future<bool> openBleSettingPage() async {
    bool result = false;
    try {
      await channel.invokeMethod('openBluetoothSetting');
      result = true;
    } catch (e) {
      log("IOS OPEN BLE SETTING ERROR : $e");
      result = false;
    }
    return result;
  }
}

///The state of Ble Tx Power
///
/// More information about Ble TxPower at
///
/// https://bluetoothle.wiki/tx_power
///
///The usual flow of state is as follows :
///
///
/// 1. [ultralow],
enum BleTXPower {
  ultralow(0),
  low(1),
  medium(2),
  high(3);

  final int toInteger;
  const BleTXPower(this.toInteger);
}

/// The state of Ble AdvertiseMode
///
/// More information about Ble AdvertiseMdoe at
///
///
/// The usual flow of state is as follows :
///
/// 1. [lowPower], This mode is intended for use cases where power consumption is a critical factor. The advertising interval is set to 1.28 seconds, which is the longest possible interval, and the advertising power is set to the lowest possible value. This mode is suitable for devices that need to advertise frequently, but do not require a fast connection.
///
/// 2. [balanced], This mode is intended for use cases where a balance between power consumption and advertising frequency is required. The advertising interval is set to 0.5 seconds, and the advertising power is set to a medium value. This mode is suitable for devices that need to advertise frequently and require a reasonably fast connection.
///
/// 3. [lowLatency], This mode is intended for use cases where a fast connection is required, and power consumption is less critical. The advertising interval is set to the shortest possible interval, which is 0.1 seconds, and the advertising power is set to the highest possible value. This mode is suitable for devices that require a fast connection and do not need to advertise frequently.

enum AdvertiseMode {
  lowPower(0),
  balanced(1),
  lowLatency(2);

  final int toInteger;

  const AdvertiseMode(this.toInteger);
}

///텍센 블루투스 오류가발생하면 잡을때 쓰는 enum
enum TecsenBluetoothErrorCode {
  BluetoothNoSupport("0"),
  BluetoothTurnOff('1'),
  BluetoothSetting('2'),
  TecsenAdvertiseError('3'),
  TecesnDistroyError('4');

  final String toInteger;
  const TecsenBluetoothErrorCode(this.toInteger);
  String get errorCode => toInteger;
}
