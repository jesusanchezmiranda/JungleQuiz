package com.tulagames.junglequiz.model.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.tulagames.junglequiz.model.room.dao.CartaDao;
import com.tulagames.junglequiz.model.room.dao.RespuestaDao;
import com.tulagames.junglequiz.model.room.dao.UsuarioDao;
import com.tulagames.junglequiz.model.room.pojo.Carta;
import com.tulagames.junglequiz.model.room.pojo.Respuesta;
import com.tulagames.junglequiz.model.room.pojo.Usuario;


@Database(entities = {Usuario.class, Carta.class, Respuesta.class}, version = 1, exportSchema = false)
public abstract class JungleQuizDatabase extends RoomDatabase {


    public abstract UsuarioDao getUsuarioDao();
    public abstract RespuestaDao getRespuestaDao();
    public abstract CartaDao getCartaDao();

    private static volatile JungleQuizDatabase INSTANCE;

    public static JungleQuizDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            JungleQuizDatabase.class, "dbJungleQuiz.sqlite").build();
                }
        return INSTANCE;
    }


}
