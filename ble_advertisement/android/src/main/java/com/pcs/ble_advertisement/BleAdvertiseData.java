import io.flutter.plugin.common.MethodCall;
import android.os.ParcelUuid;

public class BleAdvertiseData{
   // essential parameter
   private final String bluetoothName;
   private final ParcelUuid serviceUuid;
   // check has advertise option
   // advertise options parameter
   private final boolean hasAdvertiseOptions;

   // Default Values : 
   // advertiseMode = AdvertiseMode.balanced,
   // advertiseTxPower = AdvertiseTxPower.medium,
   // advertiseTimeOut = 1000,
   // connectable = true,

   private final boolean connectable;
   private final int advertiseTxPower;
   private final int advertiseMode;
   private final int advertiseInterval;
   // Others doesn't matter whether contain or not

   // private final boolean includeTxPowerLevel;
   // private final boolean setIncludeDeviceName;
   
   private final int manufactureId;
   private final byte[] manufacturerSpecificData;
   
   private final ParcelUuid serviceSolicitationUuid;

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
      this.serviceUuid = call.argument("serviceUuid");
      this.bluetoothName = call.argument("bluetoothName");
      this.hasAdvertiseOptions = call.argument("hasAdvertiseOptions");
      if(!hasAdvertiseOptions){
         return this;
      }
      this.connectable = call.argument("connectable");
      this.advertiseTxPower = call.argument("advertiseTxPower");
      this.advertiseMode = call.argument("advertiseMode");
      this.advertiseInterval = call.argument("advertiseInterval");
      this.manufactureId = call.argument("manufactureId");
      this.manufacturerSpecificData = call.argument("manufacturerSpecificData");
      return this;   
   }

}