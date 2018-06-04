package connectFour;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class InitialControl implements ActionListener
{
	// Private data field for storing the container.
	private JPanel		container;
	private GameClient	client;

	// Constructor for the initial controller.
	public InitialControl(JPanel container, GameClient client)
	{
		this.container = container;
		this.client = client;
	}

	// Handle button clicks.
	public void actionPerformed(ActionEvent ae)
	{
		// Get the name of the button clicked.
		String command = ae.getActionCommand();

		if (command.equals("Start"))
		{ // When the user clicks the start button, let the server know
			try
			{
				client.sendToServer("Start");
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CardLayout cardLayout = (CardLayout) container.getLayout();
			cardLayout.show(container, "2");
			// This shows the gameplay panel
		}
	}

}
