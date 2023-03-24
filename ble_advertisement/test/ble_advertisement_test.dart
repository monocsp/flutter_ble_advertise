import 'package:flutter_test/flutter_test.dart';
import 'package:ble_advertisement/ble_advertisement.dart';
import 'package:ble_advertisement/ble_advertisement_platform_interface.dart';
import 'package:ble_advertisement/ble_advertisement_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockBleAdvertisementPlatform
    with MockPlatformInterfaceMixin
    implements BleAdvertisementPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final BleAdvertisementPlatform initialPlatform = BleAdvertisementPlatform.instance;

  test('$MethodChannelBleAdvertisement is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelBleAdvertisement>());
  });

  test('getPlatformVersion', () async {
    BleAdvertisement bleAdvertisementPlugin = BleAdvertisement();
    MockBleAdvertisementPlatform fakePlatform = MockBleAdvertisementPlatform();
    BleAdvertisementPlatform.instance = fakePlatform;

    expect(await bleAdvertisementPlugin.getPlatformVersion(), '42');
  });
}
