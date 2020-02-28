package Package;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
	static int port=5000;
	public static void main(String[] args) {
		ServerSocket servidor = null;
		Socket clientSocket = null;
		DataInputStream inputStream;
		DataOutputStream outputStream;
		try {
			buscaPuerto(servidor, clientSocket);
			servidor = new ServerSocket(port);
		
		while (true) {
			clientSocket = servidor.accept();
			inputStream = new DataInputStream(clientSocket.getInputStream());
			outputStream = new DataOutputStream(clientSocket.getOutputStream());

			String mensaje = inputStream.readUTF();
			System.out.println("Mensaje recibido por el servidor: " + mensaje);

			mensaje = "Hola Mundo desde el servidor";
			outputStream.writeUTF("Respuesta: " + mensaje);

			clientSocket.close();
			System.out.println("Cliente desconectado");
		}
		} catch (IOException ex) {
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE,null,ex);
			// TODO: handle exception
		}
		
	}

	public static void buscaPuerto(ServerSocket servidor, Socket clientSocket) {
		int puerto;
		puerto = Server.port;
		boolean availablePort = false;
		while (!availablePort) {
			try {
				servidor = new ServerSocket(puerto);
				Server.port = puerto;
				servidor.close();
				availablePort = true;
				System.out.println("Servidor iniciado en puerto "+puerto);

			} catch (Exception e2) {
				System.out.println("Puerto "+puerto+" ocupado. Verificando en el siguiente...");
				
				puerto++;
				// TODO: handle exception
			}
		}
		
	
	}
}
