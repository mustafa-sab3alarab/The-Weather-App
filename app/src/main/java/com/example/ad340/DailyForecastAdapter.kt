package com.example.ad340

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DailyForecastAdapter(private val list: List<DailyForecast>) :
    RecyclerView.Adapter<DailyForecastAdapter.ViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val image: ImageView = itemView.findViewById(R.id.iv_forecast)
        private val textDescription: TextView = itemView.findViewById(R.id.tv_description)
        private val textTemperature: TextView = itemView.findViewById(R.id.tv_temperature)

        fun bind(dailyForecast: DailyForecast) {
            image.setImageResource(dailyForecast.image)
            textDescription.text = dailyForecast.description
            textTemperature.text = dailyForecast.temp.toString()
            itemView.setOnClickListener {
                onItemClickListener?.onItemClick(dailyForecast)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}