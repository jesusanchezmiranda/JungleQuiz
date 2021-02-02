package com.tulagames.junglequiz.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tulagames.junglequiz.R;
import com.tulagames.junglequiz.model.room.pojo.Carta;
import com.tulagames.junglequiz.model.room.pojo.Respuesta;
import com.tulagames.junglequiz.viewmodel.ViewModel;

import java.util.List;

/**
 * Clase perteneciente al fragmento fg_carta_elegida. Se encarga de mostrar la carta que eligamos en
 * el recycler y de cargar sus respuestas para poder mostrarlas en caso de que el usuario navegue.
 * */
public class CartaElegida extends Fragment {

    private ViewModel viewModel;

    public CartaElegida() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_carta_elegida, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navC = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        Carta carta = viewModel.getCarta();
        cargaRespuestas(carta);
        cargaCarta(view, carta);
        botonVolver(view, navC);
        botonPreguntas(view, navC, carta);
    }

    /**
     * Carga la carta seleccionada
     * */
    private void cargaCarta(@NonNull View view, Carta carta) {
        TextView tvDescripcion = view.findViewById(R.id.tvDescripcion);
        TextView tvCartaTitulo = view.findViewById(R.id.carta_titulo);
        ImageView ivCarta = view.findViewById(R.id.carta_imagen);
        tvDescripcion.setText(carta.getDescripcion());
        tvCartaTitulo.setText(carta.getNombreAnimal());
        Glide.with(getContext()).load(carta.getUrlfoto()).into(ivCarta);
    }

    /**
     * Carga en el viewModel las respuestas de la carta que está mostrando, en previsión de una nave
     * gación a las preguntas de esa carta
     * */
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

    /**-------------------------------------BOTONES----------------------------------------------**/

    private void botonPreguntas(@NonNull View view, NavController navC, Carta carta) {
        ImageButton btPreguntas = view.findViewById(R.id.btPreguntas);
        btPreguntas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navC.navigate(R.id.ac_cartaElegida_to_editarRespuestas);
            }
        });
    }

    private void botonVolver(@NonNull View view, NavController navC) {
        ImageButton btvolver = view.findViewById(R.id.btVolver);
        btvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navC.navigate(R.id.ac_cartaElegida_to_listaCartas);
            }
        });
    }
}