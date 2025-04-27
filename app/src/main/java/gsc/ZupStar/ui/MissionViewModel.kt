package gsc.ZupStar.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gsc.ZupStar.NetWork.Repository.MissionRepository
import gsc.ZupStar.data.MissionData
import gsc.ZupStar.data.VideoData
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class MissionViewModel @Inject constructor(
    private val missionRepository: MissionRepository
) : ViewModel() {

    private val TAG = javaClass.simpleName
    private val token = "token"

    private val _mission = MutableLiveData<MissionData>()
    val mission : LiveData<MissionData> get() = _mission

    private val _missionIdx = MutableLiveData<Int>()
    val missionIdx : LiveData<Int> get() = _missionIdx


    fun getMission(){
        viewModelScope.launch {
            val result = missionRepository.getMission( token )
                //_mission.value = result.body().result
        }
    }

    fun startMission(data: VideoData){
        viewModelScope.launch {
            //val result = missionRepository.postMission( token, data)
            //_mission_idx.value = result.body()!!.data

            _missionIdx.value = 1
            Log.d(TAG,"start mission ${_missionIdx.value}")
        }
    }

    fun completeMission(idx : Int, data : VideoData){
        viewModelScope.launch {
            //val result = missionRepository.completeMission(token,idx, data)
            //_mission.value = result.body()!!.data
            Log.d(TAG,"complete mission")
            val time = LocalTime.now()
            val dummy = MissionData(
                missionIdx.value!!,
                time.toString(),
                completed = true,
                carbonReduction = 1.0f,
                message = "Wow",
                detectedWaste = 2,
                score = 10 )
            _mission.value = dummy
        }
    }
}