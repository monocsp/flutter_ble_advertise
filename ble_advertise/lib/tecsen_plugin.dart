import 'tecsen_plugin_platform_interface.dart';

class TecsenPlugin {
  Future<String?> getPlatformVersion() {
    return TecsenPluginPlatform.instance.getPlatformVersion();
  }
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
