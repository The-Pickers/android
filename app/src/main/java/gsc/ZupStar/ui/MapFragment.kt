package gsc.ZupStar.sampledata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import gsc.ZupStar.databinding.FragmentMapBinding
import gsc.ZupStar.databinding.FragmentProfileBinding

class MapFragment : Fragment() {
    lateinit var binding : FragmentMapBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater,container,false)

        return binding.root
    }
}