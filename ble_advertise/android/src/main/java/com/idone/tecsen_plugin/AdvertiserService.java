// package com.pcs.ble_advertisement;
// import android.Manifest;
// import android.annotation.SuppressLint;
// import android.app.Notification;
// import android.app.PendingIntent;
// import android.app.Service;
// import android.bluetooth.BluetoothAdapter;
// import android.bluetooth.BluetoothManager;
// import android.bluetooth.le.AdvertiseCallback;
// import android.bluetooth.le.AdvertiseData;
// import android.bluetooth.le.AdvertiseSettings;
// import android.bluetooth.le.BluetoothLeAdvertiser;
// import android.content.Context;
// import android.content.Intent;
// import android.content.pm.PackageManager;
// import android.os.Bundle;
// import android.os.Handler;
// import android.os.IBinder;
// import android.os.Message;
// import android.os.Messenger;
// import android.os.ParcelUuid;
// import android.os.RemoteException;
// import android.util.Log;
// import android.widget.Toast;

// import androidx.core.app.ActivityCompat;

// import org.jetbrains.annotations.NotNull;
// import org.jetbrains.annotations.Nullable;

// import java.text.SimpleDateFormat;
// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
// import java.util.concurrent.TimeUnit;


// /**뭇
//  * Manages BLE Advertising independent of the main app.
//  * If the app goes off screen (or gets killed completely) advertising can continue because this
//  * Service is maintaining the necessary Callback in memory.
//  */
// public class AdvertiserService extends Service {

//     private static final String TAG = AdvertiserService.class.getSimpleName();
//     private static final int FOREGROUND_NOTIFICATION_ID = 1;
//     /**
//      * A global variable to let AdvertiserFragment check if the Service is running without needing
//      * to start or bind to it.
//      * This is the best practice method as defined here:
//      * https://groups.google.com/forum/#!topic/android-developers/jEvXMWgbgzE
//      */
//     public static boolean running = false;
//     //   public static boolean bLockOpen = false;
//     public static final String ADVERTISING_FAILED = "com.tecsen.brucegetqrbd.advertising_failed";
//     public static final String ADVERTISING_FAILED_EXTRA_CODE = "failureCode";
//     public static final int ADVERTISING_TIMED_OUT = 6;
//     private BluetoothLeAdvertiser mBluetoothLeAdvertiser;
//     private AdvertiseCallback mAdvertiseCallback;
//     private Handler mHandler;
//     private Runnable timeoutRunnable;

//     //   private String csBtAddress = "";
//     private String csStrDataTail = "";
//     private String csCommand = "";
//     private String strLogTitle = "";
//     /**
//      * Length of time to allow advertising before automatically shutting off. (10 minutes)
//      */
//     private long TIMEOUT = TimeUnit.MILLISECONDS.convert(10, TimeUnit.SECONDS);

//     @Override
//     public void onCreate() {
//         System.out.println("onCreate 호출됨");
//         running = true;
//         initializeBt();
//     //    startAdvertising();
//         setTimeout();
//         super.onCreate();
//     }

//     @Override
//     public void onDestroy() {
//         /**
//          * Note that onDestroy is not guaranteed to be called quickly or at all. Services exist at
//          * the whim of the system, and onDestroy can be delayed or skipped entirely if memory need
//          * is critical.
//          */
//         running = false;
//         stopAdvertising();
//         finalizeBt();
//         mHandler.removeCallbacks(timeoutRunnable);
//         stopForeground(true);
//         super.onDestroy();
//     }

//     /**
//      * Required for extending service, but this will be a Started Service only, so no need for
//      * binding.
//      */
//     @Override
//     public IBinder onBind(Intent intent) {
//         return null;
//     }

