package be.vives.gamesitor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import be.vives.gamesitor.databinding.ViewHeroFragmentBinding

class ViewHeroFragment : Fragment() {
    private lateinit var binding: ViewHeroFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.view_hero_fragment, container, false)
        return binding.root
    }
}
