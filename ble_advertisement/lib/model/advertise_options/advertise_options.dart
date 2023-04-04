import 'package:ble_advertisement/model/advertise_options/module/manufacture_data.dart';
import 'package:ble_advertisement/model/advertise_options/module/service_data.dart';

class AdvertiseOptions {
  /// Android, iOS
  ///
  /// Set whether the advertising type is connectable
  ///
  /// Android set to AdvertisingSetParameters.Builder.setConnectable
  ///
  /// iOS set to CBAdvertisementDataIsConnectable
  ///
  /// default : true
  final bool connectable;

  /// Android only
  ///
  /// Set advertising interval
  ///
  /// Android set to AdvertisingSetParameters.Builder.setInverval
  final int? advertiseInterval;

  /// Android, iOS
  final AdvertiseServiceData? advertiseServiceData;

  /// Android, ios
  final AdvertiseManufactureOptions? advertiseManufactureOptions;

  // final flags;
  // final transmitPowerLevel;

  // final serviceData;
  // final trans

  AdvertiseOptions({
    this.advertiseInterval,
    this.connectable = true,
    this.advertiseManufactureOptions,
    this.advertiseServiceData,
  });
}
