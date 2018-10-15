package miniproject_client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class client {

	public static void main(String[] args) {
		
		// If the game runs
		boolean keepRunning = true;
		
		// Scanner for start game
		Scanner input = new Scanner(System.in);
		
		
		while(keepRunning) {
			try{
				DataOutputStream toServer = null;
				DataInputStream fromServer = null;
				
				
				// Create a socket to connect to the server 
				Socket serverSocket = new Socket("localhost", 2000); // This shouldn't be local host but  the serve machine’s host name or IP address
				System.out.println("Client connected ");
				
				// Create an input stream to receive data from the server
				fromServer = new DataInputStream(serverSocket.getInputStream());
				
				// Create an output stream to send data to the server
				toServer = new DataOutputStream(serverSocket.getOutputStream());
				
				
				
				// Enter if you want to start the game
				System.out.print("Do you want to start a game of rock, paper scissors? Type yes or no: ");
				String wantToStartGame = input.next();
				
				
				// Send to server
				toServer.writeChars(wantToStartGame);
				//toServer.flush();
				
				
				
				// If user types yes it continues if no it stops 
				String answerStartGame = input.next();
				if ( answerStartGame.equalsIgnoreCase("no")) {
					keepRunning = false;
				}
				else {
					// Pick rock, paper, scissors 
					System.out.print("Pick! Rock = 1, paper = 2 or scissors = 3:");			
					
				}
			input.close();
			}
			catch(IOException e) {
				
			}
		}
		
	}
}