		package miniproject_client;
		import java.io.DataInputStream;
		import java.io.DataOutputStream;
		import java.io.IOException;
		import java.net.Socket;
		import java.util.Scanner;
		
		public class client {
		
			private static String playAgain;
			private static boolean playAgainServer;
		
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
						Socket socket = new Socket("192.168.43.3", 9100); // This shouldn't be local host but the serve
																				// machine host name or IP address
		
						// Create an input stream to receive data from the server
						fromServer = new DataInputStream(socket.getInputStream());
		
						// Create an output stream to send data to the server
						toServer = new DataOutputStream(socket.getOutputStream());
				
						// Enter if you want to start the game and send to server
						System.out.println("Welcome to multiplayer Rock, Paper, Scissors");
						System.out.println("Do you want to start a game? Type yes or no: ");
						String wantToStartGame = input.nextLine();
						toServer.writeUTF(wantToStartGame);
						//System.out.println("after sending string to server");
									
						
						// If user types yes it continues if no it stops
						
						if (wantToStartGame.equalsIgnoreCase("no")) {
							keepRunning = false;
						}
						// ----------------------------GAME-------------------------------
						else {
							
							
							//-----------------USERNAME
							System.out.println("Enter your username: ");
							String username = input.next();
							toServer.writeUTF(username); // sends username to server
							System.out.println("Your username is: " + username);
							System.out.println("Waiting for other players to enter a username...");
							// reading players usernames and their choice of tool
							
							String player2_username = fromServer.readUTF();
							String player3_username = fromServer.readUTF();
							
							System.out.println("other players: " + player2_username + " and " + player3_username);
							
							
							//-----------------TOOL
							// Pick rock, paper, scissors
							do {
							System.out.println("*** Pick your throw! ***");
							System.out.print("Rock = 0, paper = 1 or scissors = 2:");
							int tool = input.nextInt(); // reads users input 1, 2 or 3
							
							if(tool != 0 && tool != 1 && tool != 2) {
							System.out.println("Invalid answer. Please try again!");
							System.out.println("**********************************");
							System.out.println("*** Pick your throw! ***");
							System.out.print("Rock = 0, paper = 1 or scissors = 2:");
							tool = input.nextInt();								
								
							}
							
							System.out.println("You chose: " + toolString(tool));
							toServer.writeInt(tool); // sends which tool player picked
							System.out.println("Waiting for other players to pick their throw...");
							int player2_tool = fromServer.readInt(); //reading which tool players chosen 
							int player3_tool = fromServer.readInt();
							System.out.println("");
							System.out.println("The players choices: ");
							System.out.println("You chose: " + toolString(tool));
							System.out.println(player2_username + " chose: " + toolString(player2_tool));
							System.out.println(player3_username + " chose: " + toolString(player3_tool));
							
					
							
							// ---------------------------WHO WON---------------------------------------
		
							String result = fromServer.readUTF(); // who won
	
							
							System.out.println("Game Result: " + result);	
							
							System.out.println("Do you want to play again? Type y/n");
						     playAgain = input.next();
						     toServer.writeUTF((String) playAgain);
						     
						     String servermessege = fromServer.readUTF();
						     System.out.println(servermessege);
						    
						     playAgainServer = fromServer.readBoolean();
						    
						     
						}while(playAgainServer == true);	
							System.out.println("Thank you for playing");
							System.exit(0);
						}
						
						
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
		
		



