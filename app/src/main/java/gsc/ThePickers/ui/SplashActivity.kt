package gsc.ThePickers.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import gsc.ThePickers.R
import gsc.ThePickers.databinding.ActivitySplashBinding
import gsc.ThePickers.ui.Login.ActivityLogin
import gsc.ThePickers.util.StatusBarUtil

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val TAG = this.javaClass.simpleName
    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        StatusBarUtil.updateStatusBarColor(this, ContextCompat.getColor(this, R.color.splash))
        setContentView(binding.root)

        val spf = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val token = spf.getInt("token",-1)

        val intent = if(token<0) Intent(this,ActivityLogin::class.java)else Intent(this, MainActivity::class.java)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(intent)
            finish()
        }, 600) // 0.6초동안 대기
    }

}