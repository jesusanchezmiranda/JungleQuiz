package com.tulagames.junglequiz.model.room.pojo;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Usuario")
public class Usuario {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    @ColumnInfo(name = "nombre")
    private String nombre;

    @NonNull
    @ColumnInfo(name = "avatar")
    private int avatar;

    @NonNull
    @ColumnInfo(name = "numRes")
    private int numRes;

    @NonNull
    @ColumnInfo(name = "numResCor")
    private int numResCor;

    public Usuario() {
    }

    public Usuario(@NonNull String nombre, int avatar, int numRes, int numResCor) {
        this.nombre = nombre;
        this.avatar = avatar;
        this.numRes = numRes;
        this.numResCor = numResCor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public int getNumRes() {
        return numRes;
    }

    public void setNumRes(int numRes) {
        this.numRes = numRes;
    }

    public int getNumResCor() {
        return numResCor;
    }

    public void setNumResCor(int numResCor) {
        this.numResCor = numResCor;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", avatar=" + avatar +
                ", numRes=" + numRes +
                ", numResCor=" + numResCor +
                '}';
    }
}
