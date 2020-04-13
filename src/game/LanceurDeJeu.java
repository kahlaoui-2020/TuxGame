/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 *
 * @author kahlaoui
 */
public class LanceurDeJeu {
    
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException, InterruptedException {
    
    Jeu jeu ;
    jeu = new JeuDevineLeMotOrdre() ;
    jeu.execute();
   
    }
}
