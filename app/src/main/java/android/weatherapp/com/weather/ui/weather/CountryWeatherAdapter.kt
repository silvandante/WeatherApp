package android.weatherapp.com.weather.ui.weather

import android.weatherapp.com.weather.R
import android.weatherapp.com.weather.databinding.WeatherItemBinding
import android.weatherapp.com.weather.model.WeatherObject
import android.weatherapp.com.weather.util.DateManager.getCurrentDay
import android.weatherapp.com.weather.util.DateManager.getDayOfWeek
import android.weatherapp.com.weather.util.removeNonSpacingMarks
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.coroutines.Job
import java.util.Calendar
import java.util.Locale
import kotlin.math.roundToInt

class CountryWeatherAdapter(
    private var weatherList: ArrayList<WeatherObject?>,
    private val navigateToDetail: (weatherObject: WeatherObject) -> Unit,
    private val searchWeatherByCityName: (cityName: String) -> Unit,
    private val cleanSearchView: () -> Unit
) : RecyclerView.Adapter<CountryWeatherAdapter.CountryAdapterViewHolder>() {

    var weatherCoroutineScope: Job? = null

    private val oldWeathersList: ArrayList<WeatherObject> = arrayListOf()

    private lateinit var countryWeatherRecyclerView: RecyclerView

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        countryWeatherRecyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryAdapterViewHolder {
        val binding = WeatherItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CountryAdapterViewHolder(binding, navigateToDetail)
    }

    override fun onBindViewHolder(holder: CountryAdapterViewHolder, position: Int) {
        val currentItem = weatherList[position]
        holder.bind(currentItem)
    }

    fun updateData(weathersList: List<WeatherObject>) {
        val alreadyContains = oldWeathersList.toSet().containsAll(weathersList.toSet())

        if (!alreadyContains) {
            val difference = weathersList.toSet().minus(oldWeathersList.toSet())
            weatherList.addAll(difference)
            oldWeathersList.addAll(difference)
            notifyDataSetChanged()
        }
    }

    fun addSingleWeather(weatherObject: WeatherObject?) {
        weatherObject?.let {
            val result = oldWeathersList.filter {
                it.id == weatherObject.id
            }

            if (result.isEmpty()) {
                weatherList.add(0, weatherObject)
                notifyItemInserted(0)
                countryWeatherRecyclerView.scrollToPosition(0)
                oldWeathersList.add(0, weatherObject)
            }
        }
    }

    fun getFilter(): Filter {
        return countryWeatherFilter
    }

    private val countryWeatherFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            weatherCoroutineScope?.cancel()

            val filteredList: ArrayList<WeatherObject> = arrayListOf()
            if (constraint == null || constraint.isEmpty()) {
                oldWeathersList.let { filteredList.addAll(it) }
            } else {
                val query = constraint.toString().trim().toLowerCase().removeNonSpacingMarks()
                oldWeathersList.forEach {
                    if (it.name.toLowerCase(Locale.ROOT).removeNonSpacingMarks().contains(query)) {
                        filteredList.add(it)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results?.values is ArrayList<*>) {
                weatherList.clear()
                weatherList.addAll(results.values as ArrayList<WeatherObject>)
                notifyDataSetChanged()

                if (weatherList.isEmpty()) {
                    searchWeatherByCityName(constraint.toString())
                } else {
                    cleanSearchView()
                }
            }
        }
    }

    class CountryAdapterViewHolder(
        private val binding: WeatherItemBinding,
        private val navigateToDetail: (weatherObject: WeatherObject) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(weatherObject: WeatherObject?) {
            binding.apply {
                weatherObject?.let { weatherObject ->
                    root.setOnClickListener {
                        navigateToDetail(weatherObject)
                    }
                    tvCity.text = if (weatherObject.isCurrentLocation)
                        binding.root.context.getString(R.string.city_name_current_location, weatherObject.name, weatherObject.sys.country)
                    else
                        binding.root.context.getString(R.string.city_name, weatherObject.name, weatherObject.sys.country)
                    tvDegree.text = binding.root.context.getString(R.string.degree_detail, weatherObject.main.temp.roundToInt().toString())
                    tvDesc.text = weatherObject.weather[0].description.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                    tvHumidity.text = weatherObject.main.humidity.toString()
                    val calendar: Calendar = Calendar.getInstance()
                    calendar.timeZone.rawOffset = 0
                    val offset = if (weatherObject.timezone != 0) 3600 else 0
                    calendar.timeInMillis = calendar.timeInMillis + ((weatherObject.timezone - offset) * 1000).toLong()
                    tvDay.text = getCurrentDay(calendar.time)
                    tvDayName.text = getDayOfWeek(calendar.time)
                    Glide.with(itemView)
                        .load("http://openweathermap.org/img/wn/" + weatherObject.weather[0].icon + "@2x.png")
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .error(R.drawable.card_view_border)
                        .into(imageWeather)
                } ?: run {
                    tvCity.text = "No data! Sorry!"
                }
            }
        }
    }

    override fun getItemCount(): Int = weatherList.size
}
