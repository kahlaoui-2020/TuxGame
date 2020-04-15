/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.w3c.dom.Document;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
/**
 *
 * @author kahlaoui
 */
public class Dico extends DefaultHandler {
    
    private ArrayList<String> listeNiveau1;
    private ArrayList<String> listeNiveau2;
    private ArrayList<String> listeNiveau3;
    private ArrayList<String> listeNiveau4;
    private ArrayList<String> listeNiveau5;
    
    private String pathToDicoFile ;
    
    private StringBuffer buffer ;
    private boolean inDico;
    private int niveau;
    private boolean inMot;

    public Dico(String filePath) throws ParserConfigurationException, SAXException, IOException {
        
        super();
        this.listeNiveau1 = new ArrayList<String>();
        this.listeNiveau2 = new ArrayList<String>();
        this.listeNiveau3 = new ArrayList<String>();
        this.listeNiveau4 = new ArrayList<String>();
        this.listeNiveau5 = new ArrayList<String>();
            
        this.pathToDicoFile = filePath ;
        
    }

   
     public String getMotDepuisListeNiveaux(int niveau){
         
        
        switch (verifieNiveau(niveau)) {
            case 1: return getMotDepuisListe(listeNiveau1);
            case 2: return getMotDepuisListe(listeNiveau2);
            case 3: return getMotDepuisListe(listeNiveau3);
            case 4: return getMotDepuisListe(listeNiveau4);
            case 5: return getMotDepuisListe(listeNiveau5);
            default: return getMotDepuisListe(listeNiveau1);
        }
    }
    private String getMotDepuisListe(ArrayList<String> list){
        if(list.size() == 0) {
            System.out.println(" liste vide");
            return null ;
        } else if(list.size()==1) {
            return list.get(0) ;
        } else {
            Random r = new Random(1, list.size());
            return list.get((int) r.get()) ;
        }
    }

    private int verifieNiveau(int niveau) {
        if(niveau >=1 && niveau <= 5) {
            return niveau ;
        } else {
            return 1 ;
        }
    }
    public void ajouteMotDico(int niveau, String mot) {
        
        switch(verifieNiveau(niveau)) {
            case 1 : listeNiveau1.add(mot);break;
            case 2 : listeNiveau2.add(mot);break;
            case 3 : listeNiveau3.add(mot);break;
            case 4 : listeNiveau4.add(mot);break;
            case 5 : listeNiveau5.add(mot);break;
       
        }
        
    }
    
    public void lireDictionnaireDOM(String filePath) throws ParserConfigurationException, SAXException, IOException {
        
        DOMParser parser = new DOMParser() ;
        parser.parse(filePath) ;
        Document doc = parser.getDocument() ;
        NodeList dico = doc.getElementsByTagName("mot");
        for(int i=0; i<dico.getLength(); i++) {
            
            String mot = dico.item(i).getChildNodes().item(0).getNodeValue();
            int niveau = Integer.parseInt(((Element) dico.item(i)).getAttribute("niveau"));
            ajouteMotDico(niveau,mot) ;
  
        }
 
    }
   
    
    public void lireDictionnaire() throws ParserConfigurationException, SAXException, IOException {
        
        
        
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();      
        parser.parse(pathToDicoFile,this);
        
        
    
        
        
        
        
        
    
    
    }
    public ArrayList<String> getList1() {
        return listeNiveau1 ;
    }
    public ArrayList<String> getList2() {
        return listeNiveau2 ;
    }
    public ArrayList<String> getList3() {
        return listeNiveau3 ;
    }
    public ArrayList<String> getList4() {
        return listeNiveau4 ;
    }
    public ArrayList<String> getList5() {
        return listeNiveau5 ;
    }
    
   
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                
        if(qName.equals("dictionnaire")){
            inDico = true;
        }else if(qName.equals("mot")) {
           niveau = Integer.parseInt(attributes.getValue("niveau"));
           inMot = true ;
           buffer = new StringBuffer();
        }
    };
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
    
        if(qName.equals("dictionnaire")) {
            inDico = false ;
        } else  {
            ajouteMotDico(niveau,buffer.toString());
            buffer = null;
            inMot = false ;        
        }
    };
        
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String lecture = new String(ch,start,length);
        if(buffer != null) buffer.append(lecture);
    };

    @Override
    public void startDocument() throws SAXException {};

    @Override
    public void endDocument() throws SAXException {};
  
     
    
}
