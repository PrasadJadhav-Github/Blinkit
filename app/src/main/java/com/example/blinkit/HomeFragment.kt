package com.example.blinkit

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.blinkit.adapters.AdapterCategory
import com.example.blinkit.databinding.FragmentHomeBinding
import com.example.blinkit.models.Category


class HomeFragment : Fragment() {

    private lateinit var binding :FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomeBinding.inflate(layoutInflater)
        setStatusBarColor()
        setAllCategories()
        return binding.root

    }

    private fun setAllCategories() {
        val categoryList=ArrayList<Category>()

        for (i in 0 until Constants.allProductsCategoryIcon.size){
            categoryList.add(Category(Constants.allProductsCategory[i],Constants.allProductsCategoryIcon[i]))
        }

        binding.recyclerviewCategory.adapter=AdapterCategory(categoryList)
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


}