package com.example.e_shop.activitiesUI.uiFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.e_shop.R

class OrdersFragment : Fragment() {

    //private lateinit var notificationsViewModel: NotificationsViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_orders,container,false)
        val textView:TextView=root.findViewById(R.id.text_notifications)
        textView.text = "This is order"
            return root
        }

}