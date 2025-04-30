package gsc.ZupStar.ui.map

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import gsc.ZupStar.R
import gsc.ZupStar.data.MapData
import gsc.ZupStar.databinding.BottomsheetMapLogBinding
import gsc.ZupStar.databinding.FragmentMapBinding
import gsc.ZupStar.ui.MainActivity.Companion.misionLogList
import gsc.ZupStar.ui.MissionCompleteActivity.Companion.complete_mission_loc
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

        for (data in mapDataList) mapViews[data.location].setColorFilter(ContextCompat.getColor(requireContext(), getColor(data)), PorterDuff.Mode.SRC_IN )

        locationHelper = LocationHelper(requireActivity(), requireContext())
        locationHelper.checkLocationPermission {
            fetchLocation()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (complete_mission_loc >=0 ){
            val loc = complete_mission_loc
            complete_mission_loc = -1
            changeMap(loc)
        }

    }

    private fun changeMap(loc: Int) {
        val curColor = getColor(mapDataList[loc])
        mapDataList[loc].mission++
        val newColor = getColor(mapDataList[loc])
        if (curColor != newColor)
            animateColorFilter(mapViews[loc], curColor, newColor)
    }

    private fun initDummy () {
        for(i in 0..mapViews.size-1){
            if (i%3 == 0) mapDataList.add(MapData(i , 0))
            else  mapDataList.add(MapData(i , (i+4) % 7))
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

    private fun getColor(info : MapData) : Int{
        val colorList = listOf(R.color.map_0,R.color.map_1, R.color.map_2, R.color.map_3, R.color.map_4)
        val color = when(info.mission){
            0 ->colorList[0]
            1 ->  colorList[1]
            2,3 ->colorList[2]
            4,5 -> colorList[3]
            else -> colorList[4]
        }
        return color
    }
    private fun animateColorFilter(view: ImageView, @ColorRes fromColorRes: Int, @ColorRes toColorRes: Int) {
        Log.d(TAG,"color Change")
        val fromColor = ContextCompat.getColor(view.context, fromColorRes)
        val toColor = ContextCompat.getColor(view.context, toColorRes)

        val colorAnimator = ValueAnimator.ofObject(ArgbEvaluator(), fromColor, toColor)
        colorAnimator.duration = 700L  // 애니메이션 지속 시간 (ms)

        colorAnimator.addUpdateListener { animator ->
            val animatedColor = animator.animatedValue as Int
            view.setColorFilter(animatedColor, PorterDuff.Mode.SRC_IN)
        }

        colorAnimator.start()
    }

    fun dpToPx(dp: Int): Int {
        val scale = resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }
}