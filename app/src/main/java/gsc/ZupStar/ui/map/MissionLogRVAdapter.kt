package gsc.ZupStar.ui.map

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gsc.ZupStar.databinding.RvItemMapLogBinding

class MissionLogRVAdapter(private val itemList : List<String>) : RecyclerView.Adapter<MissionLogRVAdapter.ViewHolder>() {
    inner class ViewHolder (val binding: RvItemMapLogBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(pos: Int){
            binding.tvTitle.text = itemList[pos]
            Log.d("test" ,"item created ${pos}")
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