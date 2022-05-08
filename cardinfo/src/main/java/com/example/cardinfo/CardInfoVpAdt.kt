package com.example.cardinfo

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.example.torang_core.data.model.Restaurant


abstract class CardInfoVpAdt(fragment: Fragment) : FragmentStateAdapter(fragment) {
    abstract fun clickRestaurant(restaurant: Restaurant)
    private var restaurants: List<Restaurant> = ArrayList()
    fun setRestaurants(restaurants: List<Restaurant>) {
        this.restaurants = restaurants
        notifyDataSetChanged()
    }

    override fun createFragment(position: Int): Fragment {
        val torangCardVpItem = CardInfoItem()
        torangCardVpItem.setRestaurant(restaurants[position])
        torangCardVpItem.setOnClickListener { view: View? ->
            clickRestaurant(
                restaurants[position]
            )
        }
        return torangCardVpItem
    }

    override fun onBindViewHolder(
        holder: FragmentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }
}