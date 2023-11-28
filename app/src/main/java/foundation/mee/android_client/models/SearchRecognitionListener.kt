package foundation.mee.android_client.models

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import foundation.mee.android_client.R
import foundation.mee.android_client.utils.showConsentToast

const val DEFAULT_LANGUAGE_CODE: String = "en"

val recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
    putExtra(
        RecognizerIntent.EXTRA_LANGUAGE_MODEL,
        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
    )
    putExtra(RecognizerIntent.EXTRA_LANGUAGE, DEFAULT_LANGUAGE_CODE)
}

class SearchRecognitionListener(
    private val context: Context,
    private val onChangeIsSpeaking: (newVal: Boolean) -> Unit,
    private val onReceiveResult: (result: String) -> Unit,
    private val recognizer: SpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
) : RecognitionListener {

    fun startListening() {
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            Log.e("VoiceToTextParser: ", "Recognition is not available")
            showConsentToast(context, R.string.recognition_not_available_text)
            onChangeIsSpeaking(false)
            return
        }

        recognizer.setRecognitionListener(this)
        recognizer.startListening(recognizerIntent)

        onChangeIsSpeaking(true)
    }

    fun stopListening() {
        recognizer.stopListening()
        onChangeIsSpeaking(false)
    }

    override fun onEndOfSpeech() {
        onChangeIsSpeaking(false)
    }

    override fun onError(error: Int) {
        onChangeIsSpeaking(false)
        Log.e("SpeechRecognizerError, errorCode: ", error.toString())
        showConsentToast(context, R.string.recognition_failed_text)
    }

    override fun onResults(results: Bundle?) {
        results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            ?.getOrNull(0)
            ?.let { result ->
                onReceiveResult(result)
            }
    }

    override fun onReadyForSpeech(params: Bundle?) = Unit

    override fun onBeginningOfSpeech() = Unit

    override fun onRmsChanged(rmsdB: Float) = Unit

    override fun onBufferReceived(buffer: ByteArray?) = Unit

    override fun onPartialResults(partialResults: Bundle?) = Unit

    override fun onEvent(eventType: Int, params: Bundle?) = Unit
}