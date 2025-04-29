package gsc.ZupStar.ui.map

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import gsc.ZupStar.R
import gsc.ZupStar.databinding.BottomsheetMapLogBinding
import gsc.ZupStar.databinding.FragmentMapBinding
import gsc.ZupStar.util.LocationHelper
import gsc.ZupStar.util.StatusBarUtil

@AndroidEntryPoint
class MapFragment : Fragment() {
    lateinit var binding : FragmentMapBinding
    lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    lateinit var locationHelper: LocationHelper

    private val TAG = javaClass.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater,container,false)
        setBottomSheet()

        StatusBarUtil.updateStatusBarColor(requireActivity(), Color.WHITE)

        locationHelper = LocationHelper(requireActivity(), requireContext())

        locationHelper.checkLocationPermission {
            fetchLocation()
        }

        return binding.root
    }
    private fun initDummy (): ArrayList<String> {
        val dummy = ArrayList<String>()
        for(i in 1..5)
            dummy.add("dummy log ${i}")
        return dummy
    }

    private fun setBottomSheet(){
        // 바텀시트 바인딩 가져오기
        val bottomSheet = binding.root.findViewById<View>(R.id.bottom_sheet_map_log)
        val bottomSheetBinding = BottomsheetMapLogBinding.bind(bottomSheet)

        // 바텀시트 Behavior 적용
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.isHideable = false
        bottomSheetBehavior.skipCollapsed = false
        bottomSheetBehavior.peekHeight =  dpToPx(80)

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {}

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // 최대 높이 제한
                val maxHeight = dpToPx(420)
                if (bottomSheet.height > maxHeight) {
                    bottomSheet.layoutParams.height = maxHeight
                    bottomSheet.requestLayout()
                }
            }
        })

        // RecyclerView 설정
        val adapter = MissionLogRVAdapter(initDummy())
        bottomSheetBinding.rvMapLog.adapter = adapter
        bottomSheetBinding.rvMapLog.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    }




    private fun fetchLocation() {
        locationHelper.getCurrentLocation { location ->
            location?.let {
                val adminArea = locationHelper.getAdminArea(it)
                Log.d(TAG, location.toString())
                Log.d(TAG, "현재 도/특별시/광역시는: $adminArea")
            } ?: run {
                Log.d(TAG, "위치를 가져올 수 없습니다.")
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        locationHelper.handlePermissionResult(
            requestCode,
            grantResults,
            onPermissionGranted = { fetchLocation() },
            onPermissionDenied = {
                Toast.makeText(context, "위치 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
        )
    }

    fun dpToPx(dp: Int): Int {
        val scale = resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }
}