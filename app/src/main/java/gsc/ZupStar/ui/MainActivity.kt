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
import gsc.ZupStar.data.MapData
import gsc.ZupStar.data.MissionData
import gsc.ZupStar.databinding.ActivityMainBinding
import java.time.LocalDateTime

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var spf : SharedPreferences

    companion object{
        var missionTitle = "River Cleanup"
        const val REQUEST_CAMERA_PERMISSION = 100
        val dummy = listOf<MissionData>(
            MissionData(
                index = 996,
                title = "Beach Cleanup",
                message = "Mission Data ",
                completed = true,
                carbonReduction = 0.5f,
                detectedWaste = 0,
                score = 10,
                takenTime = "",
                startTime = LocalDateTime.now().minusDays(60).toString(),
                location = 2
            ),
            MissionData(
                index = 999,
                title = "Beach Cleanup",
                message = "Mission Data ",
                completed = true,
                carbonReduction = 3.5f,
                detectedWaste = 0,
                score = 25,
                takenTime = "",
                startTime = LocalDateTime.now().minusDays(42).toString(),
                location = 7
            ),
            MissionData(
                index = 998,
                title = "Trash Collection",
                message = "Mission Data ",
                completed = true,
                carbonReduction = 4.0f,
                detectedWaste = 0,
                score = 45,
                takenTime = "",
                startTime = LocalDateTime.now().minusDays(25).toString(),
                location = 5
            ),
            MissionData(
                index = 997,
                title = "Environmental Cleanup",
                message = "Mission Data ",
                completed = true,
                carbonReduction = 3.7f,
                detectedWaste = 0,
                score = 10,
                takenTime = "",
                startTime = LocalDateTime.now().minusDays(12).toString(),
                location = 2
            ),
            MissionData(
                index = 995,
                title = "Beach Cleanup",
                message = "Mission Data ",
                completed = true,
                carbonReduction = 7.2f,
                detectedWaste = 0,
                score = 25,
                takenTime = "",
                startTime = LocalDateTime.now().minusDays(4).toString(),
                location = 7
            ),
        )
        val misionLogList = ArrayList<MissionData>(dummy)

        val dummyMap = listOf<MapData>(
            MapData(0, 0),
            MapData(1, 0),
            MapData(2, 6),
            MapData(3, 0),
            MapData(4, 0),
            MapData(5, 4),
            MapData(6, 3),
            MapData(7, 0),
            MapData(8, 0)
        )
        val mapDataList = ArrayList<MapData>(dummyMap)


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