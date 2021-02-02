package com.tulagames.junglequiz.view;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
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

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.tulagames.junglequiz.R;
import com.tulagames.junglequiz.model.room.pojo.Usuario;
import com.tulagames.junglequiz.viewmodel.ViewModel;

import java.util.List;

/**
 * Clase perteneciente al fragmento fg_portada. Este fragmento Es el menú inicial de la aplicación
 */

public class Portada extends Fragment {

    private ViewModel viewModel;
    private int numUsuarios;

    public Portada() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_portada, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navC = Navigation.findNavController(view);
        if (!compruebaTutorial()){
            primeraVez();
            navC.navigate(R.id.ac_portada_to_tutorial);
        }
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        contadorDeJugadores();
        compruebaJugar(view, navC);
        botonAdministrar(view, navC);
        botonOpciones(view, navC);
        botonSalir(view);
    }

    /**
     * Este método crea un archivo de preferencias compartidas para, en caso de ser la primera vez
     * que se inicia la aplicación, mostrar un tutorial de la misma
     * */
    private void primeraVez() {
        SharedPreferences TutorialPreferences = getActivity().getSharedPreferences("tutorial", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editarTutorial = TutorialPreferences.edit();
        editarTutorial.putBoolean("tutorial", true);
        editarTutorial.commit();
    }

    /**
     * Método que comprueba el estado de la preferencia compartida, "tutorial".
     * */
    private boolean compruebaTutorial(){
        SharedPreferences sharedContrasenia = getActivity().getSharedPreferences("tutorial", getContext().MODE_PRIVATE);
        return sharedContrasenia.getBoolean("tutorial", false);
    }

    /**
     * Este método, dependiendo de si existe contraseña de administrador, te muestra un botón de juego u otro
     * */
    private void compruebaJugar(@NonNull View view, NavController navC) {
        if (compruebaContrasenia().equals("") || compruebaContrasenia() == null) {
            botonJugarDes(view, navC);
        } else {
            botonJugar(view, navC);
        }
    }

    /**
     * Método que devuelve la existencia de la preferencia compartida AdminPass
     * */
    private String compruebaContrasenia() {
        SharedPreferences sharedContrasenia = getActivity().getSharedPreferences("AdminPass", getContext().MODE_PRIVATE);
        return sharedContrasenia.getString("AdminPass", "");
    }

    /**
     * Método que devuelve el número de jugadores registrados
     * */
    private void contadorDeJugadores() {
        viewModel.getLiveListaUsuarios().observe(getActivity(), new Observer<List<Usuario>>() {
            @Override
            public void onChanged(List<Usuario> usuarios) {
                numUsuarios = usuarios.size();
            }
        });
    }

    /**
     * Este método, lanza un diálogo de alerta para indicar que no se puede comenzar a jugar sin jugadores,
     * a la vez que te lleva a la pantalla de contraseña del administrador
     * */
    public void dialogoAlerta(NavController navC) {
        MaterialAlertDialogBuilder alertDialog = new MaterialAlertDialogBuilder(getContext());
        alertDialog.setTitle("Ups!");
        alertDialog.setMessage("Recuerda que sin jugadores no puedes comenzar a jugar. Vamos a solucionarlo");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                navC.navigate(R.id.ac_portada_to_loginAdminRegistrado);
            }
        });
        alertDialog.show();
    }

    /**-------------------------------------BOTONES----------------------------------------------**/

    private void botonAdministrar(@NonNull View view, NavController navC) {
        ImageButton btAdmin = view.findViewById(R.id.btAdmin);
        btAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!compruebaContrasenia().equals("")) {
                    navC.navigate(R.id.ac_portada_to_loginAdminRegistrado);
                } else {
                    navC.navigate(R.id.ac_portada_to_loginAd);
                }
            }
        });
    }

    private void botonJugar(@NonNull View view, NavController navC) {
        viewModel.setEditarJugar(false);
        ImageButton btJugarDes = view.findViewById(R.id.btJugarDes);
        btJugarDes.setVisibility(View.INVISIBLE);
        ImageButton btJugar = view.findViewById(R.id.btJugar);
        btJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numUsuarios == 0) {
                    dialogoAlerta(navC);
                } else {
                    navC.navigate(R.id.ac_portada_to_listaUsuarios);
                }
            }
        });
    }

    private void botonJugarDes(@NonNull View view, NavController navC) {
        ImageButton btJugar = view.findViewById(R.id.btJugar);
        btJugar.setVisibility(View.INVISIBLE);
        ImageButton btJugarDes = view.findViewById(R.id.btJugarDes);
        btJugarDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navC.navigate(R.id.ac_portada_to_tutorial);
            }
        });
    }


    private void botonOpciones(@NonNull View view, NavController navC) {
        ImageButton btOpciones = view.findViewById(R.id.btOpciones);
        btOpciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navC.navigate(R.id.ac_portada_to_opcionesSistema);
            }
        });
    }

    private void botonSalir(@NonNull View view) {
        ImageButton btSalir = view.findViewById(R.id.btSalir);
        btSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getActivity().finishAndRemoveTask();
                } else {
                    getActivity().finish();
                    System.exit(0);
                }
            }
        });
    }
}