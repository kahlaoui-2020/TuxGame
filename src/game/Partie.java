/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author kahlaoui
 */
public class Partie {
    
    private final String date ;
    private final String mot ;
    private final int niveau ;
    private int trouve ;
    private double temps ;
    
    public Partie(String date, String mot, int niveau) {
        this.date = date ;
        this.mot = mot ;
        this.niveau = niveau ;
    }
    public Partie(Element partieElt) {
        this.date = partieElt.getAttribute("date") ;
        this.temps = Double.parseDouble(partieElt.getFirstChild().getNodeValue()) ;
        this.niveau = Integer.parseInt(((Element)(partieElt.getLastChild())).getAttribute("niveau")) ;
        this.mot = partieElt.getLastChild().getNodeValue() ;
    
    
    }
    public Element getPartie(Document doc) {
        return null;
 
    }
    public void setTrouve(int nbLettresRestantes) {
      this.trouve = (int) (((mot.length()-nbLettresRestantes)*100)/mot.length()) ;
    }
    public void setTemps(int temps) {
        this.temps = temps ;
    }
    public int getNiveau() {
        return niveau ;
    }
    public String toString() {
        return "Partie de "+date+" mot joué '"+mot+"' niveau "+niveau+" temp de partie "+temps  ;
    }

    public String getMot() {
        return this.mot ;
    }
    
}
