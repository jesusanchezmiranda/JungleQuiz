package com.tulagames.junglequiz.view;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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
 * Clase perteneciente al fragmento fg_crear_respuestas. En este fragmento creamos o editamos
 * las respuestas de las preguntas de una carta creada o editada.
 * */
public class CrearRespuestas extends Fragment {

    private ViewModel viewModel;
    private Long idCarta;

    public CrearRespuestas() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_crear_respuestas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navC = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        /*Al iniciar el fragmento, comprobamos si hemos estrado para editar o para crear una carta*/
        if (viewModel.isEditarCarta()) {
            colocaRespuestasCarta(view);
        } else {
            obtenerIdCarta();
        }
        botonVolver(view, navC);
        botonGuardar(view, navC);
    }

    /**----------EDITAR----------**/

    /**
     * Método que recupera las respuestas de la carta y las colocoa en su pregunta correspondiente.
     * */
    private void colocaRespuestasCarta(View view) {
        TextView tvResp1 = view.findViewById(R.id.tvResp1);
        TextView tvResp2 = view.findViewById(R.id.tvResp2);
        TextView tvResp3 = view.findViewById(R.id.tvResp3);
        TextView tvResp4 = view.findViewById(R.id.tvResp4);
        TextView tvResp5 = view.findViewById(R.id.tvResp5);
        tvResp1.setText(viewModel.getRespuesta().getRespuesta1());
        tvResp2.setText(viewModel.getRespuesta().getRespuesta2());
        tvResp3.setText(viewModel.getRespuesta().getRespuesta3());
        tvResp4.setText(viewModel.getRespuesta().getRespuesta4());
        tvResp5.setText(viewModel.getRespuesta().getRespuesta5());
    }


    /**----------CREAR----------**/

    /**
     * Mediante este método obtenemos el id de la carta. Número que asignarémos al campo idCarta del
     * objeto Respuesta
     * */
    private void obtenerIdCarta() {
        viewModel.getIdCarta().observe(getActivity(), new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                idCarta = aLong;
            }
        });
    }

    /**
     * Mediante este método recogemos el valor de los campos de respuesta y en el caso de no estar vacíos,
     * se lo damos a la edición o la actualización de la carta
     * */
    private boolean generaRespuestas() {
        TextView tvResp1 = getView().findViewById(R.id.tvResp1);
        TextView tvResp2 = getView().findViewById(R.id.tvResp2);
        TextView tvResp3 = getView().findViewById(R.id.tvResp3);
        TextView tvResp4 = getView().findViewById(R.id.tvResp4);
        TextView tvResp5 = getView().findViewById(R.id.tvResp5);

        if (validaTexto(tvResp1.getText().toString()) || validaTexto(tvResp2.getText().toString())
                || validaTexto(tvResp3.getText().toString()) || validaTexto(tvResp4.getText().toString())
                || validaTexto(tvResp5.getText().toString())) {
            return false;
        } else {
            if (viewModel.isEditarCarta()) {
                Respuesta respuestaActualizar = viewModel.getRespuesta();
                respuestaActualizar.setRespuesta1(tvResp1.getText().toString());
                respuestaActualizar.setRespuesta2(tvResp2.getText().toString());
                respuestaActualizar.setRespuesta3(tvResp3.getText().toString());
                respuestaActualizar.setRespuesta4(tvResp4.getText().toString());
                respuestaActualizar.setRespuesta5(tvResp5.getText().toString());
                viewModel.update(respuestaActualizar);
            } else {
                Respuesta respuesta = new Respuesta();
                respuesta.setRespuesta1(tvResp1.getText().toString());
                respuesta.setRespuesta2(tvResp2.getText().toString());
                respuesta.setRespuesta3(tvResp3.getText().toString());
                respuesta.setRespuesta4(tvResp4.getText().toString());
                respuesta.setRespuesta5(tvResp5.getText().toString());
                respuesta.setIdCarta(idCarta);
                viewModel.insert(respuesta);
            }
            return true;
        }
    }

    /**
     * Método que comprueba que no existan campos vacíos
     * */
    public boolean validaTexto(String cadena) {
        return cadena.isEmpty() || cadena.equals(" ");
    }

    /**
     * Método que  lanza un alert dialog para alertar de la obligatoriedad de rellenar todos los campos.
     * */
    public void dialogoAlerta() {
        MaterialAlertDialogBuilder alertDialog = new MaterialAlertDialogBuilder(getContext());
        alertDialog.setTitle("Error");
        alertDialog.setMessage("Para crear una carta debes de rellenar todos los campos, tanto de carta como de respuestas");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }

    /**
     * Mediante este método, una vez se cumplan diferentes obligaciones, actualizamos o creamos una carta
     * */
    private void creaOActualizaCarta(NavController navC) {
        if (viewModel.isEditarCarta() && generaRespuestas()) {
            viewModel.update(viewModel.getCarta());
            Toast.makeText(getActivity(), "Carta actualizada con exito", Toast.LENGTH_LONG).show();
            navC.navigate(R.id.ac_crearRespuestas_to_listaCartas);
        } else if (generaRespuestas()) {
            Toast.makeText(getActivity(), "Carta creada con exito", Toast.LENGTH_LONG).show();
            navC.navigate(R.id.ac_crearRespuestas_to_crearCarta);
        } else {
            dialogoAlerta();
        }
    }

    /**-------------------------------------BOTONES----------------------------------------------**/

    private void botonGuardar(@NonNull View view, NavController navC) {
        ImageButton btGuardar = view.findViewById(R.id.btGuardarCarta);
        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creaOActualizaCarta(navC);
            }
        });
    }

    private void botonVolver(@NonNull View view, NavController navC) {
        ImageButton btVolver = view.findViewById(R.id.btVolver);
        btVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!viewModel.isEditarCarta()) {
                    viewModel.deleteCarta(viewModel.getCarta());
                }
                navC.navigate(R.id.ac_crearRespuestas_to_crearCarta);
            }
        });
    }
}