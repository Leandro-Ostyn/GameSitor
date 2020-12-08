package be.vives.gamesitor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import be.vives.gamesitor.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    lateinit var viewModelExample: ViewModelExample
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val navController = this.findNavController(R.id.myNavHostFragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        viewModelExample = ViewModelProvider(this).get(ViewModelExample::class.java)
        viewModelExample.repositoryExample.gategories.observe(this, Observer {
            for (category in it){
                Timber.i(category.name)
            }
        })

        viewModelExample.repositoryExample.backgrounds.observe(this, Observer {
            for (backgrounds in it){
                Timber.i(backgrounds.name)
            }
        })

        viewModelExample.repositoryExample.getCategories()
        viewModelExample.repositoryExample.getBackgrounds()
    //    viewModelExample.repositoryExample.getStats(1)

     //   viewModelExample.stats.observe(this, Observer {
     //       Timber.i("${it.lifepoints} this was the result of stats ")
     //   })
       /// viewModelExample.setStatsId(1)
     //   viewModelExample.setTypeId(1)

    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelExample.cancelJobs()
    }
}