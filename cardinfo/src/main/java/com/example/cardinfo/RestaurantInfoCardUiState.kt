package com.example.cardinfo

import com.example.torang_core.data.model.Restaurant

/**
 * 맛집정보카드 UI 상태
 */
data class RestaurantInfoCardUiState(
    val currentPosition: Int = 0, // 현재 카드 위치
    val showCard: Boolean = false, // 카드 노출 여부
    val restaurants: List<Restaurant> = ArrayList() // 현재 검색된 맛집리스트
)