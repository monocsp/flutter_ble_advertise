import 'package:flutter_test/flutter_test.dart';
import 'package:tecsen_plugin/tecsen_plugin.dart';
import 'package:tecsen_plugin/tecsen_plugin_platform_interface.dart';
import 'package:tecsen_plugin/tecsen_plugin_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockTecsenPluginPlatform
    with MockPlatformInterfaceMixin
    implements TecsenPluginPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final TecsenPluginPlatform initialPlatform = TecsenPluginPlatform.instance;

  test('$MethodChannelTecsenPlugin is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelTecsenPlugin>());
  });

  test('getPlatformVersion', () async {
    TecsenPlugin tecsenPlugin = TecsenPlugin();
    MockTecsenPluginPlatform fakePlatform = MockTecsenPluginPlatform();
    TecsenPluginPlatform.instance = fakePlatform;

    expect(await tecsenPlugin.getPlatformVersion(), '42');
  });
}
