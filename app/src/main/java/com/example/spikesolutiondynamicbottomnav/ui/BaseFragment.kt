package com.example.spikesolutiondynamicbottomnav.ui

import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment


abstract class BaseFragment : Fragment() {
    companion object {
        const val ARG_REFERENCE_ID = "arg_reference_id"
        const val ARG_REFERENCE_TYPE = "arg_reference_type"
    }

    val referenceId by lazy {
        arguments?.getString(ARG_REFERENCE_ID)
    }

    val referenceType by lazy {
        arguments?.getString(ARG_REFERENCE_TYPE)
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        val callback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                Log.d(
//                    "DEBUG",
//                    "onBackPressed() call"
//                )
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
//    }

}