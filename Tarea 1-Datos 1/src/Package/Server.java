package Package;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Server {

	public static void main(String[] args) {
		ServerSocket servidor =null;
		Socket clientSocket = null;
		DataInputStream inputStream;
		DataOutputStream outputStream;
		
		
		int port=5011;
		try {
			servidor = new ServerSocket(port);
			System.out.println("Servidor iniciado...");
			
			while(true) { 
				clientSocket=servidor.accept();
				inputStream = new DataInputStream(clientSocket.getInputStream());
				outputStream = new DataOutputStream(clientSocket.getOutputStream());
				
				String mensaje= inputStream.readUTF();
				System.out.println("Mensaje recibido por el servidor: "+mensaje);
				
				mensaje="Hola Mundo desde el servidor";
				outputStream.writeUTF("Respuesta: "+mensaje);
				
				clientSocket.close();
				System.out.println("Cliente desconectado");
				
				
			}
			
			
		} catch (IOException ex) {
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE,null,ex);
			// TODO: handle exception
		}
		
	}
}
