import SwiftUI
import GoogleMaps

@main
struct iOSApp: App {
   init() {
        GMSServices.provideAPIKey(getAPIKey())
    }

    func getAPIKey() -> String {
        guard let path = Bundle.main.path(forResource: "Secrets", ofType: "plist"),
              let plist = NSDictionary(contentsOfFile: path) else {
            fatalError("Could not load Secrets.plist")
        }
        
        switch plist["apiKey"] as? String {
        case .some(let apiKey):
            return apiKey
        case .none:
            fatalError("Could not load apiKey from Secrets.plist")
        }
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
