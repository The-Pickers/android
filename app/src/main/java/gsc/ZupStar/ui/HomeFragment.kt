package gsc.ZupStar.sampledata

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import android.Manifest
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gsc.ZupStar.data.VideoData
import gsc.ZupStar.databinding.FragmentHomeBinding
import gsc.ZupStar.ui.MissionCompleteActivity
import gsc.ZupStar.ui.MissionViewModel
import java.time.LocalDateTime

@AndroidEntryPoint
class HomeFragment : Fragment() {
    lateinit var binding : FragmentHomeBinding
    private val TAG = javaClass.simpleName
    private val viewModel : MissionViewModel by viewModels()
    private var missionIdx : Int = 0
    private var location : String = "location"
    private var uri : Uri? = null
    companion object{
        const val REQUEST_CAMERA_PERMISSION = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        setUpObservers()
        binding.btnStart.setOnClickListener {
            checkCameraPermissionAndLaunch()
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG,"fragment resume ${missionIdx}")
        binding.btnStart.text = if (missionIdx != 0) "Complete !" else "Get Start"
    }

    private fun setUpObservers(){
        viewModel.mission.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            missionIdx = 0
            Log.d(TAG,"fragment completed ${missionIdx}")
            val intent = Intent(requireActivity(),MissionCompleteActivity::class.java)
            intent.putExtra("video_uri", uri.toString())
            intent.putExtra("result",it)
            startActivity(intent)
        } )

        viewModel.missionIdx.observe(viewLifecycleOwner, Observer {
            if(it == null) return@Observer
            missionIdx = it
            Log.d(TAG,"fragment start ${missionIdx}")
        })
    }

    private val videoCaptureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val videoUri: Uri? = result.data?.data
            if (videoUri != null) {
                Log.d(TAG, "Captured video Uri: $videoUri")
                val video  = VideoData(videoUri,location, LocalDateTime.now().toString())
                if (missionIdx != 0){
                    uri = videoUri
                    viewModel.completeMission(0,video)
                }
                else{
                    viewModel.startMission(video)
                }

            } else {
                Log.d(TAG, "No video Uri received.")
            }
        } else {
            Log.d(TAG, "Video capture cancelled or failed.")
        }
    }

    fun dispatchTakeVideoIntent() {
        val takeVideoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        videoCaptureLauncher.launch(takeVideoIntent)
    }

    private fun checkCameraPermissionAndLaunch() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // 권한 이미 허용됨 → 바로 카메라 실행
            dispatchTakeVideoIntent()
        } else {
            // 권한 요청
            requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                dispatchTakeVideoIntent()
            } else {
                Log.d(TAG, "Camera permission denied")
            }
        }


}
}