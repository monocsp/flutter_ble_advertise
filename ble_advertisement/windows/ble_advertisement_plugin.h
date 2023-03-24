#ifndef FLUTTER_PLUGIN_BLE_ADVERTISEMENT_PLUGIN_H_
#define FLUTTER_PLUGIN_BLE_ADVERTISEMENT_PLUGIN_H_

#include <flutter/method_channel.h>
#include <flutter/plugin_registrar_windows.h>

#include <memory>

namespace ble_advertisement {

class BleAdvertisementPlugin : public flutter::Plugin {
 public:
  static void RegisterWithRegistrar(flutter::PluginRegistrarWindows *registrar);

  BleAdvertisementPlugin();

  virtual ~BleAdvertisementPlugin();

  // Disallow copy and assign.
  BleAdvertisementPlugin(const BleAdvertisementPlugin&) = delete;
  BleAdvertisementPlugin& operator=(const BleAdvertisementPlugin&) = delete;

 private:
  // Called when a method is called on this plugin's channel from Dart.
  void HandleMethodCall(
      const flutter::MethodCall<flutter::EncodableValue> &method_call,
      std::unique_ptr<flutter::MethodResult<flutter::EncodableValue>> result);
};

}  // namespace ble_advertisement

#endif  // FLUTTER_PLUGIN_BLE_ADVERTISEMENT_PLUGIN_H_
