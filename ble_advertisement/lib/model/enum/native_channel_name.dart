enum BleAdvertiseTargetPlatform {
  android(channel: 'com.pcs.flutter_ble_advertisement_android'),
  iOS(channel: 'com.pcs.flutter_ble_advertisement_ios');

  final String channel;
  const BleAdvertiseTargetPlatform({required this.channel});
}

/// Using MethodChannel.invokeMethod name

enum AdvertiseMethodChannel {
  openBleSettingPage(
      name: 'com.pcs.ble_advertisement.open_ble_setting_page'),

  stopAdvertise(name: 'com.pcs.ble_advertisement.stop_advertise'),

  isAbleAdvertise(name: 'com.pcs.ble_advertisement.is_able_advertise'),

  isActivatedAdvertise(
      name: 'com.pcs.ble_advertisement.is_activated_advertise'),

  startAdvertise(name: 'com.pcs.ble_advertisement.start_advertise');

  final String name;

  const AdvertiseMethodChannel({required this.name});
}
