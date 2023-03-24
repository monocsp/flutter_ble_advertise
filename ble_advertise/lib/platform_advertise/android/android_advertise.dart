import 'package:flutter/services.dart';
import 'package:tecsen_plugin/model/advertise_options/advertise_options.dart';
import 'package:tecsen_plugin/repository/advertise_lib_abstract.dart';
import 'package:tecsen_plugin/tecsen_plugin_method_channel.dart';

class AndroidAdvertise implements IBleAdvertise {
  ///For MethodChannel Channel name;
  final MethodChannel channel;
  AndroidAdvertise({required this.channel});

  @override
  // TODO: implement isActivatedAdvertise
  Future<bool> get isActivatedAdvertise => throw UnimplementedError();

  @override
  Future<bool> openBleSettingPage() {
    // TODO: implement openBleSettingPage
    throw UnimplementedError();
  }

  @override
  Future<bool> stopAdvertise() {
    // TODO: implement stopAdvertise
    throw UnimplementedError();
  }

  @override
  // TODO: implement isAbleAdvertise
  Future<bool> get isAbleAdvertise => throw UnimplementedError();

  @override
  Future<bool> startAdvertise(
      {required String uuid,
      required String bluetoothSetName,
      AdvertiseOptions? advertiseOptions}) async {
    bool result = false;
    try {
      result = await channel.invokeMethod<bool?>('start_advertise', {
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
}
