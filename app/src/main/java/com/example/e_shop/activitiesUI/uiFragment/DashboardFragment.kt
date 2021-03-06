package com.example.e_shop.activitiesUI.uiFragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.e_shop.R
import com.example.e_shop.activitiesUI.activities.SettingsActivity

class DashboardFragment : Fragment() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    //if we want to use the option menu in fragment we need to add it
    setHasOptionsMenu(true)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    val root = inflater.inflate(R.layout.fragment_dashboard,container,false)
    val textView:TextView=root.findViewById(R.id.text_dashboard)
    textView.text = "This is Dashboard v"
    return root
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.dashboard_menu,menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    val id = item.itemId
    when(id){
      R.id.action_setting ->{
        startActivity(Intent(activity,SettingsActivity::class.java))
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }
}