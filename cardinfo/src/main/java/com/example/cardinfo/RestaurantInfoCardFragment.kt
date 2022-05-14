package com.example.cardinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.cardinfo.databinding.FragmentRestaurantInfoCardBinding
import com.example.torang_core.navigation.RestaurantDetailNavigation
import com.example.torang_core.util.EventObserver
import com.example.torang_core.util.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 맛집정보카드 화면입니다.
 * [FragmentViewPagerBinding]
 * [CardInfoVp2Adt]
 */
@AndroidEntryPoint
class RestaurantInfoCardFragment : Fragment() {
    /** 맛집정보카드 뷰모델 */
    private val viewModel: RestaurantInfoCardViewModel by viewModels()

    /** 카드정보 뷰페이저 아답터 */
    private lateinit var adapter: CardInfoVp2Adt

    /** 맛집 상세화며 이동 네비게이션 */
    @Inject
    lateinit var restaurantDetailNavigation: RestaurantDetailNavigation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 페이지 아답터 초기화
        adapter = CardInfoVp2Adt(viewModel, viewLifecycleOwner)

        // 바인딩 초기화
        val binding = FragmentRestaurantInfoCardBinding.inflate(layoutInflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                // 뷰페이저 설정
                vp.apply {
                    clipToPadding = false
                    setPageTransformer(MarginPageTransformer(50))
                    adapter = this@RestaurantInfoCardFragment.adapter
                }
                // 뷰페이저 페이지 리스너 설정
                setPagerScrollListener(vp)
            }

        //UI 구독
        subScribeUi(binding)

        return binding.root
    }

    /**
     * UI 구독
     */
    private fun subScribeUi(binding: FragmentRestaurantInfoCardBinding) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                //UI 상태 정보
                viewModel.uiState.collect {
                    moveCardPosition(binding.vp, it.currentPosition)
                    showCard(binding.vp, it.showCard)
                    adapter.setRestaurants(it.restaurants)
                }
            }
        }

        //카드 클릭 시 상세화며 이동
        viewModel.clickCardInfo.observe(viewLifecycleOwner, EventObserver {
            val mode = requireActivity().intent.getIntExtra("mode", 0)
            Logger.d("click restaurant $it")
            if (mode == 0) {
                restaurantDetailNavigation.go(requireContext(), it.restaurant_id)
            } else {
                requireActivity().onBackPressed()
            }
        })
    }

    /**
     * 페이지 스크롤 시 이벤트 전달합니다.
     */
    private fun setPagerScrollListener(vp: ViewPager2) {
        vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.setCurrentPosition(position)
            }
        })
    }

    /**
     * 카드 애니메이션으로 보여줍니다.
     */
    private fun showCard(view: View, showCard: Boolean) {
        if (
            (view.visibility == View.VISIBLE && !showCard)
            || (view.visibility == View.INVISIBLE && showCard)
        ) {
            Logger.v("ignore showcard! : visibility = ${view.visibility} showCard =  $showCard")
            return
        }

        val animation = AnimationUtils.loadAnimation(
            context, if (showCard) R.anim.slide_card_up else R.anim.slide_card_down
        )
        view.startAnimation(animation)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                if (!showCard) view.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animation) {
                if (showCard) view.visibility = View.INVISIBLE
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
    }

    /**
     * 카드 위치를 이동시킵니다.
     */
    private fun moveCardPosition(viewPater: ViewPager2, position: Int) {
        if (viewPater.currentItem != position) {
            viewPater.post {
                viewPater.setCurrentItem(position, true)
            }
        }
    }
}