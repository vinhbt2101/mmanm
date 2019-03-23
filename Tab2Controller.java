/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication3;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nvinh
 */
public class Tab2Controller implements Initializable {

    @FXML
    private AnchorPane anchor2;
    @FXML
    private TextField field1;
    @FXML
    private Button btn_1;
    @FXML
    private TextField field2;
    @FXML
    private Button btn_2;
    @FXML
    File InputFile1;
    File InputFile2;
    String file1;
    String file2;
    @FXML
    private void InputFileChooser1(ActionEvent event){
        FileChooser filechooser = new FileChooser();
        filechooser.setTitle("Select File to encrypt/decrypt");
        Stage stage = (Stage)anchor2.getScene().getWindow();
        
        InputFile1 = filechooser.showOpenDialog(stage);
                
        field1.setText(InputFile1.getPath());       
    }
    @FXML
    private void InputFileChooser2(ActionEvent event){
        FileChooser filechooser = new FileChooser();
        filechooser.setTitle("Select File to encrypt/decrypt");
        Stage stage = (Stage)anchor2.getScene().getWindow();
        
        InputFile2 = filechooser.showOpenDialog(stage);
                
        field2.setText(InputFile2.getPath());       
    }
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
