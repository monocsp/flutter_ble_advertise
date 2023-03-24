/// Android Only
///
/// Support API level 26
///
/// https://developer.android.com/reference/android/bluetooth/le/AdvertisingSetParameters#constants_1
///
/// The usual flow of state is as follows :
///
/// 1. [high], This is the default and preferred advertising mode as it consumes the least power.
/// Advertise on low frequency, around every 1000ms.
///
/// 2. [low], This has the highest power consumption and should not be used for continuous background advertising.
/// Perform high frequency, low latency advertising, around every 100ms.
///
/// 3. [max], This is balanced between advertising frequency and power consumption.
/// Maximum value for advertising interval.
///
/// 4. [medium], Advertise on medium frequency, around every 250ms.
///
/// 5. [min], Minimum value for advertising interval.

enum AdvertiseInterval {
  /// Constant Value: 1600 (0x00000640)
  high,

  /// Constant Value: 400 (0x00000190)
  medium,

  /// Constant Value: 160 (0x000000a0)
  low,

  /// Constant Value: 16777215 (0x00ffffff)
  max,

  /// Constant Value: 160 (0x000000a0)
  min;
}
