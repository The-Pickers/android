package gsc.ZupStar.ui

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import gsc.ZupStar.NetWork.Repository.MissionRepository
import gsc.ZupStar.data.ImageData
import gsc.ZupStar.data.MissionData
import gsc.ZupStar.data.VideoData
import gsc.ZupStar.ui.MainActivity.Companion.comment
import gsc.ZupStar.ui.MainActivity.Companion.missionTitle
import gsc.ZupStar.util.DateUtils
import gsc.ZupStar.util.LocationUtil
import gsc.ZupStar.util.dummyComment
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class MissionViewModel @Inject constructor(
    private val missionRepository: MissionRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val TAG = javaClass.simpleName
    private val spf = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    private val _mission = MutableLiveData<MissionData>()
    val mission : LiveData<MissionData> get() = _mission

    private val _missionList = MutableLiveData<List<MissionData>>()
    val missionList :LiveData<List<MissionData>> get() = _missionList

    private val _missionIdx = MutableLiveData<Int>()
    val missionIdx : LiveData<Int> get() = _missionIdx



    fun getToken(): String? {
        return spf.getString("token", null)
    }

    fun getMissionList(){
        viewModelScope.launch {
            try {
                val token = getToken()
                val response = missionRepository.getMissionList(token!!)
                if (response.isSuccessful) {
                    Log.d(TAG," getMissionList : ${response.body()} ")
                    _missionList.value = response.body()!!.data
                } else
                    Log.d(TAG," getMissionList 응답실패 : ${response.body()} ")

            }catch (e: Exception){
                Log.d(TAG, "getMissionList api 요청 실패: ${e}")
            }
        }
    }

    fun startMission(){
        viewModelScope.launch {
            try {
                val token = getToken()
                val response = missionRepository.postMission(token!!)
                if (response.isSuccessful) {
                    Log.d(TAG,"  startMission : ${response.body()} ")
                    _missionIdx.value = response.body()!!.data
                } else
                    Log.d(TAG," startMission 응답실패 : ${response.body()} ")

            }catch (e: Exception){
                Log.d(TAG, "startMission() api 요청 실패: ${e}")
            }
        }
    }

    // Bitmap → Uri 변환
    private fun saveBitmapToUri(bitmap: Bitmap): Uri {
        val filename = "captured_${System.currentTimeMillis()}.jpg"
        val file = File(context.cacheDir, filename)
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
        }
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }

    // 서버로 업로드
    fun completeMission(bitmap: Bitmap, idx : Int, loc : String) {
        viewModelScope.launch {
            try {
                val token = getToken()
                val uri = saveBitmapToUri(bitmap)
                val timestamp = LocalDateTime.now().toString()
                val locIdx : Int = LocationUtil.toIndex(loc)!!
                val imageData = ImageData(uri =uri, location_idx = locIdx, timestamp = timestamp)
                val response = missionRepository.completeMission(token!!, idx, imageData)
                if (response.isSuccessful) {
                    Log.d(TAG,"  completeMission : ${response.body()} ")
                    _mission.value = response.body()!!.data
                } else
                    Log.d(TAG," completeMission 응답실패 : ${response.body()} ")
            }catch (e: Exception){
                Log.d(TAG, "completeMission api 요청 실패: ${e}")
            }
        }
    }

//    fun completeMission(data : ImageData, idx: Int){
//        viewModelScope.launch {
//            //val result = missionRepository.completeMission(token,idx, data)
//            //_mission.value = result.body()!!.data
//            Log.d(TAG,"complete mission ${data}")
//            val time = DateUtils.formatDuration(startTime, LocalDateTime.now())
//            val score = if (idx %2 == 0) 10 else 25
//            val dummy = MissionData(
//                comment++,
//                startTime = startTime.toString(),
//                takenTime = time,
//                title = missionTitle,
//                completed = true,
//                carbonReduction = 1.0f,
//                message = dummyComment.getShort(idx),
//                detectedWaste = 2,
//                score = score,
//                location = LocationUtil.toIndex(data.location_name)!!
//            )
//            _mission.value = dummy
//        }
//    }


}