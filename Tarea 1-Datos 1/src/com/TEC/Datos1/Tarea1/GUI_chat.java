package com.TEC.Datos1.Tarea1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("deprecation")

public class GUI_chat extends javax.swing.JFrame implements Observer {
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private JButton btnEnviar;
	private JButton btnChats;
	private JScrollPane jScrollPane1;
	private JTextArea txtTexto;
	private JTextField txtTextoEnviar;
	private JTextField txtPuertoEnviar;
	private JLabel puerto;
	private JPopupMenu jPopupMenu;

	static JFrame frame;
	static int windows;
	private Map<String, String> map = new HashMap<>();

	Server servidor = new Server();

	private static final long serialVersionUID = 1L;

	public GUI_chat() {

		servidor.addObserver(this);
		Thread thread = new Thread(servidor);// Thread used to make the server execute simultaneously with the GUI
		thread.start();
		initComponents();
		// this.getRootPane().setDefaultButton(this.btnEnviar);

	}

	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents

	public void initComponents() {

		jScrollPane1 = new JScrollPane();
		txtTexto = new JTextArea();
		btnEnviar = new JButton();
		txtTextoEnviar = new JTextField(25);
		txtPuertoEnviar = new JTextField(3);
		puerto = new JLabel("Puerto: ");
		btnChats = new JButton("Chats");
		jPopupMenu = new JPopupMenu();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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

		btnChats.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnChatsAction(evt);
			}
		});

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				layout.createSequentialGroup().addContainerGap().addGroup(layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
						.addGroup(layout.createSequentialGroup().addComponent(puerto).addComponent(txtPuertoEnviar)
								.addComponent(txtTextoEnviar).addComponent(btnEnviar)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
						.addGroup(layout.createSequentialGroup().addComponent(btnChats).addComponent(jScrollPane1,
								javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)))
						.addContainerGap()));

		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(btnChats)
						.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 237, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(puerto)
						.addComponent(txtPuertoEnviar).addComponent(txtTextoEnviar).addComponent(btnEnviar))

				.addContainerGap()));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	// </editor-fold>//GEN-END:initComponents
	private void btnChatsAction(java.awt.event.ActionEvent evt) {
		jPopupMenu.show(btnChats, btnChats.getWidth() - jPopupMenu.getWidth(), btnChats.getHeight());

	}

	private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnEnviarActionPerformed
		
			String mensaje = this.txtTextoEnviar.getText();
			this.txtTextoEnviar.setText("");
			String puerto = this.txtPuertoEnviar.getText();
			String mipuerto = String.valueOf(servidor.getPort());
			

			Client cliente = new Client(puerto, mipuerto + "$" + mensaje);
			if (map.containsKey(puerto)) {
				String conver=map.get(puerto);
				map.replace(puerto, conver+"\n"+"You: " + mensaje);
			} else {
				map.put(puerto, "You: " + mensaje);
			}
			
			System.out.println(map);
			
			String[] chats = map.keySet().toArray(new String[map.size()]);
			jPopupMenu.removeAll();
			for (int i = 0; i < chats.length; i++) {
				final int index=i;
				JMenuItem item = new JMenuItem(chats[i]);
				item.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String key = chats[index];
						System.out.println(key);
						String conver=map.get(key);
						txtTexto.setText(conver);
					}
				});
				jPopupMenu.add(item);
			}
			this.txtTexto.setText(map.get(puerto));

			// historialChats(msj[0], mensaje);
			try {
			Thread t = new Thread(cliente);
			t.start();}
			catch (Exception e) {
				// TODO: handle exception
				this.txtTexto.setText("Error al enviar. Puerto incorrecto");
			}
			
		
	}
	@Override
	public void update(Observable o, Object arg) {
		String mensaje = (String) arg;
		
		String[] mensajeSeparado = separaMensaje(mensaje);
		
		if (map.containsKey(mensajeSeparado[0])) {/* This conditions edits the map that has the register of all the chats */
			String conver=map.get(mensajeSeparado[0]);
			map.replace(mensajeSeparado[0], conver+"\n"+mensaje);
		} else {
			map.put(mensajeSeparado[0], mensaje);
		}
		
		
		

		String[] chats = map.keySet().toArray(new String[map.size()]);
		jPopupMenu.removeAll();
		for (int i = 0; i < chats.length; i++) {
			JMenuItem item = new JMenuItem(chats[i]);
			final int index=i;
			item.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					String key = chats[index];
					System.out.println(key);
					String conver=map.get(key);
					txtTexto.setText(conver);
				}
			});
			jPopupMenu.add(item);
		}
		
		this.txtTexto.setText(map.get(mensajeSeparado[0])); // Refreshes the text of the conversation 
		System.out.println(map);
	}

	public String[] separaMensaje(String mensaje) {
		/*
		 * Method that separates the message received from the client into 2 parts: port
		 * and message
		 */
		StringTokenizer tokens = new StringTokenizer(mensaje, ":"); // Uses $ symbol as separator
		String[] messageArray = new String[tokens.countTokens()];
		int i = 0;
		while (tokens.hasMoreTokens()) {
			String str = tokens.nextToken();
			messageArray[i] = str;
			i++;
		}
		return messageArray;

	}

	public static void generaVentanas(int cantidad) {
		for (int i = 0; i < cantidad; i++) {
			GUI_sencilla window = new GUI_sencilla();

			Thread hiloChats = new Thread(window);
			hiloChats.start();

		}

	}

	private static void btnExecute(java.awt.event.ActionEvent evt, String cantidad) {// GEN-FIRST:event_btnEnviarActionPerformed
		windows = Integer.parseInt(cantidad.trim());
		generaVentanas(windows);
		frame.dispose();

	}

	public static void main(String args[]) {
		// Creando el Marco

		frame = new JFrame("Chats");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 100);

		// Creando el panel en la parte inferior y agregando componentes
		JPanel panel1 = new JPanel(); // el panel no está visible en la salida
		JPanel panel2 = new JPanel();
		JLabel label = new JLabel("Introduzca la cantidad de ventanas de chats que desea abrir: ");
		JTextField texto = new JTextField(2); // acepta hasta 10 caracteres
		JButton send = new JButton("Ejecutar");
		send.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String windows = texto.getText();
				btnExecute(evt, windows);
			}
		});

		panel1.add(label);
		panel1.add(texto);
		panel2.add(send);

		frame.getContentPane().add(BorderLayout.SOUTH, panel2);
		frame.getContentPane().add(BorderLayout.NORTH, panel1);
		frame.setVisible(true);
	}
}
