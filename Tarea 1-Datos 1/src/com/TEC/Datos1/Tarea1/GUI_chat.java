package com.TEC.Datos1.Tarea1;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Observer;


import javax.swing.*;
import java.awt.*;

@SuppressWarnings("deprecation")

public class GUI_chat extends javax.swing.JFrame implements Observer {
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private JButton btnEnviar;
	private JScrollPane jScrollPane1;
	private JTextArea txtTexto;
	private JTextField txtTextoEnviar;
	private JTextField txtPuertoEnviar;
		
	static JFrame frame;
	

	private int[] puertos;
	private String[] registroMsjs;
	
	Server servidor = new Server();
	// End of variables declaration//GEN-END:variables
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String[]> chats = new ArrayList<String[]>();

	public GUI_chat() {

		servidor.addObserver(this);
		Thread thread = new Thread(servidor);// Thread used to make the server execute simultaneously with the GUI
		thread.start();
		initComponents();
		this.getRootPane().setDefaultButton(this.btnEnviar);

	}

	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents

	private void initComponents() {

		jScrollPane1 = new javax.swing.JScrollPane();
		txtTexto = new javax.swing.JTextArea();
		btnEnviar = new javax.swing.JButton();
		txtTextoEnviar = new javax.swing.JTextField();
		txtPuertoEnviar = new javax.swing.JTextField();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Chat de puerto " + Server.port);

		txtTexto.setColumns(20);
		txtTexto.setRows(5);
		jScrollPane1.setViewportView(txtTexto);

		btnEnviar.setText("Enviar");
		btnEnviar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnEnviarActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addGroup(layout.createSequentialGroup().addComponent(txtTextoEnviar)
										.addComponent(txtPuertoEnviar)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 114,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 237,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(btnEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
								.addComponent(txtTextoEnviar).addComponent(txtPuertoEnviar))
						.addContainerGap()));

		pack();
	}// </editor-fold>//GEN-END:initComponents
	
	

	private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnEnviarActionPerformed

		String mensaje = this.txtTextoEnviar.getText();
		this.txtTextoEnviar.setText("");
		String puerto =this.txtPuertoEnviar.getText();
		String mipuerto=String.valueOf(servidor.getPort());
		this.txtTexto.append("You: "+mensaje+"\n");

		Client cliente = new Client(puerto,mipuerto+"$"+mensaje);
		//String[] msj = Server.separaMensaje(mensaje);

		//historialChats(msj[0], mensaje);
		Thread t = new Thread(cliente);
		t.start();

	}// GEN-LAST:event_btnEnviarActionPerformed

	/**
	 * @param args the command line arguments
	 * 
	 */
	
	@Override
	public void update(Observable o, Object arg) {
		this.txtTexto.append((String) arg + "\n");
	}

	public void historialChats(String Puerto, String mensajes) {
		try {
			if (chats.size() == 0) {
				String[] conver = { Puerto, mensajes };
				chats.add(conver);
				String[] elemento = chats.get(0);
				System.out.println(elemento[1].toString());

			} else {
				String[] conversacion;
				int index = 0;
				for (int i = 0; i < chats.size(); i++) {
					conversacion = chats.get(index);
					// System.out.println(conversacion[0].toString());
					// System.out.println(Puerto);
					String port = conversacion[0].toString();
					if (port == Puerto) {
						System.out.println("puta");
						conversacion[1] = mensajes;
						chats.set(index, conversacion);
						String[] elemento = chats.get(index);
						System.out.println(elemento[1].toString());

					}
					index++;
				}

			}
		} catch (Exception e) {
			System.out.println("Exception thrown : " + e);
		}

	}
	
				
			
	
	public static void generaVentanas(int cantidad) {
		for (int i=0; i<cantidad; i++) {
			GUI_sencilla window= new GUI_sencilla();
			Thread hiloChats= new Thread(window);
			hiloChats.start();
			
		}
		
	}
	
	private static void btnExecute(java.awt.event.ActionEvent evt,String cantidad) {// GEN-FIRST:event_btnEnviarActionPerformed
		int windows=Integer.parseInt(cantidad.trim());
		generaVentanas(windows);
		frame.dispose();
	
	}
	public static void main(String args[]) {
		 // Creando el Marco        
        frame = new JFrame("Chats");       
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
        frame.setSize(400, 100);        
 
        // Creando MenuBar y agregando componentes   
        //JMenuBar mb = new JMenuBar();       
        //JMenu m1 = new JMenu("ARCHIVO");       
        //JMenu m2 = new JMenu("Ayuda");       
        //mb.add(m1);       
        //mb.add(m2);       
        //JMenuItem m11 = new JMenuItem("Abrir");       
        //JMenuItem m22 = new JMenuItem("Guardar como");       
        //m1.add(m11);       
        //m2.add(m22);        
 
        // Creando el panel en la parte inferior y agregando componentes       
        JPanel panel1 = new JPanel(); // el panel no está visible en la salida    
        JPanel panel2 = new JPanel();
        JLabel label = new JLabel("Introduzca la cantidad de ventanas de chats que desea abrir: ");       
        JTextField texto = new JTextField(2); // acepta hasta 10 caracteres        
        JButton send = new JButton("Ejecutar");  
        send.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String windows=texto.getText();
				btnExecute(evt, windows);
			}
		});
        //JButton reset = new JButton("Restablecer");       
        panel1.add(label); // Componentes agregados usando Flow Layout     
        //panel.add(etiqueta); // Componentes agregados usando Flow Layout      
        panel1.add(texto);       
        panel2.add(send);       
        //panel.add(reset);        
 
        // Área de texto en el centro    
        //JTextArea ta = new JTextArea();        
 
        // Agregar componentes al marco.      
        frame.getContentPane().add(BorderLayout.SOUTH, panel2);       
        frame.getContentPane().add(BorderLayout.NORTH, panel1);  
        //frame.getContentPane().add(BorderLayout.NORTH, mb);       
        //frame.getContentPane().add(BorderLayout.CENTER, ta);       
        frame.setVisible(true);   
		

	}
}
