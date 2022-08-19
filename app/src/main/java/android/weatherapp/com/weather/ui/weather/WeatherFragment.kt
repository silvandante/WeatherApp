package android.weatherapp.com.weather.ui.weather

import android.os.Bundle
import android.weatherapp.com.weather.databinding.FragmentWeatherListBinding
import android.weatherapp.com.weather.model.WeatherObject
import android.weatherapp.com.weather.network.NetworkResult
import android.view.* // ktlint-disable no-wildcard-imports
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.* // ktlint-disable no-wildcard-imports
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private val viewModel: WeatherViewModel by activityViewModels()
    private var currentSearchCityName: String? = null
    private val weatherList: ArrayList<WeatherObject?> = arrayListOf()
    private lateinit var binding: FragmentWeatherListBinding

    private val adapter = CountryWeatherAdapter(
        weatherList,
        navigateToDetail = { weatherObject -> navigateToDetail(weatherObject) },
        searchWeatherByCityName = { cityName -> searchWeatherByCityName(cityName) },
        cleanSearchView = { cleanSearchView() }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherListBinding.inflate(layoutInflater)

        binding.recyclerViewPhoneList.adapter = adapter
        binding.recyclerViewPhoneList.layoutManager = LinearLayoutManager(context)

        binding.btSearch.setOnClickListener {
            val weatherCoroutineScope = CoroutineScope(Dispatchers.Main).launch {
                currentSearchCityName?.let {
                    binding.tvError.isVisible = true
                    binding.tvError.text = "Loading..."
                    viewModel.getWeatherByCityName(it)
                }
                if (!isActive) {
                    binding.btSearch.isVisible = false
                }
            }
            adapter.weatherCoroutineScope = weatherCoroutineScope
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.getFilter().filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.getFilter().filter(newText)
                return false
            }
        })

        viewModel.singleWeather.observe(
            viewLifecycleOwner,
            Observer { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        binding.tvError.isVisible = false
                        binding.progressBar2.isVisible = false
                        adapter.addSingleWeather(result.data)
                    }

                    is NetworkResult.Error -> {
                        binding.progressBar2.isVisible = false
                        binding.tvError.text = "Error getting weather!"
                        binding.tvError.isVisible = true
                    }

                    else -> {
                        binding.tvError.isVisible = true
                        binding.tvError.text = "Loading..."
                    }
                }
            }
        )

        viewModel.weathersList.observe(
            viewLifecycleOwner,
            Observer { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        binding.tvError.isVisible = false
                        adapter.updateData(result.data?.list ?: listOf())
                    }
                    is NetworkResult.Error -> {
                        binding.tvError.text = result.toString()
                        binding.tvError.isVisible = true
                    }
                    else -> {
                        binding.tvError.isVisible = true
                        binding.tvError.text = "Loading..."
                    }
                }
            }
        )

        getCountriesListWeather()

        return binding.root
    }

    private fun getCountriesListWeather() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Main) {
                viewModel.getCountries()?.let { countries ->
                    viewModel.getWeathers(
                        countries.joinToString(",") { country -> "${country.idApi}" }
                    )
                }
            }
        }
    }

    private fun navigateToDetail(weatherObject: WeatherObject) {
        findNavController().navigate(WeatherFragmentDirections.actionWeatherFragmentToWeatherDetailFragment(weatherObject))
    }

    private fun searchWeatherByCityName(cityName: String) {
        currentSearchCityName = cityName
        binding.btSearch.isVisible = true
    }

    private fun cleanSearchView() {
        binding.btSearch.isVisible = false
        binding.tvError.isVisible = false
    }
}
