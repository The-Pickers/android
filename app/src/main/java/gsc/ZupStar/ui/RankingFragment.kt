package gsc.ZupStar.sampledata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import gsc.ZupStar.databinding.FragmentRankingBinding
import gsc.ZupStar.ui.RankingRVAdapter

@AndroidEntryPoint
class RankingFragment : Fragment() {
    lateinit var binding : FragmentRankingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRankingBinding.inflate(inflater,container,false)

        val adapter = RankingRVAdapter(initDummy())
        binding.rvRank.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvRank.adapter = adapter

        return binding.root
    }

    private fun initDummy(): List<String> {
        val dummy = ArrayList<String>()
        for (i in 1.. 10)
            dummy.add("TEAM Dummy ${i}")
        return dummy
    }
}