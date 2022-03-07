package com.example.weatherdata.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherdata.R
import com.example.weatherdata.Result
import com.example.weatherdata.ShowFragmentListener
import com.example.weatherdata.adapter.WeatherAdapter
import com.example.weatherdata.app.App
import com.example.weatherdata.withArguments
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailFragment : Fragment(R.layout.detail_fragment) {

    @Inject
    lateinit var viewModel: DetailViewModel
    private var weatherAdapter: WeatherAdapter? = null
    private val listener: ShowFragmentListener?
        get() = activity?.let { it as ShowFragmentListener }

    companion object {
        const val DATA_KEY = "123"
        fun newInstance(name: String): DetailFragment {
            return DetailFragment().withArguments {
                putString(DATA_KEY, name)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as App).component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var name = arguments?.getString(DATA_KEY) as String
        topAppBar.title = name
        weatherAdapter = WeatherAdapter { }
        with(weatherList) {
            adapter = weatherAdapter
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
        viewModel.getDataFromDB(name)
        viewModel.list.observe(viewLifecycleOwner, {
            weatherAdapter?.updateCollections(it)
            weatherAdapter?.notifyDataSetChanged()
        })
        topAppBar.setNavigationOnClickListener {
            listener?.removeDetailFragment()
        }
        topAppBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.deleteCity -> {
                    viewModel.deleteData(name)
                    viewModel.deleteFromSharedPref(name)
                    listener?.removeDetailFragment()
                    true
                }
                else -> false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        weatherAdapter = null
    }
}