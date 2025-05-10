package gsc.ThePickers.sampledata

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
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
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.hardware.Camera
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import gsc.ThePickers.R
import gsc.ThePickers.data.AccountData
import gsc.ThePickers.databinding.FragmentHomeBinding
import gsc.ThePickers.ui.HomeViewModel
import gsc.ThePickers.ui.MainActivity.Companion.REQUEST_CAMERA_PERMISSION
import gsc.ThePickers.ui.MainActivity.Companion.misionLogList
import gsc.ThePickers.ui.MissionCompleteActivity
import gsc.ThePickers.ui.MissionViewModel
import gsc.ThePickers.util.LocationHelper
import gsc.ThePickers.util.StatusBarUtil
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {
    lateinit var binding : FragmentHomeBinding
    lateinit var locationHelper: LocationHelper
    lateinit var spf : SharedPreferences
    private val TAG = javaClass.simpleName
    private val missionViewModel : MissionViewModel by viewModels()
    private val homeViewModel : HomeViewModel by viewModels()
    private var missionIdx : Int = 0
    private var curlocation : String = "location"
    private var job: Job? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        setUpObservers()
        spf = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        StatusBarUtil.updateStatusBarColor(requireActivity(), ContextCompat.getColor(requireContext(), R.color.home))
        locationHelper = LocationHelper(requireActivity(), requireContext())
        locationHelper.checkLocationPermission {
            fetchLocation()
        }

        binding.btnStart.setOnClickListener {
            if (missionIdx == 0) {
                missionViewModel.startMission()
                binding.btnStart.text =  "Complete !"
            }
            else  checkCameraPermissionAndLaunch()
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 주기적으로 정령 멘트 교체
        job = viewLifecycleOwner.lifecycleScope.launch {
            while (isActive) {
               // homeViewModel.getComment()
                delay(10_000)  // 1분 대기
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.btnStart.text = if (missionIdx != 0) "Complete !" else "Get Start"
        homeViewModel.getAccount()
        homeViewModel.getComment()

    }

    override fun onDestroyView() {
        super.onDestroyView()
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
                dispatchTakePhotoIntent()
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
            Log.d(TAG,"fragment completed ${missionIdx}")
            missionIdx = 0
            misionLogList.add(it)
            val intent = Intent(requireActivity(),MissionCompleteActivity::class.java)
            intent.putExtra("result",it)
            startActivity(intent)
        } )

        missionViewModel.missionIdx.observe(viewLifecycleOwner, Observer {
            if(it == null) return@Observer
            missionIdx = it
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
        spf.edit().putInt("score", data.totalScore)
        binding.tvMission.text = data.missionCount.toString()
        binding.tvPoint.text ='+'+data.totalScore.toString()
        binding.tvCo2.text = data.carbonReduction.toString()
    }



    // 카메라런쳐
    // 카메라 런처 (사진 촬영용으로 수정됨)
    private val photoCaptureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val bitmap = result.data?.extras?.get("data") as? Bitmap
            if (bitmap != null) {
                Log.d(TAG, "Captured image bitmap: $bitmap")
                if (missionIdx != 0) {
                    missionViewModel.completeMission(bitmap, missionIdx, curlocation )
                }
            } else {
                Log.d(TAG, "No bitmap image received.")
            }
        } else {
            Log.d(TAG, "Image capture cancelled or failed.")
        }
    }

    // 카메라 인텐트 시작
    private fun dispatchTakePhotoIntent() {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            // 후면 카메라 요청 (제조사별 대응)
            takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING", Camera.CameraInfo.CAMERA_FACING_BACK)
            takePictureIntent.putExtra("android.intent.extras.LENS_FACING_FRONT", false)
            takePictureIntent.putExtra("android.intent.extra.USE_FRONT_CAMERA", false)
            photoCaptureLauncher.launch(takePictureIntent)
    }

    private fun checkCameraPermissionAndLaunch() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // 권한 이미 허용됨 → 바로 카메라 실행
            dispatchTakePhotoIntent()
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