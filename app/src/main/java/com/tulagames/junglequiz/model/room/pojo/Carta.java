package com.tulagames.junglequiz.model.room.pojo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "Carta")
public class Carta {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    @ColumnInfo(name = "urlfoto")
    private String urlfoto;

    @NonNull
    @ColumnInfo(name = "nombreAnimal")
    private String nombreAnimal;

    @NonNull
    @ColumnInfo(name = "descripcion")
    private String descripcion;

    public Carta() {
    }

    public Carta(@NonNull String urlfoto, @NonNull String nombreAnimal, @NonNull String descripcion) {
        this.urlfoto = urlfoto;
        this.nombreAnimal = nombreAnimal;
        this.descripcion = descripcion;
    }

    public Carta(long id, @NonNull String urlfoto, @NonNull String nombreAnimal, @NonNull String descripcion) {
        this.id = id;
        this.urlfoto = urlfoto;
        this.nombreAnimal = nombreAnimal;
        this.descripcion = descripcion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getUrlfoto() {
        return urlfoto;
    }

    public void setUrlfoto(@NonNull String urlfoto) {
        this.urlfoto = urlfoto;
    }

    @NonNull
    public String getNombreAnimal() {
        return nombreAnimal;
    }

    public void setNombreAnimal(@NonNull String nombreAnimal) {
        this.nombreAnimal = nombreAnimal;
    }

    @NonNull
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(@NonNull String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Carta{" +
                "id=" + id +
                ", urlfoto='" + urlfoto + '\'' +
                ", nombreAnimal='" + nombreAnimal + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
