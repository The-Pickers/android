package gsc.ZupStar.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gsc.ZupStar.NetWork.Repository.MissionRepository
import gsc.ZupStar.data.ImageData
import gsc.ZupStar.data.MissionData
import gsc.ZupStar.data.VideoData
import gsc.ZupStar.util.DateUtils
import gsc.ZupStar.util.LocationUtil
import gsc.ZupStar.util.dummyComment
import kotlinx.coroutines.launch
import java.time.LocalDateTime
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

    private var pos : Int = 100
    private lateinit var startTime : LocalDateTime

    private var i : Int = 0

    fun getMission(){
        viewModelScope.launch {
            val result = missionRepository.getMission( token )
                //_mission.value = result.body().result
        }
    }

    fun startMission(){
        viewModelScope.launch {
            //val result = missionRepository.postMission( token, data)
            //_mission_idx.value = result.body()!!.data
            startTime = LocalDateTime.now()
            _missionIdx.value = ++pos
            Log.d(TAG,"start mission ${_missionIdx.value}")
        }
    }

    fun completeMission(data : ImageData, idx: Int){
        viewModelScope.launch {
            //val result = missionRepository.completeMission(token,idx, data)
            //_mission.value = result.body()!!.data
            Log.d(TAG,"complete mission ${data}")
            val time = DateUtils.formatDuration(startTime, LocalDateTime.now())
            val score = if (idx %2 == 0) 10 else 25
            val dummy = MissionData(
                idx,
                startTime = startTime.toString(),
                takenTime = time,
                title = "title",
                completed = true,
                carbonReduction = 1.0f,
                message = dummyComment.getShort(idx),
                detectedWaste = 2,
                score = score,
                location = LocationUtil.toIndex(data.location_name)!!
            )
            _mission.value = dummy
        }
    }


}