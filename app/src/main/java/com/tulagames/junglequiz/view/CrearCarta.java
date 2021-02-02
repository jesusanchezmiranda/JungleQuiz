package com.tulagames.junglequiz.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.tulagames.junglequiz.R;
import com.tulagames.junglequiz.model.room.pojo.Carta;
import com.tulagames.junglequiz.viewmodel.ViewModel;

/**
 * Clase perteneciente al fragmento fg_crea_carta. Este método se encarga de, o bien mostrar los datos
 * de la carta, si se está en modo edición, o de guardarlos para crear una carta.
 * */
public class CrearCarta extends Fragment {

    private ViewModel viewModel;

    public CrearCarta() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_crea_carta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navC = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        if (viewModel.isEditarCarta()) {
            colocaDatosCarta(view);
        }
        botonCancelar(view, navC);
        botonPreguntas(view, navC);
        botonInternet(view, navC);
    }

    /**
     * Método encargado de mostrar los datos de la carta en caso de estar en modo edición de carta.
     * */
    private void colocaDatosCarta(@NonNull View view) {
        Carta carta = viewModel.getCarta();
        TextView tieNomAnimal = view.findViewById(R.id.tietNomAnimal);
        TextView tieUrlCarta = view.findViewById(R.id.tieUrlCarta);
        TextView tieDescripcionCarta = view.findViewById(R.id.tieDescripcionCarta);
        tieNomAnimal.setText(carta.getNombreAnimal());
        tieUrlCarta.setText(carta.getUrlfoto());
        tieDescripcionCarta.setText(carta.getDescripcion());
    }

    /**
     * Mediante este método, editamos o creamos una carta temporal. Depende del modo en el que hayamos
     * entrado al fragmento, ya que, en caso de estar en modo edición, vamos a actualizar los datos de
     * la carta. En caso de estar en modo de creación, se le da el caracter de carta temporal.
     * */
    private void creaCartaTemporal(@NonNull View view) {
        TextView tieNomAnimal = view.findViewById(R.id.tietNomAnimal);
        TextView tieUrlCarta = view.findViewById(R.id.tieUrlCarta);
        TextView tieDescripcionCarta = view.findViewById(R.id.tieDescripcionCarta);
        if (viewModel.isEditarCarta()) {
            Carta cartaAux = viewModel.getCarta();
            cartaAux.setNombreAnimal(tieNomAnimal.getText().toString());
            cartaAux.setUrlfoto(tieUrlCarta.getText().toString());
            cartaAux.setDescripcion(tieDescripcionCarta.getText().toString());
            viewModel.setCarta(cartaAux);
        } else {
            Carta carta = new Carta();
            carta.setNombreAnimal(tieNomAnimal.getText().toString());
            carta.setUrlfoto(tieUrlCarta.getText().toString());
            carta.setDescripcion(tieDescripcionCarta.getText().toString());
            viewModel.insert(carta);
        }
    }

    /**
     * Método usado para poder salir con un intent a una página de internet determinada.
     * */
    private void irAInternet() {
        String url = getString(R.string.url);
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**-------------------------------------BOTONES----------------------------------------------**/

    private void botonCancelar(@NonNull View view, NavController navC) {
        ImageButton btCancelar = view.findViewById(R.id.btCancelar);
        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.isEditarCarta()) {
                    viewModel.setEditarCarta(false);
                }
                navC.navigate(R.id.ac_crearCarta_to_menuAdminCartas);
            }
        });
    }

    private void botonPreguntas(@NonNull View view, NavController navC) {
        ImageButton btPreguntas = view.findViewById(R.id.btPreguntasCarta);
        btPreguntas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creaCartaTemporal(view);
                navC.navigate(R.id.ac_crearCarta_to_crearRespuestas);
            }
        });
    }

    private void botonInternet(@NonNull View view, NavController navC) {
        ImageButton btInternet = view.findViewById(R.id.btInternet);
        btInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irAInternet();
            }
        });
    }
}