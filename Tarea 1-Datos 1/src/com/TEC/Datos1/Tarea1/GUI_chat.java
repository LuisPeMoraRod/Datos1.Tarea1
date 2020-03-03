package com.TEC.Datos1.Tarea1;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

@SuppressWarnings("deprecation")

public class GUI_chat extends javax.swing.JFrame implements Observer {
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnEnviar;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTextArea txtTexto;
	private javax.swing.JTextField txtTextoEnviar;
	private javax.swing.JTextField txtPuertoEnviar;
	
	private javax.swing.JButton btnIniciar;
	private javax.swing.JTextField txtCantidad;
	private javax.swing.JLabel txtLabel;
	
	

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

	public static void main(String args[]) {
		
		for (int i = 0; i < 3; i++) {
			try {
				for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
					if ("Nimbus".equals(info.getName())) {
						javax.swing.UIManager.setLookAndFeel(info.getClassName());
						break;
					}
				}
			} catch (ClassNotFoundException ex) {
				java.util.logging.Logger.getLogger(GUI_chat.class.getName()).log(java.util.logging.Level.SEVERE, null,
						ex);
			} catch (InstantiationException ex) {
				java.util.logging.Logger.getLogger(GUI_chat.class.getName()).log(java.util.logging.Level.SEVERE, null,
						ex);
			} catch (IllegalAccessException ex) {
				java.util.logging.Logger.getLogger(GUI_chat.class.getName()).log(java.util.logging.Level.SEVERE, null,
						ex);
			} catch (javax.swing.UnsupportedLookAndFeelException ex) {
				java.util.logging.Logger.getLogger(GUI_chat.class.getName()).log(java.util.logging.Level.SEVERE, null,
						ex);
			}
			// </editor-fold>
			// </editor-fold>

			/* Create and display the form */
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					new GUI_chat().setVisible(true);
				}
			});
		}

	}
	

	

}
