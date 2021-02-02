package com.tulagames.junglequiz.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.airbnb.lottie.LottieAnimationView;
import com.tulagames.junglequiz.R;

/**
 * Clase perteneciente al fragmento fg_logo_inicio. Este fragmento hace las veces de SplashScreen.
 */

public class LogoInicio extends Fragment {

    private MediaPlayer mediaPlayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_logo_inicio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lanzaAnimacion(view);
    }

    /**
     * Método que lanza la animación de inicio de la aplicación
     * */
    private void lanzaAnimacion(View view) {
        LottieAnimationView logotula;
        logotula = view.findViewById(R.id.animationView);
        lanzarSonido();
        logotula.playAnimation();
        logotula.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                abrirPortada(view);
            }
        });
    }

    /**
     * Método que inicializa el sonido de la splashscreen.
     * */
    private void lanzarSonido() {
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.sonido_tula_intro);
        mediaPlayer.start();
    }

    /**
     * Mediante este método nos movemos a la portada de la aplicación
     * */
    private void abrirPortada(View view) {
        final NavController navC = Navigation.findNavController(view);
        navC.navigate(R.id.ac_logoInicio_to_portada);
    }
}