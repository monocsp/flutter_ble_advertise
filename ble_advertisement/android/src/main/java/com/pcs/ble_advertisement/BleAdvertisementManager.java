package com.pcs.ble_advertisement;

import android.app.Activity;
import androidx.core.content.ContextCompat;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.Manifest;
import android.content.pm.PackageManager;
import android.content.Context;
// import android.bluetooth.le.TransportDiscoveryData;
import android.util.Log;
import android.os.ParcelUuid;
import java.util.Objects;

public class BleAdvertisementManager{

   private BluetoothAdapter mBluetoothAdapter = null;
   private AdvertiseCallback mAdvertiseCallback;
   private BluetoothLeAdvertiser mBluetoothLEAdvertiser;
   private AdvertiseSettings.Builder mBluetoothLEAdvertiseSettingBuilder;
   private AdvertiseData.Builder mBluetoothLEAdvertiseDataBuilder;
   private final Activity activity;
   private static final String TAG = "FlutterBleAdvertisePlugin";

   private final int CURRENT_API_LEVEL;

   private int ADVERTISE_TX_POWER;
   private int ADVERTISE_MODE;
   private String BLUETOOTH_DEVICE_NAME;
   // public static ParcelUuid Advt_UUID = null;

   

   // Check bluetooth permission in androidManifest