//     /**
//      * Get references to system Bluetooth objects if we don't have them already.
//      */
//     @SuppressLint("MissingPermission")
//     private void initializeBt() {
//         if (mBluetoothLeAdvertiser == null) {
//             BluetoothManager mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
//             if (mBluetoothManager != null) {
//                 BluetoothAdapter mBluetoothAdapter = mBluetoothManager.getAdapter();
//                 if (mBluetoothAdapter != null) {
//                     ///Caps8 Bluetooth Key
//                     mBluetoothAdapter.setName("Caps8");
//                     mBluetoothLeAdvertiser = mBluetoothAdapter.getBluetoothLeAdvertiser();
//                 } else {
//                     System.out.println("initializeBt BLUETOOTH ADAPTER NULL ERROR ");
//                     // Toast.makeText(this, getString(R.string.bt_null), Toast.LENGTH_LONG).show();
//                 }
//             } else {
//                 System.out.println("initializeBt BLUETOOTH MANAGER NULL ERROR ");
//                 // Toast.makeText(this, getString(R.string.bt_null), Toast.LENGTH_LONG).show();
//             }
//         }

//     }
//     /**
//      * Get references to system Bluetooth objects if we don't have them already.
//      */
//     @SuppressLint("MissingPermission")
//     private void finalizeBt() {
//         if (mBluetoothLeAdvertiser == null) {
//             BluetoothManager mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
//             if (mBluetoothManager != null) {
//                 BluetoothAdapter mBluetoothAdapter = mBluetoothManager.getAdapter();
//                 if (mBluetoothAdapter != null) {
//                     mBluetoothAdapter.setName("BruceJy");
//                     //                   mBluetoothAdapter.setName("Caps8");
//                     //                  mBluetoothLeAdvertiser = mBluetoothAdapter.getBluetoothLeAdvertiser();
//                 } else {
//                     // Toas.makeText(this, getString(R.string.bt_null), Toast.LENGTH_LONG).show();
//                     System.out.println("finalizeBt BLUETOOTH ADAPTER NULL ERROR ");
//                 }
//             } else {
//                 // Toast.makeText(this, getString(R.string.bt_null), Toast.LENGTH_LONG).show();
//                 System.out.println("finalizeBt BLUETOOTH MANAGER NULL ERROR ");
//             }
//         }

//     }

//     /**
//      * Starts a delayed Runnable that will cause the BLE Advertising to timeout and stop after a
//      * set amount of time.
//      */
//     private void setTimeout() {
//         mHandler = new Handler();
//         timeoutRunnable = () -> {
//             Log.i(TAG, "AdvertiserService has reached timeout of " + TIMEOUT + " milliseconds, stopping advertising.");
//             sendFailureIntent(ADVERTISING_TIMED_OUT);
//             stopSelf();
//         };
//         mHandler.postDelayed(timeoutRunnable, TIMEOUT);
//     }

//     @Override
//     public int onStartCommand(Intent intent, int flags, int startId) {
//         System.out.println("onStartCommand 호출됨");
//         if(intent == null ) {
//             // 다시 자동으로 실행 해 달라는 옵션
//             return Service.START_STICKY;
//         }
//         else {
//             // csCommand = intent.getStringExtra("key");
//             String csBtAddress = intent.getStringExtra("bd");
//             //      csBtAddress = "1234567890AB";
//             // 현재 날짜 구하기 (시스템 시계, 시스템 타임존)
//             LocalDateTime now = LocalDateTime.now();         // 연도, 월(문자열, 숫자), 일, 일(year 기준), 요일(문자열, 숫자)
// /*
//             int year = now.getYear();  // 2022
//  //           String month = now.getMonth().toString();  // June
//             int monthValue = now.getMonthValue();  // 6
//             int dayOfMonth = now.getDayOfMonth();   // 17
//  //           int dayOfYear = now.getDayOfYear(); // 116
//  //           String dayOfWeek = now.getDayOfWeek().toString();  // Thursday
//  //           int dayOfWeekValue = now.getDayOfWeek().getValue(); // 4
//               int hour = now.getHour();
//             int minute = now.getMinute();
//             int second = now.getSecond();
// */
//             csStrDataTail = now.format(DateTimeFormatter.ofPattern("yyMM-ddHH-mmss-"));
//             String strPush = "47439011-" + csStrDataTail;

