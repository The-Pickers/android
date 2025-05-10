package gsc.ZupStar.ui.map

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import gsc.ZupStar.NetWork.Repository.HomeRepository
import gsc.ZupStar.data.MapData
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: HomeRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val TAG = javaClass.simpleName
    private val spf = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)


    private val _locationInfo = MutableLiveData<MapData>()
    val locationInfo : LiveData<MapData> get() = _locationInfo

    private val _mapList = MutableLiveData<List<MapData>>()
    val mapList : LiveData<List<MapData>> get() = _mapList

    fun getToken(): Int? {
        return spf.getInt("token", -1)
    }

    fun getSpecificInfo(pos : Int){

    }

    fun getMapList(){
        viewModelScope.launch {
            try {
                val token = getToken()
                val response = repository.getMapInfo(token!!)
                if (response.isSuccessful) {
                    Log.d(TAG," getMapList() 응답성공 : ${response.body()} ")
                    _mapList.value = response.body()!!.data
                }
                else
                    Log.d(TAG,"getMapList() 응답실패 : ${response.body()} ")
            }catch (e: Exception){
                Log.d(TAG, " getMapList() api 요청 실패: ${e}")
            }

        }
    }



}