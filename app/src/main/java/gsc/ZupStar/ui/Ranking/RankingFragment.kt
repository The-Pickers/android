package gsc.ZupStar.ui.Ranking

import android.content.Context
import android.content.SharedPreferences
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
import gsc.ZupStar.data.RankListData.RankData
import gsc.ZupStar.databinding.FragmentRankingBinding
import gsc.ZupStar.util.StatusBarUtil

@AndroidEntryPoint
class RankingFragment : Fragment() {
    lateinit var binding : FragmentRankingBinding
    private var isTeam = false
    lateinit var spf : SharedPreferences
    private var myName = ""
    private var myScore = 0

    private val teamRankList: List<RankData> = listOf(
        RankData(1, "TrashBusters", 3980, "Trash pickup crew"),
        RankData(2, "CleanSeoul", 3720, "Seoul cleanup"),
        RankData(3, "RiverSweep", 3510, "River cleaning"),
        RankData(4, "GreenBaggers", 3260, "Green bag action"),
        RankData(5, "EcoCollectors", 2950, "Eco trash team"),
        RankData(6, "ZeroLitterZone", 2780, "Litter-free zone"),
        RankData(7, "BinBrigade", 2430, "Bin patrol")
    )
    private val individualRankList: List<RankData> = listOf(
        RankData(1, "Minji Park", 1470, "TrashBusters"),
        RankData(2, "Jisoo Kim", 1390, "RiverSweep"),
        RankData(3, "Hyunwoo Lee", 1280, "CleanSeoul"),
        RankData(4, "Seyoung Choi", 1130, "TrashBusters"),
        RankData(5, "Jiwon Han", 980, "ZeroLitterZone"),
        RankData(6, "Taeyang Jung", 880, "BinBrigade"),
        RankData(7, "Yuna Seo", 730, "EcoCollectors")
    )

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
        StatusBarUtil.updateStatusBarColor(requireActivity(), ContextCompat.getColor(requireContext(), R.color.rank))
        spf = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        myScore = spf.getInt("score", 0)
        myName = spf.getString("name","")!!

        val adapter = RankingRVAdapter(individualRankList)
        binding.rvRank.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvRank.adapter = adapter
        initMyInfo()
        binding.tvIndividual.setOnClickListener {
            if (isTeam) {
                isTeam = !isTeam
                setButton(binding.tvTeam, MODE_UNSELECT)
                setButton(binding.tvIndividual, MODE_SELECT)
                binding.rvRank.adapter=  RankingRVAdapter(individualRankList)
                initMyInfo()
            }
        }
        binding.tvTeam.setOnClickListener {
            if (!isTeam){
                isTeam = !isTeam
                setButton(binding.tvTeam, MODE_SELECT)
                setButton(binding.tvIndividual, MODE_UNSELECT)
                binding.rvRank.adapter=  RankingRVAdapter(teamRankList)
                initMyInfo()
            }
        }

        return binding.root
    }

    private fun initMyInfo(){
        val myinfo = if (isTeam) RankData(9, "StreetCleaners", 1580, "Street team")
                    else RankData(24, myName, myScore, "StreetCleaners")
        binding.tvName.text = myinfo.name
        binding.tvInfo.text = myinfo.info
        binding.tvIdx.text = myinfo.idx.toString()
        binding.tvPoint.text = myinfo.rank.toString()+"pts"
    }

    private fun setButton(view: TextView, status : Int){
        val background = listOf(R.color.unselected_rank, R.color.selected_rank)
        val text = listOf(R.color.text_sub, R.color.white)
        view.setTextColor(requireContext().getColor(text[status]))
        ViewCompat.setBackgroundTintList(view, ContextCompat.getColorStateList(requireContext(), background[status])
        )
    }

}