package be.vives.gamesitor.stage

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
import timber.log.Timber
import java.util.*

class StageFragment : Fragment() {

    private lateinit var stageViewmodel: StageViewmodel
    private lateinit var binding: StageFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.stage_fragment, container, false)

        val application = requireNotNull(this.activity).application

        binding.lifecycleOwner = viewLifecycleOwner
        //  val dataSource = RepositoryExample()


        //    val viewModelFactory = StageViewmodelFactory(dataSource)

        stageViewmodel =
            ViewModelProvider(this).get(StageViewmodel::class.java)

        binding.viewmodel = stageViewmodel
        stageViewmodel.apply {
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
