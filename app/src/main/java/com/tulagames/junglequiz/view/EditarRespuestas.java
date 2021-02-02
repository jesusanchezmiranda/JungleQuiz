package com.tulagames.junglequiz.view;

import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.tulagames.junglequiz.R;
import com.tulagames.junglequiz.model.room.pojo.Respuesta;
import com.tulagames.junglequiz.viewmodel.ViewModel;

/**
 * Clase perteneciente al fragmento fg_editar_respuestas_cartas. En este fragmento puedes ir a la
 * edición o borrar la carta de donde vienen esas respuestas que estas mostrando
 * */
public class EditarRespuestas extends Fragment {

    private ViewModel viewModel;

    public EditarRespuestas() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_editar_respuestas_cartas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navC = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        colocaRespuestas(view);
        botonVolver(view, navC);
        botonEditar(view, navC);
        botonBorrar(view, navC);
    }

    /**
     * Método que coloca los valores de las respuestas de la carta, en su lugar correspondiente
     * */
    private void colocaRespuestas(@NonNull View view) {
        Respuesta respuesta = viewModel.getRespuesta();
        TextView tvResp1 = view.findViewById(R.id.tvR1E);
        tvResp1.setEnabled(false);
        TextView tvResp2 = view.findViewById(R.id.tvR2E);
        tvResp2.setEnabled(false);
        TextView tvResp3 = view.findViewById(R.id.tvR3E);
        tvResp3.setEnabled(false);
        TextView tvResp4 = view.findViewById(R.id.tvR4E);
        tvResp4.setEnabled(false);
        TextView tvResp5 = view.findViewById(R.id.tvR5E);
        tvResp5.setEnabled(false);
        tvResp1.setText(respuesta.getRespuesta1());
        tvResp2.setText(respuesta.getRespuesta2());
        tvResp3.setText(respuesta.getRespuesta3());
        tvResp4.setText(respuesta.getRespuesta4());
        tvResp5.setText(respuesta.getRespuesta5());
    }

    /**
     * Método que avisa de que se va a producir el borrado de la carta y que llama al método que las
     * borra, en caso afirmativo
     * */
    public void dialogoAlerta(NavController navC) {
        MaterialAlertDialogBuilder alertDialog = new MaterialAlertDialogBuilder(getContext());
        alertDialog.setTitle("Cuidado");
        alertDialog.setMessage("Estás a punto de borrar una carta, ¿Estás seguro?");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                borrarCartaYRespuestas();
                navC.navigate(R.id.ac_editarRespuestas_to_listaCartas);
            }
        });
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }

    private void borrarCartaYRespuestas() {
        viewModel.deleteCarta(viewModel.getCarta());
        viewModel.deleteRespuesta(viewModel.getRespuesta());
        Toast.makeText(getContext(), "Carta borrada", Toast.LENGTH_LONG).show();
    }

    /**-------------------------------------BOTONES----------------------------------------------**/

    private void botonEditar(@NonNull View view, NavController navC) {
        ImageButton btEditar = view.findViewById(R.id.btEditarCartaSelec);
        btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Pones a true el valor editarCarta, para al entrar en el fragmento crearCarta lo hagas
                * en modo edición de carta*/
                viewModel.setEditarCarta(true);
                navC.navigate(R.id.ac_editarRespuestas_to_crearCarta);
            }
        });
    }

    private void botonVolver(@NonNull View view, NavController navC) {
        ImageButton btVolver = view.findViewById(R.id.btVolverCartaSelec);
        btVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navC.navigate(R.id.ac_editarRespuestas_to_cartaElegida);
            }
        });
    }

    private void botonBorrar(@NonNull View view, NavController navC) {
        ImageButton btBorrar = view.findViewById(R.id.btBorrarCartaSelec);
        btBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoAlerta(navC);
            }
        });
    }
}