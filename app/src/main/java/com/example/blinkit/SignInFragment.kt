package com.example.blinkit

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.blinkit.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    private lateinit var binding:FragmentSignInBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSignInBinding.inflate(layoutInflater)
        setStatusBarColor()
        getUserName()
        onContinueButtonClick()

        return binding.root
    }

//continue button to pass the number
    private  fun onContinueButtonClick(){
        binding.btnContinue.setOnClickListener {
            val number = binding.edtUserName.text.toString()
            if (number.isEmpty() || number.length != 10) {
                Toast.showToast(requireContext(), "Please Enter a Valid 10-Digit Phone Number")
            } else {
                val bundle = Bundle()
                bundle.putString("number", number)
                findNavController().navigate(R.id.action_signInFragment_to_OTPFragment2, bundle)
            }
        }
    }


    private fun setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity?.window?.apply {
                statusBarColor = ContextCompat.getColor(requireContext(), R.color.yellow)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
            }
        }
    }

    private  fun getUserName(){
        binding.edtUserName.addTextChangedListener (object : TextWatcher{
            override fun beforeTextChanged(number: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            // check if number is 10 then colour is green otherwise gray
            override fun onTextChanged(number: CharSequence?, start: Int, before: Int, count: Int) {
                val length = number?.length
                if (length == 10) {
                    binding.btnContinue.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green))
                } else {
                    val bundle =Bundle()
                    bundle.putString("number", number.toString())
                    binding.btnContinue.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.grayeshBule))
                }
            }



            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

}