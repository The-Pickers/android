package gsc.ThePickers.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import gsc.ThePickers.R
import gsc.ThePickers.databinding.ActivitySplashBinding
import gsc.ThePickers.ui.Login.ActivityLogin
import gsc.ThePickers.ui.home.HomeViewModel
import gsc.ThePickers.ui.map.MapViewModel
import gsc.ThePickers.util.StatusBarUtil
import kotlin.getValue

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val TAG = this.javaClass.simpleName
    lateinit var binding: ActivitySplashBinding
    private val homeViewModel : HomeViewModel by viewModels()
    private val mapViewModel : MapViewModel by viewModels()
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setUpObservers()
        StatusBarUtil.updateStatusBarColor(this, ContextCompat.getColor(this, R.color.splash))
        homeViewModel.getAccount()
        mapViewModel.getMapList()
        homeViewModel.getComment()
        setContentView(binding.root)

        val spf = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val token = spf.getInt("token",-1)
        Log.d(TAG,"token ${token}")

        val intent = if(token<0) Intent(this,ActivityLogin::class.java)else Intent(this, MainActivity::class.java)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(intent)
        }, 700) // 0.6초동안 대기
    }
    private fun setUpObservers(){
        val spf= getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        mapViewModel.mapList.observe (this, Observer {
            if (it == null) return@Observer
            val json = gson.toJson(it)
            spf.edit().putString("mapData",json).apply()
        })
        homeViewModel.account.observe(this, Observer {
            if (it == null) return@Observer
            val json = gson.toJson(it)
            spf.edit().putString("account",json).apply()
        })
        homeViewModel.comment.observe(this, Observer {
            if (it == null) return@Observer
            spf.edit().putString("comment",it).apply()
        })
    }

}