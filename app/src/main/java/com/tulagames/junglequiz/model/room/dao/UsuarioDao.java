package com.tulagames.junglequiz.model.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.tulagames.junglequiz.model.room.pojo.Usuario;

import java.util.List;

@Dao
public interface UsuarioDao {

    @Delete
    int delete (Usuario usuario);

    @Query("delete from usuario where id = :id")
    int delete (long id);

    @Query("select * from usuario where id = :id")
    Usuario get (int id);

    @Query("select * from usuario order by id")
    LiveData<List<Usuario>> getAllUsuarios();

    @Query("select * from usuario ")
    List<Usuario> listAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Usuario usuario);

    @Update
    int update(Usuario usuario);

}
