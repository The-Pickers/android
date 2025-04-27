package gsc.ZupStar.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gsc.ZupStar.NetWork.Repository.HomeRepository
import gsc.ZupStar.data.AccountData
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
): ViewModel() {
    private val TAG = javaClass.simpleName
    private val token = "token"

    private val _comment = MutableLiveData<String>()
    val comment : LiveData<String> get() = _comment

    private val _account = MutableLiveData<AccountData>()
    val account : LiveData<AccountData> get() = _account

    fun getComment(){
        viewModelScope.launch {
            //val result = homeRepository.getComment(token)
            _comment.value = "지구의 수호자!\uD83C\uDF0D\n바다 거북 2마리를\n살렸어요\uD83D\uDC9A"
        }
    }

    fun getAccount(){
        viewModelScope.launch {
            //val result = homeRepository.getAccount(token)
            val dummy = AccountData(missionCount = 5 , totalScore = 1234, carbonReduction =  25.5f)
            _account.value = dummy
        }
    }

}