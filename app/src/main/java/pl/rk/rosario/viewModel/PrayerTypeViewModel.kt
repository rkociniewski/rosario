package pl.rk.rosario.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class PrayerTypeViewModel(app: Application) : AndroidViewModel(app) {
    /** Application context for resource access */

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app =
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application
                PrayerTypeViewModel(app)
            }
        }
    }
}
