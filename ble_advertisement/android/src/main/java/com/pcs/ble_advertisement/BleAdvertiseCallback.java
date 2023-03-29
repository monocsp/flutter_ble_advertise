
package com.pcs.ble_advertisement;

import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseSettings;
import android.content.Intent;
import android.util.Log;
import android.app.Activity;

public class BleAdvertiseCallback extends AdvertiseCallback {
    public static final String ADVERTISING_FAILED_EXTRA_CODE = "failureCode";
      public static final String ADVERTISING_FAILED = "com.pcs.flutter.ble.advertising_failed";
   private static final String TAG = BleAdvertiseCallback.class.getSimpleName();
    private final Activity activity;

    BleAdvertiseCallback(Activity activity){
    this.activity = activity;
    }


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
  private void sendFailureIntent(int errorCode){
  
  Intent failureIntent = new Intent();
  failureIntent.setAction(ADVERTISING_FAILED);
  failureIntent.putExtra(ADVERTISING_FAILED_EXTRA_CODE, errorCode);
  activity.sendBroadcast(failureIntent);
}

}
