package com.ayush.recipegenerator.presentation.login

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val authState by viewModel.authState.collectAsState()

    // Google Sign-In setup
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("20794703369-5qd05subu34nq294m568gobscuantt94.apps.googleusercontent.com")
        .requestEmail()
        .build()

    val googleSignInClient = GoogleSignIn.getClient(context, gso)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.idToken?.let { token ->
                    viewModel.signInWithGoogle(token)
                } ?: run {
                    viewModel.setErrorMessage("Google Sign-In failed: No ID Token")
                }
            } catch (e: ApiException) {
                viewModel.setErrorMessage("Google Sign-In failed: ${e.statusCode}")
                Log.e("LoginScreen", "Google Sign-In failed: ${e.statusCode}", e)
            }
        } else {
            // Not a serious error, user might have just canceled
            viewModel.resetState()
        }
    }

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            onLoginSuccess()
        }
    }

    // Animation for the background gradient
    val infiniteTransition = rememberInfiniteTransition(label = "background_transition")
    val gradientOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1500f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "gradient_offset"
    )

    // Entrance animation for the card
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        isVisible = true
    }

    val cardOffset by animateFloatAsState(
        targetValue = if (isVisible) 0f else 100f,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "card_offset"
    )
    
    val cardAlpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 800, easing = LinearEasing),
        label = "card_alpha"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.tertiary,
                        MaterialTheme.colorScheme.secondaryContainer
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(gradientOffset, gradientOffset)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth()
                .offset(y = cardOffset.dp)
                .graphicsLayer { alpha = cardAlpha },
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Welcome to",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Medium
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Recipe Generator",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    lineHeight = 40.sp
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Discover, create, and save your favorite recipes effortlessly.",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )

                Spacer(modifier = Modifier.height(48.dp))

                if (authState is AuthState.Error) {
                    Text(
                        text = (authState as AuthState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Button(
                    onClick = {
                        viewModel.onLoginClicked {
                            viewModel.resetState()
                            // Sign out of Google to ensure the account picker shows up
                            googleSignInClient.signOut().addOnCompleteListener {
                                launcher.launch(googleSignInClient.signInIntent)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 2.dp,
                        pressedElevation = 4.dp
                    ),
                    enabled = authState !is AuthState.Loading
                ) {
                    if (authState is AuthState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "G",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Continue with Google",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }
        }
    }
}
