#import "TecsenPlugin.h"
#if __has_include(<tecsen_plugin/tecsen_plugin-Swift.h>)
#import <tecsen_plugin/tecsen_plugin-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "tecsen_plugin-Swift.h"
#endif

@implementation TecsenPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftTecsenPlugin registerWithRegistrar:registrar];
}
@end
