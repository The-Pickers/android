package gsc.ZupStar.ui.Login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gsc.ZupStar.NetWork.Repository.UserRepository
import gsc.ZupStar.data.LoginData
import gsc.ZupStar.data.SignUpData
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val TAG = javaClass.simpleName

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess : LiveData<Boolean> get() = _isSuccess

    private val _token = MutableLiveData<String>()
    val token : LiveData<String> get() = _token

    fun signUp(data : SignUpData){
        viewModelScope.launch {
            try {
                val response =  userRepository.signUp(data)

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
                val response = userRepository.login(data)

                if (response.isSuccessful) {
                    Log.d(TAG," signIn 응답성공 : ${response.body()} ")
                    _token.value = response.body()!!.data.toString()
                }
                else
                    Log.d(TAG," signIn 응답실패 : ${response.body()} ")
            }catch (e: Exception){
                Log.d(TAG, " signIn api 요청 실패: ${e}")
            }

        }
    }

}