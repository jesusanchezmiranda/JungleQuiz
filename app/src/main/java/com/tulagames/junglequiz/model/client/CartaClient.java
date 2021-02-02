package com.tulagames.junglequiz.model.client;

import com.tulagames.junglequiz.model.room.pojo.Carta;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CartaClient {

    @DELETE("carta/{id}")
    Call<Integer> deleteCarta(@Path("id") long id);

    @GET("carta")
    Call<List<Carta>> getListaCartas();

}
