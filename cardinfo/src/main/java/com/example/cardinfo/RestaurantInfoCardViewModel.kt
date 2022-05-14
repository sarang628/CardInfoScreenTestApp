package com.example.cardinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.torang_core.data.model.Restaurant
import com.example.torang_core.repository.FindRepository
import com.example.torang_core.repository.MapRepository
import com.example.torang_core.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 맛집정보카드 뷰모델 입니다.
 */
@HiltViewModel
class RestaurantInfoCardViewModel @Inject constructor(
    private val mapRepository: MapRepository,
    private val findRepository: FindRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(RestaurantInfoCardUiState())
    val uiState: StateFlow<RestaurantInfoCardUiState> = _uiState

    init {
        viewModelScope.launch {
            // 현재 포커스된 맛집 위치
            findRepository.getCurrentPosition().collectLatest { position ->
                _uiState.update {
                    it.copy(currentPosition = position)
                }
            }
        }

        viewModelScope.launch {
            // 맵 클릭 감지
            mapRepository.getClickMap().collectLatest { showCard ->
                _uiState.update {
                    it.copy(showCard = showCard)
                }
            }
        }

        viewModelScope.launch {
            // 검색된 맛집 리스트
            findRepository.getSearchedRestaurant().collectLatest { restaurants ->
                _uiState.update {
                    it.copy(restaurants = restaurants)
                }
            }
        }
    }

    /** 맛집 클릭 이벤트 */
    private val _clickCardInfo = MutableLiveData<Event<Restaurant>>()
    val clickCardInfo: LiveData<Event<Restaurant>> = _clickCardInfo


    /**
     * 카드에 있는 맛집 클릭
     */
    fun clickRestaurant(restaurantData: Restaurant) {
        _clickCardInfo.value = Event(restaurantData)
    }

    /**
     * 카드 스크롤하서 이동 시 알림
     */
    fun setCurrentPosition(position: Int) {
        viewModelScope.launch {
            findRepository.setCurrentPosition(position)
        }
    }
}