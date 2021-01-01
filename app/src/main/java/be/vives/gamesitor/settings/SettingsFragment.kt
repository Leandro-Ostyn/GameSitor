package be.vives.gamesitor.settings

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import be.vives.gamesitor.R
import be.vives.gamesitor.constants.*
import be.vives.gamesitor.databinding.SettingsFragmentBinding
import java.util.*


class SettingsFragment : Fragment() {

    private val settingsViewModel: SettingsViewModel by lazy {
        val activity = requireNotNull(this.activity) {}
        ViewModelProvider(
            this,
            SettingsViewModel.SettingsViewModelFactory(activity.application)
        ).get(
            SettingsViewModel::class.java
        )
    }
    private lateinit var binding: SettingsFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val prefKeepLoggedIn = sharedPref.getBoolean(getString(R.string.KeepLoggedIn), false)
        val editor = sharedPref.edit()
        binding = DataBindingUtil.inflate(inflater, R.layout.settings_fragment, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = settingsViewModel
        binding.switchNotifications.setOnCheckedChangeListener { _: CompoundButton, b: Boolean ->
            settingsViewModel.settings.observe(viewLifecycleOwner, {
                if (it != null) {
                    it.setNotification = b
                    settingsViewModel.updateSettings(it)
                    val notifcationSettings = getNotificationSettings(requireContext())
                    if (b) {
                        notifcationSettings.setAlarm()
                    } else {
                        notifcationSettings.cancelAlarm()
                    }

                }
            })
        }
        binding.switchAnimation.setOnCheckedChangeListener { _: CompoundButton, b: Boolean ->
            settingsViewModel.settings.observe(viewLifecycleOwner, {
                if (it != null) {
                    it.hideAnimations = b
                    settingsViewModel.updateSettings(it)
                }
            })
        }
        binding.SwitchmusicOn.setOnCheckedChangeListener { _: CompoundButton, b: Boolean ->
            settingsViewModel.settings.observe(viewLifecycleOwner, { settings ->
                if (settings != null) {
                    settings.musicOn = b
                    if (b) {
                        if (!getMusic(requireContext()).isPlaying()) {
                            getMusic(requireContext()).create()
                            getMusic(requireContext()).playMusic()
                        }

                    } else {
                        getMusic(requireContext()).stopMusic()
                    }
                }
                settingsViewModel.updateSettings(settings)

            })
        }

        binding.PlayerNametxt.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                settingsViewModel.player.observe(viewLifecycleOwner, {
                    if (it != null) {
                        if (it.name != binding.PlayerNametxt.text.toString()) {
                            it.name = binding.PlayerNametxt.text.toString()
                            settingsViewModel.updatePlayer(it)
                            editor.putString(
                                getString(R.string.SitorName),
                                binding.PlayerNametxt.text.toString()
                            ).apply()
                            Toast.makeText(context, "Player name was updated ", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                })
                view?.let { context?.hideKeyboard(it) }
                return@OnKeyListener true
            }
            false
        })

        binding.btnPassword.setOnClickListener {
            val inputlist = mutableListOf<String>()
            showPassWordPrompt(
                "change your Password", "here you can change your password",
                OLD_PASSWORD, inputlist
            )
        }
        binding.SWLoggedIn.isChecked = prefKeepLoggedIn

        binding.SWLoggedIn.setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean ->
            sharedPref.edit().putBoolean(getString(R.string.KeepLoggedIn), b).apply()

        }
        binding.btnCancel.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToMainGameFragment())
        }
        return binding.root
    }

    private fun showPassWordPrompt(
        title: String,
        message: String,
        hint: String,
        inputList: MutableList<String>
    ) {

        val passwordEditText = EditText(context)
        passwordEditText.hint = hint
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setView(passwordEditText)
        builder.setPositiveButton("confirm") { dialogInterface: DialogInterface, i: Int ->
            if (hint == OLD_PASSWORD) {
                inputList.add(passwordEditText.text.toString())
                showPassWordPrompt("Set new password", "", NEW_PASSWORD, inputList)
            }
            if (hint == NEW_PASSWORD) {
                inputList.add(passwordEditText.text.toString())
                showPassWordPrompt("Confirm new password", "", CONFIRM_PASSWORD, inputList)
            }
            if (hint == CONFIRM_PASSWORD) {
                inputList.add(passwordEditText.text.toString())
                settingsViewModel.player.observe(viewLifecycleOwner, {
                    if (it != null) {
                        if (it.password == inputList[0]) {
                            if (inputList[1] == inputList[2]) {
                                it.password = inputList[2]
                                settingsViewModel.updatePlayer(it)
                                Toast.makeText(context, "Password updated !", Toast.LENGTH_SHORT)
                                    .show()
                                requireActivity().getPreferences(Context.MODE_PRIVATE).edit()
                                    .putString(getString(R.string.SitorPass), it.password).apply()
                            } else {
                                prompt()
                            }
                        } else {
                            prompt()
                        }
                    }
                })
            }
        }
        builder.setNegativeButton("cancel") { _, _ -> }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun prompt() {
        val builderInBuilder = AlertDialog.Builder(context)
        builderInBuilder.setCancelable(true)
        builderInBuilder.setTitle("SOMETHING WENT WRONG!")
        builderInBuilder.setMessage("One of the passwords didn't match")
        builderInBuilder.setNegativeButton("confirm") { _, _ -> }
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}