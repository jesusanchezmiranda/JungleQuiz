package com.tulagames.junglequiz.view;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.tulagames.junglequiz.R;
import com.tulagames.junglequiz.view.adapter.RVAdapterCorreos;
import com.tulagames.junglequiz.viewmodel.ViewModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase perteneciente al fragmento fg_enviar_mail. Mediante este fragmento, puedes enviar un mail con tu
 * puntuación en el juego.
 * */
public class EnviarMail extends Fragment {

    private ViewModel viewModel;
    private List<String> emails;

    public EnviarMail() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_enviar_mail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navC = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        gestionDeCorreo(view, navC);
        botonEnviar(view, navC);
        botonVolver(view, navC);
    }

    /**
     * Método encargado de recuperar los correos y mostrarlos en un recycler.
     * */
    private void gestionDeCorreo(@NonNull View view, NavController navC) {
        emails = viewModel.getCorreos();
        abrirRecycler(view, navC, emails);
    }

    /**
     * Método que crea el adaptador del recycler
     * */
    private RVAdapterCorreos abrirRecycler(View view, NavController navC, List<String> emails) {
        RecyclerView rvCorreos = view.findViewById(R.id.rvCorreos);
        RVAdapterCorreos rvAdapterCorreos = new RVAdapterCorreos(emails,navC,getActivity());
        rvCorreos.setAdapter(rvAdapterCorreos);
        rvCorreos.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rvAdapterCorreos;
    }

    /**
     * Método encargado de enviar el mail con la puntuación
     * */
    @SuppressLint("IntentReset")
    private void mandarcorreo(int numResCor){
        TextInputEditText tietMail;
        String direccion;
        tietMail = getView().findViewById(R.id.tietMail);
        direccion = tietMail.getText().toString(); // en este campo se mete el correo al que se le envia
        String[] TO = {direccion};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "PUNTUACION JUNGLE QUIZ");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Tu puntuacion es de: "+numResCor+ " puntos."); // * configurar email aquí!
        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar email."));
            Log.i("EMAIL", "Enviando email...");
        }
        catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "NO existe ningún cliente de email instalado!.", Toast.LENGTH_SHORT).show();
        }
    }

    /**-------------------------------------BOTONES----------------------------------------------**/

    private void botonEnviar(@NonNull View view, NavController navC) {
        ImageButton btEnviarMail = view.findViewById(R.id.btEnviarMail);
        btEnviarMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mandarcorreo(viewModel.getUsuario().getNumResCor());
            }
        });
    }

    private void botonVolver(@NonNull View view, NavController navC) {
        ImageButton btVolver = view.findViewById(R.id.btVolverMails);
        btVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navC.navigate(R.id.ac_enviarMail_to_finalizaJuego);
            }
        });
    }
}