import 'dart:async';

import 'package:flutter/services.dart';

class FlutterJailbreakDetection {
  static const MethodChannel _channel =
      const MethodChannel('flutter_jailbreak_detection');

  static Future<bool> get isJailbreak async {
    bool? jailbroken = await _channel.invokeMethod<bool>('jailbroken');
    return jailbroken ?? true;
  }

  static Future<bool> get isEmulator async {
    bool? developerMode = await _channel.invokeMethod<bool>('developerMode');
    return developerMode ?? true;
  }
}
