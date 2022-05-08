package com.example.cardinfoscreentestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.torang_core.data.model.SearchType
import com.example.torang_core.repository.FilterRepository
import com.example.torang_core.repository.FindRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var findRepository: FindRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        runBlocking {
            findRepository.searchRestaurant(searchType = SearchType.BOUND)
        }


    }
}