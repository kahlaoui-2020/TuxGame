/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import static game.Profil.xmlDateToProfileDate;
import java.util.ArrayList;
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
        this.date = xmlDateToProfileDate(partieElt.getAttribute("date")) ;
        this.temps = Double.parseDouble(partieElt.getFirstChild().getNodeValue()) ;
        this.niveau = Integer.parseInt(((Element)(partieElt.getLastChild())).getAttribute("niveau")) ;
        this.mot = partieElt.getLastChild().getNodeValue() ;
    
    
    }
    public Element getPartie(Document doc) {
        Element parties = doc.createElement("parties");
        Element partie = doc.createElement("partie");
        partie.setAttribute("date", this.date);
        Element temps = doc.createElement("temps");
        Element mot = doc.createElement("mot");
        mot.setAttribute("niveau", Integer.toString(this.niveau));
        mot.setTextContent(this.mot);
        if(this.temps != 0) partie.appendChild(temps) ;
        partie.appendChild(mot);
        parties.appendChild(partie);
        return parties;
 
    }
    public void setTrouve(int nbLettresRestantes) {
      this.trouve = (int) (((mot.length()-nbLettresRestantes)*100)/mot.length()) ;
    }
    public void setTemps(double temps) {
        this.temps = temps ;
    }
    public double getTemps() {
        return this.temps ;
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

    public String getDate() {
        return date ;
    }
    
    
    
}
