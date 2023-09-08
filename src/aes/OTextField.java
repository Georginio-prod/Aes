package aes;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * @author FATIGBA Abiola Pierre-Edy
 *
 */
public class OTextField extends JTextField
{
    private Color background = Color.WHITE;
    private Color borderColor = Color.BLACK;
    private Color focusColor = new Color(80, 199, 255);
    private String placeholder;

    /**
     * 
     */
    public OTextField() 
    {
        this.setOpaque(false);
        this.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 25));
        this.setFont(new Font("Arial", Font.BOLD, 14));
    }

    /**
     * @param text
     */
    public OTextField(String text)
    {
    	//super(text);
    	this.setToolTipText(text);
    	this.placeholder = text;
        this.setOpaque(false);
        this.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 25));
        this.setFont(new Font("Arial", Font.BOLD, 14));
        
    }

    /**
     * @param columns
     */
    public OTextField(int columns)
    {
        this.setOpaque(false);
        this.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 25));
        this.setFont(new Font("Arial", Font.BOLD, 14));
    }

    /**
     * @param text
     * @param columns
     */
    public OTextField(String text, int columns) 
    {
    	this.setToolTipText(text);
    	this.placeholder = text;
    	this.setOpaque(false);
        this.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 25));
        this.setFont(new Font("Arial", Font.BOLD, 14));
    }

    @Override
    public void paintComponent(Graphics g)
    {
        int width = this.getWidth();
        int height = this.getHeight();

        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setColor(background);
        g2d.fillRoundRect(0, 0, width, height, height, height);
        g2d.setColor(borderColor);
        g2d.drawRoundRect(0, 0, width - 1, height - 1, height, height);
        super.paintComponent(g);
        if(isFocusOwner())
        {
                setBorderColor(focusColor);
                revalidate();
                repaint();
        }
        if(!isFocusOwner())
        {
                setBorderColor(Color.BLACK);
                revalidate();
                repaint();
        }
        repaint();
    }	

    @Override
    public void paint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        super.paint(g);
        if(getText().isEmpty())
        {
            if(placeholder != null)
            {	
                    int h = this.getHeight();
                    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    Insets ins = this.getInsets();
                    FontMetrics fm = g.getFontMetrics();
                    g2d.setColor(Color.GRAY);
                    g2d.setFont(getFont().deriveFont(Font.ITALIC));
                    g2d.drawString(placeholder, ins.left, h / 2 + fm.getAscent() / 2 - 2);
            }
        }
        else
        {
                g2d.setColor(Color.BLACK);
                g2d.setFont(getFont().deriveFont(Font.BOLD));
        }
    }

    @Override
    public void setText(String txt)
    {
        try 
        {
            Document doc = getDocument();
            if (doc instanceof AbstractDocument)
            {
                    ((AbstractDocument)doc).replace(0, doc.getLength(), txt, null);
            }
            else
            {
                doc.remove(0, doc.getLength());
                doc.insertString(0, txt, null);
            }
        }
        catch (BadLocationException e) 
        {
            UIManager.getLookAndFeel().provideErrorFeedback(OTextField.this);
        }
    }

    @Override
    public String getText()
    {
        Document doc = getDocument();
        String txt;
    try 
    {	
            txt = doc.getText(0, doc.getLength());
    }
    catch (BadLocationException e)
    {
        txt = null;
    }
        return txt;
    }

    public void setPlaceholder(String placeholder)
    {
        this.placeholder = placeholder;
    }

    public String getPlaceholder()
    {
        return placeholder;
    }

    @Override
    public void setBackground(Color background)
    {
        this.background = background;
        super.setBackground(background);
    }

    public void setBorderColor(Color borderColor)
    {
        this.borderColor = borderColor;
    }
}
