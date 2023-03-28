import 'dart:typed_data';

import 'package:ble_advertisement/model/advertise_options/android_advertise_options.dart';

class AdvertiseOptions {
  /// Android, iOS
  ///
  /// Set whether the advertising type is connectable
  ///
  /// Android set to AdvertisingSetParameters.Builder.setConnectable
  ///
  /// iOS set to CBAdvertisementDataIsConnectable
  final bool? connectable;

  /// Android only
  ///
  /// Set advertising interval
  ///
  /// Android set to AdvertisingSetParameters.Builder.setInverval
  final int? advertiseInterval;

  /// Android, iOS
  ///
  /// The manufacturer data of a peripheral.
  ///
  /// Android set to AdvertiseData.Builder.addManufacturerData
  final AdvertiseManufactureOptions? advertiseManufactureOptions;

  // final flags;
  // final transmitPowerLevel;

  // final serviceData;
  // final trans

  AdvertiseOptions(
      {this.advertiseInterval,
      this.connectable,
      this.advertiseManufactureOptions});
}
