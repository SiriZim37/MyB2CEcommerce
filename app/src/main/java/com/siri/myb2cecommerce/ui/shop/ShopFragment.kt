package com.siri.myb2cecommerce.ui.shop

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.siri.myb2cecommerce.R
import com.siri.myb2cecommerce.data.model.Category
import com.siri.myb2cecommerce.data.model.Product
import com.siri.myb2cecommerce.ui.home.adapter.BannerProductAdapter
import com.siri.myb2cecommerce.ui.shop.adapter.CategoryAdapter
import java.io.IOException

class ShopFragment: Fragment() {


    private lateinit var cateList:ArrayList<Category>
    private lateinit var coverProduct:ArrayList<Product>

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var coverProductAdapter: BannerProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_shop, container, false)
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val coverRecView_shopFrag : RecyclerView = view.findViewById(R.id.coverRecView_shopFrag)
        val categoriesRecView : RecyclerView = view.findViewById(R.id.categoriesRecView)


        cateList = arrayListOf()
        coverProduct = arrayListOf()

        setCoverData()
        setCategoryData()

        coverRecView_shopFrag.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL, false)
        coverRecView_shopFrag.setHasFixedSize(true)
        coverProductAdapter = BannerProductAdapter(activity as Context, coverProduct )
        coverRecView_shopFrag.adapter = coverProductAdapter


        categoriesRecView.layoutManager = GridLayoutManager(context,2,
            LinearLayoutManager.VERTICAL,false)
        categoriesRecView.setHasFixedSize(true)
        categoryAdapter = CategoryAdapter(activity as Context, cateList )
        categoriesRecView.adapter = categoryAdapter


        return view
    }

    private fun setCategoryData() {
        // Todo : Fix
     /*   cateList.add(Category("Clothing","https://images.unsplash.com/photo-1434389677669-e08b4cac3105?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=649&q=80"))
        cateList.add(Category("Jewelry","https://images.unsplash.com/photo-1515562141207-7a88fb7ce338?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1050&q=80"))
        cateList.add(Category("Hair Accessories","https://images.unsplash.com/photo-1626954079979-ec4f7b05e032?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80"))
        cateList.add(Category("Costume Accessories","https://images.unsplash.com/photo-1606760227091-3dd870d97f1d?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80"))
        cateList.add(Category("Handbag & Wallet Accessories","https://images.unsplash.com/photo-1601924928357-22d3b3abfcfb?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80"))
        cateList.add(Category("One-Pieces","https://images.unsplash.com/photo-1529171374461-2ea966dee0f5?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80"))
        cateList.add(Category("Masks","https://images.unsplash.com/photo-1586942593568-29361efcd571?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80"))
        cateList.add(Category("Glasses","https://images.unsplash.com/photo-1546180245-c59500ad14d0?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=675&q=80"))
*/
        cateList.add(Category("Home & Kitchen", "https://images.unsplash.com/photo-1571066811602-716837d6815c?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80")) // Ceramic Mug
        cateList.add(Category("Lighting", "https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80")) // โคมไฟ
        cateList.add(Category("Bath & Towels", "https://images.unsplash.com/photo-1604917877937-206eb4b8f7cb?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80")) // ผ้าเช็ดตัว
        cateList.add(Category("Furniture", "https://images.unsplash.com/photo-1524758631624-e2822e304c36?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80")) // โต๊ะไม้
        cateList.add(Category("Storage & Organization", "https://images.unsplash.com/photo-1602592585723-4d33de998b3e?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80")) // ตะกร้าเก็บของ
        cateList.add(Category("Office Supplies", "https://images.unsplash.com/photo-1558692024-d2b637de1414?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80")) // ที่วางหนังสือ
        cateList.add(Category("Tableware", "https://images.unsplash.com/photo-1562059390-a761a084768e?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80")) // ชุดจานเซรามิก
        cateList.add(Category("Bedding", "https://images.unsplash.com/photo-1616627982458-35c50a6481d0?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80")) // ผ้าห่มขนแกะ
        cateList.add(Category("Rugs & Carpets", "https://images.unsplash.com/photo-1610576660724-9b259e8583d6?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80")) // พรมตกแต่ง
        cateList.add(Category("Storage Furniture", "https://images.unsplash.com/photo-1620295747226-3c2b38038fd6?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80")) // ตู้เก็บของไม้


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

        val coverD: List<Product> = gson.fromJson(jsonFileString, listCoverType)

        coverD.forEachIndexed { _, person ->
            coverProduct.add(person)

        }


    }


}
