package gsc.ThePickers.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gsc.ThePickers.data.TeamData
import gsc.ThePickers.databinding.RvItemTeamBinding

class SearchTeamRVAdapter(
    private val itemClick : (Int)->Unit
): RecyclerView.Adapter<SearchTeamRVAdapter.ViewHolder>(){
    private val itemList = ArrayList<TeamData>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = RvItemTeamBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(position)
    }

    override fun getItemCount(): Int  = itemList.size

    fun changeData(list: List<TeamData>){
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder (val binding : RvItemTeamBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(pos : Int){
            val item = itemList[pos]
            binding.tvName.text = item.name
            binding.tvInfo.text = item.info
            binding.ivAddTeam.setOnClickListener {
                itemClick(item.idx)
            }
        }
    }
}