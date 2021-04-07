package dev.ch8n.weather.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.ch8n.weather.data.remote.model.Weather
import dev.ch8n.weather.databinding.ItemLayoutBinding
import java.text.SimpleDateFormat
import java.util.*

class ForcastListAdapter private constructor(
    diffUtil: DiffUtil.ItemCallback<WeatherInfo>
) : ListAdapter<WeatherInfo, ForcastViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForcastViewHolder {
        val holderView = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return ForcastViewHolder(holderView)
    }

    override fun onBindViewHolder(holder: ForcastViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WeatherInfo>() {

            override fun areItemsTheSame(oldItem: WeatherInfo, newItem: WeatherInfo): Boolean =
                oldItem.time == newItem.time

            override fun areContentsTheSame(oldItem: WeatherInfo, newItem: WeatherInfo): Boolean {
                return oldItem == newItem
            }
        }

        fun newInstance() = ForcastListAdapter(DIFF_CALLBACK)
    }

}


class ForcastViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: WeatherInfo) = with(binding){
        textTemp.text = "${item.temp} ºC"
        textTime.text = item.time
        textRain.text = item.rain
        textWind.text = item.wind
    }

}


data class WeatherInfo(
    val temp: String,
    val time: String,
    val rain: String,
    val wind: String
)

fun List<Weather>.toWeatherInfo() = map {
    WeatherInfo(
        temp = it.temp?.toString() ?: "n/a",
        time = getDateTime(it.time?.toString() ?: "") ?: "",
        rain = it.rain?.toString() ?: "n/a",
        wind = it.wind?.toString() ?: "n/a"
    )
}

private fun getDateTime(s: String): String? {
    return try {
        val sdf = SimpleDateFormat("MM/dd/yyyy")
        val netDate = Date(s.toLong() * 1000)
        sdf.format(netDate)
    } catch (e: Exception) {
        e.toString()
    }
}
