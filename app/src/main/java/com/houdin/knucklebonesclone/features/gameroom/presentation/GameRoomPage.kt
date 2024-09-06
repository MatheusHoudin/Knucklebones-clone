package com.houdin.knucklebonesclone.features.gameroom.presentation

import android.content.ClipData
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import android.content.Context.CLIPBOARD_SERVICE
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import qrgenerator.QRCodeImage

@Composable
fun GameRoom(
    viewModel: GameRoomViewModel = koinViewModel()
) {
    val state = viewModel.gameRoomState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimateDottedText("Waiting for game to start")
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Scan the QR code to share the room with other players",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(36.dp))
        QRCodeImage(
            url = state.value.roomLink,
            contentScale = ContentScale.Fit,
            contentDescription = "QR Code",
            modifier = Modifier
                .size(150.dp)
                .border(width = 3.dp, color = MaterialTheme.colorScheme.primary),
            onSuccess = {},
            onFailure = {}
        )
        Spacer(modifier = Modifier.height(16.dp))

        var copyLinkText by remember { mutableStateOf(COPY_LINK) }
        val context = LocalContext.current
        val clipboardManager = LocalClipboardManager.current
        Text(
            text = copyLinkText,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.tertiary)
                .clickable {
                    copyLinkText = COPIED_LINK
                    clipboardManager.setText(AnnotatedString(state.value.roomLink))
                    Toast.makeText(context, "Link to the game was copied!", Toast.LENGTH_SHORT).show()
                }
                .padding(10.dp)
        )
    }
}

@Composable
fun AnimateDottedText(
    text: String,
    modifier: Modifier = Modifier,
    cycleDuration: Int = 1500
) {
    val transition = rememberInfiniteTransition(label = "Dots Transition")

    // Define the animated value for the number of visible dots
    val visibleDotsCount = transition.animateValue(
        initialValue = 0,
        targetValue = 4,
        typeConverter = Int.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = cycleDuration,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "Starting the game"
    )

    Text(
        text = text + ".".repeat(visibleDotsCount.value),
        modifier = modifier,
        fontWeight = FontWeight.SemiBold,
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onPrimary
    )
}

private const val COPY_LINK = "Copy link"
private const val COPIED_LINK = "Copied!"