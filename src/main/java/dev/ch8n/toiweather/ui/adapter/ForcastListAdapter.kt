package dev.ch8n.toiweather.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.ch8n.toiweather.databinding.ItemLayoutBinding

class ForcastListAdapter private constructor(
    diffUtil: DiffUtil.ItemCallback<ForcastItem>
) : ListAdapter<ForcastItem, ForcastViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForcastViewHolder {
        val holderView = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return ForcastViewHolder(holderView)
    }

    override fun onBindViewHolder(holder: ForcastViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ForcastItem>() {

            override fun areItemsTheSame(oldItem: ForcastItem, newItem: ForcastItem): Boolean =
                oldItem.attribute == newItem.attribute

            override fun areContentsTheSame(oldItem: ForcastItem, newItem: ForcastItem): Boolean {
                return (oldItem.attribute == newItem.attribute &&
                        oldItem.value == newItem.value)
            }
        }

        fun newInstance() = ForcastListAdapter(DIFF_CALLBACK)
    }

}


class ForcastViewHolder(binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    private val textAttribute = binding.textAttr
    private val textValue = binding.textValue

    fun onBind(item: ForcastItem) {
        textAttribute.text = item.attribute
        textValue.text = item.value
    }

}


data class ForcastItem(val attribute: String, val value: String)