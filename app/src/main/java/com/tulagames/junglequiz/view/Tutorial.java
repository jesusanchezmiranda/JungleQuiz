package com.tulagames.junglequiz.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.util.TimeUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.tulagames.junglequiz.R;

public class Tutorial extends Fragment {

    protected ImageView tuto1;
    protected ImageView tuto2;

    public Tutorial() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_tutorial, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navC = Navigation.findNavController(view);
        tuto1 = view.findViewById(R.id.tutorialTexto);
        tuto2 = view.findViewById(R.id.tutorialJuego);
        tuto1.setVisibility(View.VISIBLE);
        tuto2.setVisibility(View.INVISIBLE);
        botonVolver(view, navC);
        botonDerecha(view, navC);
        botonIzquierda(view, navC);
    }

    /**-------------------------------------BOTONES----------------------------------------------**/

    private void botonDerecha(@NonNull View view, NavController navC) {
        ImageButton btDerecha = view.findViewById(R.id.btDerecha);
        btDerecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tuto1.getVisibility()==View.VISIBLE){
                    tuto1.setVisibility(View.INVISIBLE);
                    tuto2.setVisibility(View.VISIBLE);
                }else {
                    tuto1.setVisibility(View.VISIBLE);
                    tuto2.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void botonIzquierda(@NonNull View view, NavController navC) {
        ImageButton btIzquierda = view.findViewById(R.id.btIzquierda);
        btIzquierda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tuto2.getVisibility()==View.VISIBLE){
                    tuto2.setVisibility(View.INVISIBLE);
                    tuto1.setVisibility(View.VISIBLE);
                }else {
                    tuto2.setVisibility(View.VISIBLE);
                    tuto1.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void botonVolver(@NonNull View view, NavController navC) {
        ImageButton btvolver = view.findViewById(R.id.btVolverPortada);
        btvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navC.navigate(R.id.ac_tutorial_to_portada);
            }
        });
    }

}