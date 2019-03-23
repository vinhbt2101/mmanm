/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import javafx.scene.control.Alert;
import static javafxapplication3.FXMLDocumentController.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


/**
 *
 * @author nvinh
 */
public class Blowfish {
    public static void encrypt(String key, File inputFile, File outputFile)	throws Exception {
		doCrypto(key,Cipher.ENCRYPT_MODE, inputFile, outputFile);
		Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("File encrypted successfully!");
                a.show();
    }
    public static void decrypt(String key, File inputFile, File outputFile)
			throws Exception {
		doCrypto(key,Cipher.DECRYPT_MODE, inputFile, outputFile);
		Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("File decrypted successfully!");
                a.show();
    }    
    private static void doCrypto(String Key ,int cipherMode, File inputFile,File outputFile) throws Exception {

		Key secretKey = new SecretKeySpec(Key.getBytes(),"Blowfish");                
                
		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(cipherMode, secretKey);

		FileInputStream inputStream = new FileInputStream(inputFile);
		byte[] inputBytes = new byte[(int) inputFile.length()];
		inputStream.read(inputBytes);

		byte[] outputBytes = cipher.doFinal(inputBytes);

		FileOutputStream outputStream = new FileOutputStream(outputFile);
		outputStream.write(outputBytes);

		inputStream.close();
		outputStream.close();

    }
}
