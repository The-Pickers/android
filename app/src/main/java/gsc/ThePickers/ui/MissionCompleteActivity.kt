package gsc.ThePickers.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import gsc.ThePickers.R
import gsc.ThePickers.data.MissionData
import gsc.ThePickers.databinding.ActivityMissionCompleteBinding
import gsc.ThePickers.util.DateUtils
import gsc.ThePickers.util.LocationUtil
import gsc.ThePickers.util.StatusBarUtil

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
        StatusBarUtil.updateStatusBarColor(this, ContextCompat.getColor(this, R.color.mission))
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