/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import static com.sun.corba.se.impl.orbutil.CorbaResourceUtil.getText;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import org.lwjgl.input.Keyboard;
import org.xml.sax.SAXException;

/**
 *
 * @author kahlaoui
 */
public class JeuDevineLeMotOrdre extends Jeu{
    
    private int nbLettresRestantes ;
    private Chronometre chrono ;
    private Letter letter ;

    public JeuDevineLeMotOrdre() throws ParserConfigurationException, SAXException, IOException {
        super() ;
    }
    
    private boolean TuxTrouveBonLettre() {
        if(getLetters().get(0).equals(this.letter)) {
            return true ;
        }
        return false ; 
    } 
    private boolean TuxTrouveLettre(Partie partie) {
        for(Letter l : getLetters()) {
            if(collision(l)){
                letter = l ;
                return true ;
            } 
        }
        return false ; 
    }
    /**
     *
     * @param partie
     */
    @Override
    protected void demarrePartie(Partie partie) {
        nbLettresRestantes = partie.getMot().length() ;
        chrono = new Chronometre(30) ;
        chrono.start();
    }

    @Override
    protected void appliqueRegles(Partie partie) {
        
        
        if(chrono.remainsTime()) {
            if(TuxTrouveLettre(partie)) {
                if(TuxTrouveBonLettre()) {
                    nbLettresRestantes-- ;
                    partie.setTrouve(nbLettresRestantes) ;
                    removeLetterFromEnv(letter) ;
                    removeLetterFromList(letter);
                } else {
                    getTux().setX(getRoom().getWidth()/2);
                    getTux().setZ(getRoom().getDepth()/2);
                    getEnv().advanceOneFrame();
                }
            }else if(nbLettresRestantes == 0) {
                setFinished(true);
                getMenuText().getText("TextEtatJeu").display();
                int key = 0 ;
                while(key != Keyboard.KEY_RETURN) {
                    key = getEnv().getKey() ;
                    getEnv().advanceOneFrame();
                }
                getMenuText().getText("TextEtatJeu").clean();
            } 
            
        } else {
            if(nbLettresRestantes == 0) {
                setFinished(true);
                getMenuText().getText("TextEtatJeu").display();
                int key = 0 ;
                while(key != Keyboard.KEY_RETURN) {
                    key = getEnv().getKey() ;
                    getEnv().advanceOneFrame();
                }
                getMenuText().getText("TextEtatJeu").clean();
            } else {
                setFinished(true);
                getMenuText().getText("TextEtatJeu").modify("Vous avez perdu ! Merci pour votre essai à la prochaine !\nEntrer pour conntinuer");
                getMenuText().getText("TextEtatJeu").display();
                int key = 0 ;
                while(key != Keyboard.KEY_RETURN) {
                    key = getEnv().getKey() ;
                    getEnv().advanceOneFrame();
                }
                getMenuText().getText("TextEtatJeu").clean();
           }
        }
}

    @Override
    protected void terminePartie(Partie partie) {
        chrono.stop();
        partie.setTemps((int) chrono.getSeconds());
        partie.setTrouve(nbLettresRestantes);
    }

    
    
    private int getNbLettresRestantes() {
        return nbLettresRestantes ;
    
    
    }
    private int getTemps() {
        return 0 ;
    
    }

    @Override
    protected Chronometre getTime() {
        return chrono ;
    }
    

   
    
}
