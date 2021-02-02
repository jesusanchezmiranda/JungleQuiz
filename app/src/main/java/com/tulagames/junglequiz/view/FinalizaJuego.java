package com.tulagames.junglequiz.view;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.tulagames.junglequiz.R;
import com.tulagames.junglequiz.model.room.pojo.Usuario;
import com.tulagames.junglequiz.viewmodel.ViewModel;

/**
 * Clase perteneciente al fragmento fg_finaliza_juego. Mediante este fragmento, obtienes información
 * sobre el número de cartas contestadas y las respuestas acertadas, del jugador que ha acabado la ronda
 * */
public class FinalizaJuego extends Fragment {

    private ViewModel viewModel;
    private Usuario usuario;
    private TypedArray avatar;

    public FinalizaJuego() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_finaliza_juego, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navC = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        mostrarUsuarioPantalla(view);
        botonFinalizar(view,navC);
        botonEnviar(view,navC);
    }

    /**
     * Muestra la información del jugador que ha terminado la ronda
     * */
    @SuppressLint("SetTextI18n")
    private void mostrarUsuarioPantalla(View view) {
        ImageView avatarUsuario = view.findViewById(R.id.avatarUsuario);
        TextView nomUsuario = view.findViewById(R.id.nomUsuario);
        TextView numresp = view.findViewById(R.id.numResp);
        TextView numrespOk = view.findViewById(R.id.numResOk);
        usuario = viewModel.getUsuario();
        nomUsuario.setText(usuario.getNombre());
        avatar = obtenerImagenesAvatares();
        avatarUsuario.setImageDrawable(avatar.getDrawable(usuario.getAvatar()));
        numresp.setText(usuario.getNumRes()+"");
        numrespOk.setText(usuario.getNumResCor()+"");
    }

    private TypedArray obtenerImagenesAvatares() {
        Resources res = getResources();
        TypedArray avatares = res.obtainTypedArray(R.array.av_circulos);
        return avatares;
    }

    /**-------------------------------------BOTONES----------------------------------------------**/

    private void botonEnviar(@NonNull View view, NavController navC) {
        ImageButton btenviar = view.findViewById(R.id.bt_Enviar);
        btenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navC.navigate(R.id.ac_finalizaJuego_to_enviarMail);
            }
        });
    }

    private void botonFinalizar(@NonNull View view, NavController navC) {
        ImageButton btFinalizar = view.findViewById(R.id.bt_finalizarRonda);
        btFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navC.navigate(R.id.ac_finalizaJuego_to_portada);
            }
        });
    }
}