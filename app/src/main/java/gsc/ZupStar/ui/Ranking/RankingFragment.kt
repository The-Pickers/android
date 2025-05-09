package gsc.ZupStar.ui.Ranking

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import gsc.ZupStar.R
import gsc.ZupStar.data.RankListData
import gsc.ZupStar.databinding.FragmentRankingBinding
import gsc.ZupStar.util.StatusBarUtil

@AndroidEntryPoint
class RankingFragment : Fragment() {
    lateinit var binding : FragmentRankingBinding
    private var isTeam = false


    companion object{
        private const val MODE_UNSELECT = 0
        private const val MODE_SELECT = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRankingBinding.inflate(inflater,container,false)
        StatusBarUtil.updateStatusBarColor(requireActivity(), ContextCompat.getColor(requireContext(), R.color.sub_color))
        val adapter = RankingRVAdapter(initDummy())
        binding.rvRank.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvRank.adapter = adapter

        binding.tvIndividual.setOnClickListener {
            if (isTeam) {
                isTeam = !isTeam
                setButton(binding.tvTeam, MODE_UNSELECT)
                setButton(binding.tvIndividual, MODE_SELECT)
                binding.rvRank.adapter=  RankingRVAdapter(initDummy())
            }
        }
        binding.tvTeam.setOnClickListener {
            if (!isTeam){
                isTeam = !isTeam
                setButton(binding.tvTeam, MODE_SELECT)
                setButton(binding.tvIndividual, MODE_UNSELECT)
                binding.rvRank.adapter=  RankingRVAdapter(initDummy())
            }
        }

        return binding.root
    }

    private fun setButton(view: TextView, status : Int){
        val background = listOf(R.color.unselected_rank, R.color.main_color)
        val text = listOf(R.color.text_sub, R.color.white)
        view.setTextColor(requireContext().getColor(text[status]))
        ViewCompat.setBackgroundTintList(view, ContextCompat.getColorStateList(requireContext(), background[status])
        )
    }

    private fun initDummy(): List<RankListData.RankData> {
        val dummy = ArrayList<RankListData.RankData>()
        val name = if (isTeam) "Team" else "name"
        for (i in 1.. 7)
            dummy.add(RankListData.RankData(i,name+i.toString(), i, "info"))
        return dummy
    }
}