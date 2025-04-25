package gsc.ZupSar.sampledata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import gsc.ZupStar.databinding.FragmentHomeBinding
import gsc.ZupStar.databinding.FragmentRankingBinding

class RankingFragment : Fragment() {
    lateinit var binding : FragmentRankingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRankingBinding.inflate(inflater,container,false)

        return binding.root
    }
}