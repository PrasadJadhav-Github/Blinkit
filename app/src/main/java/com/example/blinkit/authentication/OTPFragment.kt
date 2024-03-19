package com.example.blinkit.authentication

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.blinkit.R
import com.example.blinkit.Utils
import com.example.blinkit.activity.UsersMainActivity
import com.example.blinkit.databinding.FragmentOTPBinding
import com.example.blinkit.models.Users
import com.example.blinkit.viewmodels.AuthenticationViewModel
import kotlinx.coroutines.launch


class OTPFragment : Fragment() {
    private  val  viewmodel :AuthenticationViewModel by viewModels()
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
        sendOTP()
        onLoginButtonClick()
        return binding.root
    }

    private fun onLoginButtonClick() {
        binding.btnLogin.setOnClickListener {
            Utils.showDialog(requireContext(),"Signing you....")
            val edtTexts = arrayOf(binding.Otp1,binding.Otp2,binding.Otp3,binding.Otp4,binding.Otp5,binding.Otp6)
           val otp= edtTexts.joinToString (""){it.text.toString() }

            if ( otp.length < edtTexts.size){
                Utils.showToast(requireContext(),"Please enter valid otp...")
            }
            else{
                edtTexts.forEach { it.text?.clear();it.clearFocus() }
                verifyOtp(otp)
            }
        }
    }

    private fun verifyOtp(otp: String) {
        val user =Users(uid =Utils.getCurrentUserId(), userPhoneNumber = userNumber, userAddress = null)

    viewmodel.signInWithPhoneAuthCredential(otp, userNumber,user)
        lifecycleScope.launch {
        viewmodel.isSignedInSuccessful.collect{
            if (it){
                Utils.hideDialog()
                Utils.showToast(requireContext(),"Logged in...")
                startActivity(Intent(requireActivity(),UsersMainActivity::class.java))
                requireActivity().finish()
            }
        }
        }
    }


    private  fun sendOTP(){
        Utils.showDialog(requireContext(),"Sending OTP.....")
        viewmodel.apply {
            sendOTP(userNumber,requireActivity())
            lifecycleScope.launch {
                otpSend.collect{
                    if (it== true){
                        Utils.hideDialog()
                        Utils.showToast(requireContext(),"OTP sent...")
                    }
                }
            }

        }

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