import io.flutter.plugin.common.MethodCall;
import android.os.ParcelUuid;

public class BleAdvertiseData{
   private final ParcelUuid serviceUuid;
   private final boolean includeTxPowerLevel;
   private final boolean setIncludeDeviceName;
   private final int manufactureId;
   private final byte[] manufacturerSpecificData;
   private final ParcelUuid serviceSolicitationUuid;

   public BleAdvertiseData(ParcelUuid serviceUuid, boolean includeTxPowerLevel,boolean  setIncludeDeviceName, int manufactureId, byte[] manufacturerSpecificData,ParcelUuid serviceSolicitationUuid){

      this.serviceUuid = serviceUuid;
      this.includeTxPowerLevel = includeTxPowerLevel;
      this.setIncludeDeviceName = setIncludeDeviceName;
      this.manufactureId = manufactureId;
      this.manufacturerSpecificData = manufacturerSpecificData;
      this.serviceSolicitationUuid = serviceSolicitationUuid;
   }
   
   public static BleAdvertiseData createWithMethodChannelArg(MethodCall call){
      if(call.argument("serviceUuid") == null){

      }
      this.serviceUuid = call.argument("serviceUuid");

   
   }

}