package gsc.ZupStar.ui.Ranking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gsc.ZupStar.R
import gsc.ZupStar.data.RankListData
import gsc.ZupStar.databinding.RvItemRankBinding


class RankingRVAdapter (private val itemList : List<RankListData.RankData>) : RecyclerView.Adapter<RankingRVAdapter.ViewHolder>() {
    inner class ViewHolder (val binding: RvItemRankBinding) : RecyclerView.ViewHolder(binding.root){
        val medalList = listOf(R.drawable.item_medal_1, R.drawable.item_medal_2,R.drawable.item_medal_3)
        fun bind(pos: Int){
            val item = itemList[pos]
            binding.tvName.text = item.name
            binding.tvPoint.text = item.rank.toString()+"pts"
            binding.tvInfo.text = item.info

            if(pos<3){
                binding.tvIdx.setBackgroundResource(medalList[pos])
                binding.tvIdx.text = ""
            }
            else binding.tvIdx.text = item.idx.toString()

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