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
import android.graphics.Color
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import gsc.ZupStar.data.AccountData
import gsc.ZupStar.data.VideoData
import gsc.ZupStar.databinding.FragmentHomeBinding
import gsc.ZupStar.ui.HomeViewModel
import gsc.ZupStar.ui.MissionCompleteActivity
import gsc.ZupStar.ui.MissionViewModel
import gsc.ZupStar.util.LocationHelper
import gsc.ZupStar.util.StatusBarUtil
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@AndroidEntryPoint
class HomeFragment : Fragment() {
    lateinit var binding : FragmentHomeBinding
    lateinit var locationHelper: LocationHelper
    private val TAG = javaClass.simpleName
    private val missionViewModel : MissionViewModel by viewModels()
    private val homeViewModel : HomeViewModel by viewModels()
    private var missionIdx : Int = 0
    private var curlocation : String = "location"
    private var uri : Uri? = null
    private var job: Job? = null

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
        StatusBarUtil.updateStatusBarColor(requireActivity(),Color.BLACK)
        locationHelper = LocationHelper(requireActivity(), requireContext())

        locationHelper.checkLocationPermission {
            fetchLocation()
        }

        binding.btnStart.setOnClickListener {
            checkCameraPermissionAndLaunch()
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.btnStart.text = if (missionIdx != 0) "Complete !" else "Get Start"
        homeViewModel.getAccount()

        // 주기적으로 정령 멘트 교체
        job = viewLifecycleOwner.lifecycleScope.launch {
            while (isActive) {
                homeViewModel.getComment()
                delay(10_000)  // 1분 대기
            }
        }
    }

    override fun onPause() {
        super.onPause()
        job?.cancel()
    }

    // 권한 받아오기
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
        locationHelper.handlePermissionResult(
            requestCode,
            grantResults,
            onPermissionGranted = { fetchLocation() },
            onPermissionDenied = {
                Toast.makeText(context, "위치 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
        )
    }




    private fun setUpObservers(){
        missionViewModel.mission.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            missionIdx = 0
            Log.d(TAG,"fragment completed ${missionIdx}")
            val intent = Intent(requireActivity(),MissionCompleteActivity::class.java)
            intent.putExtra("video_uri", uri.toString())
            intent.putExtra("result",it)
            startActivity(intent)
        } )

        missionViewModel.missionIdx.observe(viewLifecycleOwner, Observer {
            if(it == null) return@Observer
            missionIdx = it
            Log.d(TAG,"fragment start ${missionIdx}")
        })

        homeViewModel.comment.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            binding.tvComment.text = it
        })

        homeViewModel.account.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            setAccountInfo(it)
        })
    }

    private fun setAccountInfo(data: AccountData) {
        binding.tvMission.text = data.missionCount.toString()
        binding.tvPoint.text ='+'+data.totalScore.toString()
        binding.tvCo2.text = data.carbonReduction.toString()
    }

    // 카메라런쳐
    private val videoCaptureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val videoUri: Uri? = result.data?.data
            if (videoUri != null) {
                Log.d(TAG, "Captured video Uri: $videoUri")
                val video  = VideoData(videoUri,curlocation, LocalDateTime.now().toString())
                if (missionIdx != 0){
                    uri = videoUri
                    missionViewModel.completeMission(video)
                }
                else{
                    missionViewModel.startMission(video)
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

    // 위치 정보 받아오기
    private fun fetchLocation() {
        locationHelper.getCurrentLocation { location ->
            location?.let {
                val adminArea = locationHelper.getAdminArea(it)
                curlocation = adminArea!!
                Log.d(TAG, location.toString())
                Log.d(TAG, "현재 도/특별시/광역시는: $adminArea")
            } ?: run {
                Log.d(TAG, "위치를 가져올 수 없습니다.")
            }
        }
    }

}