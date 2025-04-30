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
import gsc.ZupStar.util.DateUtils
import gsc.ZupStar.util.LocationUtil
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

    fun startMission(data: VideoData){
        viewModelScope.launch {
            //val result = missionRepository.postMission( token, data)
            //_mission_idx.value = result.body()!!.data
            startTime = LocalDateTime.now()
            _missionIdx.value = ++pos
            Log.d(TAG,"start mission ${_missionIdx.value}")
        }
    }

    fun completeMission(data : VideoData){
        viewModelScope.launch {
            //val result = missionRepository.completeMission(token,idx, data)
            //_mission.value = result.body()!!.data
            Log.d(TAG,"complete mission ${data}")
            val time = DateUtils.formatDuration(startTime, LocalDateTime.now())

            val dummy = MissionData(
                missionIdx.value!!,
                startTime = startTime.toString(),
                takenTime = time,
                title = "title",
                completed = true,
                carbonReduction = 1.0f,
                message = commentList[i],
                detectedWaste = 2,
                score = 10,
                location = LocationUtil.toIndex(data.location_name)!!
            )
            _mission.value = dummy
            i = (i+1)%commentList.size
        }
    }

    private val commentList = listOf<String>(
        "Earth Guardian! You've reduced carbon by 0.33 kg and trash by 0.51 kg so far 🌍💚",
        "Earth Hero! You've reduced approximately 0.33 kg of carbon and 0.51 kg of waste so far 🌍💚",
        "Amazing work! You helped reduce 0.14 kg of CO₂ and collected 0.4 kg of waste 🥰🌱",
        "Wonderful job, green warrior! You've cut 0.19 kg of carbon and picked up 0.47 kg of waste! 🌎🌱",
        "Fantastic job! You've reduced 0.112 kg of carbon and 0.165 kg of waste.💖🌱",
        "Eco-champion alert! You've reduced 0.15 kg of carbon and 0.78 kg of waste. Keep up the fantastic work! 🏆👏",
        "Eco-warrior in action! You've reduced 0.17 kg of carbon and 0.44 kg of waste! 🥰🌱",
        "Fantastic recycling! You've reduced 0.237 kg of carbon and 0.115 kg of waste. 💚🌏",
        "Bravo, eco-warrior! You've reduced 0.17 kg of carbon and 0.4 kg of waste 💖🌿",
        "Eco-champion! You've reduced 0.17 kg of carbon and 0.38 kg of waste 💚🌏 Every little bit counts!"
    )
}