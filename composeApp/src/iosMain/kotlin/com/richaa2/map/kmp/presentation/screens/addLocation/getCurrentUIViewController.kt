package com.richaa2.map.kmp.presentation.screens.addLocation

import platform.UIKit.UIViewController
import platform.UIKit.UIApplication
fun getCurrentUIViewController(): UIViewController? {
    return UIApplication.sharedApplication.keyWindow?.rootViewController
}