enum BleAdvertiseTargetPlatform {
  Android(channel: 'com.pcs.flutter_ble_advertisement'),
  iOS(channel: 'com.pcs.flutter_ble_advertisement');

  final String channel;
  const BleAdvertiseTargetPlatform({required this.channel});
}
