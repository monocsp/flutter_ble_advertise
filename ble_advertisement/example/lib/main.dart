import 'dart:developer';

import 'package:ble_advertisement/ble_advertise.dart';
import 'package:ble_advertisement/model/advertise_options/advertise_options.dart';
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
  final bleAdvertise = BleAdvertise.getInstance();

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: const Center(
          child: Text('Running on: '),
        ),
        floatingActionButton: FloatingActionButton(
            onPressed: () async {
              bool isthis = await bleAdvertise.isAbleAdvertise;
              log(isthis.toString());
              // if (isthis) {
              // await bleAdvertise.startAdvertise(
              //     serviceUuid: 'ffffffff-ffff-ffff-0101-4e4350555348',
              //     // advertiseOptions: AdvertiseOptions(),
              //     bluetoothSetName: 'Caps8');
              // } else {
              //   bleAdvertise.openBleSettingPage;
              // }
            },
            child: Icon(Icons.ac_unit_rounded)),
      ),
    );
  }
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