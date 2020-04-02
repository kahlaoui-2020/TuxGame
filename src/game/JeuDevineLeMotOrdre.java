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
public class JeuDevineLeMotOrdre extends Jeu{
    
    private int nbLettresRestantes ;
    private Chronometre chrono ;

    public JeuDevineLeMotOrdre() {
        super() ;
    }
    protected boolean TuxTrouveLettre() {
        if(collision(getListLetters().get(0))) {
            getListLetters().remove(0) ;
            return true ;
        }
        else return false ;
    }

    @Override
    protected void demarrePartie(Partie partie) {
        nbLettresRestantes = partie.getMot().length() ;
        chrono = new Chronometre(60) ;
        chrono.start();
    }

    @Override
    protected void appliqueRegles(Partie partie) {
        if(TuxTrouveLettre()) {
            nbLettresRestantes-- ;
            partie.setTrouve(nbLettresRestantes) ;
        }
    }

    @Override
    protected void terminePartie(Partie partie) {
        chrono.stop();
        partie.setTemps((int) chrono.getTime());
        partie.setTrouve(nbLettresRestantes);
    }

   
    
}
