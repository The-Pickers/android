package gsc.ZupStar.ui

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import gsc.ZupStar.data.MissionData
import gsc.ZupStar.databinding.ActivityMissionCompleteBinding
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
        binding.tvTest.text = result.message

        // Intent로 받은 videoUri 꺼내기
        val videoUriString = intent.getStringExtra("video_uri")
        val videoUri = videoUriString?.let { Uri.parse(it) }

        if (videoUri != null) {
            binding.videoView.setVideoURI(videoUri)
            binding.videoView.setOnPreparedListener { mediaPlayer ->
                mediaPlayer.isLooping = true // 계속 반복 재생하고 싶으면 추가
            }
            binding.videoView.start() // 자동 재생 시작
        }

        binding.btnComplete.setOnClickListener {
            finish()
        }
    }
}