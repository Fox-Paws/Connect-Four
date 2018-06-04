package connectFour;

import java.io.Serializable;

public class Piece implements Serializable
{
	// data members
	private boolean color; // 0:false: black, 1:true: white

	// default constructor
	public Piece()
	{
		super();
	}

	// parameterized constructor
	public Piece(boolean color)
	{
		super();
		this.color = color;
	}

	// getter
	public boolean getColor()
	{
		return color;
	}

	// setter
	public void setColor(boolean color)
	{
		this.color = color;
	}

	public boolean equals(Piece stone)
	{
		if (this.color == stone.getColor())
		{
			return true;
		}
		return false;
	}

	@Override
	public String toString()
	{
		return "stone [color=" + color + "]";
	}

} // end class
