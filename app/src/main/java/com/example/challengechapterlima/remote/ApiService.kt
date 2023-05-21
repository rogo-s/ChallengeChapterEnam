package com.example.challengechapterlima.remote

import com.example.challengechapterlima.model.DetailMovieResponse
import com.example.challengechapterlima.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/now_playing?api_key=d4e032a78d32940d67d6b1e0a21d82ca")
    suspend fun getAllMoviesNowPlaying(
        @Query("page") page: Int = 1
    ): MovieResponse

    @GET("movie/{movie_id}?api_key=d4e032a78d32940d67d6b1e0a21d82ca")
    suspend fun getDetailMovie(
        @Path("movie_id") movieId: Int
    ): DetailMovieResponse

}