package foundation.mee.android_client

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import foundation.mee.android_client.navigation.Navigator
import foundation.mee.android_client.navigation.MeeNavGraph
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeeIdentityAgentTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val controller = rememberNavController()
                    // Need to pass controller to enable navigation
                    Navigator.navController = controller
                    MeeNavGraph(controller)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

//@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MeeIdentityAgentTheme {
        Greeting("Mee")
    }
}
