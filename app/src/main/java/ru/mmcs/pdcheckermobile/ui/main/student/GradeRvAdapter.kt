package ru.mmcs.pdcheckermobile.ui.main.student

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import ru.mmcs.pdcheckermobile.data.models.Grade
import ru.mmcs.pdcheckermobile.databinding.ItemGradeBinding

class GradeRvAdapter (
    var items: List<Grade?>,
) : RecyclerView.Adapter<GradeRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGradeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.description.text = item?.feedback
        holder.binding.description.text = "%.1f".format(item?.points)
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(items: List<Grade?>){
        this.items = items
        notifyItemRangeChanged(0, itemCount)
    }

    inner class ViewHolder(val binding: ItemGradeBinding) :
        RecyclerView.ViewHolder(binding.root)

}