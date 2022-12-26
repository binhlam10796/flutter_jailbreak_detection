package appmire.be.flutterjailbreakdetection

import android.content.Context
import android.provider.Settings
import com.kimchangyoun.rootbeerFresh.RootBeer

import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding



class FlutterJailbreakDetectionPlugin : FlutterPlugin, MethodCallHandler {
    private lateinit var context: Context
    private lateinit var channel: MethodChannel


    override fun onAttachedToEngine(binding: FlutterPluginBinding) {
        channel = MethodChannel(binding.binaryMessenger, "flutter_jailbreak_detection")
        context = binding.applicationContext
        channel.setMethodCallHandler(this)
    }


    override fun onDetachedFromEngine(binding: FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }


    private fun isDevMode(): Boolean {
        return Settings.Secure.getInt(
            context.contentResolver,
            Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0
        ) != 0
    }

    private fun isEmulator(): Boolean {
        var isEmulator = (Build.MANUFACTURER.contains("Genymotion") 
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.toLowerCase().contains("droid4x")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.HARDWARE == "goldfish"
                || Build.HARDWARE == "vbox86"
                || Build.HARDWARE.toLowerCase().contains("nox")
                || Build.FINGERPRINT.startsWith("generic")
                || Build.PRODUCT == "sdk"
                || Build.PRODUCT == "google_sdk"
                || Build.PRODUCT == "sdk_x86"
                || Build.PRODUCT == "vbox86p"
                || Build.PRODUCT.toLowerCase().contains("nox")
                || Build.BOARD.toLowerCase().contains("nox")
                || (Build.BRAND.startsWith("generic") &&    Build.DEVICE.startsWith("generic")))
        return isEmulator
    }


    override fun onMethodCall(call: MethodCall, result: Result): Unit {
        if (call.method.equals("jailbroken")) {
            val rootBeer = RootBeer(context)
            result.success(rootBeer.isRooted)
        } else if (call.method.equals("developerMode")) {
            result.success(isEmulator())
        } else {
            result.notImplemented()
        }
    }


}
