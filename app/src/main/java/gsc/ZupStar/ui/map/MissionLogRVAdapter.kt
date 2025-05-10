package gsc.ZupStar.ui.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gsc.ZupStar.data.MissionData
import gsc.ZupStar.databinding.RvItemMapLogBinding
import gsc.ZupStar.util.DateUtils
import gsc.ZupStar.util.LocationUtil

class MissionLogRVAdapter() : RecyclerView.Adapter<MissionLogRVAdapter.ViewHolder>() {
    private val itemList= ArrayList<MissionData>()
    inner class ViewHolder (val binding: RvItemMapLogBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(pos: Int){
            binding.tvTitle.text = LocationUtil.toEnglishByIndex(itemList[pos].location)
            binding.tvDate.text = DateUtils.formatLocalDate(itemList[pos].clearTime)
            binding.tvScore.text ="+${itemList[pos].score} pts"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : RvItemMapLogBinding = RvItemMapLogBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  ViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(position)
    }

    fun addData(list : List<MissionData>){
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }
}