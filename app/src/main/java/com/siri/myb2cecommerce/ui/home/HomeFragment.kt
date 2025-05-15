package com.siri.myb2cecommerce.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.siri.myb2cecommerce.R
import com.siri.myb2cecommerce.data.model.Product
import com.siri.myb2cecommerce.databinding.FragmentHomeBinding
import com.siri.myb2cecommerce.ui.home.adapter.BannerProductAdapter
import com.siri.myb2cecommerce.ui.home.adapter.ProductAdapter
import com.siri.myb2cecommerce.ui.home.adapter.SaleProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
//    NewProducts.json

    lateinit var coverRecView: RecyclerView
    lateinit var newRecView: RecyclerView
    lateinit var saleRecView: RecyclerView
    lateinit var bannerProduct:ArrayList<Product>
    lateinit var newProduct:ArrayList<Product>
    lateinit var saleProduct:ArrayList<Product>

    lateinit var bannerProductAdapter: BannerProductAdapter
    lateinit var newProductAdapter: ProductAdapter
    lateinit var saleProductAdapter: SaleProductAdapter

    lateinit var animationView: LottieAnimationView

    lateinit var newLayout: LinearLayout
    lateinit var saleLayout: LinearLayout



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)



        bannerProduct = arrayListOf()
        newProduct = arrayListOf()
        saleProduct = arrayListOf()


        coverRecView = view.findViewById(R.id.coverRecView)
        newRecView = view.findViewById(R.id.newRecView)
        saleRecView = view.findViewById(R.id.saleRecView)
        newLayout = view.findViewById(R.id.newLayout)
        saleLayout = view.findViewById(R.id.saleLayout)
        animationView = view.findViewById(R.id.animationView)



        val visualSearchBtn_homePage: ImageView = view.findViewById(R.id.visualSearchBtn_homePage)

        hideLayout()

        setCoverData()
        setNewProductData()

        coverRecView.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL, false)
        coverRecView.setHasFixedSize(true)
        bannerProductAdapter = BannerProductAdapter(activity as Context, bannerProduct )
        coverRecView.adapter = bannerProductAdapter



        newRecView.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL, false)
        newRecView.setHasFixedSize(true)
        newProductAdapter = ProductAdapter(newProduct, activity as Context)
        newRecView.adapter = newProductAdapter


        saleRecView.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL, false)
        saleRecView.setHasFixedSize(true)
        saleProductAdapter = SaleProductAdapter(saleProduct, activity as Context)
        saleRecView.adapter = saleProductAdapter



        visualSearchBtn_homePage.setOnClickListener {
            startActivity(Intent(context, VisualSearchActivity::class.java))
        }





        showLayout()



        return view
    }



    private fun hideLayout(){
        animationView.playAnimation()
        animationView.loop(true)
        coverRecView.visibility = View.GONE
        newLayout.visibility = View.GONE
        saleLayout.visibility = View.GONE
        animationView.visibility = View.VISIBLE
    }
    private fun showLayout(){
        animationView.pauseAnimation()
        animationView.visibility = View.GONE
        coverRecView.visibility = View.VISIBLE
        newLayout.visibility = View.VISIBLE
        saleLayout.visibility = View.VISIBLE
    }

    fun getJsonData(context: Context, fileName: String): String? {

        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    private fun setCoverData() {

        val jsonFileString = context?.let {

            getJsonData(it, "BannerProducts.json")
        }
        val gson = Gson()

        val listCoverType = object : TypeToken<List<Product>>() {}.type

        var bnList: List<Product> = gson.fromJson(jsonFileString, listCoverType)

        bnList.forEachIndexed { idx, person ->

            bannerProduct.add(person)
            saleProduct.add(person)

        }


    }

    private fun setNewProductData() {

        val jsonFileString = context?.let {

            getJsonData(it, "NewProducts.json")
        }
        val gson = Gson()

        val listCoverType = object : TypeToken<List<Product>>() {}.type

        var coverD: List<Product> = gson.fromJson(jsonFileString, listCoverType)

        coverD.forEachIndexed { idx, person ->


            newProduct.add(person)


        }


    }

}