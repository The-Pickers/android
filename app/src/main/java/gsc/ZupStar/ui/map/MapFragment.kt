package gsc.ZupStar.ui.map

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import gsc.ZupStar.R
import gsc.ZupStar.data.MapData
import gsc.ZupStar.data.MissionData
import gsc.ZupStar.databinding.BottomsheetMapLogBinding
import gsc.ZupStar.databinding.FragmentMapBinding
import gsc.ZupStar.util.DateUtils
import gsc.ZupStar.util.LocationHelper
import gsc.ZupStar.util.StatusBarUtil
import java.time.LocalDateTime

@AndroidEntryPoint
class MapFragment : Fragment() {
    lateinit var binding : FragmentMapBinding
    lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    lateinit var locationHelper: LocationHelper
    lateinit var mapViews: List<ImageView>

    private val TAG = javaClass.simpleName
    private val misionLogList = ArrayList<MissionData>()
    private val mapDataList = ArrayList<MapData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater,container,false)
        StatusBarUtil.updateStatusBarColor(requireActivity(), Color.WHITE)
        setBottomSheet()
        mapViews = listOf( binding.ivMap1,binding.ivMap2,binding.ivMap3,binding.ivMap4,binding.ivMap5,binding.ivMap6,binding.ivMap7, binding.ivMap8, binding.ivMap9)
        initDummy()

        for (data in mapDataList) mapColoring(data)

        locationHelper = LocationHelper(requireActivity(), requireContext())
        locationHelper.checkLocationPermission {
            fetchLocation()
        }

        return binding.root
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
                    score = i*125,
                    takenTime = "",
                    startTime = LocalDateTime.now().toString(),
                    location = i%9
                )
            )
        }

        for(i in 0..mapViews.size-1){
            if (i%3 == 1) mapDataList.add(MapData(i , 0))
            else  mapDataList.add(MapData(i , (i+3) % 6))
        }
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
        val adapter = MissionLogRVAdapter(misionLogList)
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

    private fun mapColoring(info : MapData){
        val colorList = listOf(R.color.white,R.color.purple_200, R.color.purple_500, R.color.purple_700, R.color.main_color)
        val color = when(info.mission){
            0 ->colorList[0]
            1 ->  colorList[1]
            2,3 ->colorList[2]
            4,5 -> colorList[3]
            else -> colorList[4]
        }
        mapViews[info.location].setColorFilter(ContextCompat.getColor(requireContext(), color), PorterDuff.Mode.SRC_IN )
    }

    fun dpToPx(dp: Int): Int {
        val scale = resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }
}