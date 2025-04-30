package gsc.ZupStar.ui

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

    companion object{
        const val REQUEST_CAMERA_PERMISSION = 100
        val misionLogList = ArrayList<MissionData>()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val bottomNav : BottomNavigationView = binding.navBottom
        val navController = findNavController(R.id.nav_host_fragment)
        bottomNav.setupWithNavController(navController)
        initDummy()

        val targetId = intent.getIntExtra("targetFragmentId", -1)
        val currentId = navController.currentDestination?.id

        if (targetId != -1 && currentId != targetId) {
            navController.navigate(targetId)
        }

        onBackPressedDispatcher.addCallback(this){
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

    private fun initDummy () {
        for(i in 1..5){
            misionLogList.add(
                MissionData(
                    index = i,
                    title = "Mission Title ${i}",
                    message = "Mission Data ${i}",
                    completed = true,
                    carbonReduction = 0.5f,
                    detectedWaste = 0,
                    score = (i%4)*25,
                    takenTime = "",
                    startTime = LocalDateTime.now().toString(),
                    location = i%9
                )
            )
        }
    }

}