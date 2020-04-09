/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.IOException;
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
    private boolean TuxTrouveLettre() {
        /*if(collision(getListLetters().get(0))) {
            getListLetters().remove(0) ;  A verifie 
            return true ;
        }
        else */
        return false ;
    }
    /*
    private boolean tuxTrouveLettre() {
        boolean found = false;
        int lengthMot = partie.getMot().length() ;
        int index = lengthMot - nbLettresRestantes;
        
    
        
        
        
        if(collision(letter))
        {
            found = true;
            env.removeObject(lettres.get(index));
        }
        return found ;
        
    }
    
    
    
    */

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

    private Object getListLetters() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private int getNbLettresRestantes() {
        return nbLettresRestantes ;
    
    
    }
    private int getTemps() {
        return 0 ;
    
    }
    

   
    
}
