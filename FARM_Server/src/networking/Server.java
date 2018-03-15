package networking;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
	private ServerSocket serSocket;
	private Socket connection;
	
	public Server()
	{
		this.createConnection();
	}
	

	public void createConnection() {
		
		try {
			serSocket = new ServerSocket(8888);
			System.out.println("SERVER LOG:Server running on port 8888");
			while(true)
			{
				connection = serSocket.accept();
				new ServerThread(connection).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	}
