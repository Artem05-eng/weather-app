package com.example.weatherdata.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherdata.R
import com.example.weatherdata.ShowFragmentListener
import com.example.weatherdata.adapter.CityAdapter
import com.example.weatherdata.app.App
import kotlinx.android.synthetic.main.search_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.search_fragment) {

    @Inject
    lateinit var viewModel: SearchViewModel
    private var cityAdapter: CityAdapter? = null
    private var list = MutableLiveData<List<String>>()
    private var text = ""
    private val listener: ShowFragmentListener?
        get() = activity?.let { it as ShowFragmentListener }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as App).component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            list.postValue(viewModel.getAllDataFromPref())
        }
        cityAdapter = CityAdapter { position -> showDetailFragment(position) }
        with(changedCityList) {
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
        cityAdapter?.updateCityList(list.value ?: emptyList())
        cityAdapter?.notifyDataSetChanged()
        textField.setEndIconOnClickListener {
            viewModel.getCityList(text)
        }
        viewModel.result.observe(viewLifecycleOwner, {
            viewModel.putDataFromPrefs(it.city_name)
            viewModel.saveDataToDB(it)
            showDetailFragment(it.city_name)
        })
    }

    override fun onResume() {
        super.onResume()
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                lifecycleScope.launch(Dispatchers.IO) {
                    text = s?.toString() ?: ""
                    val allKeys = viewModel.getAllDataFromPref()
                    val keys = allKeys.filter { it.contains(text, true) }
                    list.postValue(keys)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        list.observe(viewLifecycleOwner, {
            cityAdapter?.updateCityList(it)
            cityAdapter?.notifyDataSetChanged()
        })
    }

    private fun showDetailFragment(position: Int) {
        listener?.showDetailFragment(list.value!![position])
    }

    private fun showDetailFragment(name: String) {
        listener?.showDetailFragment(name)
    }

    override fun onDestroy() {
        super.onDestroy()
        cityAdapter = null
    }
}