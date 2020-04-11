/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;
import env3d.Env;
import env3d.advanced.EnvNode;

/**
 *
 * @author kahlaoui
 */
public class Letter extends EnvNode {
    
    char letter ;
    public Letter(char l, double x, double z) {
        letter = l ;
        setScale(4.0) ;
        setX(x) ;
        setY(getScale()*1.1) ;
        setZ(z) ;
        if(" ".equals(l)) {
            setModel("/models/letter/cube.obj");
        } else {
            setTexture("/models/letter/"+l+".png");
        }
       
        
    }

    public char getChar() {
        return letter ;
    }

   
    
}
