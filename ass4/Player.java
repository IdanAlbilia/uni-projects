/**
 * This class represnts a Player that have a name and an array of tiles that he have.
 * @author Itai Dagan
 */
public class Player {
	private String name;
	private Tile [] tiles;
	/**
	 * Constructor that creates a new Player with name and array of tiles. 
	 * @param name The requested name for the new Player
	 * @param tiles Array of tiles for the new Player
	 */
	public Player(String name, Tile[] tiles) {
		this.name = name;
		this.tiles = new Tile[tiles.length];
		//deep copy the tiles array
		for(int i=0; i<tiles.length; i++)
		{
			if(tiles[i]!=null)
			{
				this.tiles[i] = new Tile(tiles[i]);
			}
		}
	}
	/**
	 * Constructor that creates a new Player only with the name of the Player and sets his Tiles array to size 0.
	 * @param name The requested name for the new Player
	 */
	public Player(String name) {
		this.name = name;
		this.tiles = new Tile[0]; // Initialize the tiles array to empty array.
	}
	/**
	 * Copy constructor that creates an exact deep copy of a Player from the given Player.
	 * @param other Player that we want to copy from.
	 */
	public Player(Player other) {
		if(other!= null)
		{
			this.name = other.name;
			if(other.tiles!=null)
			{
				//deep copy the tiles array
				this.tiles = new Tile[other.tiles.length];
				for(int i=0; i<other.tiles.length; i++)
				{
					if(other.tiles[i]!=null)
					{
						this.tiles[i] = new Tile(other.tiles[i]);
					}
				}
			}
			else
			{
				//if the player have name but doesn't have tiles so it will initialized to 0.
				this.tiles = new Tile[0];
			}
		}
	}
	/**
	 * This function tries to implement the give Tile array to the player, it checks if the array is not null and if the values are valid, if does it does a deep copy of the tiles array.
	 * @param tiles array of Tiles.
	 * @return true if managed to do the implementation, false if not.
	 */
	public boolean assignTiles(Tile[] tiles) {
		if(tiles == null || tiles.length >28)
		{
			return false;
		}
		for(int i=0; i<tiles.length; i++)
		{
			//going through the tiles array and checking if the array values are valid. 
			if(tiles[i] == null || tiles[i].getLeftNumber() > 6 || tiles[i].getLeftNumber() < 0 || tiles[i].getRightNumber() > 6 || tiles[i].getRightNumber() < 0)
			{
				//if there is any invalid tile it will return false.
				return false;
			}
		}
		//checks if there are any duplicated tiles in the array.
		for(int i=0; i<tiles.length-1; i++)
		{
			for(int j=i+1; j<tiles.length; j++)
			{
				if(tiles[i].equals(tiles[j]))
				{
					//if there is any duplicated tile it will return false.
					return false;
				}
			}
		}
		//deep copy the given array to the class array of tiles.
		this.tiles = new Tile[tiles.length];
		for(int i =0; i< tiles.length;i++)
		{
			if(tiles[i]!=null)
			{
				this.tiles[i] = new Tile(tiles[i]);
			}
		}
		return true;
	}
	/**
	 * This function is checking if the player have any tile that can match to the left side or the right side of the Board. if does it adds it to the board and remove it from the player tiles array.
	 * @param board The playing board.
	 * @return True if managed to play any move on the board, false if not or if the board is null.
	 */
	public boolean playMove(Board board) {
		if(board!=null)
		{
			for (int i=0; i<this.tiles.length; i++)
			{
				//going through the tiles in the array that are not null and checking if it is possible to add it to the left or right end of the board
				if(this.tiles[i] != null)
				{
					//trying to add the tile first to the left side and if it is possible it will put null in the tiles array
					if(board.addToLeftEnd(new Tile(tiles[i])))
					{
						this.tiles[i] = null;
						return true;
					}
					//trying to add the tile to the right side and if it is possible it will put null in the tiles array
					if(board.addToRightEnd(new Tile(tiles[i])))
					{
						this.tiles[i] = null;
						return true;
					}
				}
			}
		}
		//if coudlnt add it it will return false
		return false;
	}
	/**
	 * This function sums all the values of the player tiles.
	 * @return sum of the tiles values.
	 */
	public int countTiles() {
		int sum = 0;
		for (int i=0; i<this.tiles.length; i++)
		{
			if(this.tiles[i] != null)
			{
				//sums all the values of the tiles that are not null
				sum = sum + this.tiles[i].getLeftNumber() + this.tiles[i].getRightNumber();
			}
		}
		return sum;
	}
	/**
	 * This function checks if the player have any more tiles.
	 * @return true if there is at least 1 more tile, false if not.
	 */
	public boolean hasMoreTiles() {
		for (int i=0; i<this.tiles.length; i++)
		{
			//checking if there is at least 1 tile that is not null
			if(this.tiles[i] != null)
			{
				return true;
			}
		}
		return false;
	}
	/**
	 * return new string by the following template "'Player Name' :[ <"Tile1">,<"Tile2">,...,<"LastTile"> ]"
	 */
	@Override
	public String toString() {
		String str = name + ":[";
		boolean firstTime = true;
		if(this.tiles!=null)
		{
			for(int i=0; i<this.tiles.length; i++)
			{
				//add to the String the tiles that are not null
				if(this.tiles[i] != null && firstTime)
				{
					str = str + this.tiles[i].toString();
					firstTime = false;
				}
				else if(this.tiles[i] != null)
				{
					str = str + "," + this.tiles[i].toString();
				}
			}
		}
		str = str + "]";
		return str;
	}
	/**
	 * Compare between the the Players and if both of them have the exact name and the same tiles even in a different order return true, else return false.
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj != null)
		{
			if(obj instanceof Player)
			{
				Player other = (Player)obj;
				//comparing between the players names
				if(!this.name.equals(other.name))
				{
					return false;
				}
				//comparing between the players tiles array length
				if(this.tiles.length!=other.tiles.length)
				{
					return false;
				}
				//checking if all of the tiles of the 1 player exist in the array of tiles of the second player.
				for(int i= 0; i<this.tiles.length;i++)
				{
					if(this.tiles[i] != null)
					{
						boolean exist = false;
						for(int j=0; j<other.tiles.length&&!exist;j++)
						{
							if(this.tiles[i] != null && other.tiles[j] != null && this.tiles[i].equals(other.tiles[j]))
							{
								exist = true;
							}
						}
						if(!exist)
						{
							return false;
						}
					}
				}
				//checking if all of the tiles of the 2 player exist in the array of tiles of the first player.
				for(int i= 0; i<other.tiles.length;i++)
				{
					if(other.tiles[i] != null)
					{
						boolean exist = false;
						for(int j=0; j<this.tiles.length&&!exist;j++)
						{
							if(other.tiles[i] != null && this.tiles[j] != null && other.tiles[i].equals(this.tiles[j]))
							{
								exist = true;
							}
						}
						if(!exist)
						{
							return false;
						}
					}
				}
				return true;
			}
		}
		return false;
	}
}
