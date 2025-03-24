import SwiftUI
import GoogleMaps

@main
struct iOSApp: App {
   init() {
       guard let path = Bundle.main.path(forResource: "Secrets", ofType: "plist"),
             let plist = NSDictionary(contentsOfFile: path),
             let apiKey = plist["apiKey"] as? String else {
           fatalError("Could not load apiKey from Secrets.plist")
       }
        GMSServices.provideAPIKey(apiKey)
    }


    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
