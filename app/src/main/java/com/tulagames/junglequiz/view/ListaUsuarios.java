package com.tulagames.junglequiz.view;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tulagames.junglequiz.R;
import com.tulagames.junglequiz.model.room.pojo.Usuario;
import com.tulagames.junglequiz.view.adapter.RVAdapterUsuarios;
import com.tulagames.junglequiz.viewmodel.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase perteneciente al fragmento fg_lista_usuarios. Mediante este fragmento,listamos los usuarios que
 * tenémos
 */

public class ListaUsuarios extends Fragment {

    private ViewModel viewModel;
    private List<Usuario> listaUsuarios = new ArrayList<>();

    public ListaUsuarios() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_lista_usuarios, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navC = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        RVAdapterUsuarios rvAdapterUsuarios = abrirRecycler(view, navC);
        escuchaUsuarios(rvAdapterUsuarios);
        botonCancelar(view, navC);
    }

    /**
     * Método encargado de rescuperar todos usuarios que tengamos.
     * */
    private void escuchaUsuarios(RVAdapterUsuarios rvAdapterUsuarios) {
        viewModel.getLiveListaUsuarios().observe(getActivity(), new Observer<List<Usuario>>() {
            @Override
            public void onChanged(List<Usuario> usuarios) {
                listaUsuarios.clear();
                listaUsuarios.addAll(usuarios);
                rvAdapterUsuarios.notifyDataSetChanged();
            }
        });
    }

    /**
     * Método que crea el adaptador del recycler
     * */
    private RVAdapterUsuarios abrirRecycler(View view, NavController navC) {
        RecyclerView rvJugadores = view.findViewById(R.id.rvJugadores);
        TypedArray imgAvatares = obtenerImagenesAvatares();
        RVAdapterUsuarios rvAdapterUsuarios = new RVAdapterUsuarios(listaUsuarios, navC, getActivity(), imgAvatares);
        rvJugadores.setAdapter(rvAdapterUsuarios);
        rvJugadores.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rvAdapterUsuarios;
    }

    /**
     * Método que recupera el array con las imágenes de avatar de los usuarios
     * */
    private TypedArray obtenerImagenesAvatares() {
        Resources res = getResources();
        TypedArray avatares = res.obtainTypedArray(R.array.av_circulos);
        return avatares;
    }

    /**-------------------------------------BOTONES----------------------------------------------**/

    private void botonCancelar(@NonNull View view, NavController navC) {
        ImageButton btCaneclar = view.findViewById(R.id.btCancelar);
        btCaneclar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setBorrar(false);
                if (viewModel.isAdministrar()) {
                    navC.navigate(R.id.ac_listaUsuarios_to_menuAdminUsuarios);
                } else {
                    navC.navigate(R.id.ac_listaUsuarios_to_portada);
                }

            }
        });
    }
}