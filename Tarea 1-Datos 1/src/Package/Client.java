package Package;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
	

	public static void main(String[] args) {
		final String host="127.0.0.1";
		int port=5011;
		DataInputStream inputStream;
		DataOutputStream outputStream;
		
		try {
			Socket socketClient = new Socket (host,port);
			inputStream = new DataInputStream(socketClient.getInputStream());
			outputStream = new DataOutputStream(socketClient.getOutputStream());
			
			String send="Hola Mundo desde el cliente.";
			outputStream.writeUTF(send);
			System.out.println("Mensaje enviado desde el cliente: "+send);
			
			String mensaje = inputStream.readUTF();
			System.out.println("Mensaje recibido por el cliente: "+mensaje);
			socketClient.close();
			
			
			
		} catch (IOException ex) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE,null,ex);
			// TODO: handle exception
		}
		
	}
		
		
}