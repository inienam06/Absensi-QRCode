package com.abdulr.absensiqrcode.Controllers

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity

import com.abdulr.absensiqrcode.R
import kotlinx.android.synthetic.main.toolbar.*

class RiwayatFragment(activity: MainActivity) : Fragment() {
    private var listener: FragmentActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_riwayat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        btn_back.visibility = View.GONE
        title.text = "Riwayat"

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity) {
            listener = context as FragmentActivity
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
