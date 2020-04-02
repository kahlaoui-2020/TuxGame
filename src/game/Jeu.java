/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;
import env3d.Env;
import java.util.ArrayList;

/**
 *
 * @author kahlaoui
 */
public abstract class Jeu {
    
    private Env env ;
    private Room room ;
    private Profil profil ;
    private Tux tux ;
    private  ArrayList<Letter> lettres ;
    Dico dico ;
    
    
    public Jeu() {
        env = new Env() ;
        room = new Room() ;
        env.setCameraXYZ(50, 60,175) ;
        env.setCameraPitch(-20) ;
        env.setDefaultControl(false) ;
        profil = new Profil() ;
        lettres = new ArrayList<Letter>() ;
        dico = new Dico("") ;
    }
    public void execute() {
     // pour l'instant, nous nous contentons d'appeler la méthode joue comme cela
        // et nous créons une partie vide, juste pour que cela fonctionne
        joue(new Partie("","sister",3));
        // Détruit l'environnement et provoque la sortie du programme 
        env.exit();
    }
    public void joue(Partie partie) {
    // TEMPORAIRE : on règle la room de l'environnement. Ceci sera à enlever lorsque vous ajouterez les menus.
        env.setRoom(room);
        // Instancie un Tux
        tux = new Tux(env,room) ;
        env.addObject(tux);

        // Ici, on peut initialiser des valeurs pour une nouvelle partie
        //demarrePartie(partie);
        // Boucle de jeu
        boolean finished = false;
        while (!finished) {
 
            // Contrôles globaux du jeu (sortie, ...)
            //1 is for escape key
            if (env.getKey() == 1) {
                finished = true ;
            }
            tux.deplacer();

            // Contrôles des déplacements de Tux (gauche, droite, ...)
            // ... (sera complété plus tard) ...
 
            // Ici, on applique les regles
           // appliqueRegles(partie);
 
            // Fait avancer le moteur de jeu (mise à jour de l'affichage, de l'écoute des événements clavier...)
            env.advanceOneFrame();
        }
 
        // Ici on peut calculer des valeurs lorsque la partie est terminée
        terminePartie(partie);
                 System.out.println("1111111111");

    }
    protected abstract void demarrePartie(Partie partie);
    protected abstract void appliqueRegles(Partie partie);
    protected abstract void terminePartie(Partie partie);
    protected double distance(Letter letter) {
        return Math.sqrt(Math.pow(tux.getX()-letter.getX(), 2)+Math.pow(tux.getY()-letter.getY(), 2)+Math.pow(tux.getZ()-letter.getZ(), 2));
    }
    protected boolean collision(Letter letter) {
        return (distance(letter) < 1.0) ;
        
    }
    public ArrayList<Letter> getListLetters() {
        return lettres ;
    
    }
    
    
}
