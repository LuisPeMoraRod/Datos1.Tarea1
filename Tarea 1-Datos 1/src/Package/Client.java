package Package;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

	public static void main(String[] args) {
		final String host="127.0.0.1";	
		DataOutputStream outputStream;
		int port=5005;
		try {
			Socket socketClient = new Socket (host,port);
			outputStream = new DataOutputStream(socketClient.getOutputStream());
			String send="Hola Mundo desde el cliente.";
			outputStream.writeUTF(send);
			System.out.println("Mensaje enviado desde el cliente: "+send);
			socketClient.close();
			
		} catch (IOException ex) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE,null,ex);
			// TODO: handle exception
		}
		
	}
		
		
}