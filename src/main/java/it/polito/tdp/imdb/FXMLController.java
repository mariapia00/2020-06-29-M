/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.imdb.model.Collegati;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnAdiacenti"
    private Button btnAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaAffini"
    private Button btnCercaAffini; // Value injected by FXMLLoader

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxRegista"
    private ComboBox<Director> boxRegista; // Value injected by FXMLLoader

    @FXML // fx:id="txtAttoriCondivisi"
    private TextField txtAttoriCondivisi; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	int anno = this.boxAnno.getValue();
    	
    	if(anno==0) {
    		txtResult.appendText("Selezionare un anno!\n");
    		return;
    	}
    	this.model.creaGrafo(anno);
    	txtResult.appendText("Grafo creato: "+ this.model.getNVertici()+" vertici, "+ this.model.getNArchi()+" archi.\n\n");
    	this.boxRegista.getItems().clear();
    	this.boxRegista.getItems().addAll(this.model.getDirectors());
    }

    @FXML
    void doRegistiAdiacenti(ActionEvent event) {
    	if(boxRegista.getValue()!= null) {
    		List<Collegati> result = new ArrayList<>(this.model.getAdiacenti(boxRegista.getValue()));
    		for(Collegati a : result) {
    			txtResult.appendText(a.toString());
    		}
    	}
    }

    @FXML
    void doRicorsione(ActionEvent event) {
    	int n = 0;
    	n = Integer.parseInt(txtAttoriCondivisi.getText());
    	if(n!=0 && n>0 && boxRegista.getValue()!= null) {
    		List<Director> result = this.model.cercaAffini(n, boxRegista.getValue());
    		for(Director d : result) {
    			txtResult.appendText(d.toString());
    		}
    		txtResult.appendText("Lunghezza totale del cammino: "+this.model.getBestLunghezza()+"\n\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAdiacenti != null : "fx:id=\"btnAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaAffini != null : "fx:id=\"btnCercaAffini\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxRegista != null : "fx:id=\"boxRegista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAttoriCondivisi != null : "fx:id=\"txtAttoriCondivisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
   public void setModel(Model model) {
    	
    	this.model = model;
    	boxAnno.getItems().clear();
    	
    	
    	for(int anno = 2004; anno <= 2006; anno++) {
    		this.boxAnno.getItems().add(anno);
    	}
    	
    	
    }
    
}
