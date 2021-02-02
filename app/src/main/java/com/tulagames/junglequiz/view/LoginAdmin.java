package com.tulagames.junglequiz.view;

import android.content.SharedPreferences;
import android.content.res.Resources;
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

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.tulagames.junglequiz.R;
import com.tulagames.junglequiz.model.room.pojo.Carta;
import com.tulagames.junglequiz.model.room.pojo.Respuesta;
import com.tulagames.junglequiz.viewmodel.ViewModel;

/**
 * Clase perteneciente al fragmento fg_login_admin. Mediante este fragmento se registra por primera
 * vez el administrador de la aplicación
 */

public class LoginAdmin extends Fragment {

    private ViewModel viewModel;

    public LoginAdmin() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_login_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navC = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        botonAceptar(view, navC);
        botonCancelar(view, navC);
    }

    /**
     * Método que crea y guarda la preferencia compartida de la contraseña
     * */
    private void guardaContrasenia(String password) {
        SharedPreferences contraseniaPreferences = getActivity().getSharedPreferences("AdminPass", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editorContrasenia = contraseniaPreferences.edit();
        editorContrasenia.putString("AdminPass", password);
        editorContrasenia.commit();
    }

    /**
     * Método que, en caso de crearse de manera correcta la contraseña del administrador, genera el
     * mazo inicial de cartas. Se usa este método en este fragmento porque solo se entrará en él una
     * sola vez, no volviendose a generar el mazo inicial.
     * */
   private void generaMazo(){
       Resources mazo = getResources();
       String[] imgsCartas = mazo.getStringArray(R.array.imgs);
       String[] nombreCartas = mazo.getStringArray(R.array.nombre_animal);
       String[] descripciones = mazo.getStringArray(R.array.descripcion);
       String[] resp1 = mazo.getStringArray(R.array.resp1);
       String[] resp2 = mazo.getStringArray(R.array.resp2);
       String[] resp3 = mazo.getStringArray(R.array.resp3);
       String[] resp4 = mazo.getStringArray(R.array.resp4);
       String[] resp5 = mazo.getStringArray(R.array.resp5);
       for (int i = 0; i < 7 ; i++) {
           Carta carta = new Carta(i,imgsCartas[i], nombreCartas[i], descripciones[i]);
           Respuesta respuesta = new Respuesta(i,i,resp1[i],resp2[i],resp3[i],resp4[i],resp5[i]);
           if (carta.getId()!=0 && respuesta.getId()!=0){
               viewModel.insert(carta);
               viewModel.insert(respuesta);
           }
       }
   }

    /**-------------------------------------BOTONES----------------------------------------------**/

    private void botonCancelar(@NonNull View view, NavController navC) {
        ImageButton btCancelarPass = view.findViewById(R.id.btCancelar);
        btCancelarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navC.navigate(R.id.ac_loginAdmin_to_portada);
            }
        });
    }

    private void botonAceptar(@NonNull View view, NavController navC) {
        ImageButton btAceptarPass = view.findViewById(R.id.btAceptar);
        TextInputEditText tiet1 = view.findViewById(R.id.tiet1);
        TextInputEditText  tiet2 = view.findViewById(R.id.tiet2);
        TextInputLayout til1 = view.findViewById(R.id.ContraseñaAdmin);
        TextInputLayout til2 = view.findViewById(R.id.confirContraseña);
        btAceptarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Si las contraseñas son iguales, se registra y se genera el mazo inicial.
                if(tiet1.getText().toString().equals(tiet2.getText().toString())){
                    guardaContrasenia(tiet1.getText().toString());
                    generaMazo();
                    navC.navigate(R.id.ac_loginAdmin_to_menuAdmin);
                }else{
                    til1.setError("Las contraseñas no coinciden");
                    til2.setError("Las contraseñas no coinciden");
                }
            }
        });
    }
}