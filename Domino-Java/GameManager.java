/**
 * This class represents a Game Manager that have 2 teams and he manages the whole game, deal Tiles to players and plays.
 * @author Itai Dagan
 */
public class GameManager {
	/**
	 * The first Team
	 */
	private Team team1;
	/**
	 * The second Team
	 */
	private Team team2;
	/**
	 * Constructor that creates a new GameManager with 2 given teams by deep copy of the teams.
	 * @param team1 The first Team.
	 * @param team2 The second Team.
	 */
	public GameManager(Team team1, Team team2){
		//deep copy the teams by copy constructor of team
		this.team1 = new Team(team1);
		this.team2 = new Team(team2);
	}
	/**
	 * Constructor that creates a new GameManager with 2 given teams by deep copy of the teams and also deal tiles to all of the team players by the given value tiles Per Player
	 * @param team1 The first Team.
	 * @param team2 The second Team.
	 * @param tilesPerPlayer How many tiles each player should get.
	 */
	public GameManager(Team team1, Team team2, int tilesPerPlayer) {
		//deep copy the teams by copy constructor of team
		this.team1 = new Team(team1);
		this.team2 = new Team(team2);
		//calling the private function deal tiles in order to give tiles to each player
		dealTiles(tilesPerPlayer);
	}
	/**
	 * This function is managing the Domino game, it creates a board and let each team in her turn to play starting with the first team.
	 * The play goes on until one of the player have no more tiles or both of the players passed their turn one after each other.
	 * @return String that shows all of the game moves and in the end the final score of the game and which team has won or if there was a draw.
	 */
	public String play() {
		String str = "";
		Board board = new Board(28);//the Current game board
		boolean team1Turn = true;
		boolean team1Passed = false;
		boolean team2Passed = false;
		while(this.team1.hasMoreTiles()&&this.team2.hasMoreTiles())
		{
			//in this while only one team can play in each loop of the while.
			boolean notPlayed = true;// this boolean stands for if a move has been made in this loop.
			if(team1Turn&& this.team1.playMove(board))
			{
				//team1 played
				str = str + this.team1.getName() + ", success: " + board + "\n";
				team1Turn = false;
				notPlayed = false;
				team1Passed = false;
			}
			else if(team1Turn)
			{
				//team1 passed
				str = str + this.team1.getName() + ", pass: " + board + "\n";
				team1Turn = false;
				team1Passed = true;
			}
			if(notPlayed&&this.team2.playMove(board))
			{
				//team2 played
				str = str + this.team2.getName() + ", success: " + board + "\n";
				team1Turn = true;
				notPlayed = false;
				team2Passed = false;
			}
			else if (notPlayed)
			{
				//team2 passed
				str = str + this.team2.getName() + ", pass: " + board + "\n";
				team2Passed = true;
				team1Turn = true;
			}
			if(team2Passed && team1Passed)
			{
				//if both team passed
				break;
			}
		}
		int score1 = this.team1.countTiles();
		int score2 = this.team2.countTiles();
		str = str + this.team1.getName() + ", score: " + score1 + "\n" +this.team2.getName() + ", score: " + score2 + "\n";
		if(score1==0&&score2==0)
		{
			//if both of them have 0 there is draw even if one team doesnt have tiles and the other have <0,0> tile.
			str = str + "Draw! – the house wins\n";
		}
		else if(score1 < score2 || !this.team1.hasMoreTiles())
		{
			str = str +this.team1.getName()+" wins\n";
		}
		else if(score2 < score1 || !this.team2.hasMoreTiles())
		{
			str = str + this.team2.getName()+" wins\n";
		}
		else
		{
			str = str + "Draw! – the house wins\n";
		}
		//deep copy of the teams after the game.
		this.team1 = new Team(team1);
		this.team2 = new Team(team2);
		return str;
	}
	/**
	 * return a new string in the following template : "First team" | "Second team".
	 */
	@Override
	public String toString() {
		String str = "";
		str = str + this.team1.toString() + " | ";
		str = str + this.team2.toString();
		return str;
	}
	/**
	 * Compare between the 2 teams if they are the same team even in a different order.
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj != null)
		{
			if(obj instanceof GameManager)
			{
				GameManager other = (GameManager)obj;
				//Compare between the 2 teams if they are the same team even in a different order.
				if((this.team1.equals(other.team1)&&this.team2.equals(other.team2))||(this.team2.equals(other.team1)&&this.team1.equals(other.team2)))
				{
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Private function that deals to the teams players Tiles from the 28 Tiles of domino.
	 * @param numberOfTiles Indicates how many tiles each player will receive.
	 */
	private void dealTiles(int numberOfTiles) {
		Tile [] array = new Tile[28];//all the possible domino tiles.
		int counter = 0;
		for(int i=0; i<=6;i++)
		{
			for(int j=i; j<=6;j++)
			{
				//creates the domino tiles.
				array[counter] = new Tile(i,j);
				counter++;
			}
		}
		Tile [][] tilesTeam1 =  new Tile[this.team1.getNumberOfPlayers()][];//team1 tiles [][]array
		for(int i=0; i<this.team1.getNumberOfPlayers(); i++)
		{
			Tile [] tile = new Tile[numberOfTiles];
			for(int j=0; j<numberOfTiles; j++)
			{
				boolean found = false;
				while(!found)
				{
					//Looking for a tile that hasn't been used yet
					int location = (int)(Math.random() * 28);
					if(array[location]!= null)
					{
						tile[j]=array[location];
						array[location] = null;
						found = true;
					}
				}
			}
			tilesTeam1[i] = tile;
		}
		//assign the tiles array to the team
		this.team1.assignTilesToPlayers(tilesTeam1);
		Tile [][] tilesTeam2 =  new Tile[this.team2.getNumberOfPlayers()][];//team2 tiles [][]array
		for(int i=0; i<this.team2.getNumberOfPlayers(); i++)
		{
			Tile [] tile = new Tile[numberOfTiles];
			for(int j=0; j<numberOfTiles; j++)
			{
				boolean found = false;
				while(!found)
				{
					//Looking for a tile that hasn't been used yet
					int location = (int)(Math.random() * 28);
					if(array[location]!= null)
					{
						tile[j]=array[location];
						array[location] = null;
						found = true;
					}
				}
			}
			tilesTeam2[i] = tile;
		}
		//assign the tiles array to the team
		this.team2.assignTilesToPlayers(tilesTeam2);
	}
}
