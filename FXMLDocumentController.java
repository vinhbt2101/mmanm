/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.security.Key;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javafx.scene.control.ProgressBar;
import javafxapplication3.Blowfish;
import java.io.IOException;

/**
 *
 * @author nvinh
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TextField field_input;
    @FXML
    private TextField field_output;
    @FXML
    private TextField field_key;
    @FXML
    private PasswordField pass;
    @FXML
    private Button btn_input;
    @FXML
    private Button btn_output;
    @FXML
    private Button btn_key;
    @FXML
    private Button Ok;
    @FXML
    private Button btn_start;
    @FXML
    private RadioButton enc;
    @FXML
    private RadioButton dec;
    @FXML
    private RadioButton des;
    @FXML
    private RadioButton rsa;
    @FXML
    private RadioButton blowfish;
    @FXML
    private AnchorPane anchor1;
    @FXML
    private AnchorPane anchor2;
    @FXML
    private ProgressBar bar;
    static String Key = "";
    static String Algorithm;
    int mode = 1;
    String InputFileName;
    String OutputFileName;
    String OutputSavePath;
    String Extension; // extension of file encrypted
    
    File KeyFile;
    File InputFile;
    public void chooseInputKey(ActionEvent event) throws FileNotFoundException, IOException{
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                
                new FileChooser.ExtensionFilter("txt", "*.txt")
                
        );
        Stage stage = (Stage)anchor1.getScene().getWindow();
        KeyFile = fileChooser.showOpenDialog(stage);
        
        BufferedReader reader = new BufferedReader(new FileReader(KeyFile));
        String line = reader.readLine();
        while(line != null){
            Key += line;
            line = reader.readLine();
        }
        reader.close();
        

        if (KeyFile != null){
            pass.setEditable(false);
            field_key.setText(KeyFile.getPath()+"   "+Key);
        }
    }
    public void ConfirmKey(ActionEvent event){
        Key = pass.getText();
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText(Key);
        a.show();
        
    }
    
    public void SelectMode(ActionEvent event){
        if(enc.isSelected()){
            mode = 1;
        }
        if(dec.isSelected()){
            mode = 0;
        }       
    }
    
    public void SelectAlgorithm(ActionEvent event){
        if(des.isSelected()){
            Algorithm = "DES";
        }
        if(rsa.isSelected()){
            Algorithm = "RSA";
        }
        if(blowfish.isSelected()){
            Algorithm = "BlowFish";
        }      
       
    }
    
    @FXML
    private void InputFileChooser(ActionEvent event){
        FileChooser filechooser = new FileChooser();
        filechooser.setTitle("Select File to encrypt/decrypt");
        Stage stage = (Stage)anchor1.getScene().getWindow();
        
        InputFile = filechooser.showOpenDialog(stage);
        InputFileName = InputFile.getName();
        Extension = InputFileName.substring(InputFileName.lastIndexOf("."));
        OutputFileName = InputFileName.replaceFirst("[.][^.]+$", "");
        field_input.setText(InputFile.getPath());
       
    }
    
    @FXML
    private void OutputFileSave(ActionEvent event){
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Select where to save your file");
        Stage stage = (Stage)anchor1.getScene().getWindow();
        File f = dc.showDialog(stage);
        OutputSavePath = f.getAbsolutePath();
        field_output.setText(OutputSavePath);   
       
        
        
    }
    
//    public static void encrypt(File inputFile, File outputFile)	throws Exception {
//		doCrypto(Cipher.ENCRYPT_MODE, inputFile, outputFile);
//		Alert a = new Alert(Alert.AlertType.INFORMATION);
//                a.setContentText("File encrypted successfully!");
//                a.show();
//    }
//    public static void decrypt(File inputFile, File outputFile)
//			throws Exception {
//		doCrypto(Cipher.DECRYPT_MODE, inputFile, outputFile);
//		Alert a = new Alert(Alert.AlertType.INFORMATION);
//                a.setContentText("File decrypted successfully!");
//                a.show();
//    }    
//    private static void doCrypto(int cipherMode, File inputFile,File outputFile) throws Exception {
//
//		Key secretKey = new SecretKeySpec(Key.getBytes(),Algorithm);                
//                
//		Cipher cipher = Cipher.getInstance(Algorithm);
//		cipher.init(cipherMode, secretKey);
//
//		FileInputStream inputStream = new FileInputStream(inputFile);
//		byte[] inputBytes = new byte[(int) inputFile.length()];
//		inputStream.read(inputBytes);
//
//		byte[] outputBytes = cipher.doFinal(inputBytes);
//
//		FileOutputStream outputStream = new FileOutputStream(outputFile);
//		outputStream.write(outputBytes);
//
//		inputStream.close();
//		outputStream.close();
//
//    }
    
    public void Start(ActionEvent event){
        
    
        
        if(mode ==1){
            
            File encryptFile = new File(OutputSavePath+"\\" + OutputFileName + Extension + ".encrypt");            
            try {
                long starttime = System.nanoTime();
                Blowfish.encrypt(Key,InputFile, encryptFile);   
                long duration = (System.nanoTime() - starttime)/10000000;
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText(Long.toString(duration)+"ms");
                a.show();
                pass.clear();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        if(mode == 0){
            String name = InputFileName.replaceFirst("[.][^.]+$", "");            
            File decryptFile = new File(OutputSavePath+"\\" + name);
            try {
                Blowfish.decrypt(Key,InputFile, decryptFile);                
                pass.clear();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
