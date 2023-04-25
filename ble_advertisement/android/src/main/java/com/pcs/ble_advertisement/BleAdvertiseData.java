package com.pcs.ble_advertisement;
import io.flutter.plugin.common.MethodCall;
import android.os.ParcelUuid;

public class BleAdvertiseData{
   // essential parameter
   public final String bluetoothName;
   public final ParcelUuid serviceUuid;
   // check has advertise option
   // advertise options parameter
   public final boolean hasAdvertiseOptions;

   // Default Values : 
   // advertiseMode = AdvertiseMode.balanced,
   // advertiseTxPower = AdvertiseTxPower.medium,
   // advertiseTimeOut = 1000,
   // connectable = true,

   public final boolean connectable;
   public final int advertiseTxPower;
   public final int advertiseMode;
   public final int advertiseInterval;
   public final int timeout;
   // Others doesn't matter whether contain or not

   // public final boolean includeTxPowerLevel;
   public final boolean setIncludeDeviceName;
   public final boolean includeTxPowerLevel;
   
   public final int manufactureId;
   public final byte[] manufacturerSpecificData;
   
   public final ParcelUuid serviceSolicitationUuid;

   // Coming Soon...
   // required api level 33 
   // public final boolean setDiscoverable;

   // public BleAdvertiseData(String bluetoothName,ParcelUuid serviceUuid, boolean includeTxPowerLevel,boolean  setIncludeDeviceName, int manufactureId, byte[] manufacturerSpecificData,ParcelUuid serviceSolicitationUuid){
   //    this.bluetoothName = bluetoothName;
   //    this.serviceUuid = serviceUuid;
   //    this.includeTxPowerLevel = includeTxPowerLevel;
   //    this.setIncludeDeviceName = setIncludeDeviceName;
   //    this.manufactureId = manufactureId;
   //    this.manufacturerSpecificData = manufacturerSpecificData;
   //    this.serviceSolicitationUuid = serviceSolicitationUuid;
   // }
   
   public BleAdvertiseData(MethodCall call){
      String uuid = call.argument("serviceUuid");
      this.serviceUuid = ParcelUuid.fromString(uuid);
      this.bluetoothName = call.argument("bluetoothName");
      this.hasAdvertiseOptions = call.argument("hasAdvertiseOptions");
      this.timeout = call.argument("timeout");
      this.connectable = call.argument("connectable");
      this.advertiseTxPower = call.argument("advertiseTxPower");
      this.advertiseMode = call.argument("advertiseMode");
      this.advertiseInterval = call.argument("advertiseInterval");
      this.manufactureId = call.argument("manufactureId");
      this.includeTxPowerLevel = call.argument("includeTxPowerLevel");
      this.manufacturerSpecificData = call.argument("manufacturerSpecificData");
      String serviceSolicitationUuidToString = call.argument("serviceSolicitationUuid");
      this.serviceSolicitationUuid = ParcelUuid.fromString(serviceSolicitationUuidToString);
      this.setIncludeDeviceName = call.argument("setIncludeDeviceName");
      
   }

}