import 'dart:io';

import 'package:ble_advertisement/exceptions/advertise_exceptions.dart';
import 'package:ble_advertisement/model/advertise_options/manufacture_data.dart';
import 'package:ble_advertisement/model/enum/advertise_TxPower.dart';
import 'package:ble_advertisement/model/enum/advertise_mode.dart';

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

  /// Android : Add a service solicitation UUID to advertise data.
  /// Added in API level 31
  ///
  /// ios : An array of solicited service UUIDs.
  ///
  /// Android : public AdvertiseData.Builder
  /// addServiceSolicitationUuid (ParcelUuid serviceSolicitationUuid)
  ///
  ///
  ///
  /// ios : CBAdvertisementDataSolicitedServiceUUIDsKey
  /// CBAdvertisementDataSolicitedServiceUUIDsKey : [CBUUID(string: ...)]
  ///
  /// Android data format [ParcelUuid], iOS data format [CCBUUID]
  final String? serviceSolicitationUuid;

  /// Android only
  ///
  ///int: Advertising time limit. May not exceed 180000 milliseconds.
  /// A value of 0 will disable the time limit.
  ///
  /// Limit advertising to a given amount of time.
  final int advertiseTimeOut;

  /// Android, iOS
  ///
  /// int: Advertising time limit. May not exceed 180000 milliseconds.
  /// A value of 0 will disable the time limit.
  ///
  /// Limit advertising to a given amount of time.

  final AdvertiseTxPower advertiseTxPower;

  /// Android, ios
  ///
  /// Set advertise TX power level to control the transmission power level for the advertising.
  final AdvertiseManufactureOptions? advertiseManufactureOptions;

  /// Android Only
  ///
  /// Set advertise mode to control the advertising power and latency.
  ///
  /// default : AdvertiseMode.balanced
  final AdvertiseMode advertiseMode;

  /// Android Only in android 14 Coming soon
  ///
  ///
  ///
  /// Set whether the advertisement type should be discoverable or non-discoverable.
  ///
  /// default : true
  // final bool discoverable;

  get toMap => {
        "hasAdvertiseOptions": true,
        "connectable": connectable,
        "advertiseInterval": advertiseInterval,
        "serviceSolicitationUuid": serviceSolicitationUuid,
        "advertiseTimeOut": advertiseTimeOut,
        "advertiseTxPower": Platform.isAndroid
            ? advertiseTxPower.toAndroid
            : Platform.isIOS
                ? advertiseTxPower.toIOS
                : throw NoSupportCurrentDevice("Advertise Options Error"),
      }
        ..addAll(advertiseManufactureOptions?.toMap ?? {})
        ..addAll(Platform.isAndroid
            ? {"advertiseMode": advertiseMode.toInteger}
            : {});

  AdvertiseOptions(
      {this.advertiseInterval,
      // this.discoverable = true,
      this.advertiseMode = AdvertiseMode.balanced,
      this.advertiseTxPower = AdvertiseTxPower.medium,
      this.advertiseTimeOut = 1000,
      this.connectable = true,
      this.advertiseManufactureOptions,
      this.serviceSolicitationUuid});
}
