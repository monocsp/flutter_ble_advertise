import 'package:ble_advertisement/ble_advertise_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

abstract class BleAdvertisePlatform extends PlatformInterface {
  /// Constructs a BleAdvertisePlatform.
  BleAdvertisePlatform() : super(token: _token);

  static final Object _token = Object();

  static BleAdvertisePlatform _instance = MethodChannelBleAdvertise();

  /// The default instance of [BleAdvertisePlatform] to use.
  ///
  /// Defaults to [MethodChannelBleAdvertise].
  static BleAdvertisePlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [BleAdvertisePlatform] when
  /// they register themselves.
  static set instance(BleAdvertisePlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<bool?> checkBleAdvertiseSupported(
      {String? countryCode,
      required String partnerCode,
      required String serviceType});

  Future<bool?> addCardToBleAdvertise(
      {required String cardID,
      required String cData,
      required String clickURL});

  Future<void> initialized(
      {String? countryCode,
      required String partnerCode,
      required String impressionURL,
      required String serviceType});
}
