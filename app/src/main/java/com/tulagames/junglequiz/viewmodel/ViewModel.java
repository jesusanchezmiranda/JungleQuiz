package com.tulagames.junglequiz.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.tulagames.junglequiz.model.Repository;
import com.tulagames.junglequiz.model.room.pojo.Carta;
import com.tulagames.junglequiz.model.room.pojo.Respuesta;
import com.tulagames.junglequiz.model.room.pojo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ViewModel extends AndroidViewModel {

    private Repository repository;
    private Carta carta;
    private Respuesta respuesta;
    private Usuario usuario;
    private List<Carta> listaCartas;
    private List<Respuesta> listaRespuestas;
    private ArrayList<String> respuestasTemporales = new ArrayList<>();
    private boolean correeosRecuperados = false, editarJugar, borrar, administrar, editarCarta, jugando;//true es Editar False Jugar. True es borrar, false navegar

    public ViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    /*-----------------------------------------CARTAS---------------------------------------------*/

    public Carta getCarta() {
        return carta;
    }

    public void setCarta(Carta carta) {
        this.carta = carta;
    }

    public void insert(Carta carta) {
        repository.insert(carta);
    }

    public void update(Carta carta) {
        repository.update(carta);
    }

    public void deleteCarta(Carta carta) {
        repository.deleteCarta(carta);
    }

    public LiveData<List<Carta>> getLiveListaCartas() {
        return repository.getLiveListaCartas();
    }

    public LiveData<Long> getIdCarta() {
        return repository.getIdCarta();
    }

    public List<Carta> getListaCartas() {
        return listaCartas;
    }

    public void setListaCartas(List<Carta> listaCartas) {
        this.listaCartas = listaCartas;
    }

    /*----------------------------------------RESPUESTAS------------------------------------------*/

    public Respuesta getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Respuesta respuesta) {
        this.respuesta = respuesta;
    }

    public void insert(Respuesta respuesta) {
        repository.insert(respuesta);
    }

    public void update(Respuesta respuesta) {
        repository.update(respuesta);
    }

    public void deleteRespuesta(Respuesta respuesta) {
        repository.deleteRespuesta(respuesta);
    }

    public LiveData<List<Respuesta>> getLiveListaRespuestas() {
        return repository.getLiveListaRespuestas();
    }

    public void setLiveListaRespuestas(LiveData<List<Respuesta>> liveListaRespuestas) {
        repository.setLiveListaRespuestas(liveListaRespuestas);
    }

    public List<Respuesta> getListaRespuestas() {
        return listaRespuestas;
    }

    public void setListaRespuestas(List<Respuesta> listaRespuestas) {
        this.listaRespuestas = listaRespuestas;
    }

    public ArrayList<String> getRespuestasTemporales() {
        return respuestasTemporales;
    }

    public void setRespuestasTemporales(ArrayList<String> respuestasTemporales) {
        this.respuestasTemporales = respuestasTemporales;
    }

    /*-----------------------------------------USUARIOS-------------------------------------------*/

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void insert(Usuario usuario) {
        repository.insert(usuario);
    }

    public void update(Usuario usuario) {
        repository.update(usuario);
    }

    public void deleteUsuario(long id) {
        repository.deleteUsuario(id);
    }

    public LiveData<List<Usuario>> getLiveListaUsuarios() {
        return repository.getLiveListaUsuarios();
    }

    /*-----------------------------------------ESTADOS-------------------------------------------*/

    public boolean isEditarJugar() {
        return editarJugar;
    }

    public void setEditarJugar(boolean editarJugar) {
        this.editarJugar = editarJugar;
    }

    public boolean isBorrar() {
        return borrar;
    }

    public void setBorrar(boolean borrar) {
        this.borrar = borrar;
    }

    public boolean isAdministrar() {
        return administrar;
    }

    public void setAdministrar(boolean administrar) {
        this.administrar = administrar;
    }

    public boolean isEditarCarta() {
        return editarCarta;
    }

    public void setEditarCarta(boolean editarCarta) {
        this.editarCarta = editarCarta;
    }

    public boolean isJugando() {
        return jugando;
    }

    public void setJugando(boolean jugando) {
        this.jugando = jugando;
    }

    public boolean isCorreeosRecuperados() {
        return correeosRecuperados;
    }

    public void setCorreeosRecuperados(boolean correeosRecuperados) {
        this.correeosRecuperados = correeosRecuperados;
    }

    public void recuperaCorreo() {
        repository.recuperaCorreo();
    }

    public List<String> getCorreos() {
        return repository.getCorreos();
    }


    /*-----------------------------------------RETROFIT-------------------------------------------*/

    public MutableLiveData<List<Carta>> getListaCartasRetrofit() {
        return repository.getListaCartasRetrofit();
    }

    public MutableLiveData<List<Respuesta>> getListaRespuestasRetrofit() {
        return repository.getListaRespuestasRetrofit();
    }

    public void deleteCartaRetrofit(long id) {
        repository.deleteCartaRetrofit(id);
    }

    public void deleteRespuestaRetrofit(long id) {
        repository.deleteRespuestaRetrofit(id);
    }

}
