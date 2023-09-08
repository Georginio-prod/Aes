
package aes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;


public class AES {
    private static SecretKeySpec secretKey;
    private static byte[] key;

    public static void setKey( String mykey)
    {

        try
        {
            key = mykey.getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        
        }
        catch (NoSuchAlgorithmException e){  }
        catch (UnsupportedEncodingException e){ }
    }

    //encryption
    public static String encrypt (String strToEnc , String sec)
    {
        try
        {
            setKey(sec);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEnc.getBytes("UTF-8")));
            
        }
        catch(Exception e){
            
        }
        return null;
        
    }
    
    //decryption
     public static String decrypt (String strToDec , String sec)
    {
        try
        {
            setKey(sec);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String (cipher.doFinal(Base64.getDecoder().decode(strToDec)));
            
        }
        catch(Exception e){
            
        }
        return null;
        
    }

   /* 
        
    public static void main(String[] args) {
        final String secretKey = "jdkslcusdcwkids";
        String originalString = "je suis le roi de la jungle ";
        
        //Enc 
        String encSite = AES.encrypt(originalString, secretKey);
        
        //Dec
        String decSite = AES.decrypt(encSite, secretKey);
        
        //Display all
        System.out.println("Original: " + originalString);
        System.out.println("Encrypted text: " + encSite);
        System.out.println("Decrypted text: " + decSite);
    }
   
*/
}
