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

public class GUI_chat extends JFrame implements Observer {
	// Variables declaration 
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
	/*
	map created to save all the conversations the object has had using different ports. The keys are the ports and 
	the values are the conversation that used those ports used as keys
	*/

	Server servidor = new Server();

	private static final long serialVersionUID = 1L;

	public GUI_chat() {
		/*
		 * Constructor method. The server objects notifies changes to the GUI_chat object using addObserver method.
		 * A thread is declared  to make the server execute simultaneously with the GUI
		 */

		servidor.addObserver(this); 
		Thread thread = new Thread(servidor);
		thread.start();
		initComponents();
		

	}

	public void initComponents() {
		/*
		 * Method that creates a chat window.
		 */
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
		
		/*
		 * Uses the class GruopLayout to edit the GUI and organize the components created
		 */
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
	}
	private void btnChatsAction(java.awt.event.ActionEvent evt) {
		/*
		 * Method that allows the pop menu to appear
		 */
		jPopupMenu.show(btnChats, btnChats.getWidth() - jPopupMenu.getWidth(), btnChats.getHeight());

	}

	private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnEnviarActionPerformed
			/*
			 * This method sends the messages through the specified port by running a thread of an Client class object
			 */
			String mensaje = this.txtTextoEnviar.getText();
			this.txtTextoEnviar.setText("");
			String puerto = this.txtPuertoEnviar.getText();
			String mipuerto = String.valueOf(servidor.getPort());
			
			/*
			 * Adds message to the map
			 */
			Client cliente = new Client(puerto, mipuerto + "$" + mensaje);
			if (map.containsKey(puerto)) {
				String conver=map.get(puerto);
				map.replace(puerto, conver+"\n"+"You: " + mensaje);
			} else {
				map.put(puerto, "You: " + mensaje);
			}
			
			System.out.println(map);
			
			/*
			 * Handles the appearance of the items of the pop menu. This items are the ports used for the different chats
			 */
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
		/*
		 * This method edits the map and the text area whenever the server receives new data
		 */
		
		String mensaje = (String) arg;
		String[] mensajeSeparado = separaMensaje(mensaje);
		
		if (map.containsKey(mensajeSeparado[0])) {/* This conditions edits the map that has the register of all the chats */
			String conver=map.get(mensajeSeparado[0]);
			map.replace(mensajeSeparado[0], conver+"\n"+mensaje);
		} else {
			map.put(mensajeSeparado[0], mensaje);
		}
		
		 /*
		  * Loops through elements in the map with the previous conversation so that when a button from the chats menu
		  *  is selected, that conversation is displayed in the text area.
		  */
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
		/*
		 * This method generates x amount of threads that run the different chat windows. Uses the class GUI_sencilla 
		 * in the thread(s)
		 */
		for (int i = 0; i < cantidad; i++) {
			GUI_sencilla window = new GUI_sencilla();
			Thread hiloChats = new Thread(window);
			hiloChats.start();

		}

	}

	private static void btnExecute(java.awt.event.ActionEvent evt, String cantidad) {// GEN-FIRST:event_btnEnviarActionPerformed
		/*
		 * Calls the method that creates the amount of chat windows that the user required and disposes the home window
		 */
		windows = Integer.parseInt(cantidad.trim());
		generaVentanas(windows);
		frame.dispose();

	}

	public static void main(String args[]) {
		/*
		 * A frame is created. This windows requests the user to enter an amount of chat windows
		 */
		
		frame = new JFrame("Chats");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 100);

		
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JLabel label = new JLabel("Introduzca la cantidad de ventanas de chats que desea abrir: ");
		JTextField texto = new JTextField(2);
		JButton send = new JButton("Ejecutar");
		send.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String windows = texto.getText();//user enters the amount of chat windows wished
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
