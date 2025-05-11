package gsc.ThePickers.ui.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gsc.ThePickers.R
import gsc.ThePickers.data.TeamData
import gsc.ThePickers.databinding.FragmentSignUpBinding
import kotlin.getValue

@AndroidEntryPoint
class CreateTeamFargment: Fragment() {
    private val TAG = javaClass.simpleName
    lateinit var spf : SharedPreferences
    lateinit var binding: FragmentSignUpBinding
    private val viewModel : TeamViewModel by viewModels()

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
            val name = binding.etInputName.text.toString()
            val data = TeamData(0,name, "")
            viewModel.createTeam(data)
        }

        return binding.root
    }

    private fun setUpObserver(){
        // 생성 성공시 종료 -> 프로필로 돌아가기
        viewModel.isSuccess.observe(viewLifecycleOwner, Observer {
            if (it==null) return@Observer
            Log.d(TAG,"생성 ${it}")
            if (it) requireActivity().finish()
        })
    }

    private fun setView() {
        binding.etInputPassword.visibility = View.GONE
        binding.tvPassword.visibility = View.GONE
        binding.tvEmail.text = "Description"
        binding.etInputEmail.hint = "Describe your team’s cleanup activity"
        binding.tvTitle.text = "Team Build"
        binding.btnEnter.text = "Team Build"
        binding.tvChangeView.setText(Html.fromHtml("<u>" + "Add Team" + "</u>"));
        binding.tvInfo.text = "Already have a Team?"
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
    }
    private fun checkInput() {
        val nameFlag : Boolean = binding.etInputName.text.isNotEmpty()
        val infoFlag : Boolean = binding.etInputEmail.text.isNotEmpty()
        binding.btnEnter.isEnabled = nameFlag && infoFlag
    }
    private fun changeFragment(){
        requireActivity().supportFragmentManager.beginTransaction().replace(
            R.id.fragment_login, SearchTeamFragment()
        ).commit()
    }

}