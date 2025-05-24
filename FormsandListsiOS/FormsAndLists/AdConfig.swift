import Foundation

struct AdConfig {
    static let testBannerID = "ca-app-pub-3940256099942544/2934735716" // AdMob test banner ID
    static let prodBannerID = "ca-app-pub-2376879364145932/3049416695" // Your production banner ID
    
    #if DEBUG
    static let bannerID = testBannerID
    #else
    static let bannerID = prodBannerID
    #endif
} 