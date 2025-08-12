package com.chicken.two.navigation

sealed class ScreenRoutes(val route: String) {

    data object SplashScreen : ScreenRoutes(SPLASH_SCREEN)
    data object HomeScreen : ScreenRoutes(HOME_SCREEN)
    data object SettingsScreen : ScreenRoutes(SETTINGS_SCREEN)
    data object AboutScreen : ScreenRoutes(ABOUT_SCREEN)
    data object NoNetworkScreen : ScreenRoutes(NO_NETWORK_SCREEN)
    data object GameScreen : ScreenRoutes(GAME_SCREEN)
    data object ContentScreen : ScreenRoutes(CONTENT_SCREEN)

    companion object {
        private const val SPLASH_SCREEN = "Splash_Screen"
        private const val HOME_SCREEN = "Home_Screen"
        private const val SETTINGS_SCREEN = "Settings_Screen"
        private const val ABOUT_SCREEN = "About_Screen"
        private const val NO_NETWORK_SCREEN = "No_Network_Screen"
        private const val GAME_SCREEN = "Game_Screen"
        private const val CONTENT_SCREEN = "Content_Screen"
    }
}