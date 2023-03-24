
import 'ble_advertisement_platform_interface.dart';

class BleAdvertisement {
  Future<String?> getPlatformVersion() {
    return BleAdvertisementPlatform.instance.getPlatformVersion();
  }
}
