package gsc.ZupStar.ui.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import gsc.ZupStar.R
import gsc.ZupStar.databinding.FragmentSearchTeamBinding
import gsc.ZupStar.databinding.FragmentSignUpBinding
import kotlin.getValue

@AndroidEntryPoint
class SearchTeamFragment: Fragment() {
    private val TAG = javaClass.simpleName
    lateinit var spf : SharedPreferences
    lateinit var binding: FragmentSearchTeamBinding
    lateinit var adapter: SearchTeamRVAdapter
    private val viewModel : TeamViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchTeamBinding.inflate(inflater, container, false)
        spf = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        setUpObserver()
        setTextEditor()
        adapter = SearchTeamRVAdapter(itemClick = { idx ->
            viewModel.joinTeam(idx)
        })
        binding.tvChangeView.setOnClickListener {
            changeFragment()
        }

        binding.rvTeam.adapter = adapter
        binding.rvTeam.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)

        return binding.root
    }
    private fun setUpObserver(){
        viewModel.teamList.observe(viewLifecycleOwner, Observer {
            if (it==null) return@Observer
            adapter.changeData(it)
        })
        // 가입 성공시 종료 -> 프로필로 돌아가기
        viewModel.isSuccess.observe(viewLifecycleOwner, Observer {
            if (it==null) return@Observer
            Log.d(TAG,"생성 ${it}")
            if (it) requireActivity().finish()
        })
    }
    private fun setTextEditor(){
        // 글자 변할때마다 요청
        binding.etInputName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.searchTeam(binding.etInputName.text.toString());
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
    }
    private fun changeFragment(){
        requireActivity().supportFragmentManager.beginTransaction().replace(
            R.id.fragment_login, CreateTeamFargment()
        ).commit()
    }
}