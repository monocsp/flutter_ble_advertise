import 'dart:developer';
import 'dart:io';

import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
    // initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    // We also handle the message potentially returning null.
    // try {
    // platformVersion = await _tecsenPlugin.getPlatformVersion() ?? 'Unknown platform version';
    // } on PlatformException {
    //   platformVersion = 'Failed to get platform version.';
    // }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      // _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [
            GestureDetector(
              onTap: () async {
                DateFormat dateFormat = DateFormat('yyMM-ddHH-mmss');
                String date = dateFormat.format(DateTime.now()).toString();
                bool t = await TecsenLibrary.startAdvertise(
                    bluetoothSetName: "Caps8",
                    uuid: "00000000-$date-0C0C54534447");
                log("TTT : $t");

                // await MethodChannelTecsenPlugin.tecsenIoSMethodChannel
                //     .invokeMethod('advertise', {"UUID": "00000000-$date-0C0C54534447"});

                // return;

                // MethodChannelTecsenPlugin.startAdvertiserToAndroid(
                //   "Caps8",
                //   "ffffffff-ffff-ffff-0101-0C0C54534447",
                // );
                log("Button");
              },
              child: Center(
                child: Container(
                    color: Colors.green, child: const Text('Open Button')),
              ),
            ),
            GestureDetector(
              onTap: () {
                TecsenLibrary.openBlePage();
              },
              child: Center(
                child: Container(
                    color: Colors.red,
                    child: const Text('BLE setting open')),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
