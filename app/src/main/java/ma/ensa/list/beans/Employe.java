package ma.ensa.list.beans;

import android.graphics.Bitmap;

import java.util.Date;
import java.util.List;

public class Employe {
    private int id;
    private String nom;
    private String prenom;

    private Date date;

    private Service service;
    private Bitmap photo;

    public Employe() {
    }

    public Employe(int id, String nom, String prenom, Service service, Bitmap photo, Date date) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.service = service;
        this.photo = photo;
        this.date = date;

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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
