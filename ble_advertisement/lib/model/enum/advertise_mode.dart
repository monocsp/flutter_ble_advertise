/// Android Only
///
/// The state of Ble AdvertiseMode
///
/// The usual flow of state is as follows :
///
/// 1. [lowPower], This mode is intended for use cases where power consumption is a critical factor.
/// The advertising interval is set to 1.28 seconds, which is the longest possible interval, and the advertising power is set to the lowest possible value.
/// This mode is suitable for devices that need to advertise frequently, but do not require a fast connection.
///
/// 2. [balanced], This mode is intended for use cases where a balance between power consumption and advertising frequency is required.
/// The advertising interval is set to 0.5 seconds, and the advertising power is set to a medium value.
/// This mode is suitable for devices that need to advertise frequently and require a reasonably fast connection.
///
/// 3. [lowLatency], This mode is intended for use cases where a fast connection is required, and power consumption is less critical.
/// The advertising interval is set to the shortest possible interval, which is 0.1 seconds, and the advertising power is set to the highest possible value.
/// This mode is suitable for devices that require a fast connection and do not need to advertise frequently.

enum AdvertiseMode {
  ///Perform Bluetooth LE advertising in low power mode.
  ///
  ///This is the default and preferred advertising mode as it consumes the least power.
  lowPower(0),

  ///Perform Bluetooth LE advertising in balanced power mode.
  ///
  ///This is balanced between advertising frequency and power consumption.
  balanced(1),

  ///Perform Bluetooth LE advertising in low latency, high power mode.
  ///
  ///This has the highest power consumption and should not be used for continuous background advertising.
  lowLatency(2);

  final int toInteger;

  const AdvertiseMode(this.toInteger);
}
