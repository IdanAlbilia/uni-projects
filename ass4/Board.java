/**
 * This class represnts a Game board for domino that contains an array of Tiles and the maximum size for the array.
 * @author Itai Dagan
 */
public class Board {
	/**
	 * Tiles array that represents the playing board.
	 */
	private Tile [] tilesBoard;
	/**
	 * The maximum number of tiles in the board.
	 */
	private int MaxNumOfTiles;
	/**
	 * Stands for how many tiles there are in the board now.
	 */
	private int size;
	/**
	 * Constructor that creates a new play Board with the exact length that requested.
	 * @param numOfTiles a number between 1 and 28 including that will represent the length of the board.
	 */
	public Board(int numOfTiles) {
		if(numOfTiles <= 28 && numOfTiles>0)
		{
			this.tilesBoard = new Tile[numOfTiles];
			this.MaxNumOfTiles = numOfTiles;
		}
		else
		{
			//default will be 28.
			this.tilesBoard = new Tile[28];
			this.MaxNumOfTiles = 28;
		}
	}
	/**
	 * Getter for the most right value that exist in the board, if there are no tiles in the board it will return -1.
	 * @return the most right value in the board.
	 */
	public int getRightValue() {
		if(size == 0)
		{
			return -1;
		}
		return this.tilesBoard[size-1].getRightNumber();
	}
	/**
	 * Getter for the most left value that exist in the board, if there are no tiles in the board it will return -1.
	 * @return the most left value in the board.
	 */
	public int getLeftValue() {
		if(size == 0)
		{
			return -1;
		}
		return this.tilesBoard[0].getLeftNumber();
	}
	/**
	 * Getter for the Tiles array of the Board. if there are no tiles it returns null.
	 * @return Tile[] array with the most left in the beginning and the most right in the end.
	 */
	public Tile[] getBoard() {
		if(size == 0)
		{
			return null;
		}
		else
		{
			//Creating a new array of Tiles in the size of the exact number of Tiles in the current board and deep copy the tiles to this array, so the user won't be able to have the real fields.
			Tile [] copyBoard = new Tile[size];
			int counter = 0;
			for (int i= 0; i < this.tilesBoard.length; i++)
			{
				if(this.tilesBoard[i]!=null)
				{
					copyBoard[counter] = new Tile(this.tilesBoard[i]);
					counter++; 
				}
			}
			if(counter == 0)
			{
				return null;
			}
			return copyBoard;
		}
	}
	/**
	 * This function receives a Tile and trying to add it to the board, if there is still space to add and if the there is one value in the Tile that matches the most right value of the board.
	 * @param tile The requested Tile to add to the board.
	 * @return true if managed to add the tile to the board, false if not.
	 */
	public boolean addToRightEnd (Tile tile) {
		if(tile !=null && size<MaxNumOfTiles)
		{
			// create a copy of the given tile.
			Tile copyTile = new Tile(tile);
			if(size == 0)
			{
				//if there are no tiles.
				this.tilesBoard[size] = new Tile(copyTile);
				this.size++;
				return true;
			}
			for(int i = 0;i<this.tilesBoard.length;i++)
			{
				if(this.tilesBoard[i]!=null)
				{
					if(copyTile.equals(tilesBoard[i]))
					{
						return false;
					}
				}
			}
			if(this.tilesBoard[size-1].getRightNumber() == copyTile.getLeftNumber())
			{
				//if it matches the right end
				this.tilesBoard[size] = new Tile(copyTile);
				this.size++;
				return true;
			}
			copyTile.flipTile();
			if(this.tilesBoard[size-1].getRightNumber() == copyTile.getLeftNumber())
			{
				//if the flip of the tile matches the right end
				this.tilesBoard[size] = new Tile(copyTile);
				this.size++;
				return true;
			}
		}
		return false;
	}
	/**
	 * This function receives a Tile and trying to add it to the board, if there is still space to add and if the there is one value in the Tile that matches the most left value of the board.
	 * @param tile The requested Tile to add to the board.
	 * @return true if managed to add the tile to the board, false if not.
	 */
	public boolean addToLeftEnd (Tile tile) {
		if(tile !=null && this.size<this.MaxNumOfTiles)
		{
			// create a copy of the given tile.
			Tile copyTile = new Tile(tile);
			if(size == 0)
			{
				//if there are no tiles.
				this.tilesBoard[size] = new Tile(copyTile);
				this.size++;
				return true;
			}
			if(this.tilesBoard[0].getLeftNumber() == copyTile.getRightNumber())
			{
				//if it matches the left end
				Tile[] newBoard = new Tile[MaxNumOfTiles];
				//create a new board and moves all the tiles one index to the right in order to add the tile to the most left tile of the board.
				for(int i=size-1; i>=0;i--)
				{
					newBoard[i+1] = new Tile(this.tilesBoard[i]);
				}
				newBoard[0] = new Tile(copyTile);
				this.size++;
				this.tilesBoard = newBoard;
				return true;
			}
			copyTile.flipTile();
			if(this.tilesBoard[0].getLeftNumber() == copyTile.getRightNumber())
			{
				//if the flip matches the left end
				Tile[] newBoard = new Tile[MaxNumOfTiles];
				//create a new board and moves all the tiles one index to the right in order to add the tile to the most left tile of the board.
				for(int i=size-1; i>=0;i--)
				{
					newBoard[i+1] = new Tile(this.tilesBoard[i]);
				}
				newBoard[0] = new Tile(copyTile);
				this.size++;
				this.tilesBoard = newBoard;
				return true;
			}
		}
		return false;
	}
	/**
	 * 	return new string by the following template "<"Tile1">,<"Tile2">,...,<"LastTile">"
	 */
	@Override
	public String toString() {
		String str = "";
		for(int i= 0; i< size; i++)
		{
			if(i == size-1)
			{
				str = str + tilesBoard[i].toString();
			}
			else
			{
				str = str + tilesBoard[i].toString() +",";
			}
		}
		return str;
	}
	/**
	 * checks if the Boards are equal - means if they have the exact same Tiles in the same order.
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj != null)
		{
			if(obj instanceof Board)
			{
				//compare between the strings of the boards because we want them to be exactly the same order and arrangement of the tile itself.
				if(this.toString().equals(((Board)obj).toString()))
				{
					return true;
				}
			}
		}
		return false;

	}
}
