package com.richaa2.map.kmp.core
import org.koin.compose.koinInject
import org.koin.core.Koin
import kotlin.experimental.ExperimentalObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName(swiftName = "PermissionRequestProtocol")
actual interface PermissionsBridgeListener {
    actual fun requestLocationPermission(callback: PermissionResultCallback)
    actual fun isLocationPermissionGranted(): Boolean
}

//@Suppress("unused")
//fun registerPermissionHandler(listener: PermissionsBridgeListener){
//    Koin().get<PermissionBridge>().setListener(listener)
//}
