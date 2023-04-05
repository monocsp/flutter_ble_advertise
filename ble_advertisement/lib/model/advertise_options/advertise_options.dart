import 'package:ble_advertisement/model/advertise_options/manufacture_data.dart';

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

  final int advertiseTimeOut;

  /// Android, ios
  final AdvertiseManufactureOptions? advertiseManufactureOptions;

  get toMap => {
        "connectable": connectable,
        "advertiseInterval": advertiseInterval,
        "serviceSolicitationUuid": serviceSolicitationUuid,
        "advertiseTimeOut": advertiseTimeOut
      }..addAll(advertiseManufactureOptions?.toMap ?? {});

  AdvertiseOptions(
      {this.advertiseInterval,
      this.advertiseTimeOut = 1000,
      this.connectable = true,
      this.advertiseManufactureOptions,
      this.serviceSolicitationUuid});
}
