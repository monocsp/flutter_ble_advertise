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
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;

/** TecsenPlugin */
///FlutterPlugin : flutter plugin 이용
///MethodCallHandler, : handler 이용


///ActivityAware : startActivity 이용
///onAttachedToActivity, onDetachedFromActivity 필요

///PluginRegistry.ActivityResultListener : activityResult 이용
///onReattachedToActivityForConfigChanges, onDetachedFromActivityForConfigChanges, onActivityResult 필요

public class TecsenPlugin implements FlutterPlugin, MethodCallHandler,ActivityAware,PluginRegistry.ActivityResultListener {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private static final String TAG = TecsenPlugin.class.getSimpleName();

  public static final String ADVERTISING_FAILED = "com.tecsen.brucegetqrbd.advertising_failed";
  public static final String ADVERTISING_FAILED_EXTRA_CODE = "failureCode";
  private MethodChannel channel;
  private static Activity activity;
  ///for android background service intent
  private Intent _bleService = null;
  private BluetoothAdapter mBluetoothAdapter = null;
  private AdvertiseCallback mAdvertiseCallback;

  private long TIMEOUT;
  private Runnable timeoutRunnable;
  
  private BluetoothLeAdvertiser mBluetoothLeAdvertiser;
  private Handler mHandler;
  private int ADVERTISE_TX_POWER;
  private int ADVERTISE_MODE;
  private String BLUETOOTH_DEVICE_NAME;
  public static ParcelUuid Advt_UUID = null;

  rivate static final String METHOD_CHANNEL_NAME = 'com.pcs.flutter_ble_advertisement';

  //For StartActivity - ActivityAware
  @Override
  public void onAttachedToActivity(ActivityPluginBinding activityPluginBinding) {
    // TODO: your plugin is now attached to an Activity
      //현재 Activity가져옴
      activity = activityPluginBinding.getActivity();
      //현재 class에서 activityresult 사용가능
      activityPluginBinding.addActivityResultListener(this);
    
  }

