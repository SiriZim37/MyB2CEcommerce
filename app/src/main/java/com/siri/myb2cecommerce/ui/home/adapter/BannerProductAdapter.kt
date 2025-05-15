package com.siri.myb2cecommerce.ui.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.siri.myb2cecommerce.R
import com.siri.myb2cecommerce.data.model.Product
import com.siri.myb2cecommerce.ui.home.ProductDetailsActivity

class BannerProductAdapter (var ctx: Context, private val bannerProductList: ArrayList<Product>) :
    RecyclerView.Adapter<BannerProductAdapter.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val productView = LayoutInflater.from(parent.context).inflate(R.layout.banner_cover,parent,false)
        return ViewHolder(productView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {



        val bannerPro: Product = bannerProductList[position]

        holder.productNoteCover.text = bannerPro.productNote

        Glide.with(ctx)
            .load(bannerPro.productImage)
            .into(holder.productImage_coverPage)


        holder.productCheck_coverPage.setOnClickListener {

            goDetailsPage(position)


        }

    }




    override fun getItemCount(): Int {
        return bannerProductList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val productImage_coverPage: ImageView = itemView.findViewById(R.id.productImage_coverPage)
        val productNoteCover: TextView = itemView.findViewById(R.id.productNoteCover)
        val productCheck_coverPage: Button = itemView.findViewById(R.id.productCheck_coverPage)


    }

    private fun goDetailsPage(position: Int) {

        val intent = Intent(ctx , ProductDetailsActivity::class.java)
        intent.putExtra("ProductIndex", position)
        intent.putExtra("ProductFrom", "Banner")
        ctx.startActivity(intent)
    }
}