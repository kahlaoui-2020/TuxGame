package game;


import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import xml.XMLUtil;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kahlaoui
 */
public class EditeurDico {

    private String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Dico getDico() {
        return dico;
    }
    
    private Document _doc ;
    private final String path= "/home/kahlaoui/Bureau/ghaieth/TuxLetterGame_template/src/xmlFile/" ;
    private Dico dico ;
    
    
    
    EditeurDico (String nomFichier ) throws ParserConfigurationException, SAXException, IOException {
        
        dico = new Dico("src/xmlFile/"+nomFichier+"xml");
        
    }

    

    public void lireDOM(String fichier) throws SAXException, IOException {
        DOMParser parser = new DOMParser();
        parser.parse(path+fichier+".xml");
        Document doc = parser.getDocument();
        NodeList mots = doc.getElementsByTagName("mot");
        String mot;
        int niveau;
        for(int i=0; i<mots.getLength(); i++) {
            
            mot = mots.item(i).getChildNodes().item(0).getNodeValue();
            niveau = Integer.parseInt(((Element) mots.item(i)).getAttribute("niveau"));
            dico.ajouteMotDico(niveau,mot) ;
        }
 
    }
    public Document fromXML(String nomFichier) {
        try {
            return XMLUtil.DocumentFactory.fromFile(path+nomFichier+".xml");
        } catch (Exception ex) {
            Logger.getLogger(EditeurDico.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public void toXML(String nomFichier) {
        try {
            XMLUtil.DocumentTransform.writeDoc(getDoc(), path+nomFichier+".xml");
        } catch (Exception ex) {
            Logger.getLogger(EditeurDico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void ecrireDOM(String fichier) throws ParserConfigurationException{
        
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        _doc = builder.newDocument();
        
        Element dicoElt = _doc.createElement("dictionnaire");
        dicoElt.setAttribute("xmlns", "http://myGame/tux");
        dicoElt.setAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
        dicoElt.setAttribute("xsi:schemaLocation","http://myGame/tux src/dico.xsd");
        
        Element motElt ;
        for(String mot : dico.getList1()) {
            motElt = _doc.createElement("mot") ;
            motElt.setTextContent(mot);
            motElt.setAttribute("niveau","1");
            dicoElt.appendChild(motElt);
        }
        for(String mot : dico.getList2()) {
            motElt = _doc.createElement("mot") ;
            motElt.setTextContent(mot);
            motElt.setAttribute("niveau","2");
            dicoElt.appendChild(motElt);
        }
        for(String mot : dico.getList3()) {
            motElt = _doc.createElement("mot") ;
            motElt.setTextContent(mot);
            motElt.setAttribute("niveau","3");
            dicoElt.appendChild(motElt);
        }
        for(String mot : dico.getList4()) {
            motElt = _doc.createElement("mot") ;
            motElt.setTextContent(mot);
            motElt.setAttribute("niveau","4");
            dicoElt.appendChild(motElt);
        }
        for(String mot : dico.getList5()) {
            motElt = _doc.createElement("mot") ;
            motElt.setTextContent(mot);
            motElt.setAttribute("niveau","5");
            dicoElt.appendChild(motElt);
        }
        _doc.appendChild(dicoElt);
        toXML(fichier);
        
        
    
    
    }
    
    public void ajouterMot(String mot, int niveau) {
        
        _doc = fromXML(fileName);
        Element dicoElt = _doc.getDocumentElement();
        Element motElt = _doc.createElement("mot");
        motElt.setTextContent(mot);
        motElt.setAttribute("niveau",String.valueOf(niveau));
        dicoElt.appendChild(motElt);
        toXML(fileName);
    
    }
    
    
    

    
    /**
     * @return the doc
     */
    public Document getDoc() {
        return _doc;
    }

    /**
     * @param doc the doc to set
     */
    public void setDoc(Document doc) {
        this._doc = doc;
    }
}
