/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author kahlaoui
 */
public class JeuDevineLeMotOrdre extends Jeu{
    
    private int nbLettresRestantes ;
    private Chronometre chrono ;

    public JeuDevineLeMotOrdre() throws ParserConfigurationException, SAXException, IOException {
        super() ;
    }
    
    private boolean TuxTrouveLettre(Partie partie) {
        ArrayList<Letter> letters = new ArrayList() ;
        letters = getLetters() ;
        // getListLetterFromMot(partie.getMot());
        for(Letter l : letters) {
            if(collision(l)) {
                removeLetterFromEnv(l) ;
                return true ;
            } 
        }
        return false ; 
    }
    
    
    

    
    
    
    
    protected void demarrePartie(Partie partie) {
        nbLettresRestantes = partie.getMot().length() ;
        chrono = new Chronometre(60) ;
        chrono.start();
    }

    @Override
    protected void appliqueRegles(Partie partie) {
        if(TuxTrouveLettre(partie)) {
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

    
    
    private int getNbLettresRestantes() {
        return nbLettresRestantes ;
    
    
    }
    private int getTemps() {
        return 0 ;
    
    }
    

   
    
}
