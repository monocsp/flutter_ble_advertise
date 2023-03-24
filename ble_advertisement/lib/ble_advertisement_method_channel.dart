import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'ble_advertisement_platform_interface.dart';

/// An implementation of [BleAdvertisementPlatform] that uses method channels.
class MethodChannelBleAdvertisement extends BleAdvertisementPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('ble_advertisement');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
