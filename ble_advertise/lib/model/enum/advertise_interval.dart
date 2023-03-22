/// Android Only
///
/// Support API level 26
///
/// https://developer.android.com/reference/android/bluetooth/le/AdvertisingSetParameters#constants_1
enum AdvertiseInterval {
  /// Advertise on low frequency, around every 1000ms.
  /// This is the default and preferred advertising mode as it consumes the least power.
  ///
  /// Constant Value: 1600 (0x00000640)
  high,

  /// Perform high frequency, low latency advertising, around every 100ms.
  /// This has the highest power consumption and should not be used for continuous background advertising.
  ///
  /// Constant Value: 160 (0x000000a0)
  low,

  /// Maximum value for advertising interval.
  ///
  /// Constant Value: 16777215 (0x00ffffff)
  max,

  /// Advertise on medium frequency, around every 250ms.
  /// This is balanced between advertising frequency and power consumption.
  ///
  /// Constant Value: 400 (0x00000190)
  medium,

  /// Minimum value for advertising interval.
  ///
  /// Constant Value: 160 (0x000000a0)
  min;
}
