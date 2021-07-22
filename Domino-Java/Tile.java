/**
 * This class represents a Tile of domino that have right and left side with values between 0 to 6 including.
 */
public class Tile {
	/**
	 * Stands for the right side of the tile.
	 */
	private int rightNumber;
	/**
	 * Stands for the left side of the tile.
	 */
	private int leftNumber;
	/**
	 * Constructor that creates a new Tile with left number and right number.
	 * @param leftNumber number between 0 and 6 that will represent the left side of the tile.
	 * @param rightNumber number between 0 and 6 that will represent the right side of the tile.
	 */
	public Tile(int leftNumber, int rightNumber) {
		this.leftNumber = leftNumber;
		this.rightNumber = rightNumber;
	}
	/**
	 * Default constructor that creates a new Tile and set the Tile value to be invalid, this constructor is created in order to prevent situation of default constructor of Tile and then he will get the default values of int which is '0' and then the Tile <0,0> will be created.
	 */
	public Tile()
	{
		this.rightNumber = -1;
		this.leftNumber = -1;
	}
	/**
	 * Copy constructor that creates a new Tile with the same values of the other Tile.
	 * @param other Tile that we want to copy from. 
	 */
	public Tile(Tile other) {
		if(other!=null)
		{
			this.leftNumber = other.leftNumber;
			this.rightNumber = other.rightNumber;
		}
	}
	/**
	 * Getter for the left side of the tile
	 * @return int leftNumber the left side of the tile
	 */
	public int getLeftNumber() {
		return leftNumber;
	}
	/**
	 * Getter for the right side of the tile
	 * @return int rightNumber the right side of the tile
	 */
	public int getRightNumber() {
		return rightNumber;
	}
	/**
	 * Function the switches between the right and the left side of the Tile.
	 * For example - the tile was <3,6> after this function it will <6,3>.
	 */
	public void flipTile() {
		int help = this.rightNumber;
		this.rightNumber = this.leftNumber;
		this.leftNumber = help;
	}
	/**
	 * 	return new string by the following template "<"left Number","right Number">"
	 */
	@Override 
	public String toString() {
		return ("<"+this.leftNumber+","+this.rightNumber+">");
	}
	/**
	 * checks if the Tiles are equal - means if they have the exact same values even in a different arrangement.
	 * for example <3,6> and <6,3> are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj != null)
		{
			if(obj instanceof Tile)
			{
				if((this.leftNumber == ((Tile)obj).leftNumber && this.rightNumber == ((Tile)obj).rightNumber)||(this.leftNumber == ((Tile)obj).rightNumber && this.rightNumber == ((Tile)obj).leftNumber))
				{
					return true;
				}
			}
		}
		return false;
	}

}
