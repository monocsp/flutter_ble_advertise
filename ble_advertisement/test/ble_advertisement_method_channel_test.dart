import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:ble_advertisement/ble_advertisement_method_channel.dart';

void main() {
  MethodChannelBleAdvertisement platform = MethodChannelBleAdvertisement();
  const MethodChannel channel = MethodChannel('ble_advertisement');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.getPlatformVersion(), '42');
  });
}
