package com.TEC.Datos1.Tarea1;
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*;

public class Popup extends JFrame implements ActionListener { 
  
    // java button 
    static JButton b; 
  
    // java frame 
    static JFrame f; 
  
    // popup menu 
    static JPopupMenu pm; 
  
    // default constructor 
    Popup() 
    { 
    } 
  
    // main class 
    @SuppressWarnings("deprecation")
	public static void main(String[] args) 
    { 
        // create a frame 
        f = new JFrame("Popup"); 
  
        // set the size of the frame 
        f.setSize(400, 400); 
  
        // close the frame when close button is pressed 
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
  
        // create anew panel 
        JPanel p = new JPanel(); 
  
        // create an object of mouse class 
        Popup pop = new Popup(); 
  
        // create a button 
        b = new JButton("click"); 
  
        // addActionListener 
        b.addActionListener(pop); 
        p.add(b); 
        f.add(p); 
        f.show(); 
    } 
  
    // when the button is clicked 
    public void actionPerformed(ActionEvent e) 
    { 
        String s = e.getActionCommand(); 
        if (s.equals("click")) { 
            // create a popup menu 
            pm = new JPopupMenu("Message"); 
  
            // create a label 
            JLabel l = new JLabel("this is the popup menu"); 
  
            // add the label to the popup 
            pm.add(l); 
  
            // add the popup to the frame 
            pm.show(f, 100, 100); 
        } 
    } 
}