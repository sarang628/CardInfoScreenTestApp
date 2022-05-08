package com.example.cardinfo

import com.example.torang_core.data.model.Restaurant

data class CardInfoUiState(
    val currentPosition: Int = 0,
    val showCard: Boolean = false,
    val restaurants: List<Restaurant> = ArrayList()
)
