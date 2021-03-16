package dev.ch8n.toiweather.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.ch8n.toiweather.R
import kotlinx.android.synthetic.main.item_layout.view.*

class ForcastListAdapter private constructor(diffUtil: DiffUtil.ItemCallback<ForcastItem>) :
    ListAdapter<ForcastItem, ForcastViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForcastViewHolder {
        val holderView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ForcastViewHolder(holderView)
    }

    override fun onBindViewHolder(holder: ForcastViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ForcastItem>() {

            override fun areItemsTheSame(oldItem: ForcastItem, newItem: ForcastItem): Boolean =
                oldItem.day == newItem.day

            override fun areContentsTheSame(oldItem: ForcastItem, newItem: ForcastItem): Boolean {
                return (oldItem.day == newItem.day &&
                        oldItem.temp == newItem.temp)
            }
        }

        fun newInstance() = ForcastListAdapter(DIFF_CALLBACK)
    }

}


class ForcastViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textDay = view.text_day
    val textTemp = view.text_temp

    fun onBind(item: ForcastItem) {
        textDay.text = item.day
        textTemp.text = item.temp
    }

}


data class ForcastItem(val day: String, val temp: String)