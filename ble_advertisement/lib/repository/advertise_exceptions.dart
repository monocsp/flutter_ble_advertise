import 'dart:developer';

class CustomException implements Exception {
  final _message;
  final _prefix;

  get errorMessage => _message ?? "";
  get prefix => _prefix ?? "";

  CustomException([this._message, this._prefix]) {
    log('BLE_Advertise_Error [$_prefix] : $_message');
  }

  String toString() {
    return "prefix : $_prefix message : $_message";
  }
}

/// When current device does no support
///
/// Android, iOS only.
///
/// else Exception
class NoSupportCurrentDevice extends CustomException {
  NoSupportCurrentDevice([String? message])
      : super(message, 'NoSupportCurrentDevice');
}
