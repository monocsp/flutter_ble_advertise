//
//  Generated file. Do not edit.
//

// clang-format off

#include "generated_plugin_registrant.h"

#include <ble_advertisement/ble_advertisement_plugin.h>

void fl_register_plugins(FlPluginRegistry* registry) {
  g_autoptr(FlPluginRegistrar) ble_advertisement_registrar =
      fl_plugin_registry_get_registrar_for_plugin(registry, "BleAdvertisementPlugin");
  ble_advertisement_plugin_register_with_registrar(ble_advertisement_registrar);
}
