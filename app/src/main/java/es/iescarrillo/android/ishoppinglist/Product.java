package es.iescarrillo.android.ishoppinglist;

import java.io.Serializable;

public class Product implements Serializable {
    int id;
    String nom;
    String info;
    boolean estado;

    public Product(int id, String nom, String info, boolean estado) {
        this.id = id;
        this.nom = nom;
        this.info = info;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return nom;
    }
}

