#include "include/ble_advertisement/ble_advertisement_plugin_c_api.h"

#include <flutter/plugin_registrar_windows.h>

#include "ble_advertisement_plugin.h"

void BleAdvertisementPluginCApiRegisterWithRegistrar(
    FlutterDesktopPluginRegistrarRef registrar) {
  ble_advertisement::BleAdvertisementPlugin::RegisterWithRegistrar(
      flutter::PluginRegistrarManager::GetInstance()
          ->GetRegistrar<flutter::PluginRegistrarWindows>(registrar));
}
