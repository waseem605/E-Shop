package com.example.e_shop.activitiesUI.uiFragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import com.example.e_shop.R
import com.example.e_shop.activitiesUI.activities.AddProductActivity
import com.example.e_shop.firestore.FirestoreClass
import com.example.e_shop.model.Product

class ProductFragment : BaseFragment() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    //if we want to use the option menu in fragment we need to add it
    setHasOptionsMenu(true)
  }

  //A function to get the successful product list from cloud firestore.
  // @param productsList Will receive the product list from cloud firestore.

  fun successProductsListFromFireStore(productsList: ArrayList<Product>) {

    // Hide Progress dialog.
    hideProgressDialog()

    for (i in productsList){
      Log.i("Product Name ", i.title)
    }

    /*
    if (productsList.size > 0) {

      rv_my_product_item.visibility = View.VISIBLE
      tv_no_products_found.visibility = View.GONE

      rv_my_product_items.layoutManager = LinearLayoutManager(activity)
      rv_my_product_items.setHasFixedSize(true)

      val adapterProducts =
        MyProductsListAdapter(requireActivity(), productsList, this@ProductsFragment)
      rv_my_product_items.adapter = adapterProducts
    } else {
      rv_my_product_items.visibility = View.GONE
      tv_no_products_found.visibility = View.VISIBLE
    }

     */
  }

  private fun getProductListFromFireStore(){
    showProgressDialog(resources.getString(R.string.please_wait))
    FirestoreClass().getProductsList(this@ProductFragment)
  }

  override fun onResume() {
    super.onResume()
    getProductListFromFireStore()
  }

  override fun onCreateView(
    inflater: LayoutInflater,container: ViewGroup?, savedInstanceState: Bundle?
  ): View? {

    val root = inflater.inflate(R.layout.fragment_product,container,false)
    val textView:TextView=root.findViewById(R.id.tv_no_product_found)
    textView.text = "This is Product"
    return root
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.add_product_menu,menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    val id = item.itemId
    when(id){
      R.id.action_add_product ->{
        startActivity(Intent(activity, AddProductActivity::class.java))
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }
}