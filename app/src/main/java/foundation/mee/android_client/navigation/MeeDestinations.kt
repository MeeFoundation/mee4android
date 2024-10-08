package foundation.mee.android_client.navigation

enum class MeeDestinations(val route: String) {
    WELCOME_SCREEN("welcome_pages"),
    CONNECTIONS("connections"),
    MANAGE("manage"),
    CONSENT("consent"),
    INITIAL_FLOW("initial_flow"),
    CONTEXT_RECOVERY_FLOW("context_recovery_flow"),
    SETTINGS("settings")
}