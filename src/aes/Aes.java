package aes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Aes extends JFrame {
    
    private JButton chiffrerBut;
    private JButton dechiffreBut;
    private OTextField saisiefield;
    private OTextField codefield;
    private Label nom;
    private OTextField chiffrement;
    private OTextField message;
    private Label entrer;
    
    public Aes() {
        super();
        setTitle("Outils de chiffrement et de déchiffrement ");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
        listen();
    }
    
    public void init() {
        ImageIcon icon = new ImageIcon(new ImageIcon("/home/etonam/NetBeansProjects/AES/src/aes/restartIcon.png").getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT));
        chiffrerBut = new JButton("Chiffrer", icon);
        dechiffreBut = new JButton("Déchiffrer", icon);
        saisiefield = new OTextField("Saisir le texte claire");
        codefield = new OTextField("le message chiffré");
        nom = new Label("AES Encryption");
        entrer = new Label("saisir la clé");
        message = new OTextField("le message originale");
        chiffrement = new OTextField("saisir la clé");
        
        JPanel mainPane = new JPanel();
        JPanel aPane = new JPanel(new BorderLayout());
        JPanel bPane = new JPanel();
        JPanel chiffreBut = new JPanel();
        JPanel saisie = new JPanel();
        JPanel entre = new JPanel();
        JPanel chiffre = new JPanel();
        
        JPanel aCenterPane = new JPanel(new GridLayout(2, 1));
        JPanel aSouthPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel a1cPane = new JPanel();
        JPanel a2cPane = new JPanel();
        JPanel saisieFieldPane = new JPanel();
        JPanel codefieldPane = new JPanel();
        //JPanel codefield = new JPanel();
        //JPanel message = new JPanel();

        Box b1 = Box.createHorizontalBox();
        Box b2 = Box.createHorizontalBox();
        Box b3 = Box.createVerticalBox();
        Box c1 = Box.createHorizontalBox();
        Box c2 = Box.createHorizontalBox();
        
        saisie.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        chiffreBut.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        aSouthPane.add(chiffreBut);
        
        aPane.setBackground(Color.white);
        bPane.setBackground(Color.gray);
        
        c1.add(a1cPane);
        c2.add(a2cPane);
        aCenterPane.add(c1);
        aCenterPane.add(c2);
        
        a1cPane.add(saisie);
        a2cPane.add(entrer);
        a2cPane.add(chiffrement);
        
        chiffrement.setPreferredSize(new Dimension(250, 50));
        saisiefield.setPreferredSize(new Dimension(250, 50));
        //entrer.setPreferredSize(new Dimension(100, 40));

        aPane.add(aCenterPane, BorderLayout.CENTER);
        aPane.add(aSouthPane, BorderLayout.SOUTH);
        chiffreBut.add(chiffrerBut);
        
        aPane.add(aCenterPane, BorderLayout.CENTER);
        aPane.add(aSouthPane, BorderLayout.SOUTH);
        chiffreBut.add(dechiffreBut);
        
        
        
        saisie.add(saisiefield);
        
        b1.add(aPane);
        b2.add(bPane);
        b3.add(b1);
        b3.add(b2);
        
        bPane.add(message);
        bPane.add(codefield);
        
        message.setPreferredSize(new Dimension(270, 50));
        codefield.setMinimumSize(new Dimension(300, 50));
        codefield.setPreferredSize(new Dimension(300, 50));
        
        mainPane.setLayout(new BorderLayout(1, 1));
        mainPane.add(b3, BorderLayout.CENTER);
        
        mainPane.setBackground(Color.BLUE);
        mainPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Mode de Cryptage"));
        
        this.getContentPane().add(mainPane);
    }
    
    private void listen() {
        chiffrerBut.addActionListener(e -> {
            if (e.getSource() instanceof JButton) {
                JButton button = (JButton) e.getSource();
                if (saisiefield.getText().isEmpty() || chiffrement.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "saisir un texte", "Erreur", JOptionPane.WARNING_MESSAGE);
                } else {
                    String msg = saisiefield.getText();
                    String key = chiffrement.getText();
                    final AESed crypteur = new AESed();
                    String message = crypteur.encrypt(msg, key);
                    codefield.setText(message);
                    this.message.setText(msg);
                    codefield.repaint();
                    codefield.revalidate();
                    this.message.revalidate();
                    this.message.repaint();
                    saisiefield.setText("");
                    chiffrement.setText(""); 
                }
            }
            
        });
        
        dechiffreBut.addActionListener(e ->{
            if (e.getSource() instanceof JButton) {
                JButton button = (JButton) e.getSource();
                if (saisiefield.getText().isEmpty() || chiffrement.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "saisir un texte", "Erreur", JOptionPane.WARNING_MESSAGE);
                } else {
                    String msg = saisiefield.getText();
                    String key = chiffrement.getText();
                    final AESed crypteur = new AESed();
                    String message = crypteur.decrypt(msg, key);
                    codefield.setText(message);
                    this.message.setText(msg);
                    codefield.repaint();
                    codefield.revalidate();
                    this.message.revalidate();
                    this.message.repaint();
                    saisiefield.setText("");
                    chiffrement.setText(""); 
                }
            }
            
        });
        
    }
    
    class AESed {
        
        private SecretKeySpec secretKey;
        private byte[] key;
        
        public void setKey(String mykey) {
            
            try {
                key = mykey.getBytes("UTF-8");
                MessageDigest sha = MessageDigest.getInstance("SHA-1");
                key = sha.digest(key);
                key = Arrays.copyOf(key, 16);
                secretKey = new SecretKeySpec(key, "AES");
                
            } catch (NoSuchAlgorithmException e) {
            } catch (UnsupportedEncodingException e) {
            }
        }

        //encryption
        public String encrypt(String strToEnc, String sec) {
            try {
                setKey(sec);
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                return Base64.getEncoder().encodeToString(cipher.doFinal(strToEnc.getBytes("UTF-8")));
                
            } catch (Exception e) {
                
            }
            return null;
            
        }

        //decryption
        public String decrypt(String strToDec, String sec) {
            try {
                setKey(sec);
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                return new String(cipher.doFinal(Base64.getDecoder().decode(strToDec)));
                
            } catch (Exception e) {
                
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
}
