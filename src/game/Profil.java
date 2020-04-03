/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.ArrayList;
import org.w3c.dom.Document;

/**
 *
 * @author kahlaoui
 */
public class Profil {
    private String nom ;
    private String dateNaissance ;
    private String avatar ;
    private ArrayList<Partie> parties ;

    public Profil(String nom, String dateNaissance) {
        this.nom = nom;
        this.dateNaissance = dateNaissance;
    }

    public Profil(String fileName) {
    
    }

    Profil() {
    }
    private void toXML(String fileName) {
    
    }
    private Document fromXML(String fileName) {
        return null;
    }

    public void ajouterPartie(Partie partie) {
        parties.add(partie) ;

    }
    public Partie getPartie(String date) {
        return null;
    }
    public int getDernierNiveau() {
        return 0;
    
    }
    public String toString() {
        return null;
    }
    public void sauvegarder(String fileName) {
    
    }
    private String xmlDateToProfile(String d) {
        return null;
    
    }
    private String profileDateToXmlDate(String d) {
        return null;
    }

    boolean charge(String nomJoueur) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
