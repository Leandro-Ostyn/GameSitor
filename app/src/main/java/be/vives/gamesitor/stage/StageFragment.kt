package be.vives.gamesitor.stage

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import be.vives.gamesitor.R
import be.vives.gamesitor.databinding.StageFragmentBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import timber.log.Timber
import java.util.*

class StageFragment : Fragment() {

    private val stageViewModel: StageViewmodel by lazy {
        val activity = requireNotNull(this.activity) {}
        ViewModelProvider(this, StageViewmodel.StageViewmodelFactory(activity.application)).get(
            StageViewmodel::class.java
        )
    }
    private lateinit var binding: StageFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.stage_fragment, container, false)


        binding.lifecycleOwner = viewLifecycleOwner

        stageViewModel.items.observe(viewLifecycleOwner, Observer {
            for (item in it) {
                Timber.i(item.name + " this is the name")
            }
        })
        binding.viewmodel = stageViewModel
        stageViewModel.apply {
            binding.btnAttack.setOnClickListener() {
                calculateDamageToEnemy()

            }
            attacked.observe(viewLifecycleOwner, Observer {
                if (it) {

                    Handler(Looper.getMainLooper()).postDelayed({
                        calculateDamageToHero()
                    }, 1000)

                }
            })
            gameWon.observe(viewLifecycleOwner, Observer {
                if (it) {
                    Timber.i("i won ")
                }
            })
        }
        return binding.root
    }
}
