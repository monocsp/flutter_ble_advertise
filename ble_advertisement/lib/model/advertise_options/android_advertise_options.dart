import 'package:flutter/services.dart';

class AdvertiseManufactureOptions {
  ///int: Manufacturer ID assigned by Bluetooth SIG.
  final int? manufacturerId;

  ///byte: Manufacturer specific data
  final Uint8List? manufacturerSpecificData;

  AdvertiseManufactureOptions(
      {this.manufacturerId, this.manufacturerSpecificData});
}
