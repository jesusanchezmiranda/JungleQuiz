package com.tulagames.junglequiz.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;

import com.tulagames.junglequiz.R;
import com.tulagames.junglequiz.receiver.CargaReceiver;

/**
 * Clase perteneciente a la actividad principal. Es en este activity se piden los permisos necesarios,
 * se lanza el intent del consumo de la batería, se gestiona la música, sus estados y loop, se
 * controla la pantalla completa y que se esconda la barra de navegación del sistema.
 * */


public class MainActivity extends AppCompatActivity {

    private static MediaPlayer mediaPlayer;
    private CargaReceiver cargaReceiver = new CargaReceiver();
    private static final int PERMISO = 100;
    private String[] codigosPermisos = {Manifest.permission.READ_CONTACTS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ventanaCompleta();
        init();
        getPermissions();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ventanaCompleta();
        if (obtenerEstadoMusica()) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registraReceiverBateria();
        ventanaCompleta();
        if (obtenerEstadoMusica()) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(cargaReceiver);
        ventanaCompleta();
        if (obtenerEstadoMusica()) {
            mediaPlayer.pause();
        }
    }

    /**
     * Este método controla el estado de la barra de navegación al cerrarse el teclado
     * */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        ventanaCompleta();
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Mediante este método se controla lo a acción de pulsar atras en la barra de navegación. Al
     * dejarlo vacío, anulamos caulquier uso que tenga.
     * */
    @Override
    public void onBackPressed() {
    }

    /**----------------------------------------PERMISOS------------------------------------------**/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISO:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    explainPermission();
                }
                break;
        }
    }

    @SuppressLint("NewApi")
    private void pidoPermiso() {
        requestPermissions(codigosPermisos, PERMISO);
    }

    private void explainPermission(String... permissions) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permisos Necesarios");
        builder.setMessage("Necesitas los permisos para ejecutar la aplicacion correctamente");
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pidoPermiso();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.fromParts("package", getPackageName(), null)));
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int contactsPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
            if (contactsPermission == PackageManager.PERMISSION_GRANTED) {
            } else {
                if (shouldShowRequestPermissionRationale(String.valueOf(codigosPermisos))) {
                    explainPermission(Manifest.permission.READ_CONTACTS);
                } else {
                    requestPermissions(codigosPermisos, PERMISO);
                }
            }
        }
    }

    private void registraReceiverBateria() {
        IntentFilter intentFilter = new IntentFilter(BatteryManager.EXTRA_BATTERY_LOW);
        intentFilter.addAction(Intent.ACTION_BATTERY_LOW);
        registerReceiver(cargaReceiver, intentFilter);
    }

    /**----------------------------------------MUSICA--------------------------------------------**/

    private void init() {
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.fondo);
        mediaPlayer.setLooping(true);
    }

    private boolean obtenerEstadoMusica() {
        SharedPreferences musicaEstado = MainActivity.this.getSharedPreferences("musica", Context.MODE_PRIVATE);
        return musicaEstado.getBoolean("musica", true);
    }

    public static void pausarMusica() {
        mediaPlayer.pause();
    }

    public static void encenderMusica() {
        mediaPlayer.start();
    }

    /**
     * Este método esconde diferentes elementos del sistema y da a la palicación el espacio total de
     * la pantalla
     * */
    private void ventanaCompleta() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
        decorView.setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        ventanaCompleta();
                    }
                });
    }
}