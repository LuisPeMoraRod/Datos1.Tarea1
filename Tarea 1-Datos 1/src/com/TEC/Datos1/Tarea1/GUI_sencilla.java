package com.TEC.Datos1.Tarea1;

import javax.swing.*;

class GUI_sencilla extends javax.swing.JFrame{
	private javax.swing.JButton btnEnviar;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JTextArea txtTexto;
	private javax.swing.JTextField txtTextoEnviar;
	private javax.swing.JFrame frame;
	// End of variables declaration//GEN-END:variables
	/**
	 * 
	 */
	
	public GUI_sencilla(){
		creaVentana();
	}
	private static final long serialVersionUID = 1L;
	private void creaVentana() {
		frame = new JFrame();// creating instance of JFrame

		btnEnviar = new JButton("Enviar");// creating instance of JButton
		btnEnviar.setBounds(130, 100, 100, 40);// x axis, y axis, width, height
		
		jScrollPane1 = new javax.swing.JScrollPane();
		jScrollPane2 = new javax.swing.JScrollPane();
		txtTexto = new javax.swing.JTextArea();
		txtTextoEnviar = new javax.swing.JTextField();


		frame.add(btnEnviar);// adding button in JFrame
		
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Chat de puerto " + Server.port);

		frame.setSize(400, 500);// 400 width and 500 height
		frame.setLayout(null);// using no layout managers
		frame.setVisible(true);// making the frame visible
		
	}
	public static void main(String[] args) {
		
	}
}