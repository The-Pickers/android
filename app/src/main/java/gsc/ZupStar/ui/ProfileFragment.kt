package gsc.ZupStar.sampledata

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import gsc.ZupStar.databinding.FragmentProfileBinding
import gsc.ZupStar.util.StatusBarUtil

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    lateinit var binding : FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        StatusBarUtil.updateStatusBarColor(requireActivity(), Color.WHITE)
        return binding.root
    }
}