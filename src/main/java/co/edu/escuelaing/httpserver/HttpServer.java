package co.edu.escuelaing.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import co.edu.escuelaing.NanoSpring.NanoSpringApplication;
import co.edu.escuelaing.NanoSpring.demo.ObtainResources;

public class HttpServer {
	private Map<String,NanoSpringApplication > handlers= new HashMap<>() ;
	
	public HttpServer() {
		super();
	}
	
	/**
	 * This method is used to start the application client-server
	 * @throws IOException
	 */
	public void startServer() throws IOException {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(getPort());
		} catch (IOException e) {
			System.err.println("Could not listen on port: 35000.");
			System.exit(1);
		}

		boolean running = true;
		while (running) {
			Socket clientSocket = null;
			try {
				System.out.println("Listo para recibir ...");
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				System.err.println("Accept failed.");
				System.exit(1);
			}
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String inputLine, outputLine = null;
			String path = "";
			boolean encontro=false;
			while ((inputLine = in.readLine()) != null) {
				System.out.println("Recibí: " + inputLine);
				if (inputLine.startsWith("GET")) {
					path = inputLine.split(" ")[1];	
					
					for(String handlerPath:handlers.keySet()) {
						System.out.println(path+"--"+handlerPath);
						if(path.startsWith(handlerPath)) {
							
							if(path.contains(".js")) {
								String resource= path.split("/")[2];
								outputLine = ObtainResources.outResource(resource, "js");
							}else if(path.contains(".PNG")){
								String resource= path.split("/")[2];
								ObtainResources.outResourceImage(resource,clientSocket.getOutputStream());
							}else {
								outputLine = handlers.get(handlerPath).handle(path.substring(handlerPath.length()));
							}
							encontro=true;
						}
					}
				}
				if (!in.ready()) {
					break;
				}
			}
			if(!encontro) {
				outputLine = ObtainResources.outResource("index.html", "html");
			}
			
			out.println(outputLine);
			out.close();
			in.close();
			clientSocket.close();
		}
		serverSocket.close();
	}
	

	
	/**
	 * 
	 * This method is used to get to port of system
	 * @return the port of system or 35000 if is empty
	 */
	private int getPort() {
		if (System.getenv("PORT") != null) {
			return Integer.parseInt(System.getenv("PORT"));
		}
		return 35000; // returns default port if heroku-port isn't set(i.e. on localhost)
	}

	public void registerHandler(NanoSpringApplication nanoSpringApplication, String string) {
		
		handlers.put(string,nanoSpringApplication);
		
	}
	
}
