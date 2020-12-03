package com.example.spikesolutiondynamicbottomnav.ui

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

}