//             if( csCommand.equalsIgnoreCase("attend") == true) {
//                 strPush += "010154534447";
//                 strLogTitle =  "Attend Data :";
//             }
//             else if( csCommand.equalsIgnoreCase("gohome") == true) {
//                 strPush += "020254534447";
//                 strLogTitle =  "Go Home Data :";
//             }
//             else if( csCommand.equalsIgnoreCase("goout") == true) {
//                 strPush += "040454534447";
//                 strLogTitle =  "Go Out Data :";
//             }
//             else if( csCommand.equalsIgnoreCase("return") == true) {
//                 strPush += "080854534447";
//                 strLogTitle =  "Return Data :";
//             }
//             else if( csCommand.equalsIgnoreCase("check") == true) {
//                 strPush += "0A0A54534447";
//                 strLogTitle =  "Check Data :";
//             }
//             else if( csCommand.equalsIgnoreCase("open") == true) {
//                 strPush += "0C0C54534447";
//                 strLogTitle = "Open Data :";
//             }
//             else if( csCommand.equalsIgnoreCase("lock") == true) {
//                 strPush += "C0C054534447";
//                 strLogTitle =  "Lock Data :" ;
//             }
//             else if( csCommand.equalsIgnoreCase("time") == true) {
//                 strPush += "0E0E54534447";
//                 strLogTitle =  "Time Data :";
//             }
//             else if( csCommand.equalsIgnoreCase("message") == true) {
//                 strPush += "E0E054534447";
//                 strLogTitle =  "Message Data :";
//             }
//             else { // ( csCommand.equalsIgnoreCase("alarm") == true)
//                 strPush += "11AA54534447";
//                 strLogTitle =  "Alarm Data :";
//             }

//             Constants.Advt_Service_UUID = ParcelUuid.fromString(csBtAddress.toString());
//             Log.d( TAG, strLogTitle + Constants.Advt_Service_UUID.toString());

//             startAdvertising();
//         }
//         return super.onStartCommand(intent, flags, startId);
//     }

//     /**
//      * Starts BLE Advertising.
//      */
//     private void startAdvertising() {
//         //    goForeground();

//         Log.i(TAG, "Service: Try to do Advertising");
//         System.out.println("START ADVERTISING!");
//         if (mAdvertiseCallback == null) {
//             AdvertiseSettings settings = buildAdvertiseSettings();
//             AdvertiseData data = buildAdvertiseData();
//             mAdvertiseCallback = new SampleAdvertiseCallback();

//             if (mBluetoothLeAdvertiser != null) {
//                 Log.i(TAG, "Service: Starting Advertising");
//                 if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADVERTISE) != PackageManager.PERMISSION_GRANTED) {
//                     // TODO: Consider calling
//                     //    ActivityCompat#requestPermissions
//                     // here to request the missing permissions, and then overriding
//                     //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                     //                                          int[] grantResults)
//                     // to handle the case where the user grants the permission. See the documentation
//                     // for ActivityCompat#requestPermissions for more de
//                 }
//                 mBluetoothLeAdvertiser.startAdvertising(settings, data,
//                         mAdvertiseCallback);
//             }
//         }
//     }

//     /**
//      * Move service to the foreground, to avoid execution limits on background processes.
//      *
//      * Callers should call stopForeground(true) when background work is complete.
//      */
//     // private void goForeground() {
//     //     Intent notificationIntent = new Intent(this, MainActivity.class);
//     //     PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
//     //             notificationIntent, 0);
//     //     Notification n = new Notification.Builder(this)
//     //             .setContentTitle("Advertising device via Bluetooth")
//     //             .setContentText("This device is discoverable to others nearby.")
//     //             .setSmallIcon(R.drawable.ic_launcher)
//     //             .setContentIntent(pendingIntent)
//     //             .build();
//     //     startForeground(FOREGROUND_NOTIFICATION_ID, n);
//     // }

