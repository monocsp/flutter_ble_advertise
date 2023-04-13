package com.pcs.ble_advertisement;

import android.app.Activity;
import androidx.core.app.ActivityCompat;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.Manifest;
import android.content.pm.PackageManager;
// import android.bluetooth.le.TransportDiscoveryData;
import android.util.Log;
import android.os.ParcelUuid;

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
      this.CURRENT_API_LEVEL = Integer.valueOf(android.os.Build.VERSION.SDK);
      this.activity = activity;
   }


   private boolean hasBluetoothConnectManifestPermission(){

   if(CURRENT_API_LEVEL>31){
      return ActivityCompat.checkSelfPermission(activity,Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED;
   }
   return ActivityCompat.checkSelfPermission(activity,Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED;

   
   }

   // Check bluetooth permission in androidManifest
   // this advertise permission for android12 (api 31)
   private boolean hasBluetoothAdvertiseManifestPermission(){
   if(CURRENT_API_LEVEL>31){
   return ActivityCompat.checkSelfPermission(activity,Manifest.permission.BLUETOOTH_ADVERTISE) == PackageManager.PERMISSION_GRANTED;
   }
   return ActivityCompat.checkSelfPermission(activity,Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED;
   
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

   

///Advertising Bluetooth연결
// public boolean startAdvertising() {
// try{
//   bleAdvertisementManager.startAdvertising();

// }catch(Exception e){

// }

  

// }



// public void setAdvertiseSetting(boolean connectable,int timeout,int advertiseMode, int advertiseTxPower){
//    if(CURRENT_API_LEVEL < 21){
//       throw new Exception("[Android AdvertiseSettings Set Error] : No Support AdvertiseSettings Under API Level 21 (Android 5.0 LOLLIPOP)");
//    }

//    this.mBluetoothLEAdvertiseSettingBuilder = new AdvertiseSettings.Builder();

//    ///Will be add in Android 14 Coming soon...  
//    // mBluetoothLEAdvertiseSettingBuilder.setDiscoverable(discoverable);
//    mBluetoothLEAdvertiseSettingBuilder.setAdvertiseMode(advertiseMode);
//    mBluetoothLEAdvertiseSettingBuilder.setTxPowerLevel(advertiseTxPower);   
//    mBluetoothLEAdvertiseSettingBuilder.setTimeout(timeout);
//    mBluetoothLEAdvertiseSettingBuilder.setConnectable(connectable);
// }

public void setAdvertiseData(ParcelUuid serviceUuid, boolean includeTxPowerLevel,boolean  setIncludeDeviceName, int manufactureId, byte[] manufacturerSpecificData,ParcelUuid serviceSolicitationUuid){
   if(CURRENT_API_LEVEL < 21){
      throw new Exception("[Android AdvertiseData Set Error] : No Support AdvertiseData Under API Level 21 (Android 5.0 LOLLIPOP)");
   }
   this.mBluetoothLEAdvertiseDataBuilder = new AdvertiseData.Builder();

   if(serviceUuid != null){
      mBluetoothLEAdvertiseDataBuilder.addServiceUuid(serviceUuid);   
   }

   boolean isValidManufactureId = (manufactureId != null) && (manufactureId > 0);
   boolean isValidManufacturerSpecificData = (manufacturerSpecificData != null);

   if(isValidManufactureId && isValidManufacturerSpecificData){
      mBluetoothLEAdvertiseDataBuilder.addManufacturerData(manufactureId, manufacturerSpecificData);
   }

   if(includeTxPowerLevel != null){
      mBluetoothLEAdvertiseDataBuilder.setIncludeTxPowerLevel(includeTxPowerLevel);
   }

   if(setIncludeDeviceName != null){
      mBluetoothLEAdvertiseDataBuilder.setIncludeDeviceName(setIncludeDeviceName);
   }  
   
   if(CURRENT_API_LEVEL >= 31){
      if(serviceSolicitationUuid != null){
         mBluetoothLEAdvertiseDataBuilder.addServiceSolicitationUuid(serviceSolicitationUuid);
      }
   }

   // Coming Soon...
   // if(CURRENT_API_LEVEL >= 33){
   //    if(transportDiscoveryData != null){
   //       mBluetoothLEAdvertiseDataBuilder.addTransportDiscoveryData(transportDiscoveryData);
   //    }
   // }
   
}

}