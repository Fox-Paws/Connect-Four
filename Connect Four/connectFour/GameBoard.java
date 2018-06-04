package connectFour;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

public class GameBoard extends JPanel
{
	private Piece[][] board;

	public GameBoard()
	{
		board = new Piece[6][7];
		repaint();
	}

	public void updateBoard(Piece[][] board)
	{
		// Essentially updating the client's local copy of the board
		// This is done by making a new board and populating it with the update
		this.board = new Piece[6][7];
		for (int r = 0; r < 6; r++)
		{
			for (int c = 0; c < 7; c++)
			{
				if (board[r][c] == null)
				{
					continue;
				}
				this.board[r][c] = new Piece(board[r][c].getColor());
			}
		}
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		// Overrided paintComponent for painting on this JPanel
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		for (int i = 0; i < 7; i++)
		{
			// Draw horizontal lines
			g2d.setColor(Color.black);
			g2d.drawLine(5, i * 60 + 5, 425, i * 60 + 5);
		}
		for (int i = 0; i < 8; i++)
		{
			// Draw vertical lines
			g2d.drawLine(i * 60 + 5, 5, i * 60 + 5, 365);
		}
		for (int r = 0; r < 6; r++)
		{
			for (int c = 0; c < 7; c++)
			{
				if (board[r][c] == null)
				{
					continue;
				}
				else if (board[r][c].getColor() == true)
				{
					// if the value of the piece is true, make a red circle
					g2d.setColor(Color.red);
					g2d.fill(new Ellipse2D.Double(c * 60 + 10, r * 60 + 10, 50, 50));
				}
				else if (board[r][c].getColor() == false)
				{
					// if the value of the piece is false, make a black circle
					g2d.setColor(Color.black);
					g2d.fill(new Ellipse2D.Double(c * 60 + 10, r * 60 + 10, 50, 50));
				}
			}
		}
	}
}
