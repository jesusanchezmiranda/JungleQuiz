package com.tulagames.junglequiz.model.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.tulagames.junglequiz.model.room.pojo.Respuesta;

import java.util.List;

@Dao
public interface RespuestaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Respuesta respuesta);

    @Update
    int update(Respuesta respuesta);

    @Delete
    int delete (Respuesta respuesta);

    @Query("select * from respuesta")
    LiveData<List<Respuesta>> getAllRespuestas();

}
