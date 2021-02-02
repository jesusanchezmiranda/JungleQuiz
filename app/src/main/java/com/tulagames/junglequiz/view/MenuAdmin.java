package com.tulagames.junglequiz.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.tulagames.junglequiz.R;
import com.tulagames.junglequiz.viewmodel.ViewModel;

/**
 * Clase perteneciente al fragmento fg_menu_admin. Este fragmento contiene el menú inicial del
 * administrador de la aplicación
 */

public class MenuAdmin extends Fragment {

    private ViewModel viewModel;

    public MenuAdmin() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_menu_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navC = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        viewModel.setAdministrar(true);
        botonUsuarios(view, navC);
        botonCarta(view, navC);
        botonVolver(view, navC);
    }

    /**-------------------------------------BOTONES----------------------------------------------**/

    private void botonUsuarios(@NonNull View view, NavController navC) {
        ImageButton btUsuarios = view.findViewById(R.id.btUsuario);
        btUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navC.navigate(R.id.ac_menuAdmin_to_menuAdminUsuarios);
            }
        });
    }

    private void botonCarta(@NonNull View view, NavController navC) {
        ImageButton btcartas = view.findViewById(R.id.btCartas);
        btcartas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navC.navigate(R.id.ac_menuAdmin_to_menuAdminCartas);
            }
        });
    }

    private void botonVolver(@NonNull View view, NavController navC) {
        ImageButton btvolver = view.findViewById(R.id.btVolver);
        btvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setAdministrar(false);
                navC.navigate(R.id.ac_menuAdmin_to_portada);
            }
        });
    }
}