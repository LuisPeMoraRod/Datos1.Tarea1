package com.TEC.Datos1.Tarea1;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.StringTokenizer;
import java.util.Observable;

@SuppressWarnings("deprecation")
public class Server extends Observable implements Runnable{
	static int port=5000;
	static int myport;
	private int mipuerto;
	
	@Override
	public void run() {
		ServerSocket servidor = null;
		Socket clientSocket = null;
		DataInputStream inputStream;
		
		try {
			myport=buscaPuerto(servidor, clientSocket);
			servidor = new ServerSocket(myport);
			mipuerto=servidor.getLocalPort();
		while (true) {
			clientSocket = servidor.accept();
			inputStream = new DataInputStream(clientSocket.getInputStream());
			String mensaje = inputStream.readUTF();
			String [] mensajePuerto=separaMensaje(mensaje); //Message separated in an array: mensajePuerto[0]=port and mensajePuerto[1]=message
			System.out.println("Puerto emisor: "+mensajePuerto[0]);
			System.out.println("Mensaje recibido por el puerto"+myport+":" + mensajePuerto[1]);
			
			 this.setChanged();
             this.notifyObservers(mensajePuerto[0]+": "+mensajePuerto[1]);
             this.clearChanged();
             
			clientSocket.close();
			System.out.println("Cliente desconectado");
		}
		} catch (IOException ex) {
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE,null,ex);
			// TODO: handle exception
		}
		
	}
	
	public static int buscaPuerto(ServerSocket servidor, Socket clientSocket) {
		/*
		 * Method that searches a port within the communication can be done with the socket.
		 * The method uses the static attribute "port" that changes every time a new free port is 
		 * found.
		 * 
		 */
		int puerto;
		puerto = port;
		boolean availablePort = false;
		while (!availablePort) {
			try {
				servidor = new ServerSocket(puerto);
				Server.port = puerto;
				servidor.close();
				availablePort = true;
				System.out.println("Servidor iniciado en puerto "+puerto);
				
			} catch (Exception e2) {
				//System.out.println(e2);
				System.out.println("Puerto "+puerto+" ocupado. Verificando en el siguiente...");
				puerto++;
				// TODO: handle exception
			}
		}
		return puerto;
	}
	
	public static String[] separaMensaje(String mensaje) {
		/*
		 * Method that separates the message received from the client into 2 parts: port and message
		 */
		StringTokenizer tokens=new StringTokenizer (mensaje,"$"); //Uses $ symbol as separator
		String[] messageArray = new String[tokens.countTokens()];
		int i = 0;
		while (tokens.hasMoreTokens()) {
			String str = tokens.nextToken();
			messageArray[i] = str;
			i++;
		}
		return messageArray;
				
	}
	
	public int getPort() {
		return mipuerto;
	}
	
}