  @Override
  public void onDetachedFromActivity() {
    //activity 초기화
    activity = null;
    // TODO: your plugin is no longer associated with an Activity.
    // Clean up references.
  }

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), METHOD_CHANNEL_NAME);
    channel.setMethodCallHandler(this);

  }
  /**
   * 
   * ffffffff-ffff-ffff-0101-4e4350555348 : 출근
   * ffffffff-ffff-ffff-0404-4e4350555348 : 퇴근
   * ffffffff-ffff-ffff-0202-4e4345584954 : 외출
   * ffffffff-ffff-ffff-0808-4e4345584954 : 복귀
   * ffffffff-ffff-ffff-0A0A-4e4345584954 : 순찰
   * ffffffff-ffff-ffff-0C0C-4e4345584954 : 문열기
   */

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {

    ///블루투스 세팅페이지 오픈
    if(call.method.equals("openBluetoothSetting")){
      try{
        Intent intentOpenBluetoothSettings = new Intent();
        intentOpenBluetoothSettings.setAction(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS); 
        activity.startActivityForResult(intentOpenBluetoothSettings,0); 
        // startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
        result.success(true);
      }catch(Exception e){
        System.out.println("TECSEN advertise ERROR IOException!");
        result.error("10","TECSEN Bluetooth Setting Page Open ERROR",e);
      }
      return;
    }

    ///Method 호출 시 작동하는 plugin 부분
    ///들어오는 serial data에 따라서 블루투스가 작동
    if (call.method.equals("advertise")) {
      System.out.println("start method");
      String data;
      int timeOut;
      int txPower;
      int advertiseMode;
      try{
        BLUETOOTH_DEVICE_NAME = call.argument("NAME");
        data = call.argument("DATA");
        if(BLUETOOTH_DEVICE_NAME == null){result.error("TECSEN ERROR","BLUETOOTH DEVICE NAME NULL POINTER EXCEPTION ","");
      return;}
        if(data == null){result.error("TECSEN ERROR","BLUETOOTH DATA NULL POINTER EXCEPTION ","");return;}

        
        Advt_UUID = ParcelUuid.fromString(data);

        ADVERTISE_TX_POWER = call.argument("TXPOWER");
        ADVERTISE_MODE = call.argument("ADVERTISEMODE");
        
        timeOut = call.argument("TIMEOUT");
        TIMEOUT = TimeUnit.MILLISECONDS.convert(timeOut, TimeUnit.MILLISECONDS);

        // ParcelUuid Advt_Service_UUID = ParcelUuid.fromString(data);

        ///현재 기기가 bluetooth를 지원하는지 체크함
        if(BluetoothAdapter.getDefaultAdapter() == null){
          result.error("0","BLUETOOTH ERROR ","THIS DEVICE DOES NOT SUPPORT BLUETOOTH");
          return;
        }
        ///현재 기기가 bluetooth 권한이 설정되어있는지 체크
        if(!BluetoothAdapter.getDefaultAdapter().isEnabled()){
          result.error("1","BLUETOOTH TURN OFF ","PERMISSION DENIED");
          return;
        }
        
        if(!startBLEConnection()){
          System.out.println("CANNOT START BLUETOOTH");
          result.error("2","BLUETOOTH SETTING ERROR ","CANNOT BLUETOOTH SETTING");
          return;
        }
        
        startAdvertising();
        
        // _bleService.putExtra("key","open"); //key,전달할값
        // _bleService.putExtra("bd", data);//bd,전달할값
        //Android background service start
        ///Activity.startService해야 service가 시작됨.
        // activity.startService(_bleService);
        // Boolean is =  isMyServiceRunning(AdvertiserService.class);

        result.success(true);
        

      }catch(Exception e){
        stop();
        System.out.println("TECSEN advertise ERROR IOException!");
        result.error("3","TECSEN ERROR",e);
        
      }
      return;
    }
    ///Service 제거할때 사용하는 것.
    ///advertise하고 종료할때 제거가 되나, 오류가생겨서 안될 때 사용
    if(call.method.equals("distory")) {
      try{
        stop();
        result.success(true);
      }catch(Exception e){

        System.out.println("TECSEN distory ERROR IOException!");
        result.error("4","TECSEN ERROR",e);
      }
      return;
    }

    result.notImplemented();
    return;
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
  
  ///
  public boolean startBLEConnection(){
    ///이미 블루투스가 작동되고 있음.
    // if(mBluetoothAdapter != null){
    //   System.out.println("mBluetoothAdapter is Not NUll Already Started Advertise");  
    //   mBluetoothAdapter = null;
    //   return false;}
    
    mBluetoothAdapter = ((BluetoothManager) activity.getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter();
    // mBluetoothAdapter = (activity.getSystemService(Context.BLUETOOTH_SERVICE));
    // Is Bluetooth supported on this device?
    if(mBluetoothAdapter == null){
    System.out.println("mBluetoothAdapter is NULL");
    return false;
    }
     // Is Bluetooth turned on?
    if(!mBluetoothAdapter.isEnabled()){
      System.out.println("mBluetoothAdapter is UnAvaliable");
      return false;
      // return;
    }
    // Are Bluetooth Advertisements supported on this device?
    if(!mBluetoothAdapter.isMultipleAdvertisementSupported()){
      System.out.println("mBluetoothAdapter Not Support Current Device");
      return false;
    
    }    
      ///BT설정
      initializeBt();
      setTimeout();
      return true;
      // IntentFilter failureFilter = IntentFilter(AdvertiserService.ADVERTISING_FAILED);
      // registerReceiver(advertisingFailureReceiver, failureFilter); 
  }
  

private void initializeBt() {

  // if (mBluetoothLeAdvertiser == null) {
    
      BluetoothManager mBluetoothManager = (BluetoothManager) activity.getSystemService(Context.BLUETOOTH_SERVICE);
      // if (mBluetoothManager != null) {
  
          BluetoothAdapter mBluetoothAdapter = mBluetoothManager.getAdapter();
          // if (mBluetoothAdapter != null) {
            
              ///Caps8 Bluetooth Key
              mBluetoothAdapter.setName(BLUETOOTH_DEVICE_NAME);
              mBluetoothLeAdvertiser = mBluetoothAdapter.getBluetoothLeAdvertiser();
  //         } else {
  //             System.out.println("initializeBt BLUETOOTH ADAPTER NULL ERROR ");
  //             // Toast.makeText(this, getString(R.string.bt_null), Toast.LENGTH_LONG).show();
  //         }
  //     } else {
  //         System.out.println("initializeBt BLUETOOTH MANAGER NULL ERROR ");
  //         // Toast.makeText(this, getString(R.string.bt_null), Toast.LENGTH_LONG).show();
  //     }
  // }

}
///BLE Timeout시 작동하는 함수
private void setTimeout() {
  mHandler = new Handler();
  timeoutRunnable = () -> {
      Log.i(TAG, "AdvertiserService has reached timeout of " + TIMEOUT + " milliseconds, stopping advertising.");
      stop();
  };
  mHandler.postDelayed(timeoutRunnable, TIMEOUT);
}

private void stop(){
      stopAdvertising();
        // finalizeBt();
        Advt_UUID = null;
        BLUETOOTH_DEVICE_NAME = null;
        if(mHandler == null){return;}
        if(timeoutRunnable == null){return;
          
        }
        mHandler.removeCallbacks(timeoutRunnable);
        
}

///Advertising멈춤. 
private void stopAdvertising() {
  Log.i(TAG, "Service: Stopping Advertising");
  if(mBluetoothAdapter == null) return;
  if(mAdvertiseCallback == null)return;
  
  mBluetoothLeAdvertiser.stopAdvertising(mAdvertiseCallback);
  mAdvertiseCallback = null;
  
  mBluetoothAdapter = null;      
}
// 

///Advertising Bluetooth연결
private void startAdvertising() {
  //    goForeground();
  
  
  if (mAdvertiseCallback == null) {
    System.out.println("ADVERTISE SETTING");
      AdvertiseSettings settings = buildAdvertiseSettings();  
      AdvertiseData data = buildAdvertiseData();
      mAdvertiseCallback = new SampleAdvertiseCallback();

      if (mBluetoothLeAdvertiser != null) {
        
          if (ActivityCompat.checkSelfPermission(activity,"android.permission.BLUETOOTH_CONNECT") != PackageManager.PERMISSION_GRANTED) {
            
          //     // TODO: Consider calling
          //     //    ActivityCompat#requestPermissions
          //     // here to request the missing permissions, and then overriding
          //     //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
          //     //                                          int[] grantResults)
          //     // to handle the case where the user grants the permission. See the documentation
          //     // for ActivityCompat#requestPermissions for more de
          }

          
// BluetoothAdapter mba=  ((BluetoothManager)activity.getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter();
// mba.setName(BLUETOOTH_DEVICE_NAME);
// BluetoothLeAdvertiser blead = mba.getBluetoothLeAdvertiser();
// blead.startAdvertising(settings,data,mAdvertiseCallback);
          mBluetoothLeAdvertiser.startAdvertising(settings, data,
                  mAdvertiseCallback);
                  System.out.println("START ADVERTISING!");
      }
  }
}
///Advertise Setting 부분 설정
private AdvertiseSettings buildAdvertiseSettings() {
  AdvertiseSettings.Builder settingsBuilder = new AdvertiseSettings.Builder();
      //  settingsBuilder.setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED);
      settingsBuilder.setAdvertiseMode(ADVERTISE_MODE);
      settingsBuilder.setTxPowerLevel(ADVERTISE_TX_POWER);
  // settingsBuilder.setAdvertiseMode(AdvertiseSettings.LIMITED_ADVERTISING_MAX_MILLIS);
  // settingsBuilder.setAdvertiseMode(3);
  settingsBuilder.setConnectable(true);
  
  
  
  ///Timeout설정
  // settingsBuilder.setTimeout(1000);
  return settingsBuilder.build();
}

///Advertise Data 부분 uuid를 태워서 전송
private AdvertiseData buildAdvertiseData() {

  /**
   * Note: There is a strict limit of 31 Bytes on packets sent over BLE Advertisements.
   *  This includes everything put into AdvertiseData including UUIDs, device info, &
   *  arbitrary service or manufacturer data.
   *  Attempting to send packets over this limit will result in a failure with error code
   *  AdvertiseCallback.ADVERTISE_FAILED_DATA_TOO_LARGE. Catch this error in the
   *  onStartFailure() method of an AdvertiseCallback implementation.
   */
  AdvertiseData.Builder dataBuilder = new AdvertiseData.Builder();
  dataBuilder.addServiceUuid(Advt_UUID);
  Log.i(TAG, "Advt_UUID : "+Advt_UUID.getUuid().toString());
  dataBuilder.setIncludeDeviceName(true);

  /* For example - this will cause advertising to fail (exceeds size limit) */
  //    String failureData = "asdghkajsghalkxcjhfa;sghtalksjcfhalskfjhasldkjfhdskf";
  //    dataBuilder.addServiceData(Constants.Service_UUID, ADVT_PUSH_SYNC);

  return dataBuilder.build();
}




private class SampleAdvertiseCallback extends AdvertiseCallback {

  @Override
  public void onStartFailure(int errorCode) {
      super.onStartFailure(errorCode);
      System.out.println("ADVERTISE FAILED ERROR CODE " + errorCode);
      
      Log.i(TAG, "Advertising failed");
      
      sendFailureIntent(errorCode);
      

  }

  @Override
  public void onStartSuccess(AdvertiseSettings settingsInEffect) {
      super.onStartSuccess(settingsInEffect);
      Log.i(TAG, "Advertising successfully started");
  }
}

/**
* Builds and sends a broadcast intent indicating Advertising has failed. Includes the error
* code as an extra. This is intended to be picked up by the {@code AdvertiserFragment}.
*/
private void sendFailureIntent(int errorCode){
  
  Intent failureIntent = new Intent();
  failureIntent.setAction(ADVERTISING_FAILED);
  failureIntent.putExtra(ADVERTISING_FAILED_EXTRA_CODE, errorCode);
  activity.sendBroadcast(failureIntent);
}


// private void finalizeBt() {
  //   if (mBluetoothLeAdvertiser == null) {
  //       BluetoothManager mBluetoothManager = (BluetoothManager)activity.getSystemService(Context.BLUETOOTH_SERVICE);
  //       if (mBluetoothManager != null) {
  //           BluetoothAdapter mBluetoothAdapter = mBluetoothManager.getAdapter();
  //           if (mBluetoothAdapter != null) {
  //               mBluetoothAdapter.setName("BruceJy");
  //               //                   mBluetoothAdapter.setName("Caps8");
  //               //                  mBluetoothLeAdvertiser = mBluetoothAdapter.getBluetoothLeAdvertiser();
  //           } else {
  //               // Toas.makeText(this, getString(R.string.bt_null), Toast.LENGTH_LONG).show();
  //               System.out.println("finalizeBt BLUETOOTH ADAPTER NULL ERROR ");
  //           }
  //       } else {
  //           // Toast.makeText(this, getString(R.string.bt_null), Toast.LENGTH_LONG).show();
  //           System.out.println("finalizeBt BLUETOOTH MANAGER NULL ERROR ");
  //       }
  //   }
  
  // }
}
