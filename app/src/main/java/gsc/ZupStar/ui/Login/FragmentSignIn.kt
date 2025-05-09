package gsc.ZupStar.ui.Login

import android.content.Context
import android.content.Intent
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
import gsc.ZupStar.ui.MainActivity

@AndroidEntryPoint
class FragmentSignIn:Fragment() {
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

        binding.btnEnter.setOnClickListener {
            spf.edit().putInt("token",1).apply()
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        return binding.root
    }

    private fun setView() {
        binding.tvTitle.text = "Log In"
        binding.tvName.visibility = View.GONE
        binding.etInputName.visibility = View.GONE
        binding.btnEnter.text = "Sing In"
        binding.tvChangeView.setText(Html.fromHtml("<u>" + "Sign up" + "</u>"));
    }

    private fun changeFragment(){
        (context as ActivityLogin).supportFragmentManager.beginTransaction().replace(
            R.id.fragment_login, FragmentSignUp()
        ).commit()
    }
}