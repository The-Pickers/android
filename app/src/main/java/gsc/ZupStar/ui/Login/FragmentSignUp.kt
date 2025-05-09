package gsc.ZupStar.ui.Login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import gsc.ZupStar.R
import gsc.ZupStar.databinding.FragmentSignUpBinding

@AndroidEntryPoint
class FragmentSignUp:Fragment() {
    private val TAG = javaClass.simpleName
    lateinit var binding: FragmentSignUpBinding
    lateinit var spf : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        spf = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        setView()
        binding.tvChangeView.setOnClickListener {
            changeFragment()
        }

        return binding.root
    }

    private fun setView() {
        binding.tvTitle.text = "Create Account"
        binding.tvName.visibility = View.VISIBLE
        binding.etInputName.visibility = View.VISIBLE
        binding.btnEnter.text = "Sign Up"
        binding.tvChangeView.setText(Html.fromHtml("<u>" + "Sign in" + "</u>"));
    }

    private fun changeFragment(){
        (context as ActivityLogin).supportFragmentManager.beginTransaction().replace(
            R.id.fragment_login, FragmentSignIn()
        ).commit()
    }
}