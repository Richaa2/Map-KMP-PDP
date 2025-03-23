import SwiftUI
import GoogleMaps

@main
struct iOSApp: App {
   init() {

        GMSServices.provideAPIKey("AIzaSyC6E2oBFBEpA96NQZ-ELZpxx5vOyXs2LC8")
    }


    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
