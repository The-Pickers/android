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
    private var token : Int = -1

    companion object{
        var missionTitle = "River Cleanup"
        var comment = 100
        const val REQUEST_CAMERA_PERMISSION = 100
        val misionLogList = ArrayList<MissionData>()

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