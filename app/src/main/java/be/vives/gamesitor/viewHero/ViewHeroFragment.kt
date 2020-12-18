package be.vives.gamesitor.viewHero

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import be.vives.gamesitor.R
import be.vives.gamesitor.databinding.ViewHeroFragmentBinding

class ViewHeroFragment : Fragment() {
    private lateinit var binding: ViewHeroFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.view_hero_fragment, container, false)
        return binding.root
    }
}
