/**
 * This class represnts a Team of players in Domino that have a name and an array of players.
 * @author Itai Dagan 
 */
public class Team {
	private Player [] players;
	private String name;
	/**
	 * Constructor for the new team that get a team name and array of players for the team.
	 * @param name The team name
	 * @param players Array of Player
	 */
	public Team(String name, Player[] players) {
		this.name = new String(name);
		this.players = new Player[players.length];
		//deep copy the players of the team
		for(int i=0;i<this.players.length;i++)
		{
			if(players[i]!=null)
			{
				this.players[i] = new Player(players[i]);
			}
		}
	}
	/**
	 * Copy constructor that creates an exact deep copy of the given team to copy from.
	 * @param other The desired team to be.
	 */
	public Team(Team other)
	{
		if(other!= null)
		{
			this.name = other.name;
			if(other.players!=null)
			{
				this.players = new Player[other.players.length];
				//deep copy the players of the team
				for(int i=0; i<this.players.length; i++)
				{
					if(other.players[i]!=null)
					{
						this.players[i] = new Player(other.players[i]);
					}
				}
			}
		}
	}
	/**
	 * Getter for the Players of the team. If there are no players it returns null.
	 * @return that returns an array of the players.
	 */
	public Player[] getPlayers(){
		int numOfPlayers = this.getNumberOfPlayers();
		//if there are no players.
		if(numOfPlayers == 0)
		{
			return null;
		}
		else
		{
			//deep copy the players of the team and put in the new array only those ones that are not null.
			Player[] copyArray = new Player[numOfPlayers];
			int counter = 0;
			for(int i=0;i<this.players.length;i++)
			{
				if(this.players[i]!=null)
				{
					copyArray[counter] = new Player(this.players[i]);
					counter++;
				}
			}
			//return the copied array
			return copyArray;
		}
	}
	/**
	 * Getter for the team name.
	 * @return The team name.
	 */
	public String getName(){
		String newString = new String(this.name);
		return newString;
	}
	/**
	 * This function is checking if one of the team players can make a move with the given board, if does the player is doing the move and exit from the function. Which mean only player can play at a time.
	 * @param board The given board to play with.
	 * @return  True if managed to play any move on the board, false if not or if the board is null.
	 */
	public boolean playMove(Board board){
		if(board!=null)
		{
			for(int i=0;i<this.players.length;i++)
			{
				//going through all the players of the team to see if someone can make a move.
				if(this.players[i] != null && this.players[i].playMove(board))
				{
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * This function sums all the values of the players tiles.
	 * @return sum of the tiles values of all the players.
	 */
	public int countTiles(){
		int sum = 0;
		for (int i=0; i<this.players.length; i++)
		{
			if(this.players[i]!=null)
			{
				sum = sum + this.players[i].countTiles(); 
			}
		}
		return sum;
	}
	/**
	 * This function checks if any of the players have any more tiles.
	 * @return true if there is at least 1 more player with tiles, false if not.
	 */
	public boolean hasMoreTiles(){
		for (int i=0; i<this.players.length; i++)
		{
			if(this.players[i]!=null)
			{
				if(this.players[i].hasMoreTiles())
				{
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * This function checks how many players there are in the team.
	 * @return the counter of the team players.
	 */
	public int getNumberOfPlayers(){
		int counter = 0;
		for(int i=0;i<this.players.length; i++)
		{
			if(this.players[i] != null)
			{
				//counts only those that are not null
				counter++;
			}
		}
		return counter;
	}
	/**
	 * This function is trying to assign tiles to all of the team players, if all of them can get the given tiles - which means there are in the given tiles[][] array at least as many arrays as the team players and the input is not null.
	 * @param allHands Two dimension array that contain in each index an array of tiles to assign for the player.
	 * @return true if managed to do the implementation to all of the players, false if not or if the value is invalid.
	 */
	public boolean assignTilesToPlayers(Tile[][] allHands){
		if(allHands == null || this.getNumberOfPlayers() == 0 ||allHands.length != this.getNumberOfPlayers())
		{
			return false;
		}
		Player test = new Player("test");
		//check if is it possible to assign to a tester player and if does it will do it on the real players of the team
		for(int i=0;i<this.getNumberOfPlayers();i++)
		{
			if(!test.assignTiles(allHands[i]))
			{
				//if can't put it to the tester player it will return false automatically
				return false;
			}
		}
		//if it passed the test player it will call the assign tiles function of player.
		for(int i=0;i<this.players.length;i++)
		{
			if(this.players[i]!=null)
			{
				this.players[i].assignTiles(allHands[i]);
			}
		}
		return true;
	}
	/**
	 * return new string by the following template "'Team Name' {["Player1"],["Player2"],...,["LastPlayer"]}"
	 */
	@Override
	public String toString() {
		String str =  "Team: " + this.name + " {";
		boolean firstTime = true;
		for(int i=0; i<this.players.length; i++)
		{
			if(this.players[i] != null && firstTime)
			{
				str = str + this.players[i].toString();
				firstTime = false;
			}
			else if(this.players[i] != null)
			{
				str = str + "," + this.players[i].toString();
			}
		}
		if(firstTime)
		{
			str = str + "No players in the team";
		}
		str = str + "}";
		return str;
	}
	/**
	 * Compare between the teams and check if they have the exact players in the exact order.
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj != null)
		{
			if(obj instanceof Team)
			{
				Team other = (Team)obj;
				if(!this.name.equals(other.name))
				{
					return false;
				}
				if(this.getNumberOfPlayers()!=other.getNumberOfPlayers())
				{
					return false;
				}
				if(this.players!=null && other.players==null)
					return false;
				if(this.players==null && other.players!=null)
					return false;
				if(this.players==null && other.players==null)
					return true;
				for(int i= 0; i<this.players.length;i++)
				{
					if(this.players[i]==null&&other.players[i]==null)
					{
						//this situation is OK because both of them are null so it will just continue to next index
					}
					else if(this.players[i]==null||other.players[i]==null)
					{
						return false;
					}
					else if(!this.players[i].equals(other.players[i]))
					{
						//calling player equals method
						return false;
					}

				}
				return true;
			}
		}
		return false;
	}
}
