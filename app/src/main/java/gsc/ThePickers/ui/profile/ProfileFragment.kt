package gsc.ThePickers.ui.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gsc.ThePickers.R
import gsc.ThePickers.databinding.FragmentProfileBinding
import gsc.ThePickers.ui.Login.UserViewModel
import gsc.ThePickers.util.StatusBarUtil

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val TAG = javaClass.simpleName
    lateinit var spf : SharedPreferences
    lateinit var binding : FragmentProfileBinding
    private val viewModel : UserViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        StatusBarUtil.updateStatusBarColor(requireActivity(), ContextCompat.getColor(requireContext(), R.color.profile))
        spf = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        setUpObserver()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProfile()
    }

    private fun setUpObserver(){
        viewModel.profile.observe(viewLifecycleOwner, Observer {
            Log.d(TAG,"fragment ${it}")
            if (it == null) return@Observer
            binding.tvName.text = it.name

            if (it.team.isNotEmpty()) {
                binding.tvTeam.text = it.team
            } else {
                binding.tvTeam.setOnClickListener {
                    Log.d(TAG, "click")
                    val intent = Intent(requireActivity(), EditTeamActivity::class.java)
                    requireActivity().startActivity(intent)
                }
            }
            getImgUri()
        })
    }

    private fun getImgUri(){
        val uriString =  spf.getString("profile_image",null)
        Log.d(TAG,"uri : ${uriString}")
        if (uriString != null) {
            val uri = uriString?.let { Uri.parse(it) }
            binding.ivProfileImg.setImageURI(uri)
        }

    }
}