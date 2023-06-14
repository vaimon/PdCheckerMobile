package ru.mmcs.pdcheckermobile.ui.main.judge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.mmcs.pdcheckermobile.data.models.Grade
import ru.mmcs.pdcheckermobile.data.models.Project
import ru.mmcs.pdcheckermobile.databinding.ItemGradeBinding
import ru.mmcs.pdcheckermobile.databinding.ItemProjectBinding

class ProjectsRvAdapter (
    var items: List<Project>,
    private val onItemInteractionListener: OnItemInteractionListener? = null
) : RecyclerView.Adapter<ProjectsRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemProjectBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.title.text = item.name
        holder.binding.description.text = item.description

        holder.binding.root.setOnClickListener {
            onItemInteractionListener?.onItemClicked(item)
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(items: List<Project>){
        this.items = items
        notifyItemRangeChanged(0, itemCount)
    }

    inner class ViewHolder(val binding: ItemProjectBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnItemInteractionListener {
        fun onItemClicked(item: Project?)
    }

}