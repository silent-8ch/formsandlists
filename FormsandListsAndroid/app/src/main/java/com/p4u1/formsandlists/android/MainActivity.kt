package com.p4u1.formsandlists.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.p4u1.formsandlists.android.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var currentScreen by remember { mutableStateOf(Screen.Login) }
            var showSuccess by remember { mutableStateOf(false) }
            
            when (currentScreen) {
                Screen.Login -> {
                    LoginScreen(onLoginSuccess = { currentScreen = Screen.List })
                }
                Screen.List -> {
                    ListScreen(showSuccess = showSuccess, onSuccess = { showSuccess = true })
                }
            }
        }
    }
}

enum class Screen {
    Login, List
}

data class ListItem(
    val id: Int,
    val text: String = "List Item ${Random.nextInt(100, 999)}"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(showSuccess: Boolean, onSuccess: () -> Unit) {
    var items by remember { mutableStateOf(listOf<ListItem>()) }
    val maxItems = 3
    
    LaunchedEffect(items.size) {
        if (items.size == maxItems && !showSuccess) {
            onSuccess()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (showSuccess) "Good Job!" else "Add three items",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        },
        floatingActionButton = {
            if (items.size < maxItems) {
                FloatingActionButton(
                    onClick = {
                        items = items + ListItem(items.size)
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add item")
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items) { item ->
                    ListItemRow(
                        item = item,
                        isActive = showSuccess
                    )
                }
            }
            
            if (showSuccess) {
                ConfettiAnimation()
            }
            
            if (items.isEmpty()) {
                Text(
                    text = "Tap + to add items",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun ListItemRow(
    item: ListItem,
    isActive: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isActive) 
                MaterialTheme.colorScheme.primaryContainer 
            else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = item.text,
                style = MaterialTheme.typography.bodyLarge,
                color = if (isActive) 
                    MaterialTheme.colorScheme.onPrimaryContainer 
                else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            
            Text(
                text = "Password is password",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(80.dp))
            
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("hint: username") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Enter password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Button(
                onClick = {
                    if (password == "password") {
                        onLoginSuccess()
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                "Incorrect password. Remember, the password is 'password'",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
            ) {
                Text("Login")
            }
        }
    }
}

@Composable
fun SuccessScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Good Job!",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            
            ConfettiAnimation()
        }
    }
}

@Composable
fun ConfettiAnimation() {
    val confettiColors = listOf(
        ConfettiRed,
        ConfettiBlue,
        ConfettiGreen,
        ConfettiYellow,
        ConfettiPurple
    )
    
    var particles by remember { mutableStateOf(emptyList<ConfettiParticle>()) }
    val scope = rememberCoroutineScope()
    
    LaunchedEffect(Unit) {
        // Create initial particles spread across the width
        val screenWidth = 1000f // Approximate screen width
        particles = List(50) { // Start with fewer particles
            val startX = Random.nextFloat() * screenWidth
            ConfettiParticle.create(Offset(startX, -50f), confettiColors)
        }
        
        // Animation loop
        scope.launch {
            while (true) {
                delay(16) // Approximately 60 FPS
                particles = particles.filter { it.alpha > 0 }.map {
                    it.apply { update(0.016f) }
                }
                
                // Add new particles more frequently but fewer at a time
                if (Random.nextFloat() < 0.3f) { // 30% chance each frame
                    val newParticles = List(2) { // Add just 2 particles at a time
                        val startX = Random.nextFloat() * screenWidth
                        ConfettiParticle.create(Offset(startX, -50f), confettiColors)
                    }
                    particles = particles + newParticles
                }
                
                // Limit total particles to prevent performance issues
                if (particles.size > 100) {
                    particles = particles.take(100)
                }
            }
        }
    }
    
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        particles.forEach { particle ->
            rotate(particle.rotation, particle.position) {
                drawRect(
                    color = particle.color,
                    topLeft = Offset(-particle.size / 2, -particle.size / 2),
                    size = androidx.compose.ui.geometry.Size(particle.size, particle.size),
                    alpha = particle.alpha
                )
            }
        }
    }
}