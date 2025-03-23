import SwiftUI
import GoogleMaps

@main
struct iOSApp: App {
   init() {

        GMSServices.provideAPIKey("AIzaSyC6E2oBFBEpA96NQZ-ELZpxx5vOyXs2LC8")
    }

//  @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
//
//class AppDelegate: NSObject, UIApplicationDelegate,PermissionRequestProtocol {
//
//    func application(
//        _ application: UIApplication,
//        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil
//    ) -> Bool {
//
//
//    KoinIosKt.doInitKoinIos()
//
//    PermissionBridge_iosKt.registerPermissionHandler(listener: self)
//
//    return true
//    }
//
//    func requestLocationPermission(callback: PermissionResultCallback) {
//            let store = CNLocationStore()
//            switch CNLocationStore.authorizationStatus(for: .locations) {
//            case .notDetermined:
//                store.requestAccess(for: .locations) { granted, error in
//                    DispatchQueue.main.async {
//                        if granted {
//                            callback.onPermissionGranted()
//                            print("Locations permission granted")
//                        } else {
//                            callback.onPermissionDenied(isPermanentDenied: false)
//                            print("Locations permission denied")
//                        }
//                    }
//                }
//            case .denied:
//                print("Locations access is denied")
//                DispatchQueue.main.async {
//                    callback.onPermissionDenied(isPermanentDenied: true)
//                }
//            case .restricted:
//                print("Locations access is restricted")
//                DispatchQueue.main.async {
//                    callback.onPermissionDenied(isPermanentDenied: true)
//                }
//            case .authorized:
//                print("Locations access already authorized")
//                DispatchQueue.main.async {
//                    callback.onPermissionGranted()
//                }
//            @unknown default:
//                fatalError("Unknown authorization status")
//            }
//        }
//
//    func isLocationPermissionGranted() -> Bool {
//        let status = CNLocationStore.authorizationStatus(for: .locations)
//        return status == .authorized
//    }
//
//
//    }