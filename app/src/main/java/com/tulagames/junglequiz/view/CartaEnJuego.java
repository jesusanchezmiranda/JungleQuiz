package com.tulagames.junglequiz.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.tulagames.junglequiz.R;
import com.tulagames.junglequiz.model.room.pojo.Carta;
import com.tulagames.junglequiz.model.room.pojo.Respuesta;
import com.tulagames.junglequiz.viewmodel.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Clase perteneciente al fragmento fg_carta_en_juego. Es la clase encargada de mostrar la carta que
 * se está jugando. Es por decirlo de alguna manera, parte del tablero de juego.
 * */
public class CartaEnJuego extends Fragment {

    private ViewModel viewModel;
    private List<Carta> listaCartas = new ArrayList<>();

    public CartaEnJuego() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_carta_en_juego, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navC = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        /*En este punto recuperamos los contactos*/
        if (!viewModel.isCorreeosRecuperados()){
            viewModel.recuperaCorreo();
            viewModel.setCorreeosRecuperados(true);
        }
        cargaJugador(view);
        /*Este if sirve para diferenciar si es la primera vez que entramos a este fragmento. En caso
        * de ser así, recuperamos la lista de cartas, previamente barajada en el fragmento anterior.
        * Si volvemos a este fragmento desde la zona de las preguntas, significa que estamos jugando.
        * Así que no necesitamos recuperar el mazo*/
        if (!viewModel.isJugando()) {
            listaCartas = viewModel.getListaCartas();
        }
        cargaCarta();
        botonCambiar(view);
        botonPreguntas(view, navC);
    }

    /**
     * Recoge la primera carta de la lista de cartas, la aisla y carga sus respuestas. A continuación
     * muestra sus datos.
     * */
    private void cargaCarta() {
        TextView tvDescripcion = getView().findViewById(R.id.tvDescripcion);
        TextView tvCartaTitulo = getView().findViewById(R.id.carta_titulo);
        ImageView ivCarta = getView().findViewById(R.id.carta_imagen);
        viewModel.setCarta(listaCartas.get(0));
        listaCartas.remove(0);
        cargaRespuestas(viewModel.getCarta());
        tvDescripcion.setText(viewModel.getCarta().getDescripcion());
        tvCartaTitulo.setText(viewModel.getCarta().getNombreAnimal());
        Glide.with(getContext()).load(viewModel.getCarta().getUrlfoto()).into(ivCarta);

    }

    /**
     * Carga las respuesta de una carta que se le pasa.
     * **/
    private void cargaRespuestas(Carta carta) {
        viewModel.getLiveListaRespuestas().observe(getActivity(), new Observer<List<Respuesta>>() {
            @Override
            public void onChanged(List<Respuesta> respuestas) {
                Respuesta respuestaAux = new Respuesta();
                for (int i = 0; i < respuestas.size(); i++) {
                    if (respuestas.get(i).getIdCarta() == carta.getId()) {
                        respuestaAux = respuestas.get(i);
                    }
                    viewModel.setRespuesta(respuestaAux);
                }
            }
        });
    }

    /**
     * Muestra los datos del jugador
     * */
    private void cargaJugador(@NonNull View view) {
        cargaNombreJugador(view);
        cargaImagenJugador(view);
    }

    /**
     * Carga y muestra el nombre del jugador
     * */
    private void cargaNombreJugador(@NonNull View view) {
        TextView tvNombreJugador = view.findViewById(R.id.tvNombreJugador);
        tvNombreJugador.setText(viewModel.getUsuario().getNombre());
    }

    /**
     * Carga y muesta la imagen del jugador dependiendo de id de imágen.
     * */
    private void cargaImagenJugador(@NonNull View view) {
        ImageView avatarJugador = view.findViewById(R.id.avatarJugador);
        int idImagen = viewModel.getUsuario().getAvatar();
        switch (idImagen) {
            case 0:
                avatarJugador.setImageResource(R.drawable.btn_castor);
                break;
            case 1:
                avatarJugador.setImageResource(R.drawable.btn_leon);
                break;
            case 2:
                avatarJugador.setImageResource(R.drawable.btn_ciervo);
                break;
            case 3:
                avatarJugador.setImageResource(R.drawable.btn_ardi);
                break;
            case 4:
                avatarJugador.setImageResource(R.drawable.btn_elefante);
                break;
            case 5:
                avatarJugador.setImageResource(R.drawable.btn_conejillo);
                break;
            case 6:
                avatarJugador.setImageResource(R.drawable.btn_hipo);
                break;
            case 7:
                avatarJugador.setImageResource(R.drawable.btn_mapache);
                break;
            case 8:
                avatarJugador.setImageResource(R.drawable.btn_mofeta);
                break;
            case 9:
                avatarJugador.setImageResource(R.drawable.btn_osa);
                break;
            case 10:
                avatarJugador.setImageResource(R.drawable.btn_tigre);
                break;
            case 11:
                avatarJugador.setImageResource(R.drawable.btn_zorra);
                break;
        }
    }

    /**
     * Método que muestra un diálogo de alerta para indicar que se ha acabado el mazo de cartas y por
     * tanto, la ronda.
     * */
    public void dialogoAlerta() {
        MaterialAlertDialogBuilder alertDialog = new MaterialAlertDialogBuilder(getContext());
        alertDialog.setTitle("Mazo acabado");
        alertDialog.setMessage("Ya has visto todas las cartas, finaliza la ronda para volver a empezar.");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }

    /**-------------------------------------BOTONES----------------------------------------------**/

    private void botonCambiar(@NonNull View view) {
        ImageButton btCambiar = view.findViewById(R.id.btCambiar);
        btCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listaCartas.isEmpty()) {
                    dialogoAlerta();
                } else {
                    cargaCarta();
                }
            }
        });
    }

    private void botonPreguntas(@NonNull View view, NavController navC) {
        ImageButton btvolver = view.findViewById(R.id.btPreguntas);
        btvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navC.navigate(R.id.ac_cartaEnJuego_to_respuestasCarta);
            }
        });
    }
}