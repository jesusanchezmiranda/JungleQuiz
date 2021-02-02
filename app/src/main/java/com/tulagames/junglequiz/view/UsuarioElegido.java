package com.tulagames.junglequiz.view;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.content.res.TypedArray;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.tulagames.junglequiz.R;
import com.tulagames.junglequiz.model.room.pojo.Carta;
import com.tulagames.junglequiz.model.room.pojo.Usuario;
import com.tulagames.junglequiz.viewmodel.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase perteneciente al fragmento fg_usuario_elegido. Este fragmento muestra todos los datos del
 * usuario elegido y, en un segundo plano, prepara las cartas, como preproceso de  comenzar a juegar.
 */
public class UsuarioElegido extends Fragment {

    private ViewModel viewModel;
    private Usuario usuario;
    private TypedArray avatar;

    public UsuarioElegido() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_usuario_elegido, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navC = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        barajaCartas();
        mostrarUsuarioPantalla(view);
        botonJugar(view, navC);
        botonEditar(view, navC);
        BotonVolver(view, navC);
    }

    /**
     * Método que recupera el usuario elegído y muesta sus datos.
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

    /**
     * Método encargado de cargar el array con las imágenes de avatares de usuarios.
     * */
    private TypedArray obtenerImagenesAvatares() {
        Resources res = getResources();
        TypedArray avatares = res.obtainTypedArray(R.array.av_circulos);
        return avatares;
    }

    /**
     * Método encargado de recuperar todas las cartas que estén almacenadas, generar un mazo de
     * cartas y mezclarlo. Este método actua de manera preventiva, suponiendo que nos encontramos en
     * la antesala del comienzo del juego.
     * */
    private void barajaCartas() {
        List<Carta> listaCartas = new ArrayList<>();
        viewModel.getLiveListaCartas().observe(getActivity(), new Observer<List<Carta>>() {
            @Override
            public void onChanged(List<Carta> cartas) {
                listaCartas.clear();
                listaCartas.addAll(cartas);
                Collections.shuffle(listaCartas);
                viewModel.setListaCartas(listaCartas);
            }
        });
    }

    /**-------------------------------------BOTONES----------------------------------------------**/

    private void botonEditar(@NonNull View view, NavController navC) {
        ImageButton btEditar = view.findViewById(R.id.btUsuario);
        /*En caso de no estar en modo de editar, no se muestra el botón*/
        if (!viewModel.isEditarJugar()){
            btEditar.setVisibility(View.INVISIBLE);
        }else{
            btEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewModel.setUsuario(usuario);
                    navC.navigate(R.id.ac_usuarioElegido_to_addUsuario);
                }
            });
        }
    }

    private void BotonVolver(@NonNull View view, NavController navC) {
        ImageButton btVolver = view.findViewById(R.id.btVolver);
        btVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navC.navigate(R.id.ac_usuarioElegido_to_listaUsuarios);
            }
        });
    }

    private void botonJugar(@NonNull View view, NavController navC) {
        ImageButton btJugar = view.findViewById(R.id.btJugar);
        /*En caso de estar en modo de editar, no se muestra el botón*/
        if (viewModel.isEditarJugar()){
            btJugar.setVisibility(View.INVISIBLE);
        }else{
            btJugar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewModel.getRespuestasTemporales().clear();
                    navC.navigate(R.id.ac_usuarioElegido_to_cartaEnJuego);
                }
            });
        }
    }
}