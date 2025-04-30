package gsc.ZupStar.ui

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import gsc.ZupStar.R
import gsc.ZupStar.data.MissionData
import gsc.ZupStar.databinding.ActivityMissionCompleteBinding
import gsc.ZupStar.util.DateUtils
import gsc.ZupStar.util.LocationUtil
import gsc.ZupStar.util.StatusBarUtil

@AndroidEntryPoint
class MissionCompleteActivity : AppCompatActivity() {
    lateinit var binding: ActivityMissionCompleteBinding
    lateinit var result : MissionData
    private val TAG = javaClass.simpleName

    companion object{
        var complete_mission_loc : Int = -1;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMissionCompleteBinding.inflate(layoutInflater)
        StatusBarUtil.updateStatusBarColor(this, Color.WHITE)
        setContentView(binding.root)


        result = intent.getParcelableExtra<MissionData>("result")!!
        binding.tvComment.text = result.message
        binding.tvPoint.text = "+${result.score} pts"
        binding.tvTime.text = DateUtils.formatTime(result.takenTime)
        binding.tvPlace.text = LocationUtil.toEnglishByIndex(result.location)

        complete_mission_loc = result.location-1

        // 완료시 맵 뷰로 바로 이동
        binding.btnComplete.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("targetFragmentId", R.id.navigation_map)  // 이동할 NavGraph 목적지 ID
                //addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
             }
            startActivity(intent)
            finish()
        }
    }
}