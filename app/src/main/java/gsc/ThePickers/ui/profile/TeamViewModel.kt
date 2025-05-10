package gsc.ThePickers.ui.profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import gsc.ThePickers.NetWork.Repository.TeamRepository
import gsc.ThePickers.NetWork.Request.TeamBody
import gsc.ThePickers.data.TeamData
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamViewModel @Inject constructor(
    private val repository: TeamRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val TAG = javaClass.simpleName
    private val spf = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun getToken(): Int? {
        return spf.getInt("token", -1)
    }
    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess : LiveData<Boolean> get() = _isSuccess

    private val _index = MutableLiveData<Int>()
    val index : LiveData<Int> get() = _index

    private val _teamList = MutableLiveData<List<TeamData>>()
    val teamList : LiveData<List<TeamData>> get() = _teamList

    fun createTeam(data : TeamData){
        viewModelScope.launch {
            try {
                val token = getToken()
                val response = repository.createTeam(token!!, data)
                if (response.isSuccessful) {
                    Log.d(TAG," createTeam 응답 성공: ${response.body()} ")
                    _isSuccess.value = response.body()!!.success
                } else
                    Log.d(TAG," createTeam 응답 실패 : ${response.body()}")
            }catch (e: Exception){
                Log.d(TAG, "createTeam api 요청 실패: ${e}")
            }
        }
    }

    fun searchTeam(name : String ){
        viewModelScope.launch {
            try {
                val token = getToken()
                val response = repository.searchTeam(token!!, TeamBody(name))
                if (response.isSuccessful) {
                    Log.d(TAG," searchTeam 응답 성공: ${response.body()} ")
                    _teamList.value = response.body()!!.data
                } else
                    Log.d(TAG," searchTeam 응답 실패 : ${response.body()}")
            }catch (e: Exception){
                Log.d(TAG, "searchTeam api 요청 실패: ${e}")
            }
        }
    }

    fun joinTeam(idx : Int ){
        viewModelScope.launch {
            try {
                val token = getToken()
                val response = repository.joinTeam(token!!, idx)
                if (response.isSuccessful) {
                    Log.d(TAG," searchTeam 응답 성공: ${response.body()} ")
                    _isSuccess.value = response.body()!!.success
                } else
                    Log.d(TAG," searchTeam 응답 실패 : ${response.body()}")
            }catch (e: Exception){
                Log.d(TAG, "searchTeam api 요청 실패: ${e}")
            }
        }
    }
}