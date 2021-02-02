package com.tulagames.junglequiz.model.room.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.tulagames.junglequiz.model.room.pojo.Carta;

import java.util.List;

@Dao
public interface CartaDao {

    @Delete
    int delete (Carta carta);


    @Query("delete from carta where id = :id")
    int delete (long id);


    @Query("select * from carta where id = :id")
    Carta get (int id);


    @Query("select * from carta order by id")
    LiveData<List<Carta>> getAllCartas();

    @Query("select * from carta ")
    List<Carta> listAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Carta carta);

    @Update
    int update(Carta carta);

    @Query("select id from carta order by id desc limit 1")
    LiveData<Long> getIDCarta();

}
