import 'dart:io';

import 'package:flutter/services.dart';
import 'package:tecsen_plugin/tecsen_plugin_method_channel.dart';

abstract class TecsenPluginPlatform {
  late MethodChannel channel;
  TecsenPluginPlatform(this.channel);

  ///Tecsen기기에 데이터를 전송하기위한 advertise 메소드
  ///
  ///[bluetoothSetName] : tecsen 블루투스 기기에 저장되어있는 이름
  ///
  ///[uuid] : tecsen 블루투스 기기에 전송하는 uuid데이터
  ///
  ///For Android Optional Parameter
  ///
  ///[txPower] : TXPower 설정
  ///default : TecsenTXPower.ADVERTISE_TX_POWER_MEDIUM.toInteger
  ///[timeOutMiliSec] : Advertise 시간설정 단위는 milisecond
  ///default : 1000
  ///[advertiseMode] : advertise 레이턴시 모드
  ///default : TecsenAdvertiseMode.ADVERTISE_MODE_BALANCED.toInteger

  Future<bool> startAdvertise(
      {required String bluetoothSetName,
      required String uuid,
      TecsenTXPower? txPower,
      int? timeOutMiliSec,
      TecsenAdvertiseMode? advertiseMode});

  ///블루투스가 꺼져있다면 블루투스 설정화면을 여는 메소드
  ///
  ///안드로이드 : 사용가능
  ///
  ///IoS : 사용가능
  Future<bool> openBleSettingPage();
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
