package gsc.ZupStar.ui.Login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gsc.ZupStar.R
import gsc.ZupStar.data.LoginData
import gsc.ZupStar.data.SignUpData
import gsc.ZupStar.databinding.FragmentSignUpBinding
import gsc.ZupStar.ui.MainActivity
import gsc.ZupStar.ui.MissionViewModel

@AndroidEntryPoint
class FragmentSignIn:Fragment() {
    private val TAG = javaClass.simpleName
    lateinit var binding: FragmentSignUpBinding
    lateinit var spf : SharedPreferences
    private val viewModel : UserViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        spf = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        setView()
        checkInput()
        setTextEditor()
        setUpObserver()
        binding.tvChangeView.setOnClickListener {
            changeFragment()
        }

        binding.btnEnter.setOnClickListener {
            val data = LoginData(
                id =  binding.etInputEmail.text.toString(),
                password = binding.etInputPassword.text.toString()
            )
            spf.edit().putString("token","token").apply()
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
            //viewModel.signIn(data)
        }

        return binding.root
    }
    private fun setUpObserver(){
        viewModel.token.observe(viewLifecycleOwner, Observer {
            if (it==null) return@Observer
            spf.edit().putString("token",it).commit()
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        })
    }

    private fun setView() {
        binding.tvTitle.text = "Log In"
        binding.tvName.visibility = View.GONE
        binding.etInputName.visibility = View.GONE
        binding.btnEnter.text = "Sing In"
        binding.tvChangeView.setText(Html.fromHtml("<u>" + "Sign up" + "</u>"));
    }

    private fun setTextEditor(){
        binding.etInputEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                checkInput()
            }
        })
        binding.etInputPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                checkInput()
            }
        })
    }

    private fun checkInput() {
        val emailFlag : Boolean = binding.etInputEmail.text.isNotEmpty()
        val pwFlag : Boolean = binding.etInputPassword.text.isNotEmpty()
        binding.btnEnter.isEnabled =  emailFlag && pwFlag
    }

    private fun changeFragment(){
        requireActivity().supportFragmentManager.beginTransaction().replace(
            R.id.fragment_login, FragmentSignUp()
        ).commit()
    }
}