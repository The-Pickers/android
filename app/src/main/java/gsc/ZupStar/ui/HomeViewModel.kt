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

    private val dummy = arrayListOf<String>()
    private var idx = 0

    init {
        initDummy()
    }

    fun getComment(){
        viewModelScope.launch {
            //val result = homeRepository.getComment(token)
            _comment.value = dummy[idx]
            idx = (idx+1)%dummy.size
        }
    }

    fun getAccount(){
        viewModelScope.launch {
            //val result = homeRepository.getAccount(token)
            val dummy = AccountData(missionCount = 5 , totalScore = 1234, carbonReduction =  25.5f)
            _account.value = dummy
        }
    }


    private fun initDummy(){
        dummy.add("새들의 노랫소리가 더욱 아름답게 울려 퍼지겠어요 \uD83D\uDC26\uD83C\uDFB6")
        dummy.add("강물처럼 맑은 당신의 노력이 세상을 적시고 있어요 \uD83C\uDFDE\uFE0F\uD83D\uDCA7")
        dummy.add("당신의 노력으로 아름다운 꽃밭이 더욱 풍성해지고 있어요 \uD83C\uDF38\uD83C\uDF37")
        dummy.add("당신의 노력이 세상을 향해 힘찬 새싹 \uD83C\uDF3F을 틔우고 있어요!")
        dummy.add("숲 \uD83C\uDF32이 당신의 따뜻한 손길로 더욱 풍성해지고 있어요!")
        dummy.add("나무 한 그루가 힘차게 자라고 있어요 \uD83C\uDF32")
        dummy.add("바다거북이 미소짓는 깨끗한 바다, 당신 덕분이에요! \uD83D\uDC22\uD83C\uDF0A")
        dummy.add("고래 한 마리가 깊은 바다를 누비고 있어요 \uD83D\uDC0B")
        dummy.add("씨앗 하나가 싹을 틔웠어요 \uD83C\uDF31")
        dummy.add("바다거북 한 마리가 자유를 찾았어요 \uD83D\uDC22")
        dummy.add("강물이 맑게 흐르기 시작했어요 \uD83C\uDFDE\uFE0F")
    }
}