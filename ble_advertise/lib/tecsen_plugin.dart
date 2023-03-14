
import 'tecsen_plugin_platform_interface.dart';

class TecsenPlugin {
  Future<String?> getPlatformVersion() {
    return TecsenPluginPlatform.instance.getPlatformVersion();
  }
}
