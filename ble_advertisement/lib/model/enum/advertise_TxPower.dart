/// Android iOS support
///
///The state of Ble Tx Power
///
/// More information about Ble TxPower at
///
/// https://bluetoothle.wiki/tx_power
///
/// The usual flow of state is as follows :
///
/// 1. [high], Advertise using high TX power level.
/// This corresponds to largest visibility range of the advertising packet.
///
/// 2. [medium], Advertise using medium TX power level.
///
/// 3. [low], Advertise using low TX power level.
///
/// 4. [ultralow], Advertise using the lowest transmission (TX) power level.
/// Low transmission power can be used to restrict the visibility range of advertising packets.
enum AdvertiseTxPower {
  /// Constant Value: 1 (0x00000001)
  high(toAndroid: 3, toIOS: 1),

  /// Constant Value: -7 (0xfffffff9)
  medium(toAndroid: 2, toIOS: -7),

  /// Constant Value: -15 (0xfffffff1)
  low(toAndroid: 1, toIOS: -15),

  /// Constant Value: -21 (0xffffffeb)
  ultralow(toAndroid: 0, toIOS: -21);

  final int toAndroid;
  final int toIOS;
  const AdvertiseTxPower({required this.toAndroid, required this.toIOS});
}

///
/// More information about Ble TxPower at
///
/// https://bluetoothle.wiki/tx_power
///
///The usual flow of state is as follows :
///
///
/// 1. [ultralow],
enum BleTXPower {
  ultralow(0),
  low(1),
  medium(2),
  high(3);

  final int toInteger;
  const BleTXPower(this.toInteger);
}
