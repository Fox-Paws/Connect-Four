package connectFour;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;

import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class GameplayPanel extends JPanel
{
	private JLabel				label;
	private JLabel				labelTitle;
	private ArrayList<JButton>	buttons;
	private JButton				resign;

	public GameplayPanel(GameplayControl gpc)
	{
		// More GUI operations with panels, labels, and buttons
		JPanel right = new JPanel(new GridLayout(1, 1, 480, 480));
		JPanel rightContainer = new JPanel(new BorderLayout());
		gpc.getGameBoard().setVisible(true);
		rightContainer.add(gpc.getGameBoard(), BorderLayout.CENTER);
		JPanel left = new JPanel(new GridLayout(3, 1, 100, 160));
		labelTitle = new JLabel("Connect Four");
		labelTitle.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 32));
		JPanel but = new JPanel(new GridLayout(1, 2, 100, 160));

		JButton exit = new JButton("Exit");
		resign = new JButton("Resign");
		resign.setEnabled(false);

		JPanel buttonGrid = new JPanel(new GridLayout(1, 7));
		buttons = new ArrayList<JButton>(7);
		for (int i = 0; i < 7; i++)
		{
			buttons.add(new JButton("Drop"));
			// Action command is what is sent to the action listener when the button is
			// pressed
			buttons.get(i).setActionCommand(new Integer(i).toString());
			buttonGrid.add(buttons.get(i));
			buttons.get(i).addActionListener(gpc);
			buttons.get(i).setEnabled(false);
		}
		rightContainer.add(buttonGrid, BorderLayout.NORTH);
		right.add(rightContainer);
		resign.addActionListener(gpc);
		exit.addActionListener(gpc);
		but.add(resign);
		but.add(exit);
		label = new JLabel("");
		label.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 44));
		left.add(labelTitle);
		left.add(label);
		left.add(but);

		JPanel jp = new JPanel(new GridLayout(1, 2, 1, 10));
		jp.add(left);
		jp.add(right);
		this.add(jp);
		this.setVisible(true);
	}

	public void setMessage(String message)
	{
		label.setText(message);
	}

	public void setPlayer(boolean color)
	{
		// Appends red or black to the main label
		String result;
		if (color)
		{
			result = "red";
		}
		else
		{
			result = "black";
		}
		labelTitle.setText(labelTitle.getText() + " - " + result);
	}

	public void disableButtons()
	{
		// disables all gameplay buttons after a win has occurred
		for (JButton b : buttons)
		{
			b.setEnabled(false);
		}
		// reuse the resign button to offer rematch
		resign.setText("Rematch");
		resign.setActionCommand("Rematch");
	}

	public void disableButton(int col)
	{
		// disables the resign button if necessary
		if (col == -1)
		{
			resign.setEnabled(false);
		}
		else
		{
			// disables specific gameplay button if column is full
			buttons.get(col).setEnabled(false);
		}
	}

	public void enableButtons()
	{
		// enables all buttons and restores the resign functionality
		for (JButton b : buttons)
		{
			b.setEnabled(true);
		}
		resign.setText("Resign");
		resign.setActionCommand("Resign");
		resign.setEnabled(true);
	}
}
