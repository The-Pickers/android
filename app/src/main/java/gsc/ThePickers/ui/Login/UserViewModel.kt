package gsc.ThePickers.ui.Login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import gsc.ThePickers.NetWork.Repository.UserRepository
import gsc.ThePickers.data.LoginData
import gsc.ThePickers.data.SignUpData
import gsc.ThePickers.data.UserData
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val TAG = javaClass.simpleName

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess : LiveData<Boolean> get() = _isSuccess

    private val _token = MutableLiveData<Int>()
    val token : LiveData<Int> get() = _token

    private val _profile = MutableLiveData<UserData>()
    val profile : LiveData<UserData> get() = _profile

    private val spf = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    fun getToken(): Int? {
        return spf.getInt("token", -1)
    }


    fun signUp(data : SignUpData){
        viewModelScope.launch {
            try {
                val response =  repository.signUp(data)

                if (response.isSuccessful) {
                    Log.d(TAG," signUp 응답성공 : ${response.body()} ")
                    _isSuccess.value = response.body()!!.success
                }
                else
                    Log.d(TAG," signUp 응답실패 : ${response.body()} ")
            }catch (e: Exception){
                Log.d(TAG, " signUp api 요청 실패: ${e}")
            }
        }
    }

    fun signIn(data : LoginData){
        viewModelScope.launch {
            try {
                val response = repository.login(data)
                if (response.isSuccessful) {
                    Log.d(TAG," signIn 응답성공 : ${response.body()} ")
                    _token.value = response.body()!!.data.token
                }
                else
                    Log.d(TAG," signIn 응답실패 : ${response.body()} ")
            }catch (e: Exception){
                Log.d(TAG, " signIn api 요청 실패: ${e}")
            }

        }
    }
    
    fun getProfile(){
        viewModelScope.launch {
            try {
                val token = getToken()
                val response = repository.getUserInfo(token!!)
                if (response.isSuccessful) {
                    Log.d(TAG," getProfile() 응답성공 : ${response.body()} ")
                    _profile.value = response.body()!!.data
                }
                else
                    Log.d(TAG,"getProfile() 응답실패 : ${response.body()} ")
            }catch (e: Exception){
                Log.d(TAG, " getProfile() api 요청 실패: ${e}")
                _profile.value = UserData(0,"qwer","aaaa")
            }

        }
    }

}