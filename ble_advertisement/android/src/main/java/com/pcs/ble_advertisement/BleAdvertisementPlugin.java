package com.pcs.ble_advertisement;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;

///ParcelUuid을 사용하기위한 import
import android.os.ParcelUuid;
///Intent를 위한 import
import android.content.Intent;
///Android Activity를 사용하기위한 import
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.PluginRegistry;
///Activity를 사용하기 위한 import
import android.app.Activity;

import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import java.util.concurrent.TimeUnit;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.os.Handler;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;


/** BleAdvertisementPlugin */
///FlutterPlugin : flutter plugin 이용
///MethodCallHandler, : handler 이용


///ActivityAware : startActivity 이용
///onAttachedToActivity, onDetachedFromActivity 필요

///PluginRegistry.ActivityResultListener : activityResult 이용
///onReattachedToActivityForConfigChanges, onDetachedFromActivityForConfigChanges, onActivityResult 필요
public class BleAdvertisementPlugin implements FlutterPlugin, MethodCallHandler,ActivityAware,PluginRegistry.ActivityResultListener  {
 /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private static final String TAG = BleAdvertisementPlugin.class.getSimpleName();

  
  private MethodChannel channel;
  private static Activity activity;
  ///for android background service intent
  private Intent _bleService = null;
  
  

  // private long TIMEOUT;
  // private Runnable timeoutRunnable;
  
  // private Handler mHandler;
  private int ADVERTISE_TX_POWER;
  private int ADVERTISE_MODE;

  

  private BleAdvertisementManager bleAdvertisementManager;

  private static final String METHOD_CHANNEL_NAME = "com.pcs.flutter_ble_advertisement_android";
  private static final String ERROR_TAG = " BLE Advertise ERROR] :";

  //For StartActivity - ActivityAware
  @Override
  public void onAttachedToActivity(ActivityPluginBinding activityPluginBinding) {
    // TODO: your plugin is now attached to an Activity
      //현재 Activity가져옴
      activity = activityPluginBinding.getActivity();
      //현재 class에서 activityresult 사용가능
      activityPluginBinding.addActivityResultListener(this);
      bleAdvertisementManager = new BleAdvertisementManager(activity);
    
  }
    @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
    // stopService(_bleService);
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {
    // TODO: the Activity your plugin was attached to was
    // destroyed to change configuration.
    // This call will be followed by onReattachedToActivityForConfigChanges().
  }

  @Override
  public void onReattachedToActivityForConfigChanges(ActivityPluginBinding activityPluginBinding) {
    // TODO: your plugin is now attached to a new Activity
    // after a configuration change.
  }

  @Override
  public boolean onActivityResult(int requestCode, int resultCode, Intent data) {

    return false;
  }
  

