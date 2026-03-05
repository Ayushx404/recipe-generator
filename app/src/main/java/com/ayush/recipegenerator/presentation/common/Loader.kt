package com.ayush.recipegenerator.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants.IterateForever
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import com.ayush.recipegenerator.R

@Composable
fun Loader(
    modifier: Modifier = Modifier
) {

    val dynamicProperties = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR_FILTER,
            value = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                MaterialTheme.colorScheme.primary.hashCode(),
                BlendModeCompat.SRC_ATOP
            ),
            keyPath = arrayOf(
                "**"
            )
        )
    )

    // Load Lottie animation for no record found
    val loaderComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            R.raw.loader
        ),
    )

    // Animate the Lottie composition indefinitely
    val progress by animateLottieCompositionAsState(
        composition = loaderComposition,
        iterations = IterateForever
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
    ) {
        LottieAnimation(
            composition = loaderComposition, progress = { progress },
            dynamicProperties = dynamicProperties
        )
    }
}

@Preview(showSystemUi = true, device = Devices.PIXEL_5)
@Composable
private fun LoaderPreview() {
    Loader()
}