package gsc.ThePickers.ui.Login

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
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import gsc.ThePickers.R
import gsc.ThePickers.data.LoginData
import gsc.ThePickers.databinding.FragmentSignUpBinding
import gsc.ThePickers.ui.home.HomeViewModel
import gsc.ThePickers.ui.MainActivity
import gsc.ThePickers.ui.map.MapViewModel

@AndroidEntryPoint
class FragmentSignIn:Fragment() {
    private val TAG = javaClass.simpleName
    lateinit var spf : SharedPreferences
    lateinit var binding: FragmentSignUpBinding
    private val viewModel : UserViewModel by viewModels()
    private val homeViewModel : HomeViewModel by viewModels()
    private val  mapViewModel : MapViewModel by viewModels()
    private val gson = Gson()
    private var flag1 = false;
    private var flag2 = false;
    private var flag3 = false;
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
//            spf.edit().putString("token","token").apply()
//            val intent = Intent(requireActivity(), MainActivity::class.java)
//            startActivity(intent)
//            requireActivity().finish()
            viewModel.signIn(data)
        }

        return binding.root
    }
    private fun setUpObserver(){
        viewModel.token.observe(viewLifecycleOwner, Observer {
            if (it==null) return@Observer
            spf.edit().putInt("token",it).apply()
            homeViewModel.getComment()
            homeViewModel.getAccount()
            mapViewModel.getMapList()
        })
        mapViewModel.mapList.observe (viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            val json = gson.toJson(it)
            spf.edit().putString("mapData",json).apply()
            flag1 = true
            checkValue()
        })
        homeViewModel.account.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            val json = gson.toJson(it)
            spf.edit().putInt("score",it.totalScore).apply()
            spf.edit().putString("account",json).apply()
            flag2 = true
            checkValue()
        })
        homeViewModel.comment.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            spf.edit().putString("comment",it).apply()
            flag3 = true
            checkValue()
        })
    }

    private fun checkValue(){
        if(flag1 && flag2 && flag3){
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun setView() {
        binding.layoutTop.visibility= View.GONE
        binding.tvTitle.text = "Log In"
        binding.btnEnter.text = "Sign In"
        binding.tvChangeView.setText(Html.fromHtml("<u>" + "Sign up" + "</u>"));
        binding.tvInfo.text = "Don't have an account yet?"
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