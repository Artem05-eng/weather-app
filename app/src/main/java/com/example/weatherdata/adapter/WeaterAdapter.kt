package com.example.weatherdata.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherdata.R
import com.example.weatherdata.Result
import com.example.weatherdata.Weather
import com.example.weatherdata.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.card_weather.*

class WeatherAdapter(
    private val onItemClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<WeatherAdapter.WeatherHolder>() {

    private var list: List<Weather> = emptyList()

    fun updateCollections(newList: List<Weather>) {
        list = newList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
        return WeatherHolder(parent.inflate(R.layout.card_weather), onItemClicked)
    }

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }


    class WeatherHolder(
        override val containerView: View?,
        private val onItemClicked: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(containerView!!), LayoutContainer {
        init {
            containerView!!.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }

        fun onBind(result: Weather) {
            date_text.text = result.datetime
            weather_text.text = result.temp
        }
    }

}