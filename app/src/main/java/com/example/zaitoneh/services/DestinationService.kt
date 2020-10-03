package com.smartherd.globofly.services

import com.example.zaitoneh.database.Item
import kotlinx.coroutines.Deferred

import retrofit2.Call
import retrofit2.http.*

interface DestinationService {

    @GET("destination")
    fun getDestinationList(@QueryMap filter: HashMap<String, String>): Call<List<Item>>

    @GET("destination/{id}")
    fun getDestination(@Path("id") id: Int): Call<Item>

    //@POST("destination")
    @POST("newItem")
    fun addDestination(@Body newDestination: Item): Call<Item>
    @GET("listItemsREST")
    fun getProperties():
    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result
            Deferred<List<Item>>

    @FormUrlEncoded
    @PUT("destination/{id}")
    fun updateDestination(
        @Path("id") id: Int,
        @Field("city") city: String,
        @Field("description") desc: String,
        @Field("country") country: String
    ): Call<Item>

    @DELETE("destination/{id}")
    fun deleteDestination(@Path("id") id: Int): Call<Unit>
}