  @Override
  public void onDetachedFromActivity() {
    //activity 초기화
    activity = null;
    bleAdvertisementManager = null;
    // TODO: your plugin is no longer associated with an Activity.
    // Clean up references.
  }

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), METHOD_CHANNEL_NAME);
    channel.setMethodCallHandler(this);

  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {

    // open Bluetooth setting page
    if(call.method.equals(AdvertiseMethodChannel.openBleSettingPage.getName())){
      openBleSettingPage(result);
      return;
    }

    if(call.method.equals(AdvertiseMethodChannel.isAbleAdvertise.getName())){
         isAbleAdvertiseCallByMethodChannel(result);
         return;
    }

    if (call.method.equals(AdvertiseMethodChannel.startAdvertise.getName())) {
    try{
        if(bleAdvertisementManager == null){
        //  result.error(TAG,ERROR_TAG," BleAdvertisementManager is Null");
        bleAdvertisementManager = new BleAdvertisementManager(activity);
          return;
         }
         if(!bleAdvertisementManager.checkAbleBluetooth()){
         result.error(TAG,ERROR_TAG," Current Cannot use BLE, Please Check Ble setting, using 'isAbleAdvertise' ");
          return;
         }
        boolean checkContainBluetoothName = hasBluetoothName(call);

        if(checkContainBluetoothName){
          result.error(TAG,ERROR_TAG," Bluetooth Name is Null Please try again after setting bluetooth name");
          return;
        }

        boolean checkContainServiceUuid = hasServiceUUid(call);

        if(checkContainServiceUuid){
          result.error(TAG,ERROR_TAG," ServiceUuid error Please try again after setting ServiceUuid");
          return;
        }

        BleAdvertiseData bleAdvertiseData = new BleAdvertiseData(call);
        bleAdvertisementManager.setBluetoothAdapterName(bleAdvertiseData.bluetoothName);
        bleAdvertisementManager.setAdvertiseData(bleAdvertiseData);
        bleAdvertisementManager.setAdvertiseSettings(bleAdvertiseData);
        bleAdvertisementManager.startAdvertise();
        
        // _bleService.putExtra("key","open"); //key,전달할값
        // _bleService.putExtra("bd", data);//bd,전달할값
        //Android background service start
        ///Activity.startService해야 service가 시작됨.
        // activity.startService(_bleService);
        // Boolean is =  isMyServiceRunning(AdvertiserService.class);

        result.success(true);
      }catch(Exception e){
        
        System.out.println("Ble advertise ERROR IOException!");
        result.error("3","Ble ERROR",e);
        
      }
      return;
    }

    ///Service 제거할때 사용하는 것.
    /// advertise하고 종료할때 제거가 되나, 오류가생겨서 안될 때 사용
    if(call.method.equals(AdvertiseMethodChannel.stopAdvertise.getName())) {
      try{
        // stop();
        result.success(true);
      }catch(Exception e){

        System.out.println("Ble distory ERROR IOException!");
        result.error("4","Ble ERROR",e);
      }
      return;
    }
    result.notImplemented();
    return;
  }

  // Check Format serviceUuid
  private boolean hasServiceUUid(@NonNull MethodCall call){
    String bluetoothServiceUuid = call.argument("serviceUuid");
    if(bluetoothServiceUuid != null){
      if(bluetoothServiceUuid != ""){
        try{
        ParcelUuid.fromString(bluetoothServiceUuid);
        return true;
        }catch(Exception e){
          Log.e(TAG, "[Service Uuid Form not match] : Please Check Service Uuid Format Example (1111-1111-1111-1111-1111-111111111111)" + e);
        }
      }
    }
    return false;
  }

  // Check Bluetooth Name
  private boolean hasBluetoothName(@NonNull MethodCall call){
    String bluetoothName = call.argument("bluetoothSetName");
      if(bluetoothName != null){
        return true;
    }
    return false;
  }


  // MethodCannel call this Method
  // isAbleAdvertise
  private boolean isAbleAdvertiseCallByMethodChannel(@NonNull Result result){
   try{
        boolean confirmPermission = bleAdvertisementManager.checkAbleBluetooth();
        if(confirmPermission){
        result.success(true);
        return true;
        }
        result.success(false);
      }catch(Exception e){
        result.error(TAG,ERROR_TAG,e);
      }
      return false;
  }

  // MethodCannel call this Method
  // openBluettothSettingPage in Android settings
  private void openBleSettingPage(@NonNull Result result){
  try{
        Intent intentOpenBluetoothSettings = new Intent();
        intentOpenBluetoothSettings.setAction(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS); 
        activity.startActivityForResult(intentOpenBluetoothSettings,0); 
        result.success(true);
      }catch(Exception e){
        result.error(TAG,ERROR_TAG,e);
      }
      return;
  }





// private AdvertiseSettings setAdvertiseSetting(){
//     AdvertiseData.Builder advertiseData = new AdvertiseData.Builder();

// }



/**
* Builds and sends a broadcast intent indicating Advertising has failed. Includes the error
* code as an extra. This is intended to be picked up by the {@code AdvertiserFragment}.
*/


}


