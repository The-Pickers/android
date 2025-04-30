package gsc.ZupStar.ui

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import gsc.ZupStar.data.MissionData
import gsc.ZupStar.databinding.ActivityMissionCompleteBinding
import gsc.ZupStar.util.DateUtils
import gsc.ZupStar.util.StatusBarUtil

@AndroidEntryPoint
class MissionCompleteActivity : AppCompatActivity() {
    lateinit var binding: ActivityMissionCompleteBinding
    lateinit var result : MissionData
    private val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMissionCompleteBinding.inflate(layoutInflater)
        StatusBarUtil.updateStatusBarColor(this, Color.WHITE)
        setContentView(binding.root)

        result = intent.getParcelableExtra<MissionData>("result")!!
        binding.tvComment.text = result.message
        binding.tvPoint.text = "+${result.score} pts"
        binding.tvTime.text = DateUtils.formatTime(result.takenTime)


        binding.btnComplete.setOnClickListener {
            finish()
        }
    }
}