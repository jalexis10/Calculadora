package com.example.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculadora.ui.theme.CalculadoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraUI()
        }
    }
}

@Composable
fun CalculadoraUI(){
    Column (
        modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "0",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            ButtonCalculadora( "C")
            ButtonCalculadora( "+/-")
            ButtonCalculadora( "%")
            ButtonCalculadora( "/")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            ButtonCalculadora( "7")
            ButtonCalculadora( "8")
            ButtonCalculadora( "9")
            ButtonCalculadora( "X")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            ButtonCalculadora( "4")
            ButtonCalculadora( "5")
            ButtonCalculadora( "6")
            ButtonCalculadora( "-")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            ButtonCalculadora( "1")
            ButtonCalculadora( "2")
            ButtonCalculadora( "3")
            ButtonCalculadora( "+")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            ButtonCalculadora( "0")
            ButtonCalculadora( ".")
            ButtonCalculadora( "=")
        }

    }
}

@Composable
fun ButtonCalculadora(texto: String, modifier: Modifier = Modifier) {
    Button(
        onClick = { /* Sin lógica aún */ },
        modifier = modifier.padding(4.dp)
    ) {
        Text(text = texto, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculadoraTheme {
        CalculadoraUI()
    }
}