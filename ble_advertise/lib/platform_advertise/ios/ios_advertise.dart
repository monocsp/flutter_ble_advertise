import 'package:flutter/services.dart';
import 'package:tecsen_plugin/advertise_options.dart';
import 'package:tecsen_plugin/repository/advertise_lib_abstract.dart';
import 'package:tecsen_plugin/tecsen_plugin_method_channel.dart';

class IOSAdvertise implements IBleAdvertise {
  ///For MethodChannel Channel name;
  final MethodChannel channel;
  IOSAdvertise({required this.channel});

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
      AdvertiseOptions? advertiseOptions}) {
    // TODO: implement startAdvertise
    throw UnimplementedError();
  }
}
