import 'package:tecsen_plugin/tecsen_ble/iBleAdvertise.dart';
import 'package:tecsen_plugin/tecsen_plugin_method_channel.dart';

class IOSAdvertise implements IBleAdvertise {
  @override
  // TODO: implement isActivatedAdvertise
  Future<bool> get isActivatedAdvertise => throw UnimplementedError();

  @override
  Future<bool> openBleSettingPage() {
    // TODO: implement openBleSettingPage
    throw UnimplementedError();
  }

  @override
  Future<bool> startAdvertise(
      {required String bluetoothSetName,
      required String uuid,
      BleTXPower? bleTxPower,
      int? timeOutMiliSec,
      AdvertiseMode? advertiseMode}) {
    // TODO: implement startAdvertise
    throw UnimplementedError();
  }

  @override
  Future<bool> stopAdvertise() {
    // TODO: implement stopAdvertise
    throw UnimplementedError();
  }
}
