package gsc.ThePickers.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import gsc.ThePickers.R
import gsc.ThePickers.data.MapData
import gsc.ThePickers.data.MissionData
import gsc.ThePickers.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var spf : SharedPreferences

    companion object{
        const val REQUEST_CAMERA_PERMISSION = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNav : BottomNavigationView = binding.navBottom
        val navController = findNavController(R.id.nav_host_fragment)
        bottomNav.setupWithNavController(navController)

        val targetId = intent.getIntExtra("targetFragmentId", -1)
        val currentId = navController.currentDestination?.id

        if (targetId != -1 && currentId != targetId) {
            bottomNav.selectedItemId = targetId
        }

        onBackPressedDispatcher.addCallback(this) {
            if (navController.currentDestination?.id != R.id.navigation_home) {
                navController.popBackStack(R.id.navigation_home, false)
                bottomNav.selectedItemId = R.id.navigation_home
            } else {
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onDestroy() {
        super.onDestroy()
    }


}