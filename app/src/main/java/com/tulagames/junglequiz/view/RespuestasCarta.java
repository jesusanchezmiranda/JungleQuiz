package com.tulagames.junglequiz.view;

import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.tulagames.junglequiz.R;

import com.tulagames.junglequiz.model.room.pojo.Respuesta;
import com.tulagames.junglequiz.model.room.pojo.Usuario;
import com.tulagames.junglequiz.viewmodel.ViewModel;

import java.util.ArrayList;

/**
 * Clase perteneciente al fragmento fg_respuestas_carta. Este fragmento es el encargado de gestionar
 * las respuestas a las preguntas de la carta en juego
 */

public class RespuestasCarta extends Fragment {

    private ViewModel viewModel;
    private Respuesta respuesta;
    private Usuario usuario;

    public RespuestasCarta() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_respuestas_carta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navC = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        colocaRespuestasTemporales(view);
        usuario = viewModel.getUsuario();
        botonContestar(view, navC);
        botonVolver(view, navC);
        botonFinalizar(view, navC);
    }

    /**
     * Este método, recupera las respuestas introducidas anteriormente y las coloca en su lugar.
     * */
    private void colocaRespuestasTemporales(@NonNull View view) {
        ArrayList<String> respuestas = viewModel.getRespuestasTemporales();
        TextView tvResp1 = view.findViewById(R.id.tvPr1);
        TextView tvResp2 = view.findViewById(R.id.tvPr2);
        TextView tvResp3 = view.findViewById(R.id.tvPr3);
        TextView tvResp4 = view.findViewById(R.id.tvPr4);
        TextView tvResp5 = view.findViewById(R.id.tvPr5);
        if (respuestas.size()==5){
            tvResp1.setText(respuestas.get(0));
            tvResp2.setText(respuestas.get(1));
            tvResp3.setText(respuestas.get(2));
            tvResp4.setText(respuestas.get(3));
            tvResp5.setText(respuestas.get(4));
        }
    }

    /**
     * Método que, en caso de contestar de manera completa, todas las preguntas, recoge las respuestas
     * y las envía a analizar.
     * */
    private boolean recogeRespuestas(@NonNull View view) {
        TextView tvResp1 = view.findViewById(R.id.tvPr1);
        TextView tvResp2 = view.findViewById(R.id.tvPr2);
        TextView tvResp3 = view.findViewById(R.id.tvPr3);
        TextView tvResp4 = view.findViewById(R.id.tvPr4);
        TextView tvResp5 = view.findViewById(R.id.tvPr5);
        if (compruebaCampoTexto(String.valueOf(tvResp1.getText())) || compruebaCampoTexto(String.valueOf(tvResp2.getText())) ||
                compruebaCampoTexto(String.valueOf(tvResp3.getText())) || compruebaCampoTexto(String.valueOf(tvResp4.getText())) ||
                compruebaCampoTexto(String.valueOf(tvResp5.getText()))) {
            Toast.makeText(getContext(), "No puedes dejar preguntas en blanco", Toast.LENGTH_LONG).show();
            return false;
        } else {
            guardaRespuestasTemporales(view);
            generaPuntuacion();
            return true;
        }
    }

    /**
     * Método que analiza las respuestas introducidas, con las de la carta.
     * */
    private void generaPuntuacion() {
        ArrayList<Integer> validaRespuestas = new ArrayList();
        respuesta = viewModel.getRespuesta();
        ArrayList respJugador = viewModel.getRespuestasTemporales();
        int respAcertadas = usuario.getNumResCor();
        for (int i = 0; i < 5; i++) {
            switch (i) {
                case 0:
                    if (respJugador.get(0).equals(respuesta.getRespuesta1())) {
                        validaRespuestas.add(1);
                        respAcertadas++;
                    }else{
                        validaRespuestas.add(-1);
                    }
                    break;
                case 1:
                    if (respJugador.get(1).equals(respuesta.getRespuesta2())) {
                        validaRespuestas.add(1);
                        respAcertadas++;
                    }else{
                        validaRespuestas.add(-1);
                    }
                    break;
                case 2:
                    if (respJugador.get(2).equals(respuesta.getRespuesta3())) {
                        validaRespuestas.add(1);
                        respAcertadas++;
                    }else{
                        validaRespuestas.add(-1);
                    }
                    break;
                case 3:
                    if (respJugador.get(3).equals(respuesta.getRespuesta4())) {
                        validaRespuestas.add(1);
                        respAcertadas++;
                    }else{
                        validaRespuestas.add(-1);
                    }
                    break;
                case 4:
                    if (respJugador.get(4).equals(respuesta.getRespuesta5())) {
                        validaRespuestas.add(1);
                        respAcertadas++;
                    }else{
                        validaRespuestas.add(-1);
                    }
                    break;
            }
        }
        actualizaPuntuacion(respAcertadas);
        feedBackRespuestas(validaRespuestas);
    }

    /**
     * Método que actualiza la puntuación del jugador, en la ronda en curso
     * */
    private void actualizaPuntuacion(int respAcertadas) {
        usuario.setNumResCor(respAcertadas);
        usuario.setNumRes(usuario.getNumRes() + 1);
        viewModel.update(usuario);
    }

    /**
     * Este método guarda temporalmente las respuestas, para poder navegar entre el fragmento con la
     * carta que está en juego, donde tenemos la explicación de la misma, y el fragmento con las preguntas
     * */
    private void guardaRespuestasTemporales(@NonNull View view) {
        TextView tvResp1 = view.findViewById(R.id.tvPr1);
        TextView tvResp2 = view.findViewById(R.id.tvPr2);
        TextView tvResp3 = view.findViewById(R.id.tvPr3);
        TextView tvResp4 = view.findViewById(R.id.tvPr4);
        TextView tvResp5 = view.findViewById(R.id.tvPr5);
        ArrayList<String> respuestasTemporales = new ArrayList<>();
        respuestasTemporales.add(tvResp1.getText().toString());
        respuestasTemporales.add(tvResp2.getText().toString());
        respuestasTemporales.add(tvResp3.getText().toString());
        respuestasTemporales.add(tvResp4.getText().toString());
        respuestasTemporales.add(tvResp5.getText().toString());
        viewModel.setRespuestasTemporales(respuestasTemporales);
    }

    /**
     * Método que muestra un diálogo de alerta para indicar que se ha acabado el mazo de cartas y por
     * tanto, la ronda.
     * */
    public void feedBackRespuestas(ArrayList<Integer> validaRespuestas) {
        int acertadas=0,falladas=0;
        for (int i = 0; i < validaRespuestas.size(); i++) {
            if (validaRespuestas.get(i)==1){
                acertadas++;
            }else{
                falladas++;
            }
        }
        MaterialAlertDialogBuilder alertDialog = new MaterialAlertDialogBuilder(getContext());
        alertDialog.setTitle("Carta acabada");
        alertDialog.setMessage("Has acertado "+acertadas+" y has fallado "+falladas+" de las cinco preguntas");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }


    private boolean compruebaCampoTexto(String tvResp) {
        return tvResp.isEmpty();
    }

    /**-------------------------------------BOTONES----------------------------------------------**/

    private void botonContestar(@NonNull View view, NavController navC) {
        ImageButton btContestar = view.findViewById(R.id.btContestar);
        btContestar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Dependiendo de si se han recogido todas las respuestas y de si quedan más cartas
                en juego, o no, navegamos a un sitio u otro*/
                if (recogeRespuestas(view)) {
                    if (viewModel.getListaCartas().size()==0){
                        navC.navigate(R.id.ac_respuestaCarta_to_finalizaJuego);
                    }else{
                        viewModel.getRespuestasTemporales().clear();
                        navC.navigate(R.id.ac_respuestasCarta_to_cartaEnJuego);
                    }
                }
            }
        });
    }

    private void botonVolver(@NonNull View view, NavController navC) {
        ImageButton btVolver = view.findViewById(R.id.btVolver);
        btVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardaRespuestasTemporales(view);
                viewModel.getListaCartas().add(0, viewModel.getCarta());
                navC.navigate(R.id.ac_respuestasCarta_to_cartaEnJuego);
            }
        });
    }

    private void botonFinalizar(@NonNull View view, NavController navC) {
        ImageButton btFinalizar = view.findViewById(R.id.bt_finalizar);
        btFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setJugando(false);
                navC.navigate(R.id.ac_respuestaCarta_to_finalizaJuego);
            }
        });
    }
}