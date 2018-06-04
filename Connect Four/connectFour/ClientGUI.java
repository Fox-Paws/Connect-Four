package connectFour;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ClientGUI extends JFrame
{

	// Constructor that creates the client GUI.
	public ClientGUI(String ip, int port)
	{
		// Set up the chat client.
		GameClient client = new GameClient();
		client.setHost(ip);
		client.setPort(port);
		try
		{
			client.openConnection();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		// Set the title and default close operation.
		this.setTitle("Connect Four Client");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create the card layout container.
		CardLayout cardLayout = new CardLayout();
		JPanel container = new JPanel(cardLayout);

		// Create the Controllers next
		// Next, create the Controllers
		InitialControl ic = new InitialControl(container, client);
		GameplayControl gpc = new GameplayControl(container, client);

		// Set the client info
		client.setInitialControl(ic);
		client.setGameplayControl(gpc);

		// Create the four views. (need the controller to register with the Panels
		// JPanel view4 = new ContactsPanel();
		JPanel view1 = new InitialPanel(ic);
		JPanel view2 = new GameplayPanel(gpc);

		// Add the views to the card layout container.
		container.add(view1, "1");
		container.add(view2, "2");

		// Show the initial view in the card layout.
		cardLayout.show(container, "1");

		// Add the card layout container to the JFrame.
		// GridBagLayout makes the container stay centered in the window.
		this.setLayout(new GridBagLayout());
		this.add(container);

		// Show the JFrame.
		this.setSize(1500, 1000);
		this.setVisible(true);
	}

	// Main function that creates the client GUI when the program is started.
	public static void main(String[] args)
	{
		if (args.length != 2)
		{
			new ClientGUI("localhost", 8300);
		}
		else
		{
			new ClientGUI(args[0], Integer.parseInt(args[1]));
		}
	}
}
