package gsc.ZupStar.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import gsc.ZupStar.NetWork.Repository.HomeRepository
import gsc.ZupStar.data.AccountData
import gsc.ZupStar.util.dummyComment
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    @ApplicationContext private val context: Context
): ViewModel() {
    private val TAG = javaClass.simpleName
    private val spf = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    private val _comment = MutableLiveData<String>()
    val comment : LiveData<String> get() = _comment

    private val _account = MutableLiveData<AccountData>()
    val account : LiveData<AccountData> get() = _account

    private var idx = 0

    fun getToken(): Int? {
        return spf.getInt("token", -1)
    }


    fun getComment(){
        viewModelScope.launch {
            //val result = homeRepository.getComment(token)
            _comment.value = dummyComment.getComment(idx)
            idx = (idx+1)%10
        }
    }

    fun getAccount(){
        viewModelScope.launch {
            try {
                val token = getToken()
                val response = repository.getAccount(token!!)
                if (response.isSuccessful) {
                    Log.d(TAG," getAccount() 응답성공 : ${response.body()} ")
                   _account.value = response.body()!!.data
                }
                else
                    Log.d(TAG," getAccount() 응답실패 : ${response.body()} ")
            }catch (e: Exception){
                Log.d(TAG, " getAccount() api 요청 실패: ${e}")
            }

        }
    }





}