package gsc.ZupStar.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gsc.ZupStar.databinding.RvItemRankBinding


class RankingRVAdapter (private val itemList : List<String>) : RecyclerView.Adapter<RankingRVAdapter.ViewHolder>() {
    inner class ViewHolder (val binding: RvItemRankBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(pos: Int){
            binding.tvTeamName.text = itemList[pos]
            binding.tvIdx.text = "${pos+1}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : RvItemRankBinding = RvItemRankBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  ViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }
}