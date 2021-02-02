package com.tulagames.junglequiz.model.room.pojo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Respuesta")
public class Respuesta {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    @ColumnInfo(name = "idCarta")
    private long idCarta;

    @NonNull
    @ColumnInfo(name = "respuesta1")
    private String respuesta1;

    @NonNull
    @ColumnInfo(name = "respuesta2")
    private String respuesta2;

    @NonNull
    @ColumnInfo(name = "respuesta3")
    private String respuesta3;

    @NonNull
    @ColumnInfo(name = "respuesta4")
    private String respuesta4;

    @NonNull
    @ColumnInfo(name = "respuesta5")
    private String respuesta5;

    public Respuesta() {
    }

    public Respuesta(long id, long idCarta, String respuesta1, String respuesta2, String respuesta3, String respuesta4, String respuesta5) {
        this.id = id;
        this.idCarta = idCarta;
        this.respuesta1 = respuesta1;
        this.respuesta2 = respuesta2;
        this.respuesta3 = respuesta3;
        this.respuesta4 = respuesta4;
        this.respuesta5 = respuesta5;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdCarta() {
        return idCarta;
    }

    public void setIdCarta(long idCarta) {
        this.idCarta = idCarta;
    }

    public String getRespuesta1() {
        return respuesta1;
    }

    public void setRespuesta1(String respuesta1) {
        this.respuesta1 = respuesta1;
    }

    public String getRespuesta2() {
        return respuesta2;
    }

    public void setRespuesta2(String respuesta2) {
        this.respuesta2 = respuesta2;
    }

    public String getRespuesta3() {
        return respuesta3;
    }

    public void setRespuesta3(String respuesta3) {
        this.respuesta3 = respuesta3;
    }

    public String getRespuesta4() {
        return respuesta4;
    }

    public void setRespuesta4(String respuesta4) {
        this.respuesta4 = respuesta4;
    }

    public String getRespuesta5() {
        return respuesta5;
    }

    public void setRespuesta5(String respuesta5) {
        this.respuesta5 = respuesta5;
    }

    @Override
    public String toString() {
        return "Respuesta{" +
                "id=" + id +
                ", idCarta=" + idCarta +
                ", respuesta1=" + respuesta1 +
                ", respuesta2=" + respuesta2 +
                ", respuesta3=" + respuesta3 +
                ", respuesta4=" + respuesta4 +
                ", respuesta5=" + respuesta5 +
                '}';
    }
}
