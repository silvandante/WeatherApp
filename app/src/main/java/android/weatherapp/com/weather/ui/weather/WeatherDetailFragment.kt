package android.weatherapp.com.weather.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.weatherapp.com.weather.R
import android.weatherapp.com.weather.databinding.FragmentWeatherDetailBinding
import android.weatherapp.com.weather.util.DateManager.getCurrentDay
import android.weatherapp.com.weather.util.DateManager.getDayOfWeek
import android.weatherapp.com.weather.util.DateManager.getTime
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Locale
import kotlin.math.roundToInt

@AndroidEntryPoint
class WeatherDetailFragment : Fragment() {

    private val args: WeatherDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentWeatherDetailBinding.inflate(layoutInflater)

        args.weatherObject.let {
            binding.tvDescDetail.text = it.weather[0].description.replaceFirstChar { word -> if (word.isLowerCase()) word.titlecase(Locale.getDefault()) else word.toString() }
            binding.tvCityName.text = if (it.isCurrentLocation) getString(R.string.city_name_current_location, it.name, it.sys.country) else getString(R.string.city_name, it.name, it.sys.country)
            binding.tvMaxMin.text = getString(R.string.temp_max_min, it.main.temp_max.roundToInt().toString(), it.main.temp_min.roundToInt().toString())
            binding.tvDetailDegree.text = getString(R.string.degree_detail, it.main.temp.roundToInt().toString())
            binding.tvOcean.text = it.main.sea_level.toString()
            binding.tvPressure.text = it.main.pressure.toString()
            binding.tvHumidity.text = it.main.humidity.toString()
            val calendar: Calendar = Calendar.getInstance()
            calendar.timeZone.rawOffset = 0
            val offset = if (it.timezone != 0) 3600 else 0
            calendar.timeInMillis = calendar.timeInMillis + ((it.timezone - offset) * 1000).toLong()
            binding.tvDayDetail.text = getCurrentDay(calendar.time)
            binding.tvDayWeekDetail.text = getDayOfWeek(calendar.time)
            binding.tvHour.text = getTime(calendar.time)
            Glide.with(binding.tvWeatherIcon)
                .load("http://openweathermap.org/img/wn/" + it.weather[0].icon + "@4x.png")
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.card_view_border)
                .into(binding.tvWeatherIcon)
        }

        return binding.root
    }
}
