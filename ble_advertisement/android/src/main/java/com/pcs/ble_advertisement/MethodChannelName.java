package com.pcs.ble_advertisement;

enum AdvertiseMethodChannel{
   openBleSettingPage(
       "com.pcs.ble_advertisement.open_ble_setting_page"),

  stopAdvertise( "com.pcs.ble_advertisement.stop_advertise"),

  isAbleAdvertise( "com.pcs.ble_advertisement.is_able_advertise"),

  isActivatedAdvertise(
       "com.pcs.ble_advertisement.is_activated_advertise"),

  startAdvertise( "com.pcs.ble_advertisement.start_advertise");

  private final String name;

  AdvertiseMethodChannel(String name){
  this.name = name;
  }
   public String getName(){return name;}

}