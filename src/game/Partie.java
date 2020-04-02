/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author kahlaoui
 */
public class Partie {
    
    private final String date ;
    private final String mot ;
    private final int niveau ;
    private int trouve ;
    private int temps ;
    
    public Partie(String date, String mot, int niveau) {
        this.date = date ;
        this.mot = mot ;
        this.niveau = niveau ;
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
        return null ;
    }

    public String getMot() {
        return this.mot ;
    }
    
}
