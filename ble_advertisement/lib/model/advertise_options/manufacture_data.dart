import 'package:flutter/services.dart';

/// Android : Add manufacturer specific data.
/// Please refer to the Bluetooth Assigned Numbers document provided by the Bluetooth SIG for a list of existing company identifiers.
///
/// ios : The manufacturer data of a peripheral. The value associated with this key is an NSData object.
///
///
/// Android : public AdvertiseData.Builder addManufacturerData (int manufacturerId, byte[] manufacturerSpecificData)
///
/// ios : CBAdvertisementDataManufacturerDataKey
/// CBAdvertisementDataManufacturerDataKey

class AdvertiseManufactureOptions {
  /// Android : [int] Manufacturer ID assigned by Bluetooth SIG.
  ///
  /// ios : No Support
  final int? manufacturerId;

  /// Android : [byte] Manufacturer specific data
  ///
  /// ios : [NSData]
  final Uint8List manufacturerSpecificData;

  get toMap => {
        "manufacturerId": manufacturerId,
        "manufacturerSpecificData": manufacturerSpecificData
      };

  AdvertiseManufactureOptions(
      {this.manufacturerId, required this.manufacturerSpecificData});
}
