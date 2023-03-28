
package com.pcs.ble_advertisement;

import android.bluetooth.le.AdvertiseCallback;

public class BleAdvertiseCallback extends AdvertiseCallback {

   private static final String TAG = BleAdvertiseCallback.class.getSimpleName();

  @Override
  public void onStartFailure(int errorCode) {
      super.onStartFailure(errorCode);
      System.out.println("ADVERTISE FAILED ERROR CODE " + errorCode);
      
      Log.i(TAG, "[Flutter Ble Advertise] : Advertising failed "+ errorCode);
      
      sendFailureIntent(errorCode);
      

  }

  @Override
  public void onStartSuccess(AdvertiseSettings settingsInEffect) {
      super.onStartSuccess(settingsInEffect);
      Log.i(TAG, "[Flutter Ble Advertise] : successfully started");
  }
}
