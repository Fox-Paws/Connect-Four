package connectFour;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import javax.swing.*;

import java.io.IOException;
import java.util.ArrayList;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class GameServer extends AbstractServer
{
	// Data fields for this chat server.
	private JTextArea				log;
	private JLabel					status;
	private boolean					running	= false;
	ArrayList<ConnectionToClient>	clients	= new ArrayList<ConnectionToClient>(2);
	GameData						game	= new GameData();
	private int						rematch	= 0;

	// Constructor for initializing the server with default settings.
	public GameServer()
	{
		super(12345);
		this.setTimeout(500);
	}

	// Getter that returns whether the server is currently running.
	public boolean isRunning()
	{
		return running;
	}

	// Setters for the data fields corresponding to the GUI elements.
	public void setLog(JTextArea log)
	{
		this.log = log;
	}

	public void setStatus(JLabel status)
	{
		this.status = status;
	}

	// When the server starts, update the GUI.
	public void serverStarted()
	{
		running = true;
		status.setText("Listening");
		status.setForeground(Color.GREEN);
		log.append("Server started\n");
	}

	// When the server stops listening, update the GUI.
	public void serverStopped()
	{
		status.setText("Stopped");
		status.setForeground(Color.RED);
		log.append("Server stopped accepting new clients - press Listen to start accepting new clients\n");
	}

	// When the server closes completely, update the GUI.
	public void serverClosed()
	{
		running = false;
		status.setText("Close");
		status.setForeground(Color.RED);
		log.append("Server and all current clients are closed - press Listen to restart\n");
	}

	// When a client connects or disconnects, display a message in the log.
	public void clientConnected(ConnectionToClient client)
	{
		log.append("Client " + client.getId() + " connected\n");
		clients.add(client);
		if (clients.size() > 1)
		{
			stopListening();
		}
	}

	// When a message is received from a client, handle it.
	public void handleMessageFromClient(Object arg0, ConnectionToClient client)
	{
		if (arg0 instanceof String)
		{
			if (((String) arg0).equals("Start"))
			{ // When the user selects the "start" button
				if (clients.size() > 1)
				{ // When we reach 2 players, we can start playing
					game.setPlaying(true);
				}
				try
				{
					// Sends the player's color to the client with 0 = black and 1 = red
					client.sendToClient(new Boolean(clients.get(0).getId() != client.getId()));
					for (ConnectionToClient c : clients)
					{ // Send the game to all connected clients
						c.sendToClient(game);
					}
				}
				catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return;
			}
			else if (((String) arg0).equals("Rematch"))
			{ // If a user selects rematch, this is executed
				rematch++;
				if (rematch == 2)
				{
					game = new GameData();
					game.setPlaying(true);
					rematch = 0;
					for (ConnectionToClient c : clients)
					{
						try
						{
							c.sendToClient(game);
						}
						catch (IOException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				return;
			}

			boolean clientNo;
			int move = Integer.parseInt((String) arg0);

			if (client.getId() == clients.get(0).getId())
			{ // Determines which client sent the move
				clientNo = false;
			}
			else
			{
				clientNo = true;
			}

			if (move == -1)
			{ // -1 is received when a player resigns.
				game.setWhoseTurn(!clientNo);
				game.resignWin();
				try
				{
					clients.get(0).sendToClient(game);
					clients.get(1).sendToClient(game);
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
			else if (game.isWhoseTurn() != clientNo)
			{ // If the current turn isn't the sending client, don't really do anything
				try
				{
					client.sendToClient(game);
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Not your turn");
				return;
			}
			else if (game.checkMove(move))
			{ // If the turn is from the correct player, check the move
				// If the move is invalid, let the user select another one during their turn
				try
				{
					client.sendToClient(game);
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Invalid move");
				return;
			}

			// put the game piece from current move into the board
			game.addPiece(move, clientNo);

			if (game.checkWin(move))
			{ // If a player wins....
				log.append(clientNo + " wins!\n");
				// the checkwin method automatically sets a "won" flag in the game data
				for (ConnectionToClient c : clients)
				{
					try
					{
						c.sendToClient(game);
					}
					catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			else
			{
				// If there wasn't a win, change whose turn it is and send to client
				game.setWhoseTurn(!clientNo);
				for (ConnectionToClient c : clients)
					try
					{
						c.sendToClient(game);
					}
					catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
	}

	// Method that handles listening exceptions by displaying exception information.
	public void listeningException(Throwable exception)
	{
		running = false;
		status.setText("Exception occurred while listening");
		status.setForeground(Color.RED);
		log.append("Listening exception: " + exception.getMessage() + "\n");
		log.append("Press Listen to restart server\n");
	}
}
