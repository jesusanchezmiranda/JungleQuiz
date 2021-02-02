package com.tulagames.junglequiz.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CargaReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Jungle Quiz. Batería baja. Por favor, conecte el cargador.", Toast.LENGTH_LONG).show();

    }


}



