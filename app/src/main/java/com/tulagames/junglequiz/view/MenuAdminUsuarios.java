package com.tulagames.junglequiz.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.tulagames.junglequiz.R;
import com.tulagames.junglequiz.viewmodel.ViewModel;

/**
 * Clase perteneciente al fragmento fg_menu_admin_usuarios. Este fragmento contiene el menú de gestión
 * de las usuarios.
 */

public class MenuAdminUsuarios extends Fragment {

    private ViewModel viewModel;

    public MenuAdminUsuarios() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_menu_admin_usuarios, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navC = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        botonCrear(view,navC);
        botonEditar(view,navC);
        botonVolver(view,navC);
        botonBorrar(view,navC);
    }

    /**-------------------------------------BOTONES----------------------------------------------**/

    private void botonCrear(@NonNull View view, NavController navC) {
        ImageButton btCrear = view.findViewById(R.id.btCrearUsuario);
        btCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setEditarJugar(false);
                navC.navigate(R.id.ac_menuAdminUsuarios_to_crearEditarUsuario);
            }
        });
    }

    private void botonEditar(@NonNull View view, NavController navC) {
        ImageButton btEditar = view.findViewById(R.id.btEditarUsuario);
        btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setEditarJugar(true); //true es Editar False Jugar
                navC.navigate(R.id.ac_menuAdminUsuarios_to_listaUsuarios);
            }
        });
    }

    private void botonBorrar(@NonNull View view, NavController navC) {
        ImageButton btBorrar = view.findViewById(R.id.btBorrarCartaSelec);
        btBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setBorrar(true);
                navC.navigate(R.id.ac_menuAdminUsuarios_to_listaUsuarios);
            }
        });
    }

    private void botonVolver(@NonNull View view, NavController navC) {
        ImageButton btvolver = view.findViewById(R.id.btVolver);
        btvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navC.navigate(R.id.ac_menuAdminUsuarios_to_menuAdmin);
            }
        });
    }
}