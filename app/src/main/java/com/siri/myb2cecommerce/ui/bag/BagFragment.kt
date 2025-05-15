package com.siri.myb2cecommerce.ui.bag

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.siri.myb2cecommerce.R
import com.siri.myb2cecommerce.data.viewmodel.CartViewModel
import com.siri.myb2cecommerce.data.entitys.ProductEntity
import com.siri.myb2cecommerce.ui.bag.adapter.CartAdapter
import com.siri.myb2cecommerce.ui.bag.adapter.CartItemClickAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BagFragment : Fragment(), CartItemClickAdapter {

    lateinit var cartRecView: RecyclerView
    lateinit var cartAdapter: CartAdapter
    lateinit var animationView: LottieAnimationView
    lateinit var totalPriceBagFrag: TextView
    lateinit var Item: ArrayList<ProductEntity>
    var sum:Int = 0
    private val cartViewModel: CartViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_bag, container, false)

        // Fix : Initialize the ViewModel Dont need to initialil because use Hilt
        // cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)

        cartRecView = view.findViewById(R.id.cartRecView)
        animationView = view.findViewById(R.id.animationViewCartPage)
        totalPriceBagFrag = view.findViewById(R.id.totalPriceBagFrag)
        val bottomCartLayout: LinearLayout = view.findViewById(R.id.bottomCartLayout)
        val emptyBagMsgLayout: LinearLayout = view.findViewById(R.id.emptyBagMsgLayout)
        val MybagText: TextView = view.findViewById(R.id.MybagText)
        Item = arrayListOf()


        animationView.playAnimation()
        animationView.loop(true)
        bottomCartLayout.visibility = View.GONE
        MybagText.visibility = View.GONE
        emptyBagMsgLayout.visibility = View.VISIBLE


        cartRecView.layoutManager = LinearLayoutManager(context)
        cartAdapter = CartAdapter(activity as Context, this )
        cartRecView.adapter = cartAdapter



        cartViewModel.allproducts.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                cartAdapter.updateList(it)
                Item.clear()
                sum = 0
                Item.addAll(it)
            }
        })


        cartViewModel.allproducts.observe(viewLifecycleOwner, Observer {List ->
            List?.let {
                cartAdapter.updateList(it)
                Item.clear()
                sum = 0
                Item.addAll(it)
            }

            if (List.size == 0){
                animationView.playAnimation()
                animationView.loop(true)
                bottomCartLayout.visibility = View.GONE
                MybagText.visibility = View.GONE
                emptyBagMsgLayout.visibility = View.VISIBLE

            }
            else{
                emptyBagMsgLayout.visibility = View.GONE
                bottomCartLayout.visibility = View.VISIBLE
                MybagText.visibility = View.VISIBLE
                animationView.pauseAnimation()
            }

            Item.forEach {
                sum += it.price
            }
            totalPriceBagFrag.text = "$" + sum
        })




        return view
    }

    override fun onItemDeleteClick(product: ProductEntity) {
        cartViewModel.deleteCart(product)
        Toast.makeText(context,"Removed From Bag", Toast.LENGTH_SHORT).show()
    }

    override fun onItemUpdateClick(product: ProductEntity) {
        cartViewModel.updateCart(product)
    }


}