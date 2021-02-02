package com.tulagames.junglequiz.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tulagames.junglequiz.model.client.CartaClient;
import com.tulagames.junglequiz.model.client.RespuestaClient;
import com.tulagames.junglequiz.model.room.JungleQuizDatabase;
import com.tulagames.junglequiz.model.room.dao.CartaDao;
import com.tulagames.junglequiz.model.room.dao.RespuestaDao;
import com.tulagames.junglequiz.model.room.dao.UsuarioDao;
import com.tulagames.junglequiz.model.room.pojo.Carta;
import com.tulagames.junglequiz.model.room.pojo.Respuesta;
import com.tulagames.junglequiz.model.room.pojo.Usuario;
import com.tulagames.junglequiz.util.UtilThread;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    private CartaDao cartaDao;
    private RespuestaDao respuestaDao;
    private UsuarioDao usuarioDao;
    private LiveData<List<Carta>> liveListaCartas;
    private LiveData<List<Respuesta>> liveListaRespuestas;
    private LiveData<List<Usuario>> liveListaUsuarios;
    private List<String> correos = new ArrayList<>();
    private LiveData<Long> idCarta;
    private Retrofit retrofit;
    private CartaClient cartaClient;
    private RespuestaClient respuestaClient;
    private Context context;

    public Repository(Context context) {
        JungleQuizDatabase db = JungleQuizDatabase.getDatabase(context);
        cartaDao = db.getCartaDao();
        respuestaDao = db.getRespuestaDao();
        usuarioDao = db.getUsuarioDao();
        liveListaCartas = cartaDao.getAllCartas();
        liveListaUsuarios = usuarioDao.getAllUsuarios();
        liveListaRespuestas = respuestaDao.getAllRespuestas();
        idCarta = cartaDao.getIDCarta();
        /*--RETROFIT--*/
        String url ="https://www.tulagames.com/laravel/public/api/";
        retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        this.context = context;

    }
    /*-----------------------------------------CARTAS---------------------------------------------*/

    public void insert(Carta carta){
        UtilThread.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                cartaDao.insert(carta);
            }
        });
    }

    public void deleteCarta(Carta carta){
        UtilThread.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                cartaDao.delete(carta);
            }
        });
    }

    public void update(Carta carta){
        UtilThread.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                cartaDao.update(carta);
            }
        });
    }

    public LiveData<List<Carta>> getLiveListaCartas() {
        return liveListaCartas;
    }

    public void setLiveListaCartas(LiveData<List<Carta>> liveListaCartas) {
        this.liveListaCartas = liveListaCartas;
    }

    public LiveData<Long> getIdCarta() {
        return idCarta;
    }

    public void setIdCarta(LiveData<Long> idCarta) {
        this.idCarta = idCarta;
    }


    /*----------------------------------------RESPUESTAS------------------------------------------*/

    public void insert(Respuesta respuesta){
        UtilThread.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                respuestaDao.insert(respuesta);
            }
        });
    }


    public void update(Respuesta respuesta){
        UtilThread.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                respuestaDao.update(respuesta);
            }
        });
    }

    public void deleteRespuesta(Respuesta respuesta){
        UtilThread.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                respuestaDao.delete(respuesta);
            }
        });
    }

    public LiveData<List<Respuesta>> getLiveListaRespuestas() {
        return liveListaRespuestas;
    }

    public void setLiveListaRespuestas(LiveData<List<Respuesta>> liveListaRespuestas) {
        this.liveListaRespuestas = liveListaRespuestas;
    }

    /*-----------------------------------------USUARIOS-------------------------------------------*/

    public void insert(Usuario usuario){
        UtilThread.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                usuarioDao.insert(usuario);
            }
        });
    }

    public void update(Usuario usuario){
        UtilThread.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                usuarioDao.update(usuario);
            }
        });
    }

    public void deleteUsuario(long id){
        UtilThread.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                usuarioDao.delete(id);
            }
        });
    }

    public LiveData<List<Usuario>> getLiveListaUsuarios() {
        return liveListaUsuarios;
    }

    public void setLiveListaUsuarios(LiveData<List<Usuario>> liveListaUsuarios) {
        this.liveListaUsuarios = liveListaUsuarios;
    }

    /*-----------------------------------------RETROFIT-------------------------------------------*/

    public MutableLiveData<List<Respuesta>> getListaRespuestasRetrofit(){
        respuestaClient = retrofit.create(RespuestaClient.class);
        MutableLiveData<List<Respuesta>> liveDataRespuestas= new MutableLiveData<>();
        Call<List<Respuesta>> requestRespuesta = respuestaClient.getListaRespuestas();
        requestRespuesta.enqueue(new Callback<List<Respuesta>>() {
            @Override
            public void onResponse(Call<List<Respuesta>> call, Response<List<Respuesta>> response) {
                liveDataRespuestas.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Respuesta>> call, Throwable t) {
                Toast.makeText(context, "Se ha producido un fallo. Datos NO importados", Toast.LENGTH_LONG).show();
            }
        });
        return liveDataRespuestas;
    }

    public MutableLiveData<List<Carta>> getListaCartasRetrofit(){
        cartaClient = retrofit.create(CartaClient.class);
        MutableLiveData<List<Carta>> liveDataCarta = new MutableLiveData<>();
        Call<List<Carta>> requestCarta = cartaClient.getListaCartas();
        requestCarta.enqueue(new Callback<List<Carta>>() {
            @Override
            public void onResponse(Call<List<Carta>> call, Response<List<Carta>> response) {
                liveDataCarta.setValue(response.body());
            }
            @Override
            public void onFailure(Call<List<Carta>> call, Throwable t) {
                Toast.makeText(context, "Se ha producido un fallo. Datos NO importados", Toast.LENGTH_LONG).show();
            }
        });
        return liveDataCarta;
    }

    public void deleteCartaRetrofit(long id){
        cartaClient = retrofit.create(CartaClient.class);
        Call<Integer> requestCarta = cartaClient.deleteCarta(id);
        requestCarta.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }

    public void deleteRespuestaRetrofit(long id){
        respuestaClient = retrofit.create(RespuestaClient.class);
        Call<Integer> requestRespuesta = respuestaClient.deleteRespuesta(id);
        requestRespuesta.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
            }
        });
    }

    public List<String> getCorreos() {
        return correos;
    }

    public void recuperaCorreo(){
        UtilThread.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                ContentResolver cr = context.getContentResolver();
                Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
                if (cur.getCount() > 0) {
                    while (cur.moveToNext()) {
                        String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                        Cursor cur1 = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
                        while (cur1.moveToNext()) {
                            String email = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                            correos.add(email);
                        }
                        cur1.close();
                    }
                }
            }
        });
    }
}
