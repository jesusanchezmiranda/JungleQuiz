package com.tulagames.junglequiz.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.tulagames.junglequiz.R;
import com.tulagames.junglequiz.model.room.pojo.Carta;
import com.tulagames.junglequiz.view.adapter.RVAdapterCartas;
import com.tulagames.junglequiz.viewmodel.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase perteneciente al fragmento fg_lista_cartas. Mediante este fragmento,listamos las cartas que
 * tenémos
 */

public class ListaCartas extends Fragment {

    private ViewModel viewModel;
    private List<Carta> listaCartas = new ArrayList<>();

    public ListaCartas() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_lista_cartas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navC = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        RVAdapterCartas rvAdapterCartas = abrirRecycler(view, navC);
        escuchaCartas(rvAdapterCartas);
        botonCancelar(view, navC);
    }

    /**
     * Método encargado de rescuperar todas las cartas que tengamos.
     * */
    private void escuchaCartas(RVAdapterCartas rvAdapterCartas) {
        viewModel.getLiveListaCartas().observe(getActivity(), new Observer<List<Carta>>() {
            @Override
            public void onChanged(List<Carta> cartas) {
                listaCartas.clear();
                listaCartas.addAll(cartas);
                rvAdapterCartas.notifyDataSetChanged();
            }
        });
    }

    /**
     * Método que crea el adaptador del recycler
     * */
    private RVAdapterCartas abrirRecycler(View view, NavController navC) {
        RecyclerView rvCartas = view.findViewById(R.id.rvCartas);
        RVAdapterCartas rvAdapterCartas = new RVAdapterCartas(listaCartas, navC, getActivity(), getContext());
        rvCartas.setAdapter(rvAdapterCartas);
        rvCartas.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rvAdapterCartas;
    }

    /**
     * -------------------------------------BOTONES----------------------------------------------
     **/

    private void botonCancelar(@NonNull View view, NavController navC) {
        ImageButton btCaneclar = view.findViewById(R.id.btCancelar);
        btCaneclar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navC.navigate(R.id.ac_listaCartas_to_menuAdminCartas);
            }
        });
    }
}