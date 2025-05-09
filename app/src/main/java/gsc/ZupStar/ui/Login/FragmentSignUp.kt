package gsc.ZupStar.ui.Login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
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
import gsc.ZupStar.data.SignUpData
import gsc.ZupStar.databinding.FragmentSignUpBinding

@AndroidEntryPoint
class FragmentSignUp :Fragment() {
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
            val data = SignUpData(
                name = binding.etInputName.text.toString(),
                id =  binding.etInputEmail.text.toString(),
                password = binding.etInputPassword.text.toString()
            )
            viewModel.signUp(data)
        }

        return binding.root
    }

    private fun setUpObserver(){
        viewModel.isSuccess.observe(viewLifecycleOwner, Observer {
            if (it==null) return@Observer
            if (it)
                changeFragment()
        })
    }

    private fun setView() {
        binding.tvTitle.text = "Create Account"
        binding.tvName.visibility = View.VISIBLE
        binding.etInputName.visibility = View.VISIBLE
        binding.btnEnter.text = "Sign Up"
        binding.tvChangeView.setText(Html.fromHtml("<u>" + "Sign in" + "</u>"));
        binding.tvInfo.text = "Already have an account?"
    }

    private fun setTextEditor(){
        binding.etInputName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
               checkInput()
            }
        })
        binding.etInputEmail.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                checkInput()
            }
        })
        binding.etInputPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                checkInput()
            }
        })
    }

    private fun checkInput() {
        val nameFlag : Boolean = binding.etInputName.text.isNotEmpty()
        val emailFlag : Boolean = binding.etInputEmail.text.isNotEmpty()
        val pwFlag : Boolean = binding.etInputPassword.text.isNotEmpty()
        binding.btnEnter.isEnabled = nameFlag && emailFlag && pwFlag
    }

    private fun changeFragment(){
        requireActivity().supportFragmentManager.beginTransaction().replace(
            R.id.fragment_login, FragmentSignIn()
        ).commit()
    }
}