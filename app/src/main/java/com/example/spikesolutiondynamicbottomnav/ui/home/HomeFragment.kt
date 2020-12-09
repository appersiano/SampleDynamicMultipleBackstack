package com.example.spikesolutiondynamicbottomnav.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.spikesolutiondynamicbottomnav.R
import com.example.spikesolutiondynamicbottomnav.ui.BaseFragment
import com.example.spikesolutiondynamicbottomnav.ui.navigation.nav_graph

class HomeFragment : BaseFragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        textView.text = this::class.java.simpleName

        val editText: EditText = root.findViewById(R.id.editTextTextArg)
        referenceId?.let {
            editText.setText(it)
        }

        val button: Button = root.findViewById(R.id.button)
        button.setOnClickListener {
            findNavController().navigate(
                nav_graph.dest.homeTwo, bundleOf(
                    ARG_REFERENCE_ID to editText.text.toString()
                )
            )
        }

        return root
    }
}