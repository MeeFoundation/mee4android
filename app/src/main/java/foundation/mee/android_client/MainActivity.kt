package foundation.mee.android_client

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import foundation.mee.android_client.ui.theme.MeeIdentityAgentTheme
import uniffi.mee_agent.*

class MainActivity : ComponentActivity() {
    companion object {
        init {
            System.loadLibrary("uniffi_mee_agent")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
            var appDir = getApplicationInfo().dataDir + "/mee"
            var agent = getAgent(
                MeeAgentConfig(
                    appDir,
                null,
                MeeAgentDidRegistryConfig.DidKey
            )
            )
        print("test")
        print( agent.listMaterializedContexts())
        super.onCreate(savedInstanceState)
        setContent {
            MeeIdentityAgentTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Mee")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MeeIdentityAgentTheme {
        Greeting("Mee")
    }
}