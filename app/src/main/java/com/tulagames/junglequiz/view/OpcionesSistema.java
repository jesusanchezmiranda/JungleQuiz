package com.tulagames.junglequiz.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.tulagames.junglequiz.R;

/**
 * Clase perteneciente al fragmento fg_opciones_sistema. Este fragmento contiene las  opciones de la
 * palicación. En este caso solo está disponible la opción de reproducir, o no, la música de fondo
 */

public class OpcionesSistema extends Fragment {

    private SwitchMaterial swMusica;

    public OpcionesSistema() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_opciones_sistema, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navC = Navigation.findNavController(view);
        gestionarMusica(view);
        botonVolver(view,navC);
    }

    /**
     * Este método guarda el estado de la preferencia de la música
     * */
    private void guardaEstadoMusica(boolean estado) {
        SharedPreferences musicaEstado = getActivity().getSharedPreferences("musica", Context.MODE_PRIVATE);
        SharedPreferences.Editor editorMusica = musicaEstado.edit();
        editorMusica.putBoolean("musica",estado);
        editorMusica.commit();
    }

    /**
     * Este método devuelve el estado de la preferencia de la música
     * */
    private boolean obtenerEstadoMusica(){
        SharedPreferences musicaEstado = getActivity().getSharedPreferences("musica", getContext().MODE_PRIVATE);
        return musicaEstado.getBoolean("musica", true);
    }

    /**
     * Este método gestiona el estado de la preferencia de la música
     * */
    private void gestionarMusica(@NonNull View view) {
        swMusica = view.findViewById(R.id.sw_musica);
        swMusica.setChecked(obtenerEstadoMusica());
        swMusica.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MainActivity.encenderMusica();
                    guardaEstadoMusica(true);
                } else {
                    MainActivity.pausarMusica();
                    guardaEstadoMusica(false);
                }
            }
        });
    }

    /**-------------------------------------BOTONES----------------------------------------------**/

    private void botonVolver(@NonNull View view, NavController navC) {
        ImageButton btVolver = view.findViewById(R.id.btBorrarCartaSelec);
        btVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navC.navigate(R.id.ac_opcionesSistema_to_portada);
            }
        });
    }
}