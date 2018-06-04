package connectFour;

import ocsf.client.AbstractClient;

public class GameClient extends AbstractClient
{
	// Private data fields for storing the GUI controllers.
	private InitialControl	ic;
	private GameplayControl	gpc;
	boolean					whois;

	// Setters for the GUI controllers.
	public void setInitialControl(InitialControl ic)
	{
		this.ic = ic;
	}

	public void setGameplayControl(GameplayControl gpc)
	{
		this.gpc = gpc;
	}

	// Constructor for initializing the client with default settings.
	public GameClient()
	{
		super("localhost", 8300);
	}

	// Method that handles messages from the server.
	public void handleMessageFromServer(Object arg0)
	{
		if (arg0 instanceof GameData)
		{
			if (!((GameData) arg0).isPlaying())
			{ // when waiting for the second player to connect...
				gpc.displayLabel("Searching for opponent...");
				gpc.disableButton(-1);
				gpc.drawChess(((GameData) arg0).getBoard());
			}
			else
			{
				// During regular gameplay..
				gpc.enableButtons();
				int columns[] = ((GameData) arg0).getColumns();
				for (int i = 0; i < 7; i++)
				{
					if (columns[i] > 5)
					{ // If a column is full, disable the "drop" button for that column
						gpc.disableButton(i);
					}
				}

				if (((GameData) arg0).isWhoseTurn() == whois)
				{ // If it's our turn...
					if (((GameData) arg0).checkWon())
					{
						gpc.displayLabel("You won!");
						gpc.disableButtons();
					}
					else
					{
						gpc.displayLabel("Your move");
					}

					// update the game board
					gpc.drawChess(((GameData) arg0).getBoard());

				}
				else
				{ // If it's their turn...
					if (((GameData) arg0).checkWon())
					{
						gpc.displayLabel("Opponent wins!");
						gpc.disableButtons();
					}
					else
					{
						gpc.displayLabel("Other player's turn");
					}
					gpc.drawChess(((GameData) arg0).getBoard());
				}
			}

		}
		else if (arg0 instanceof Boolean)
		{
			// Server sends a Boolean indicating which player this is
			// which will either be false -> black, or true -> red
			whois = ((Boolean) arg0).booleanValue();
			gpc.setPlayer(((Boolean) arg0).booleanValue());
		}

	}
}
