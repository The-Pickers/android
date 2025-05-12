package gsc.ThePickers.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import gsc.ThePickers.NetWork.Repository.HomeRepository
import gsc.ThePickers.data.AccountData
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    @ApplicationContext private val context: Context
): ViewModel() {
    private val TAG = javaClass.simpleName

    private val _comment = MutableLiveData<String>()
    val comment : LiveData<String> get() = _comment

    private val _account = MutableLiveData<AccountData>()
    val account : LiveData<AccountData> get() = _account

    private val spf = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    fun getToken(): Int? {
        return spf.getInt("token", -1)
    }


    fun getComment(){
        viewModelScope.launch {
            try {
                val token = getToken()
                val response = repository.getComment(token!!)
                if (response.isSuccessful) {
                    Log.d(TAG," getComment() 응답성공 : ${response.body()} ")
                    _comment.value = response.body()!!.data.message
                }
                else
                    Log.d(TAG," getComment() 응답실패 : ${response.body()} ")
            }catch (e: Exception){
                Log.d(TAG, " getComment() api 요청 실패: ${e}")
            }
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
                _account.value = AccountData(0,0,0f)
            }

        }
    }





}