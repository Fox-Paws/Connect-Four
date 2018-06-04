package connectFour;

import java.io.Serializable;
import java.util.Arrays;

public class GameData implements Serializable
{
	// data members
	private boolean		whoseTurn;		// false = black, true = red
	private Piece[][]	board;			// the 15x15 grid (2D array) that stores stone objects
	private int[]		columns;
	private boolean		won		= false;
	private boolean		playing	= false;

	// default constructor
	public GameData()
	{
		super();
		board = new Piece[6][7];
		columns = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		whoseTurn = false;
	}

	// parameterized constructor
	public GameData(Piece[][] board, boolean whoseTurn)
	{
		super();
		this.whoseTurn = whoseTurn;
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
				columns[c]++;
			}
		}
	}

	public Piece[][] getBoard()
	{
		return board;
	}

	public int[] getColumns()
	{
		// gets the number of pieces in each column
		return columns;
	}

	public void setBoard(Piece[][] board)
	{
		this.board = board;
	}

	public boolean isWhoseTurn()
	{
		return whoseTurn;
	}

	public void setWhoseTurn(boolean whoseTurn)
	{
		this.whoseTurn = whoseTurn;
	}

	public boolean isPlaying()
	{
		return playing;
	}

	public void setPlaying(boolean val)
	{
		playing = val;
	}

	public boolean checkMove(int move)
	{ // returns 1 (true) if column is full
		// returns 0 (false) if column has room
		if (columns[move] < 6)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public void addPiece(int move, boolean color)
	{
		// the board goes from top to bottom, but columns goes from bottom to top
		// so 5 - columns is necessary to put it in the correct location
		this.board[5 - columns[move]][move] = new Piece(color);
		columns[move]++;
	}

	public boolean checkWin(int move)
	{ // returns a 1 (true) if a move creates a winning condition
		// returns a 0 (false) if a move does not create a winning condition
		if (checkHorizontal(move) || checkVertical(move) || checkDiagonal(move))
		{
			return true;
		}
		return false;
	}

	public boolean checkWon()
	{
		return won;
	}

	public void resignWin()
	{
		won = true;
	}

	/*
	 * Win checking is performed by having directions from the piece that was
	 * placed. So left is to the left, and right is to the right.
	 * 
	 * If there is an interruption in one direction (such as an empty space or
	 * opposite color piece) that direction cannot contribute to lineCount because a
	 * win is when you connect four pieces together So when !left or !right, that
	 * direction is skipped since it cannot contribute.
	 */

	private boolean checkHorizontal(int move)
	{
		Piece moveColor = board[6 - columns[move]][move];
		int i = move;
		int idx = 1;
		int lineCount = 1;
		boolean left, right;
		left = right = true;

		for (; idx < 4; idx++)
		{
			if (!left || (i - idx) < 0 || board[6 - columns[move]][i - idx] == null)
			{
				left = false;
			}
			else
			{
				if (moveColor.equals(board[6 - columns[move]][i - idx]))
				{
					lineCount++;
				}
				else
				{
					left = false;
				}
			}
			if (!right || (i + idx) > 6 || board[6 - columns[move]][i + idx] == null)
			{
				right = false;
			}
			else
			{
				if (moveColor.equals(board[6 - columns[move]][i + idx]))
				{
					lineCount++;
				}
				else
				{
					right = false;
				}
			}
			if (lineCount > 3)
			{
				won = true;
				return true;
			}
			else if (!left && !right)
			{
				return false;
			}
		}
		return false;
	}

	private boolean checkVertical(int move)
	{
		Piece moveColor = board[6 - columns[move]][move];
		int i = 6 - columns[move];
		int idx = 1;
		int lineCount = 1;
		boolean up, down;
		up = down = true;

		for (; idx < 4; idx++)
		{
			if (!up || (i - idx) < 0 || board[i - idx][move] == null)
			{
				up = false;
			}
			else
			{
				if (moveColor.equals(board[i - idx][move]))
				{
					lineCount++;
				}
				else
				{
					up = false;
				}
			}
			if (!down || (i + idx) > 5 || board[i + idx][move] == null)
			{
				down = false;
			}
			else
			{
				if (moveColor.equals(board[i + idx][move]))
				{
					lineCount++;
				}
				else
				{
					down = false;
				}
			}
			if (lineCount > 3)
			{
				won = true;
				return true;
			}
			else if (!up && !down)
			{
				return false;
			}
		}
		return false;
	}

	private boolean checkDiagonal(int move)
	{
		Piece moveColor = board[6 - columns[move]][move];
		int r = 6 - columns[move];
		int c = move;
		int idx = 1;
		int lineCount = 1;
		boolean topL, topR, bottomL, bottomR;
		topL = topR = bottomL = bottomR = true;

		for (; idx < 4; idx++)
		{
			if (!topL || (r - idx) < 0 || (c - idx) < 0 || board[r - idx][c - idx] == null)
			{
				topL = false;
			}
			else
			{
				if (moveColor.equals(board[r - idx][c - idx]))
				{
					lineCount++;
				}
				else
				{
					topL = false;
				}
			}
			if (!bottomR || (r + idx) > 5 || (c + idx) > 6 || board[r + idx][c + idx] == null)
			{
				bottomR = false;
			}
			else
			{
				if (moveColor.equals(board[r + idx][c + idx]))
				{
					lineCount++;
				}
				else
				{
					bottomR = false;
				}
			}
			if (lineCount > 3)
			{
				won = true;
				return true;
			}
			else if (!topL && !bottomR)
			{
				break;
			}
		}

		lineCount = 1;
		idx = 1;

		for (; idx < 5; idx++)
		{
			if (!topR || (r - idx) < 0 || (c + idx) > 6 || board[r - idx][c + idx] == null)
			{
				topR = false;
			}
			else
			{
				if (moveColor.equals(board[r - idx][c + idx]))
				{
					lineCount++;
				}
				else
				{
					topR = false;
				}
			}
			if (!bottomL || (r + idx) > 5 || (c - idx) < 0 || board[r + idx][c - idx] == null)
			{
				bottomL = false;
			}
			else
			{
				if (moveColor.equals(board[r + idx][c - idx]))
				{
					lineCount++;
				}
				else
				{
					bottomL = false;
				}
			}
			if (lineCount > 3)
			{
				won = true;
				return true;
			}
			else if (!topR && !bottomL)
			{
				return false;
			}
		}
		return false;
	}

	@Override
	public String toString()
	{
		return "GameData [board=" + Arrays.toString(board) + "]";
	}

} // end class
