// import 'package:plugin_platform_interface/plugin_platform_interface.dart';

// import 'tecsen_plugin_method_channel.dart';

// abstract class TecsenPluginPlatform extends PlatformInterface {
//   /// Constructs a TecsenPluginPlatform.
//   TecsenPluginPlatform() : super(token: _token);

//   static final Object _token = Object();

//   static TecsenPluginPlatform _instance = MethodChannelTecsenPlugin();

//   /// The default instance of [TecsenPluginPlatform] to use.
//   ///
//   /// Defaults to [MethodChannelTecsenPlugin].
//   static TecsenPluginPlatform get instance => _instance;

//   /// Platform-specific implementations should set this with their own
//   /// platform-specific class that extends [TecsenPluginPlatform] when
//   /// they register themselves.
//   static set instance(TecsenPluginPlatform instance) {
//     PlatformInterface.verifyToken(instance, _token);
//     _instance = instance;
//   }

//   Future<String?> getPlatformVersion() {
//     throw UnimplementedError('platformVersion() has not been implemented.');
//   }
// }
