package gsc.ZupStar.ui.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import gsc.ZupStar.R
import gsc.ZupStar.databinding.BottomsheetMapLogBinding
import gsc.ZupStar.databinding.FragmentMapBinding

class MapFragment : Fragment() {
    lateinit var binding : FragmentMapBinding
    lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater,container,false)
        setBottomSheet()

        return binding.root
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
    fun dpToPx(dp: Int): Int {
        val scale = resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    private fun initDummy (): ArrayList<String> {
        val dummy = ArrayList<String>()
        for(i in 1..5)
            dummy.add("dummy log ${i}")
        return dummy
    }
}