//     /**
//      * Stops BLE Advertising.
//      */
//     private void stopAdvertising() {
//         Log.i(TAG, "Service: Stopping Advertising");
//         if (mBluetoothLeAdvertiser != null) {
//             if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADVERTISE) != PackageManager.PERMISSION_GRANTED) {
//                 // TODO: Consider calling
//                 //    ActivityCompat#requestPermissions
//                 // here to request the missing permissions, and then overriding
//                 //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                 //                                          int[] grantResults)
//                 // to handle the case where the user grants the permission. See the documentation
//                 // for ActivityCompat#requestPermissions for more details.
// //                return;
//             }
//             mBluetoothLeAdvertiser.stopAdvertising(mAdvertiseCallback);
//             mAdvertiseCallback = null;
//         }
//     }

//     /**
//      * Returns an AdvertiseData object which includes the Service UUID and Device Name.
//      */
//     private AdvertiseData buildAdvertiseData() {

//         /**
//          * Note: There is a strict limit of 31 Bytes on packets sent over BLE Advertisements.
//          *  This includes everything put into AdvertiseData including UUIDs, device info, &
//          *  arbitrary service or manufacturer data.
//          *  Attempting to send packets over this limit will result in a failure with error code
//          *  AdvertiseCallback.ADVERTISE_FAILED_DATA_TOO_LARGE. Catch this error in the
//          *  onStartFailure() method of an AdvertiseCallback implementation.
//          */
//         AdvertiseData.Builder dataBuilder = new AdvertiseData.Builder();
//         dataBuilder.addServiceUuid(Constants.Advt_Service_UUID);
//         dataBuilder.setIncludeDeviceName(true);

//         /* For example - this will cause advertising to fail (exceeds size limit) */
//         //    String failureData = "asdghkajsghalkxcjhfa;sghtalksjcfhalskfjhasldkjfhdskf";
//         //    dataBuilder.addServiceData(Constants.Service_UUID, ADVT_PUSH_SYNC);

//         return dataBuilder.build();
//     }

//     /**
//      * Returns an AdvertiseSettings object set to use low power (to help preserve battery life)
//      * and disable the built-in timeout since this code uses its own timeout runnable.
//      */
//     private AdvertiseSettings buildAdvertiseSettings() {
//         AdvertiseSettings.Builder settingsBuilder = new AdvertiseSettings.Builder();
//         //      settingsBuilder.setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_POWER);
//         settingsBuilder.setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED);
//         settingsBuilder.setConnectable(true);
//         settingsBuilder.setTimeout(0);
//         return settingsBuilder.build();
//     }

//     /**
//      * Custom callback after Advertising succeeds or fails to start. Broadcasts the error code
//      * in an Intent to be picked up by AdvertiserFragment and stops this Service.
//      */
//     private class SampleAdvertiseCallback extends AdvertiseCallback {

//         @Override
//         public void onStartFailure(int errorCode) {
//             super.onStartFailure(errorCode);

//             Log.i(TAG, "Advertising failed");
//             sendFailureIntent(errorCode);
//             stopSelf();

//         }

//         @Override
//         public void onStartSuccess(AdvertiseSettings settingsInEffect) {
//             super.onStartSuccess(settingsInEffect);
//             Log.i(TAG, "Advertising successfully started");
//         }
//     }

//     /**
//      * Builds and sends a broadcast intent indicating Advertising has failed. Includes the error
//      * code as an extra. This is intended to be picked up by the {@code AdvertiserFragment}.
//      */
//     private void sendFailureIntent(int errorCode){
//         Intent failureIntent = new Intent();
//         failureIntent.setAction(ADVERTISING_FAILED);
//         failureIntent.putExtra(ADVERTISING_FAILED_EXTRA_CODE, errorCode);
//         sendBroadcast(failureIntent);
//     }

// }
