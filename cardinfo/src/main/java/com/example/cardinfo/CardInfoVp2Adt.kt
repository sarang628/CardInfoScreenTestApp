package com.example.cardinfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.cardinfo.databinding.FragmentTorangCardVpItemBinding
import com.example.torang_core.data.model.Restaurant
import com.example.torang_core.util.Logger

/**
 * [CardInfoViewHolder]
 */
class CardInfoVp2Adt(val cardInfoViewModel: CardInfoViewModel, val lifecycleOwner: LifecycleOwner) :
    RecyclerView.Adapter<CardInfoViewHolder>() {

    private var restaurants: List<Restaurant> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardInfoViewHolder {
        return CardInfoViewHolder(
            FragmentTorangCardVpItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ).apply {
                cardInfoViewModel = this@CardInfoVp2Adt.cardInfoViewModel
                lifecycleOwner = this@CardInfoVp2Adt.lifecycleOwner
            }
        )
    }

    override fun onBindViewHolder(holder: CardInfoViewHolder, position: Int) {
        holder.setData(restaurants[position])
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    fun setRestaurants(it: List<Restaurant>?) {
        Logger.v("restaurant size = ${it?.size}")
        it?.let {
            restaurants = it
            notifyDataSetChanged()
        }
    }
}

class CardInfoViewHolder(
    val binding: FragmentTorangCardVpItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun setData(restaurantData: Restaurant) {
        binding.restaurant = restaurantData
    }
}