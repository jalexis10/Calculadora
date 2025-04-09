package com.example.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculadora.ui.theme.CalculadoraTheme




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraTheme {
                CalculadoraUI()
            }
        }
    }
}

@Composable
fun CalculadoraUI() {
    var resultado by remember { mutableStateOf("0") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = resultado,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Primera fila
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            ButtonCalculadora("C") { resultado = "0" }
            ButtonCalculadora("+/-") {
                if (resultado != "0") {
                    resultado = if (resultado.startsWith("-")) resultado.drop(1) else "-$resultado"
                }
            }
            ButtonCalculadora("%") {
                try {
                    resultado = (resultado.toDouble() / 100).toString()
                } catch (e: Exception) {
                    resultado = "Error"
                }
            }
            ButtonCalculadora("/") { resultado += "/" }
        }

        // Fila 2
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            ButtonCalculadora("7") { resultado = if (resultado == "0") "7" else resultado + "7" }
            ButtonCalculadora("8") { resultado = if (resultado == "0") "8" else resultado + "8" }
            ButtonCalculadora("9") { resultado = if (resultado == "0") "9" else resultado + "9" }
            ButtonCalculadora("X") { resultado += "X" }
        }

        // Fila 3
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            ButtonCalculadora("4") { resultado = if (resultado == "0") "4" else resultado + "4" }
            ButtonCalculadora("5") { resultado = if (resultado == "0") "5" else resultado + "5" }
            ButtonCalculadora("6") { resultado = if (resultado == "0") "6" else resultado + "6" }
            ButtonCalculadora("-") { resultado += "-" }
        }

        // Fila 4
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            ButtonCalculadora("1") { resultado = if (resultado == "0") "1" else resultado + "1" }
            ButtonCalculadora("2") { resultado = if (resultado == "0") "2" else resultado + "2" }
            ButtonCalculadora("3") { resultado = if (resultado == "0") "3" else resultado + "3" }
            ButtonCalculadora("+") { resultado += "+" }
        }

        // Fila final
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            ButtonCalculadora("0") { resultado = if (resultado == "0") "0" else resultado + "0" }
            ButtonCalculadora(".") { resultado += "." }
            ButtonCalculadora("=") {
                resultado = evaluarExpresion(resultado)
            }
        }
        }
    }


@Composable
fun RowScope.ButtonCalculadora(texto: String, onClick: () -> Unit = {}) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .weight(1f)
            .padding(4.dp)
            .height(64.dp),
    ) {
        Text(text = texto, style = MaterialTheme.typography.headlineMedium)
    }
}
fun evaluarExpresion(expresion: String): String {
    return try {
        val sanitized = expresion.replace("X", "*")
        val result = object : Any() {
            var pos = -1
            var ch: Int = 0

            fun nextChar() {
                ch = if (++pos < sanitized.length) sanitized[pos].code else -1
            }

            fun eat(charToEat: Int): Boolean {
                while (ch == ' '.code) nextChar()
                if (ch == charToEat) {
                    nextChar()
                    return true
                }
                return false
            }

            fun parse(): Double {
                nextChar()
                val x = parseExpression()
                if (pos < sanitized.length) throw RuntimeException("Caracter inesperado: " + ch.toChar())
                return x
            }

            fun parseExpression(): Double {
                var x = parseTerm()
                while (true) {
                    when {
                        eat('+'.code) -> x += parseTerm()
                        eat('-'.code) -> x -= parseTerm()
                        else -> return x
                    }
                }
            }

            fun parseTerm(): Double {
                var x = parseFactor()
                while (true) {
                    when {
                        eat('*'.code) -> x *= parseFactor()
                        eat('/'.code) -> x /= parseFactor()
                        else -> return x
                    }
                }
            }

            fun parseFactor(): Double {
                if (eat('+'.code)) return parseFactor()
                if (eat('-'.code)) return -parseFactor()

                var x: Double
                val startPos = pos
                if (eat('('.code)) {
                    x = parseExpression()
                    eat(')'.code)
                } else if (ch in '0'.code..'9'.code || ch == '.'.code) {
                    while (ch in '0'.code..'9'.code || ch == '.'.code) nextChar()
                    x = sanitized.substring(startPos, pos).toDouble()
                } else {
                    throw RuntimeException("Carácter inesperado: " + ch.toChar())
                }

                return x
            }
        }.parse()
        result.toString()
    } catch (e: Exception) {
        "Error"
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculadoraTheme {
        CalculadoraUI()
    }
}
