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
import android.util.Log;

public class BleAdvertisementManager{

   
   private BluetoothAdapter mBluetoothAdapter = null;
   private AdvertiseCallback mAdvertiseCallback;
   private BluetoothLeAdvertiser mBluetoothLeAdvertiser;
   private final Activity activity;
   private static final String TAG = "FlutterBleAdvertisePlugin";

   private int ADVERTISE_TX_POWER;
   private int ADVERTISE_MODE;
   private String BLUETOOTH_DEVICE_NAME;
   // public static ParcelUuid Advt_UUID = null;

   // Check bluetooth permission in androidManifest

   public BleAdvertisementManager(Activity activity){
      this.activity = activity;
   }


   private boolean hasBluetoothConnectManifestPermission(){
   int currentDeviceApiLevel = Integer.valueOf(android.os.Build.VERSION.SDK);

   if(currentDeviceApiLevel>31){
      return ActivityCompat.checkSelfPermission(activity,Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED;
   }
   return ActivityCompat.checkSelfPermission(activity,Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED;

   
   }

   // Check bluetooth permission in androidManifest
   // this advertise permission for android12 (api 31)
   private boolean hasBluetoothAdvertiseManifestPermission(){
   int currentDeviceApiLevel = Integer.valueOf(android.os.Build.VERSION.SDK);

   if(currentDeviceApiLevel>31){
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



}