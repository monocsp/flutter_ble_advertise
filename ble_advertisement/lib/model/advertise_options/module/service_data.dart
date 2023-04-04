import 'package:flutter/services.dart';

/// Android : Add a service UUID to advertise data
///
/// ios : A dictionary that contains service-specific advertisement data.
///
///
/// Android : AdvertiseData.Builder
/// addServiceData(ParcelUuid serviceDataUuid, byte[] serviceData)
///
/// ios : CBAdvertisementDataServiceDataKey
/// CBAdvertisementDataServiceDataKey : [serviceDataUuid: serviceData]

class AdvertiseServiceData {
  /// Android : [ParcelUuid] 16-bit UUID of the service the data is associated with
  ///
  /// ios : [CBUUID]
  final String serviceDataUuid;

  /// Android : [byte] Service data
  ///
  /// ios : [NSData]
  final Uint8List? serviceData;

  AdvertiseServiceData({required this.serviceDataUuid, this.serviceData});
}
