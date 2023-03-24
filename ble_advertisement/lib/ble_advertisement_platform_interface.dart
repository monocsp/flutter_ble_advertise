import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'ble_advertisement_method_channel.dart';

abstract class BleAdvertisementPlatform extends PlatformInterface {
  /// Constructs a BleAdvertisementPlatform.
  BleAdvertisementPlatform() : super(token: _token);

  static final Object _token = Object();

  static BleAdvertisementPlatform _instance = MethodChannelBleAdvertisement();

  /// The default instance of [BleAdvertisementPlatform] to use.
  ///
  /// Defaults to [MethodChannelBleAdvertisement].
  static BleAdvertisementPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [BleAdvertisementPlatform] when
  /// they register themselves.
  static set instance(BleAdvertisementPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