   public BleAdvertisementManager(Activity activity){
      this. mBluetoothAdapter = ((BluetoothManager) activity.getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter();
      this.CURRENT_API_LEVEL = Integer.valueOf(android.os.Build.VERSION.SDK);
      this.activity = activity;
   }


   private boolean hasBluetoothConnectManifestPermission(){
      Log.e(TAG,"CURRENT_API_LEVEL" + this.CURRENT_API_LEVEL);
   if(this.CURRENT_API_LEVEL>30){
      Log.e(TAG,"[ContextCompat.checkSelfPermission(activity,Manifest.permission.BLUETOOTH_CONNECT)] : " + ContextCompat.checkSelfPermission(activity,Manifest.permission.BLUETOOTH_CONNECT));
      return ContextCompat.checkSelfPermission(this.activity.getApplicationContext(),Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED;
   }
   Log.e(TAG,"[ContextCompat.checkSelfPermission(activity,Manifest.permission.BLUETOOTH)]");
   return ContextCompat.checkSelfPermission(activity,Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED;

   
   }

   // Check bluetooth permission in androidManifest
   // this advertise permission for android12 (api 31)
   private boolean hasBluetoothAdvertiseManifestPermission(){
   if(CURRENT_API_LEVEL>30){
   return ContextCompat.checkSelfPermission(activity,Manifest.permission.BLUETOOTH_ADVERTISE) == PackageManager.PERMISSION_GRANTED;
   }
   return ContextCompat.checkSelfPermission(activity,Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED;
   
   }
   
   // Check all about bluetooth permission in manifest
   private boolean checkBluetoothManifestPermission(){
    boolean hasBluetoothConnectPermission = hasBluetoothConnectManifestPermission();
    
      if(!hasBluetoothConnectPermission){
         Log.e(TAG, "[REQUIRED ANDROID BLUETOOTH PERMISSION] : Please Check permission bluetooth connect in AndroidManifest.xml");
         return false;
      }
      boolean hasBluetoothAdvertisePermission = hasBluetoothAdvertiseManifestPermission();
      if(!hasBluetoothAdvertisePermission){
      Log.e(TAG, "[REQUIRED ANDROID BLUETOOTH PERMISSION] : Please Check permission bluetooth advertise in AndroidManifest.xml");
      return false;
      }
      return true;
   }

   // Check about support bluetooth current device
   private boolean isSupportBluetoothCurrentDevice(){
      if(BluetoothAdapter.getDefaultAdapter() == null){
      
          return false;
        }
        return true;
   }
   
   // Check about Turn on Bluetooth current device
   private boolean isTurnOnBluetoothCurrentDevice(){
        if(!BluetoothAdapter.getDefaultAdapter().isEnabled()){
          return false;
        }
        return true;
   
   }

   private boolean checkCurrentDeviceAboutBluetooth(){
      boolean isAbleBluetooth = isSupportBluetoothCurrentDevice();
      if(!isAbleBluetooth){
          Log.e(TAG, "[NO SUPPORT] : No support current android device " + android.os.Build.MODEL.toString());
      return false;}
      boolean isTurnOnBluetooth = isTurnOnBluetoothCurrentDevice();

            if(!isTurnOnBluetooth){
            Log.e(TAG,"[TURN OFF BLUETOOTH] : Please Turn on Bluetooth in bluetooth setting page");
            return false;
            }
            return true;
   }


   public boolean checkAbleBluetooth(){
   boolean isAbleBluetoothManifestPermission = checkBluetoothManifestPermission();
     if(!isAbleBluetoothManifestPermission) {return false;}
   boolean isAbleBluetooth = checkCurrentDeviceAboutBluetooth();
   if(!isAbleBluetooth){return false;}
      return true;   
   }

// mBluetoothAdapter set Name
public void setBluetoothAdapterName(String name){
   mBluetoothAdapter.setName(name);
}

// setting AdvertiseData
public void setAdvertiseData(BleAdvertiseData bleAdvertiseData) throws Exception{
   if(CURRENT_API_LEVEL < 21){
      throw new Exception("[Android AdvertiseData Set Error] : No Support AdvertiseData Under API Level 21 (Android 5.0 LOLLIPOP)");
   }
   this.mBluetoothLEAdvertiseDataBuilder = new AdvertiseData.Builder();

   
   mBluetoothLEAdvertiseDataBuilder.addServiceUuid(bleAdvertiseData.serviceUuid);   
   

   boolean isValidManufactureId = Objects.isNull(bleAdvertiseData.manufactureId);
   boolean isValidManufacturerSpecificData = Objects.isNull(bleAdvertiseData.manufacturerSpecificData != null);

   if(isValidManufactureId && isValidManufacturerSpecificData){
      mBluetoothLEAdvertiseDataBuilder.addManufacturerData(bleAdvertiseData.manufactureId, bleAdvertiseData.manufacturerSpecificData);
   }

   if(Objects.isNull(bleAdvertiseData.includeTxPowerLevel)){
      mBluetoothLEAdvertiseDataBuilder.setIncludeTxPowerLevel(bleAdvertiseData.includeTxPowerLevel);
   }

   if(Objects.isNull(bleAdvertiseData.setIncludeDeviceName)){
      mBluetoothLEAdvertiseDataBuilder.setIncludeDeviceName(bleAdvertiseData.setIncludeDeviceName);
   }  
   
   if(CURRENT_API_LEVEL >= 31){
      if(Objects.isNull(bleAdvertiseData.serviceSolicitationUuid)){
         mBluetoothLEAdvertiseDataBuilder.addServiceSolicitationUuid(bleAdvertiseData.serviceSolicitationUuid);
      }
   }

   // Coming Soon...
   // if(CURRENT_API_LEVEL >= 33){
   //    if(transportDiscoveryData != null){
   //       mBluetoothLEAdvertiseDataBuilder.addTransportDiscoveryData(transportDiscoveryData);
   //    }
   // }
   
}

// set Advertise Setting
public void setAdvertiseSettings(BleAdvertiseData bleAdvertiseData)throws Exception {
if(CURRENT_API_LEVEL < 21){
      throw new Exception("[Android AdvertiseSettings Set Error] : No Support AdvertiseSettings Under API Level 21 (Android 5.0 LOLLIPOP)");
   }
  this.mBluetoothLEAdvertiseSettingBuilder = new AdvertiseSettings.Builder();
  
   if(Objects.isNull(bleAdvertiseData.advertiseMode)){
      this.mBluetoothLEAdvertiseSettingBuilder.setAdvertiseMode(bleAdvertiseData.advertiseMode); 
   }

  if(Objects.isNull(bleAdvertiseData.advertiseTxPower)){
      this.mBluetoothLEAdvertiseSettingBuilder.setTxPowerLevel(bleAdvertiseData.advertiseTxPower);
  }

  if(Objects.isNull(bleAdvertiseData.connectable)){
      this.mBluetoothLEAdvertiseSettingBuilder.setConnectable(bleAdvertiseData.connectable);
  }

  if(Objects.isNull(bleAdvertiseData.timeout)){
      this.mBluetoothLEAdvertiseSettingBuilder.setTimeout(bleAdvertiseData.timeout);
  }
 
  ///Will be add in Android 14 Coming soon...  
  // mBluetoothLEAdvertiseSettingBuilder.setDiscoverable(discoverable);

}



public void startAdvertise(){
   this.mBluetoothLEAdvertiser = this.mBluetoothAdapter.getBluetoothLeAdvertiser();
   this.mBluetoothLEAdvertiser.startAdvertising(this.mBluetoothLEAdvertiseSettingBuilder.build(), this.mBluetoothLEAdvertiseDataBuilder.build(),mAdvertiseCallback);
}

/// Advertising Stop. 
private void stopAdvertising() {
  Log.i(TAG, "Service: Stopping Advertising");
  if(mBluetoothAdapter == null) return;
    
  this.mBluetoothLEAdvertiser.stopAdvertising(mAdvertiseCallback);
  this.mAdvertiseCallback = null;
  this.mBluetoothAdapter = null;      
}

// ///BLE Timeout시 작동하는 함수
// private void setTimeout() {
//   mHandler = new Handler();
//   timeoutRunnable = () -> {
//       Log.i(TAG, "AdvertiserService has reached timeout of " + TIMEOUT + " milliseconds, stopping advertising.");
//       stop();
//   };
//   mHandler.postDelayed(timeoutRunnable, TIMEOUT);
// }

// private void stop(){
//       stopAdvertising();
//         // finalizeBt();
        

//         if(mHandler == null){return;}
//         if(timeoutRunnable == null){return;
          
//         }
//         mHandler.removeCallbacks(timeoutRunnable);
        
// }


}