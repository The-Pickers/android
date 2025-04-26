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
import gsc.ZupStar.R
import gsc.ZupStar.databinding.FragmentHomeBinding
import gsc.ZupStar.ui.MissionCompleteActivity


class HomeFragment : Fragment() {
    lateinit var binding : FragmentHomeBinding

    private val TAG = javaClass.simpleName
    private var isWorking : Boolean = false
    companion object{
        const val REQUEST_CAMERA_PERMISSION = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)

        binding.btnStart.setOnClickListener {
            checkCameraPermissionAndLaunch()
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.btnStart.text = if (isWorking) "Complete !" else "Get Start"
    }
    private val videoCaptureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val videoUri: Uri? = result.data?.data
            if (videoUri != null) {
                Log.d(TAG, "Captured video Uri: $videoUri")
                if (isWorking){
                    val intent = Intent(requireActivity(),MissionCompleteActivity::class.java)
                    intent.putExtra("video_uri", videoUri.toString())
                    startActivity(intent)
                }
                else{
                   // binding.btnStart.text="Complete !"
                }
                isWorking = !isWorking

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