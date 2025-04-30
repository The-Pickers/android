package gsc.ZupStar.ui.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gsc.ZupStar.data.MissionData
import gsc.ZupStar.databinding.RvItemMapLogBinding
import gsc.ZupStar.util.DateUtils
import java.time.LocalDateTime

class MissionLogRVAdapter(private val itemList: ArrayList<MissionData>) : RecyclerView.Adapter<MissionLogRVAdapter.ViewHolder>() {
    inner class ViewHolder (val binding: RvItemMapLogBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(pos: Int){
            binding.tvTitle.text = itemList[pos].title
            binding.tvDate.text = DateUtils.formatLocalDate(itemList[pos].startTime,pos+1*(pos+2))
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
}