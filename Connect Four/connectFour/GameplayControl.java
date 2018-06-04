package connectFour;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GameplayControl implements ActionListener
{
	// Private data fields for the container and game client.
	private JPanel		container;
	private GameClient	client;
	private GameBoard	gb;
	private boolean		resign	= false;

	// Constructor for the gameplay controller.
	public GameplayControl(JPanel container, GameClient client)
	{
		this.container = container;
		this.client = client;
		gb = new GameBoard();
	}

	public GameBoard getGameBoard()
	{
		return gb;
	}

	public void drawChess(Piece[][] board)
	{
		gb.updateBoard(board);
	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		// Get the name of the button clicked.
		String command = ae.getActionCommand();

		if (command == "Resign")
		{
			// Tell server this client resigns by sending "-1"
			try
			{
				client.sendToServer("-1");
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resign = true;
		}
		else if (command == "Exit")
		{
			System.exit(0);
		}
		else
		{
			// A "Drop" button was selected, so send that move to the server
			try
			{
				client.sendToServer(command);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void displayLabel(String message)
	{
		GameplayPanel gameplayPanel = (GameplayPanel) container.getComponent(1);
		;
		gameplayPanel.setMessage(message);
	}

	public void setPlayer(boolean color)
	{
		// Sets the player's color (red or black)
		GameplayPanel gp = (GameplayPanel) container.getComponent(1);
		;
		gp.setPlayer(color);
	}

	public void disableButton(int col)
	{
		// Disables a specific button
		GameplayPanel gp = (GameplayPanel) container.getComponent(1);
		;
		gp.disableButton(col);
	}

	public void disableButtons()
	{
		// Disables all gameplay buttons
		GameplayPanel gp = (GameplayPanel) container.getComponent(1);
		;
		gp.disableButtons();
	}

	public void enableButtons()
	{
		// Enables all gameplay buttons
		GameplayPanel gp = (GameplayPanel) container.getComponent(1);
		;
		gp.enableButtons();
	}
}