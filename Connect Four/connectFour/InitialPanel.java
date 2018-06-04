package connectFour;

import java.awt.*;
import javax.swing.*;

public class InitialPanel extends JPanel
{
	// Constructor for the initial panel.
	public InitialPanel(InitialControl ic)
	{

		// Create the information label.
		JLabel label = new JLabel("Connect Four", JLabel.CENTER);

		// Create the login button.
		JButton startButton = new JButton("Start");
		startButton.addActionListener(ic);
		JPanel startButtonBuffer = new JPanel();
		startButtonBuffer.add(startButton);

		// Arrange the components in a grid.
		JPanel grid = new JPanel(new GridLayout(3, 1, 5, 5));
		grid.add(label);
		grid.add(startButtonBuffer);
		this.add(grid);
	}
}
