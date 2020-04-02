/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import env3d.Env;
import env3d.advanced.EnvNode;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author kahlaoui
 */
public class Tux extends EnvNode {

    private final Env env;
    private final Room room;
    
    public Tux(Env env, Room room) {
        this.env = env ;
        this.room = room ;    
        setScale(4.0) ;
        setX(this.room.getWidth()/2);// positionnement au milieu de la largeur de la room
        setY(getScale() * 1.1); // positionnement en hauteur basé sur la taille de Tux
        setZ(this.room.getDepth()/2); // positionnement au milieu de la profondeur de la room
        setTexture("models/tux/tux_special.png");
        setModel("models/tux/tux.obj");       
    }
    public void deplacer() {
       if (env.getKeyDown(Keyboard.KEY_Z) || env.getKeyDown(Keyboard.KEY_UP)) { // Fleche 'haut' ou Z
       // Haut
        this.setRotateY(180);
        this.setZ(this.getZ() - 1.0);
       }
       if (env.getKeyDown(Keyboard.KEY_Q) || env.getKeyDown(Keyboard.KEY_LEFT)) { // Fleche 'gauche' ou Q
       // Gauche
        this.setRotateY(270);
        this.setX(this.getX() - 1.0); /* ? */
       }
       if (env.getKeyDown(Keyboard.KEY_D) || env.getKeyDown(Keyboard.KEY_RIGHT)) { // Fleche 'droite' ou D
       // Droite 
        this.setRotateY(90);
        this.setX(this.getX() + 1.0); /* ? */
       }
       if (env.getKeyDown(Keyboard.KEY_S) || env.getKeyDown(Keyboard.KEY_DOWN)) { // Fleche 'bas' ou S
       // Bas 
        this.setRotateY(0);
        this.setZ(this.getZ() + 1.0);  /*  ?  */
       }
       testeRoomCollision(this.getX(),this.getZ()) ;
    
    }
    public boolean testeRoomCollision(double x, double y) {
        if(getX() > room.getWidth()) {
            this.setX(room.getWidth());
        }else if(getX() < 0) this.setX(0);
        if(getZ() > room.getDepth()) {
            this.setZ(room.getDepth());
        } else if(getZ()<0) this.setZ(0);
        return false ;
    } 
    
}
