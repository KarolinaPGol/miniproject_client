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
				Socket serverSocket = new Socket("localhost", 3000); // This shouldn't be local host but the serve
																		// machine�s host name or IP address
				//System.out.println("Client connected ");

				// Create an input stream to receive data from the server
				fromServer = new DataInputStream(serverSocket.getInputStream());

				// Create an output stream to send data to the server
				toServer = new DataOutputStream(serverSocket.getOutputStream());

				// Enter if you want to start the game
				System.out.println("Welcome!");
				System.out.println("Do you want to start a game of rock, paper scissors? Type yes or no: ");
				String wantToStartGame = input.nextLine();
				

				// Send to server 'yes' or 'no'
				toServer.writeUTF(wantToStartGame);
				// toServer.flush();
				System.out.println("Server: " + fromServer.readUTF());

				// If user types yes it continues if no it stops
				
				if (wantToStartGame.equalsIgnoreCase("no")) {
					keepRunning = false;
				}
				// ----------------------------GAME-------------------------------
				else {

					System.out.println("Enter your username: ");
					String username = input.next();
					toServer.writeUTF(username); // sends username to server

					// reading players usernames and their choice of tool
					String player1_username = fromServer.readUTF();
					String player2_username = fromServer.readUTF();
					String player3_username = fromServer.readUTF();
					String[] players = {player1_username, player2_username, player3_username};
					
					// Pick rock, paper, scissors
					System.out.print("Pick! Rock = 1, paper = 2 or scissors = 3:");
					int tool = input.nextInt(); // reads users input 1, 2 or 3
					toServer.writeInt(tool); // sends which tool player picked

					int player1_tool = fromServer.readInt();
					int player2_tool = fromServer.readInt(); //reading which tool players chosen 
					int player3_tool = fromServer.readInt();
					int[] tools = {player1_tool, player2_tool, player3_tool};
					
					//System.out.println("You chose: " + toolString(tool));
					
					for(int i=0; i<players.length; i++){
						if(players[i] == username)
							System.out.println("You chose" + toolString(tool));
						else
							System.out.println(players[i] + "chose" + toolString(tools[i]));
					}

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
					
					
							
				}

				input.close();
			} catch (IOException e) {

			}
		}

	}

	static String toolString(int tool) { // changes the int tool to the string
		if (tool == 1)
			return "Rock";
		if (tool == 2)
			return "Paper";
		if (tool == 3)
			return "Scissors";
		return "Wrong input"; // if nothing from 1,2,3
	}

}