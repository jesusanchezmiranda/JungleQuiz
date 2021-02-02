package com.tulagames.junglequiz.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.tulagames.junglequiz.R;
import com.tulagames.junglequiz.model.room.pojo.Usuario;
import com.tulagames.junglequiz.viewmodel.ViewModel;

import java.util.List;

/**
 * Clase perteneciente al fragmento fg_crear_editar_usuario. En este fragmento creamos o editamos el
 * a los usuarios.
 * */
public class CrearEditarUsuario extends Fragment implements View.OnClickListener {

    private ImageView contAvatar;
    private TextView nUsuario;
    private int contador,idImagen;
    private ViewModel viewModel;
    private  Usuario usuarioActualizar;

    public CrearEditarUsuario() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_crear_editar_usuario, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navC = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        botonVolver(view, navC);
        botonCrear(view, navC);
        botonEditar(view, navC);
        init(view);
        EditarOCrear();
        recuperaNombreUsuario(view);
    }

    /**
     * Método que inicializa las imágenes y prepara los listener de los botones de las imágenes
     * */
    private void init(View view) {
        contAvatar = view.findViewById(R.id.contenedorAvatar);
        nUsuario = view.findViewById(R.id.nUsuario);
        ImageButton btCastor = view.findViewById(R.id.btCastor);
        ImageButton btLeon = view.findViewById(R.id.btLeon);
        ImageButton btCiervo = view.findViewById(R.id.btCiervo);
        ImageButton btArdi = view.findViewById(R.id.btArdi);
        ImageButton btElefante = view.findViewById(R.id.btElefante);
        ImageButton btConejillo = view.findViewById(R.id.btConejillo);
        ImageButton btHipo = view.findViewById(R.id.btHipo);
        ImageButton btMapache = view.findViewById(R.id.btMapache);
        ImageButton btMofeta = view.findViewById(R.id.btMofeta);
        ImageButton btOsa = view.findViewById(R.id.btOsa);
        ImageButton btTigre = view.findViewById(R.id.btTigre);
        ImageButton btZorra = view.findViewById(R.id.btZorra);
        escuchaBotones(btCastor, btLeon, btCiervo, btArdi, btElefante, btConejillo, btHipo, btMapache, btMofeta, btOsa, btTigre, btZorra);
    }

    /**
     * Método usado para diferenciar entre el modo de edición o creación y mostrar diferentes botones,
     * en función del modo en el que nos encontremos.
     * */
    private void EditarOCrear() {
        if (viewModel.isEditarJugar()){
            usuarioActualizar = viewModel.getUsuario();
            nUsuario.setText(usuarioActualizar.getNombre());
            muestraBotonesEditar();
        }else{
            contadorDeJugadores();
            contAvatar.setImageResource(R.drawable.av_castor);
        }
    }

    /**----------CREAR----------**/

    /**
     * Método que recoge datos y crea un usuario
     * */
    private void creaUsuario(Usuario usuario) {
        /* Usamos este if para controlar la longitud establecida como máximo. Debémos de controlarlo
        * porque se ha implementado los métodos TextWatcher*/
        if (nUsuario.getText().length()<=12){
            usuario.setAvatar(idImagen);
            usuario.setNombre(nUsuario.getText().toString());
            usuario.setNumRes(0);
            usuario.setNumResCor(0);
            viewModel.insert(usuario);
            Toast.makeText(getContext(), "Usuario creado con exito", Toast.LENGTH_LONG).show();
            contadorDeJugadores();
            contAvatar.setImageResource(R.drawable.av_castor);
        }
    }

    /**
     * Método que cuenta el número de jugadores que tenémos para poder dar un nombre por defecto, en
     * caso de no poner ninguno.
     * */
    private void contadorDeJugadores() {
        viewModel.getLiveListaUsuarios().observe(getActivity(), new Observer<List<Usuario>>() {
            @Override
            public void onChanged(List<Usuario> usuarios) {
                contador = usuarios.size();
                contador++;
                nUsuario.setText("Usuario " + contador);
            }
        });
    }

    /**
     * Método que controla el contenido de la caja de texto y diferentes estados, vacías, con texto...
     * */
    private void compruebaCajaTexto(TextView tietNomUsuario) {
        if (tietNomUsuario.getText().toString().isEmpty() || tietNomUsuario.getText().equals(" ")) {
            if (viewModel.isEditarJugar()){
                nUsuario.setText(usuarioActualizar.getNombre());
            }else{
                contadorDeJugadores();
            }
        } else {
            tietNomUsuario.clearComposingText();
            nUsuario.setText(tietNomUsuario.getText());
        }
    }

    /**
     * Método que devuelve la imágen de usuario que corresponda al id que se le pase.
     * */
    private void escuchaBotonesAvatar(int id) {
        switch (id) {
            case R.id.btCastor:
                contAvatar.setImageResource(R.drawable.av_castor);
                idImagen=0;
                break;
            case R.id.btLeon:
                contAvatar.setImageResource(R.drawable.av_leon);
                idImagen=1;
                break;
            case R.id.btCiervo:
                contAvatar.setImageResource(R.drawable.av_ciervo);
                idImagen=2;
                break;
            case R.id.btArdi:
                contAvatar.setImageResource(R.drawable.av_ardi);
                idImagen=3;
                break;
            case R.id.btElefante:
                contAvatar.setImageResource(R.drawable.av_elefante);
                idImagen=4;
                break;
            case R.id.btConejillo:
                contAvatar.setImageResource(R.drawable.av_conejillo);
                idImagen=5;
                break;
            case R.id.btHipo:
                contAvatar.setImageResource(R.drawable.av_hipo);
                idImagen=6;
                break;
            case R.id.btMapache:
                contAvatar.setImageResource(R.drawable.av_mapache);
                idImagen=7;
                break;
            case R.id.btMofeta:
                contAvatar.setImageResource(R.drawable.av_mofeta);
                idImagen=8;
                break;
            case R.id.btOsa:
                contAvatar.setImageResource(R.drawable.av_osa);
                idImagen=9;
                break;
            case R.id.btTigre:
                contAvatar.setImageResource(R.drawable.av_tigre);
                idImagen=10;
                break;
            case R.id.btZorra:
                contAvatar.setImageResource(R.drawable.av_zorra);
                idImagen=11;
                break;
        }
    }

    /**----------EDITAR----------**/

    /***
     * Método que recupera los datos y actualiza a un usuario determinado
     */
    private void editarUsuario() {
        usuarioActualizar.setAvatar(idImagen);
        usuarioActualizar.setNombre(nUsuario.getText().toString());
        viewModel.update(usuarioActualizar);
        Toast.makeText(getContext(), "Usuario actualizado con exito", Toast.LENGTH_LONG).show();
        nUsuario.setText(usuarioActualizar.getNombre());
        muestraBotonesEditar();
    }

    /***
     * Método que, en caso de estar en modo edición, muestra la imagen de avatar del usuario que se
     * va a editar
     */

    private void muestraBotonesEditar() {
        idImagen = usuarioActualizar.getAvatar();
        switch (idImagen) {
            case 0:
                contAvatar.setImageResource(R.drawable.av_castor);
                break;
            case 1:
                contAvatar.setImageResource(R.drawable.av_leon);
                break;
            case 2:
                contAvatar.setImageResource(R.drawable.av_ciervo);
                break;
            case 3:
                contAvatar.setImageResource(R.drawable.av_ardi);
                break;
            case 4:
                contAvatar.setImageResource(R.drawable.av_elefante);
                break;
            case 5:
                contAvatar.setImageResource(R.drawable.av_conejillo);
                break;
            case 6:
                contAvatar.setImageResource(R.drawable.av_hipo);
                break;
            case 7:
                contAvatar.setImageResource(R.drawable.av_mapache);
                break;
            case 8:
                contAvatar.setImageResource(R.drawable.av_mofeta);
                break;
            case 9:
                contAvatar.setImageResource(R.drawable.av_osa);
                break;
            case 10:
                contAvatar.setImageResource(R.drawable.av_tigre);
                break;
            case 11:
                contAvatar.setImageResource(R.drawable.av_zorra);
                break;
        }
    }

    /**----------CREAR Y EDITAR----------**/

    /***
     * Método que recibe las imágenes y prepara los listeners de cada imagen
     */
    private void escuchaBotones(ImageButton btCastor, ImageButton btLeon, ImageButton btCiervo, ImageButton btArdi, ImageButton btElefante, ImageButton btConejillo, ImageButton btHipo, ImageButton btMapache, ImageButton btMofeta, ImageButton btOsa, ImageButton btTigre, ImageButton btZorra) {
        btCastor.setOnClickListener(this);
        btLeon.setOnClickListener(this);
        btCiervo.setOnClickListener(this);
        btArdi.setOnClickListener(this);
        btElefante.setOnClickListener(this);
        btConejillo.setOnClickListener(this);
        btHipo.setOnClickListener(this);
        btMapache.setOnClickListener(this);
        btMofeta.setOnClickListener(this);
        btOsa.setOnClickListener(this);
        btTigre.setOnClickListener(this);
        btZorra.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        escuchaBotonesAvatar(view.getId());
    }

    /**
     * Método que controla la caja de texto del nombre de usuario con diferentes listeners.
     * */
    private void recuperaNombreUsuario(View view) {
        TextView tietNomUsuario = view.findViewById(R.id.tietNomUsuario);
        tietNomUsuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                nUsuario.setText("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                compruebaCajaTexto(tietNomUsuario);
            }

            @Override
            public void afterTextChanged(Editable s) {
                compruebaCajaTexto(tietNomUsuario);
            }
        });
    }

/**-------------------------------------BOTONES----------------------------------------------**/

    private void botonCrear(@NonNull View view, NavController navC) {
        ImageButton btCrear = view.findViewById(R.id.btCrearUsuario);
        ImageButton btEditar = view.findViewById(R.id.btEditarUsuario);
        if (!viewModel.isEditarJugar()){
            btEditar.setVisibility(View.INVISIBLE);
            btEditar.setClickable(false);
            Usuario usuario = new Usuario();
            btCrear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    creaUsuario(usuario);
                }
            });
        }
    }

    private void botonEditar(@NonNull View view, NavController navC) {
        ImageButton btCrear = view.findViewById(R.id.btCrearUsuario);
        ImageButton btEditar = view.findViewById(R.id.btEditarUsuario);
        if (viewModel.isEditarJugar()) {
            btCrear.setVisibility(View.INVISIBLE);
            btCrear.setClickable(false);
            btEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editarUsuario();
                }
            });
        }
    }

    private void botonVolver(@NonNull View view, NavController navC) {
        ImageButton btVolver = view.findViewById(R.id.btVolver);
        btVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navC.navigate(R.id.ac_crearEditarUsuario_to_menuAdminUsuarios);
            }
        });
    }
}