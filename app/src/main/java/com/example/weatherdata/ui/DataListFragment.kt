package com.example.weatherdata.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherdata.R
import com.example.weatherdata.ShowFragmentListener
import com.example.weatherdata.adapter.CityAdapter
import com.example.weatherdata.app.App
import kotlinx.android.synthetic.main.datalist_fragment.*
import javax.inject.Inject

class DataListFragment : Fragment(R.layout.datalist_fragment) {

    @Inject
    lateinit var viewModel: DataListViewModel
    private var cityAdapter: CityAdapter? = null
    private var list: List<String> = emptyList()
    private val listener: ShowFragmentListener?
        get() = activity?.let { it as ShowFragmentListener }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as App).component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cityAdapter = CityAdapter { position -> showDetailFragment(position) }
        with(cityList) {
            adapter = cityAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.HORIZONTAL
                )
            )
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        viewModel.getWeatherDataFromDB()
        viewModel.list.observe(viewLifecycleOwner, {
            list = it.map { it.city_name }
            cityAdapter?.updateCityList(list)
            cityAdapter?.notifyDataSetChanged()
        })
    }

    private fun showDetailFragment(position: Int) {
        listener?.showDetailFragment(list[position])
    }

    override fun onDestroy() {
        super.onDestroy()
        cityAdapter = null
    }
}