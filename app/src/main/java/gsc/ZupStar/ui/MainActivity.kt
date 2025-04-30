package gsc.ZupStar.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import gsc.ZupStar.R
import gsc.ZupStar.data.MissionData
import gsc.ZupStar.databinding.ActivityMainBinding
import java.time.LocalDateTime

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var spf : SharedPreferences

    companion object{
        const val REQUEST_CAMERA_PERMISSION = 100
        val dummy = listOf<MissionData>(
            MissionData(
                index = 999,
                title = "Mission Title ",
                message = "Mission Data ",
                completed = true,
                carbonReduction = 0.5f,
                detectedWaste = 0,
                score = 25,
                takenTime = "",
                startTime = LocalDateTime.now().minusDays(17).toString(),
                location = 7
            ),
            MissionData(
                index = 998,
                title = "Mission Title ",
                message = "Mission Data ",
                completed = true,
                carbonReduction = 0.5f,
                detectedWaste = 0,
                score = 45,
                takenTime = "",
                startTime = LocalDateTime.now().minusDays(11).toString(),
                location = 5
            ),
            MissionData(
                index = 997,
                title = "Mission Title ",
                message = "Mission Data ",
                completed = true,
                carbonReduction = 0.5f,
                detectedWaste = 0,
                score = 10,
                takenTime = "",
                startTime = LocalDateTime.now().minusDays(4).toString(),
                location = 2
            )
        )
        val misionLogList = ArrayList<MissionData>(dummy)

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
            bottomNav.selectedItemId = targetId // ✅ 이것만으로 충분
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