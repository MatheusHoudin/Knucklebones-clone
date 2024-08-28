package com.houdin.knucklebonesclone.features.home.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.houdin.knucklebonesclone.R
import com.houdin.knucklebonesclone.shared.components.button.KnuckleButtonPrimary
import com.houdin.knucklebonesclone.shared.components.button.KnuckleButtonSecondary
import com.houdin.knucklebonesclone.shared.theme.KnucklebonesCloneTheme

@Composable
fun HomePage(
    startPlaying: () -> Unit
) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Image(
                painter = painterResource(id = R.drawable.knucklebones),
                contentDescription = "Knucklebones Icon",
                modifier = Modifier.size(46.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Knucklebones",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Spacer(modifier = Modifier.height(60.dp))
        KnuckleButtonPrimary(
            "Play",
            modifier = Modifier.fillMaxWidth()
        ) { startPlaying() }
        Spacer(modifier = Modifier.height(16.dp))
        KnuckleButtonSecondary(
            "How to play?",
            modifier = Modifier.fillMaxWidth()
        ) {
            Intent(Intent.ACTION_VIEW, Uri.parse("https://cult-of-the-lamb.fandom.com/wiki/Knucklebones")).let {
                context.startActivity(it)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    KnucklebonesCloneTheme {
        HomePage() {}
    }
}