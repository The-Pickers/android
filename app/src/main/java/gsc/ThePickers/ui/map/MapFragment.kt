package gsc.ThePickers.ui.map

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.content.SharedPreferences
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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import gsc.ThePickers.R
import gsc.ThePickers.data.AccountData
import gsc.ThePickers.data.MapData
import gsc.ThePickers.databinding.BottomsheetMapLogBinding
import gsc.ThePickers.databinding.FragmentMapBinding
import gsc.ThePickers.ui.MissionViewModel
import gsc.ThePickers.util.LocationHelper
import gsc.ThePickers.util.StatusBarUtil
import kotlin.getValue

@AndroidEntryPoint
class MapFragment : Fragment() {
    lateinit var binding : FragmentMapBinding
    lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    lateinit var locationHelper: LocationHelper
    lateinit var mapViews: List<ImageView>
    lateinit var spf : SharedPreferences
    private val adapter = MissionLogRVAdapter()
    private val missionViewModel : MissionViewModel by viewModels()
    private val  mapViewModel : MapViewModel by viewModels()
    private val TAG = javaClass.simpleName
    private val gson = Gson()
    private var mapList : List<MapData> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater,container,false)
        spf = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        mapViews = listOf( binding.ivMap1,binding.ivMap2,binding.ivMap3,binding.ivMap4,binding.ivMap5,binding.ivMap6,binding.ivMap7, binding.ivMap8, binding.ivMap9)
        StatusBarUtil.updateStatusBarColor(requireActivity(), ContextCompat.getColor(requireContext(), R.color.map))
        val json = spf.getString("mapData",null)
        if (json != null){
            val type = object : TypeToken<List<MapData>>() {}.type
            mapList = gson.fromJson(json, type)
            for (data in mapList)
                mapViews[data.location-1].setColorFilter(ContextCompat.getColor(requireContext(), getColor(data)), PorterDuff.Mode.SRC_IN )
        }
        setBottomSheet()
        setUpObservers()



        locationHelper = LocationHelper(requireActivity(), requireContext())
        locationHelper.checkLocationPermission {
            fetchLocation()
        }
        Log.d(TAG,"create")
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        missionViewModel.getMissionList()
        mapViewModel.getMapList()
    }

    private fun setUpObservers(){
        missionViewModel.missionList.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            adapter.addData(it)
        } )

        mapViewModel.mapList.observe (viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            if(mapList.isNotEmpty()){
                for (data in it){
                    val loc = data.location-1
                    val curColor = getColor(mapList[loc])
                    val newColor = getColor(it[loc])
                    if (curColor != newColor)
                        animateColorFilter(mapViews[loc], curColor, newColor)
                }
            }else
                for (data in it) mapViews[data.location-1].setColorFilter(ContextCompat.getColor(requireContext(), getColor(data)), PorterDuff.Mode.SRC_IN )
        } )
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
        colorAnimator.duration = 1000L  // 애니메이션 지속 시간 (ms)

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