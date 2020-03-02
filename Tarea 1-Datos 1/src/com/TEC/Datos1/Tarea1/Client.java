package com.TEC.Datos1.Tarea1;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements Runnable{
    private String send;

    public Client(String send) {
        this.send = send;
    }
    
	@Override
	public void run() {
		final String host="127.0.0.1";	
		DataOutputStream outputStream;
		try {
			
			String[] mensaje=Server.separaMensaje(send);
			int port = esPuertoValido(mensaje[0]);
			if (0<port && port<65535) {
				Socket socketClient = new Socket (host,port);
				outputStream = new DataOutputStream(socketClient.getOutputStream());
				outputStream.writeUTF(send);
				System.out.println(send);
				socketClient.close();
			}
			else {
				System.out.println("Invalid port.");
			}
			
			
		} catch (IOException ex) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE,null,ex);
			// TODO: handle exception
		}
		
	}
	
	public static int esPuertoValido(String str) {
		/*
		 * Methods that converts a string into a integer type value. The string received as a parameter 
		 * must be the port used for the communication through the socket.
		 */
		try {
			int puerto=Integer.parseInt(str.trim());
			return puerto;
		} catch (NumberFormatException nfe) {
			System.out.println("NumberFormatException: " + nfe.getMessage());
			return 0;
			// TODO: handle exception
		}
	}
		
		
}