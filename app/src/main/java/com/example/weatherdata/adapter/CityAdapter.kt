package com.example.weatherdata.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherdata.R
import com.example.weatherdata.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.card_city.*

class CityAdapter(
    private val onItemClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<CityAdapter.CityHolder>() {

    private var list: List<String> = emptyList()

    fun updateCityList(newList: List<String>) {
        list = newList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        return CityHolder(parent.inflate(R.layout.card_city), onItemClicked)
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }


    class CityHolder(
        override val containerView: View?,
        private val onItemClicked: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(containerView!!), LayoutContainer {
        init {
            containerView!!.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }

        fun onBind(city: String) {
            city_text.text = city
        }
    }
}