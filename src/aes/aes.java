
package aes;

import javax.swing.SwingUtilities;


public class aes {
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() ->
        {
           
           new Aes().setVisible(true);
        });
        
    }


}