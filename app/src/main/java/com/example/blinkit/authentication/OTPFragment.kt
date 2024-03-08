package com.example.blinkit.authentication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.blinkit.R
import com.example.blinkit.databinding.FragmentOTPBinding


class OTPFragment : Fragment() {
    private  lateinit var  userNumber :String
    private  lateinit var  binding :FragmentOTPBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =FragmentOTPBinding.inflate(layoutInflater,container,false)
        customizingEnterOtp()
        getUserNumber()
        onBackArrowClick()
        return binding.root
    }

    private fun getUserNumber() {
        val bundle = arguments
        userNumber = bundle?.getString("number", "") ?: ""
        binding.txtUserName.text = userNumber
    }

    private  fun onBackArrowClick(){
        binding.tableViewOtpFragment.setNavigationOnClickListener{
            findNavController().navigate(R.id.action_OTPFragment_to_signInFragment)
        }

    }


    private fun customizingEnterOtp(){
        val edtTexts = arrayOf(binding.Otp1,binding.Otp2,binding.Otp3,binding.Otp4,binding.Otp5,binding.Otp6)
        for (i in edtTexts.indices){
            edtTexts[i].addTextChangedListener(object :TextWatcher{
                //this 2 function is not usefull
                override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (s?.length==1){
                        if (i<edtTexts.size-1){
                            edtTexts[i+1].requestFocus()
                        }
                    }else if (s?.length==0){
                        if (i>0){
                            edtTexts[i-1].requestFocus()
                        }
                    }

                }
            })

        }
    }


}