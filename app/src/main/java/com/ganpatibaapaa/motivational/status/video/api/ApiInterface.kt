package com.ganpatibaapaa.motivational.status.video.api

import com.ganpatibaapaa.motivational.status.video.model.Video
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiInterface {

    @GET("random_video_list.php")

    fun randomVideoList(@Query("per_page") perPage: Int = 15, @Query("page") page: Int = 0, @Header("Authorization") user: String = "") : Call<ArrayList<Video>>

    companion object{
//        var BASE_URL = "https://www.governmentmaster.com/krishna_motivational/api/"
        var BASE_URL = "https://videostatus.link/krishna_motivational/api/"

        fun create(): ApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}