package com.example.appvigis
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class splashScreenActivity : AppCompatActivity() {

    private val SPLASH_TIME: Long = 19000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        getDelegate().setLocalNightMode(
            AppCompatDelegate.MODE_NIGHT_NO);



    val deTextView: TextView = findViewById(R.id.text_By)
        val logoSvg: ImageView = findViewById<ImageView>(R.id.svgLogo)

        // Inicia la animación
        val animation1: Animation = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba)
        val animation2: Animation = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo)
        deTextView.setAnimation(animation2)
        logoSvg.setAnimation(animation1)

        // Verificar la conexión a Internet en segundo plano usando coroutines
        GlobalScope.launch(Dispatchers.Main) {
            if (isNetworkAvailable()) {
                // Si hay conexión a Internet, espera un poco antes de continuar la animación
                delay(2000) // Espera 2 segundos (ajusta según tus necesidades)
                startNextActivity()
            } else {
                // Si no hay conexión a Internet, muestra un mensaje de error
                runOnUiThread {
                    Toast.makeText(this@splashScreenActivity, "No hay conexión a Internet", Toast.LENGTH_SHORT).show()
                }
                // Puedes decidir si quieres cerrar la aplicación en este punto
                // finish()
            }
        }
    }

    private fun startNextActivity() {
        // Iniciar la siguiente actividad después de la verificación de Internet y la animación
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, SPLASH_TIME)
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}
