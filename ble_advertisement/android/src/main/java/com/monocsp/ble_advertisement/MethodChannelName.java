package com.monocsp.ble_advertisement;

enum AdvertiseMethodChannel{
   openBleSettingPage(
       "com.monocsp.ble_advertisement.open_ble_setting_page"),

  stopAdvertise( "com.monocsp.ble_advertisement.stop_advertise"),

  isAbleAdvertise( "com.monocsp.ble_advertisement.is_able_advertise"),

  isActivatedAdvertise(
       "com.monocsp.ble_advertisement.is_activated_advertise"),

  startAdvertise( "com.monocsp.ble_advertisement.start_advertise");

  private final String name;

  AdvertiseMethodChannel(String name){
  this.name = name;
  }
   public String getName(){return name;}

}