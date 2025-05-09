package gsc.ZupStar.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import gsc.ZupStar.data.MapData
import javax.inject.Inject

@HiltViewModel
class mapViewMidel @Inject constructor(

) : ViewModel(){
    private val TAG = javaClass.simpleName
    private val token = 0

    private val _locationInfo = MutableLiveData<MapData>()
    val locationInfo : LiveData<MapData> get() = _locationInfo

    private val _locInfoList = MutableLiveData<List<MapData>>()
    val locInfoList : LiveData<List<MapData>> get() = _locInfoList

    fun getSpecificInfo(pos : Int){

    }

    fun getLocInfoList(){
        //_locInfoList.value = initDummy()
    }



}