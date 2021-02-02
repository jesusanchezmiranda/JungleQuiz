package com.tulagames.junglequiz.view;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.tulagames.junglequiz.R;

/**
 * Clase perteneciente al fragmento fg_login_admin_registrado. Mediante este fragmento se comprueba
 * la contraseña del administrador de la aplicación
 */

public class LoginAdminRegistrado extends Fragment {

    public LoginAdminRegistrado() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_login_admin_registrado, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navC = Navigation.findNavController(view);
        botonAceptar(view, navC);
        botonVolver(view, navC);
    }

    /**
     * Método que recupera la preferencia compartida de la contraseña del administrador de la
     * aplicación
     * */
    private String compruebaContrasenia(){
        SharedPreferences sharedContrasenia = getActivity().getSharedPreferences("AdminPass", getContext().MODE_PRIVATE);
        return sharedContrasenia.getString("AdminPass", "");
    }

    /**-------------------------------------BOTONES----------------------------------------------**/

    /**
     * Método que comprueba que la contraseña sea correcta.
     * */
    private void botonAceptar(View view, NavController navC) {
        ImageButton btAceptarPass = view.findViewById(R.id.btAceptar);
        TextInputEditText tiet2 = view.findViewById(R.id.tiet2);
        TextInputLayout til1 = view.findViewById(R.id.ContraseñaAdmin);
        btAceptarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tiet2.getText().toString().equals(compruebaContrasenia())){
                    navC.navigate(R.id.ac_loginAdminRegistrado_to_menuAdmin);
                }else{
                    til1.setError("La contraseña es erronea");
                }
            }
        });
    }

    private void botonVolver(@NonNull View view, NavController navC) {
        ImageButton btCancelarPass = view.findViewById(R.id.btCancelar);
        btCancelarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navC.navigate(R.id.ac_loginAdminRegistrado_to_portada);
            }
        });
    }
}