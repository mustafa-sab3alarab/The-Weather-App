package com.example.ad340

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.ad340.api.DailyForecast
import java.text.SimpleDateFormat
import java.util.*

private val DATE_FORMAT = SimpleDateFormat("dd-MM-yyyy")

class DailyForecastAdapter(private val list: List<DailyForecast>, private val tempDisplaySettingManger: TempDisplaySettingManger)

    : RecyclerView.Adapter<DailyForecastAdapter.ViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val image: ImageView = itemView.findViewById(R.id.iv_forecast)
        private val textDescription: TextView = itemView.findViewById(R.id.tv_description)
        private val textTemperature: TextView = itemView.findViewById(R.id.tv_temperature)
        private val tempDate: TextView = itemView.findViewById(R.id.temp_date)

        fun bind(dailyForecast: DailyForecast) {
            val iconId = dailyForecast.weather[0].icon
            image.load("http://openweathermap.org/img/wn/${iconId}@2x.png")
            textDescription.text = dailyForecast.weather[0].description
            textTemperature.text = formatTempForDisplay(dailyForecast.temp.max,tempDisplaySettingManger.getTempDisplaySettings())
            tempDate.text = DATE_FORMAT.format(Date(dailyForecast.date * 1000))
            itemView.setOnClickListener {
                onItemClickListener?.onItemClick(dailyForecast)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_view, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}