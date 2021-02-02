package com.tulagames.junglequiz.model.client;

import com.tulagames.junglequiz.model.room.pojo.Respuesta;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RespuestaClient {

    @DELETE("respuesta/{id}")
    Call<Integer> deleteRespuesta(@Path("id") long id);

    @GET("respuesta")
    Call<List<Respuesta>> getListaRespuestas();

}
