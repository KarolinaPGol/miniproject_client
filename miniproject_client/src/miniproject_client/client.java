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

		while (keepRunning) {
			try {
				DataOutputStream toServer = null;
				DataInputStream fromServer = null;
		
				
				// Create a socket to connect to the server
				Socket socket = new Socket("localhost", 1100); // This shouldn't be local host but the serve
																		// machines host name or IP address

				// Create an input stream to receive data from the server
				fromServer = new DataInputStream(socket.getInputStream());

				// Create an output stream to send data to the server
				toServer = new DataOutputStream(socket.getOutputStream());
				
		
				// Enter if you want to start the game and send to server
				System.out.println("******* WELCOME *******");
				System.out.println("Do you want to start a game of rock, paper scissors? Type yes or no: ");
				String wantToStartGame = input.nextLine();
				toServer.writeUTF(wantToStartGame);
				//System.out.println("after sending string to server");
				System.out.println("Server: " + fromServer.readUTF());
				
				
				
				// If user types yes it continues if no it stops
				
				if (wantToStartGame.equalsIgnoreCase("no")) {
					keepRunning = false;
				}
				// ----------------------------GAME-------------------------------
				else {
					//System.out.println("Enter no: ");
					//int no= input.nextInt();
					//toServer.writeInt(no); // sends username to server
					//System.out.println(fromServer.readInt());
					
					//-----------------USERNAME
					System.out.println("Enter your username: ");
					String username = input.next();
					toServer.writeUTF(username); // sends username to server
					
					
					// reading players usernames and their choice of tool
					
					String player2_username = fromServer.readUTF();
					String player3_username = fromServer.readUTF();
					
					System.out.println("Other players: " + player2_username + " and " + player3_username);
					//String[] players = {player1_username, player2_username, player3_username};
					//System.out.println(player1_username + player2_username + player3_username );
					
					//-----------------TOOL
					// Pick rock, paper, scissors
					
					System.out.println("******* Pick! *******");
					System.out.print("Rock = 0, paper = 1 or scissors = 2:");
					int tool = input.nextInt(); // reads users input 1, 2 or 3
					
					
					if (tool != 0 && tool != 1 && tool != 2) {
						System.out.println("Invalid answer. Please try again! ");
						System.out.println("**********************************");
						System.out.println("******* Pick! *******");
						System.out.print("Rock = 0, paper = 1 or scissors = 2:");
						tool = input.nextInt();
						
					}
						
					
										
					System.out.println("client; you chose: " + tool);
					toServer.writeInt(tool); // sends which tool player picked
					
					int player2_tool = fromServer.readInt(); //reading which tool players chosen 
					int player3_tool = fromServer.readInt();
					
					System.out.println("You chose: " + toolString(tool));
					System.out.println(player2_username + " chose " + toolString(player2_tool));
					System.out.println(player3_username + " chose " + toolString(player3_tool));
					
			
					/*
					// ---------------------------WHO WON---------------------------------------

					// GO HARDCODE OR GO HOME

					int result = fromServer.readInt(); // who won

					if (result == 0) {
						System.out.println("All players are tie");
					} else if (result == 1) {
						System.out.println("Player 1 wins");
					} else if (result == 2) {
						System.out.println("Player 2 wins");
					} else if (result == 3) {
						System.out.println("Player 3 wins");
					} else if (result == 4) {
						System.out.println("Player 2 & player 3 are tie");
					} else if (result == 5) {
						System.out.println("Player 1 & player 2 are tie");
					} else if (result == 6) {
						System.out.println("Player 1 & player 3 are tie");
					}
					
					
					*/		
				}
				
				
				input.close();
			} catch (IOException e) {

			}
		}

	}

	static String toolString(int tool) { // changes the int tool to the string
		if (tool == 0)
			return "Rock";
		if (tool == 1)
			return "Paper";
		if (tool == 2)
			return "Scissors";
		return "Invalid input"; // if nothing from 1,2,3
	}

}