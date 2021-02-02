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
import android.widget.Toast;

import com.tulagames.junglequiz.R;
import com.tulagames.junglequiz.model.room.pojo.Carta;
import com.tulagames.junglequiz.model.room.pojo.Respuesta;
import com.tulagames.junglequiz.viewmodel.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase perteneciente al fragmento fg_menu_admin_cartas. Este fragmento contiene el menú de gestión
 * de las cartas y en el además se realizan una serie de procesos internos para obtener datos de las
 * cartas que serán usados posteriormente.
 */


public class MenuAdminCartas extends Fragment {

    private ViewModel viewModel;
    private List<Carta> listaCartas = new ArrayList<>();
    private List<Respuesta> listaRespuestas = new ArrayList<>();
    private long idUltimaCarta;
    public MenuAdminCartas() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_menu_admin_cartas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navC = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        obtenUltimoIdCarta();
        botonCrear(view, navC);
        botonEditar(view, navC);
        botonVolver(view, navC);
        botonImportar(view, navC);
    }

    /**
     * Mediante este método obtenémos el último id de la carta del mazo de cartas. Este número será
     * usado en caso de que se importen cartas desde internet.
     * */
    private void obtenUltimoIdCarta(){
        viewModel.getLiveListaCartas().observe(getActivity(), new Observer<List<Carta>>() {
            @Override
            public void onChanged(List<Carta> cartas) {
                listaCartas.clear();
                listaCartas.addAll(cartas);
                int i = listaCartas.size()-1;
                idUltimaCarta = listaCartas.get(i).getId();
            }
        });
    }

    /**
     * Método que importa las cartas que se hayan creado y almacenado en la página web de la aplicación
     * */
    private void importarCartasRetrofit(long idUltimaCarta) {
        obtenUltimoIdCarta();
        viewModel.getListaCartasRetrofit().observe(getActivity(), new Observer<List<Carta>>() {
            @Override
            public void onChanged(List<Carta> cartas) {
                listaCartas.clear();
                listaCartas.addAll(cartas);
                long id= idUltimaCarta;
                if (listaCartas.size()>0){
                    for (int i = 0; i < listaCartas.size(); i++) {
                        id++;
                        listaCartas.get(i).setId(id);
                        viewModel.insert(listaCartas.get(i));
                        importarRespuestasRetrofit(idUltimaCarta);
                    }
                }else{
                    Toast.makeText(getContext(), "No hay datos que importar", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Método que importa las respuestas que se hayan creado y almacenado en la página web de la aplicación
     * */
    private void importarRespuestasRetrofit(long idUltimaCarta) {
        viewModel.getListaRespuestasRetrofit().observe(getActivity(), new Observer<List<Respuesta>>() {
            @Override
            public void onChanged(List<Respuesta> respuestas) {
                listaRespuestas.clear();
                listaRespuestas.addAll(respuestas);
                /*Para que el idCarta case con las cartas importadas y el resto de elementos
                (cartas y respuestas) de la base de datos interna, hacemos uso del idUltimaCarta*/
                long id = idUltimaCarta;
                for (int i = 0; i < listaRespuestas.size(); i++) {
                    id++;
                    listaRespuestas.get(i).setId(id);
                    listaRespuestas.get(i).setIdCarta(id);
                    viewModel.insert(listaRespuestas.get(i));
                }
                Toast.makeText(getContext(), "Cartas importadas", Toast.LENGTH_LONG).show();
                /*Tras importar cartas y respuestas, procedemos a borrar todos los elementos de
                * almacenados desde la web*/
                borraRespuestasRetrofit();
            }
        });
    }

    /**-------------------------------------BOTONES----------------------------------------------**/

    private void botonCrear(@NonNull View view, NavController navC) {
        ImageButton btCrear = view.findViewById(R.id.btrCrearCarta);
        btCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navC.navigate(R.id.ac_menuAdminCartas_to_crearCarta);
            }
        });
    }

    private void botonEditar(@NonNull View view, NavController navC) {
        ImageButton btEditar = view.findViewById(R.id.btGestionarCarta);
        btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navC.navigate(R.id.ac_menuAdminCartas_to_listaCartas);
            }
        });
    }

    private void botonVolver(@NonNull View view, NavController navC) {
        ImageButton btvolver = view.findViewById(R.id.btVolver);
        btvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navC.navigate(R.id.ac_menuAdminCartas_to_menuAdmin);
            }
        });
    }

    private void botonImportar(@NonNull View view, NavController navC) {
        ImageButton btImportarCarta = view.findViewById(R.id.btImportarCarta);
        btImportarCarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importarCartasRetrofit(idUltimaCarta);
            }
        });
    }


    private void borraRespuestasRetrofit(){
        viewModel.getListaRespuestasRetrofit().observe(getActivity(), new Observer<List<Respuesta>>() {
            @Override
            public void onChanged(List<Respuesta> respuestas) {
                listaRespuestas.clear();
                listaRespuestas.addAll(respuestas);
                for (int i = 0; i < listaRespuestas.size(); i++) {
                  viewModel.deleteRespuestaRetrofit(listaRespuestas.get(i).getId());
                }
                borraCartasRetrofit();
            }
        });
    }

    private void borraCartasRetrofit() {
        viewModel.getListaCartasRetrofit().observe(getActivity(), new Observer<List<Carta>>() {
            @Override
            public void onChanged(List<Carta> cartas) {
                listaCartas.clear();
                listaCartas.addAll(cartas);
                for (int i = 0; i < listaCartas.size(); i++) {
                    viewModel.deleteCartaRetrofit(listaCartas.get(i).getId());
                }
                Toast.makeText(getContext(), "Cartas Borradas del servidor", Toast.LENGTH_LONG).show();
            }
        });
    }
}