package be.vives.gamesitor.login

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import be.vives.gamesitor.R
import be.vives.gamesitor.databinding.FragmentLoginBinding
import be.vives.gamesitor.models.loginModels.LoggedInUserView
import kotlinx.android.synthetic.main.fragment_login.*
import timber.log.Timber
import java.util.*


class LoginFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val prefUserName =
            sharedPref.getString(getString(R.string.SitorName), getString(R.string.SitorName))
        val prefPassword =
            sharedPref.getString(getString(R.string.SitorPass), getString(R.string.SitorPass))
        val prefKeepLoggedIn = sharedPref.getBoolean(getString(R.string.KeepLoggedIn), false)
        val username = binding.username
        val password = binding.password
        val login = binding.login
        val loading = binding.loading

        if (prefUserName != getString(R.string.SitorName) && prefPassword != getString(R.string.SitorPass)) {
            username.setText(prefUserName)
            password.setText(prefPassword)
            binding.checkRemmember.isChecked = true
            if (prefPassword != null && prefUserName != null && prefKeepLoggedIn) {
                binding.chkLoggedIn.isChecked = true
                Handler().postDelayed( { performLogin(prefUserName, prefPassword) }, 500)

            }
        }
        loginViewModel =
            ViewModelProvider(this, LoginViewModelFactory(requireActivity().application))
                .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(viewLifecycleOwner, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(viewLifecycleOwner, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }
        if (password.text.toString() != "") {
            loginViewModel.loginDataChanged(username.text.toString(), password.text.toString())
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            login.setOnClickListener {
                performLogin(username.text.toString(), password.text.toString())
            }
        }


        loginViewModel.loggedInPlayer.observe(viewLifecycleOwner, {
            if (it != null) {
                loginViewModel.setPlayerForGame(it)
                findNavController().navigate(LoginFragmentDirections.actionLoginFragment2ToLoadingFragment())
            }
        })

        return binding.root
    }

    private fun showErrorPrompt(title: String, message: String, register: Boolean) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(
            "Confirm"
        ) { _, _ ->
            if (register) {
                prepareInventory().observe(viewLifecycleOwner,{inventoryId ->
                    if (inventoryId!=""){
                       loginViewModel.register(username.text.toString(),password.text.toString(),inventoryId)
                        loginViewModel.setRegistering()
                    }
                })
            }
            binding.loading.visibility = View.GONE
        }
        if (register) {
            builder.setNegativeButton(
                android.R.string.cancel
            ) { dialog, which ->
                binding.loading.visibility = View.GONE
            }
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        Toast.makeText(
            requireContext(),
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(requireContext(), errorString, Toast.LENGTH_SHORT).show()
    }

    private fun performLogin(username: String, password: String) {
        if (binding.chkLoggedIn.isChecked) {
            val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putBoolean(getString(R.string.KeepLoggedIn), true)
            editor.apply()
            binding.checkRemmember.isChecked = true

        }
        if (binding.checkRemmember.isChecked) {
            val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString(getString(R.string.SitorName), username)
            editor.putString(getString(R.string.SitorPass), password)
            editor.apply()
        } else{
            val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString(getString(R.string.SitorName), getString(R.string.SitorName))
            editor.putString(getString(R.string.SitorPass), getString(R.string.SitorPass))
            editor.apply()
        }

        loading.visibility = View.VISIBLE
        loginViewModel.getPlayer(username)
            .observe(viewLifecycleOwner,
                { databasePlayer ->
                    if (databasePlayer != null) {
                        if (password == databasePlayer.password) {
                            loginViewModel.login(databasePlayer)
                        } else {
                            showErrorPrompt(
                                "Password is Wrong",
                                "Password is Case sensitive !",
                                false
                            )
                        }

                    } else {
                        showErrorPrompt(
                            "Player Not found!",
                            "If you want to register click on confirm, else try again and press cancel",
                            true
                        )
                    }
                })
    }

    private fun  prepareInventory(): MutableLiveData<String> {
        val inventoryid = MutableLiveData<String>()
        loginViewModel.prepareInventoryForPlayer()
            .observe(viewLifecycleOwner,
                { list ->
                    loginViewModel.createInventoryForPlayer()
                        .observe(viewLifecycleOwner,
                            {
                                if (it != null) {
                                  inventoryid.postValue(it)
                                }
                            })
                })
        return inventoryid
    }

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }
}

