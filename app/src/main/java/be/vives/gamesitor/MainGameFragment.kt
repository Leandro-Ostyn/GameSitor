package be.vives.gamesitor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import be.vives.gamesitor.databinding.MainGameFragmentBinding

class MainGameFragment : Fragment() {

    private lateinit var binding: MainGameFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_game_fragment, container, false)
        // Inflate the layout for this fragment
        binding.btnBattle.setOnClickListener {
           findNavController().navigate(MainGameFragmentDirections.actionMainGameFragmentToStageFragment())
        }
        binding.btnEquipment.setOnClickListener {
           findNavController().navigate(MainGameFragmentDirections.actionMainGameFragmentToViewHeroFragment())
        }
        binding.btnInventory.setOnClickListener {
           findNavController().navigate(MainGameFragmentDirections.actionMainGameFragmentToBagFragment())
        }
        binding.btnShop.setOnClickListener {
            findNavController().navigate(MainGameFragmentDirections.actionMainGameFragmentToShopFragment())
        }
        return binding.root
    }

}