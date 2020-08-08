// Author: Itai Dagan
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class Sudoku {
	// **************   Sudoku - Part1 (iterative)   **************
	public static void main(String[] args) {

		/*int[][] board ={
				{0,6,0,1,0,4,0,5,0},
				{0,0,8,3,0,5,6,0,0},
				{2,0,0,6,0,9,0,0,1},
				{8,0,0,4,3,7,0,0,6},
				{0,0,6,8,5,2,3,0,0},
				{7,0,0,9,6,1,0,0,4},
				{5,0,0,7,0,3,0,0,2},
				{0,0,7,2,0,6,9,0,0},
				{6,4,2,5,9,8,1,7,3}};
		int[][] board2 ={
				{6,3,4,6,0,8,9,1,2},
				{6,0,2,1,9,5,3,4,8},
				{1,9,0,3,4,2,5,0,7},
				{8,5,9,0,6,1,4,2,3},
				{4,2,6,8,0,3,7,9,1},
				{7,1,3,0,2,0,8,5,6},
				{9,6,1,5,3,7,0,8,4},
				{2,8,7,4,1,9,6,0,5},
				{3,0,5,2,8,6,1,7,0}};
		int[][] board4 ={
				{5,3,4,6,7,8,9,1,2},
				{6,7,2,1,9,5,3,4,8},
				{1,9,8,3,4,2,5,6,7},
				{8,5,9,7,6,1,4,2,3},
				{4,2,6,8,5,3,7,9,1},
				{7,1,3,9,2,4,8,5,6},
				{9,6,1,5,3,7,2,8,4},
				{2,9,7,4,1,9,6,3,5},
				{3,4,5,2,8,6,1,7,4}};
		int [][] board5 = {
				{3, 0, 6, 5, 0, 8, 4, 0, 0},
				{5, 2, 0, 0, 0, 0, 0, 0, 0},
				{0, 8, 7, 0, 0, 0, 0, 3, 1},
				{0, 0, 3, 0, 1, 0, 0, 8, 0},
				{9, 0, 0, 8, 6, 3, 0, 0, 5},
				{0, 5, 0, 0, 9, 0, 6, 0, 0},
				{1, 3, 0, 0, 0, 0, 2, 5, 0},
				{0, 0, 0, 0, 0, 0, 0, 7, 4},
				{0, 0, 5, 2, 0, 6, 3, 0, 0}
		};
		int [][] board6 = {
				{ 0, 6, 0,  0, 0, 1,  0, 9, 4 },
				{ 3, 0, 0,  0, 0, 7,  1, 0, 0 },
				{ 0, 0, 4,  0, 9, 0,  0, 0, 0 },
				{ 7, 0, 6,  5, 0, 0,  2, 0, 9 },
				{ 0, 3, 0,  0, 2, 0,  0, 6, 0 },
				{ 9, 0, 2,  0, 0, 6,  3, 0, 1 },
				{ 0, 0, 0,  0, 5, 0,  0, 0, 0 },
				{ 0, 0, 7,  3, 0, 0,  0, 0, 2 },
				{ 4, 1, 0,  7, 0, 0,  0, 8, 0 },
		};
		int [][] board7 = {
				{ 5,6,8,2,3,1,7,9,4 },
				{ 3,9,4,6,8,7,1,2,6 },
				{ 2,7,1,4,9,5,5,3,8 },
				{ 7,8,6,5,1,3,2,4,9 },
				{ 1,3,5,9,2,4,8,6,7 },
				{ 9,4,2,8,7,6,3,5,1 },
				{ 6,2,9,1,5,8,4,7,3 },
				{ 8,5,7,3,4,9,6,1,2 },
				{ 4,1,3,7,6,2,9,8,5 },
		};
		int[][] board8 = {{2,0,0,0,0,0,0,0,0},
				{4,0,9,0,0,3,0,8,5},
				{3,0,1,0,2,0,0,0,0},
				{1,0,0,5,0,7,0,0,0},
				{6,0,4,0,0,0,1,0,0},
				{7,9,0,0,0,0,0,0,0},
				{5,4,6,0,0,0,0,7,3},
				{9,3,2,0,1,0,0,0,0},
				{8,1,7,3,4,0,0,0,9}};
		int [][] board9 = {	{9,7,0,0,0,0,0,4,3},
				{0,0,6,0,0,0,8,0,0},
				{0,0,0,8,0,5,0,0,0},
				{1,0,0,0,9,0,0,0,2},
				{0,6,0,5,0,4,0,7,0},
				{7,0,0,0,3,0,0,0,8},
				{0,0,0,3,0,2,0,0,0},
				{0,0,1,0,0,0,9,0,0},
				{6,8,0,0,0,0,0,2,5}};
		int [][] board10 = {{8,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0}};
		int [][] board11 = {{3,0,9,0,0,0,4,0,0},
				{2,0,0,7,0,9,0,0,0},
				{0,8,7,0,0,0,0,0,0},
				{7,5,0,0,6,0,2,3,0},
				{6,0,0,9,0,4,0,0,8},
				{0,2,8,0,5,0,0,4,1},
				{0,0,0,0,0,0,5,9,0},
				{0,0,0,1,0,6,0,0,7},
				{0,0,6,0,0,0,1,0,4}};*/

		//int [][] s1 = readBoardFromFile("S1.txt");
		//int [][][] domains = eliminateDomains(s1);
		//printBoard(eliminateDomains(s1), s1);
		//System.out.println("i , j : 1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9 ");
		//System.out.println("------------------------------------------");
		//System.out.println(solveSudoku(board4));
		//System.out.println(blankSpace(s1,eliminateDomains(s1)));
		//printBoard(eliminateDomains(board),board);
		//System.out.println(solved(board4));
		//int [][] board3 = eliminateDomainsHelp(s1);
		//System.out.println(solved(board3));
		//System.out.println(valid(board4));
	}
	// **************   Sudoku - Read Board From Input File   **************
	public static int[][] readBoardFromFile(String fileToRead){
		int[][] board = new int[9][9];
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(fileToRead)); // change S1.txt to any file you like (S2.txt, ...)
			int row = 0;
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				for(int column = 0; column < line.length(); column++){
					char c = line.charAt(column);
					if(c == 'X')
						board[row][column] = 0;
					else board[row][column] = c - '0';
				}
				row++;
			}
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return board;
	}
	// **************   Sudoku - Part1 (iterative)   **************
	public static int[][][] eliminateDomains(int[][] board){
		// This function gets a 2D array which represents the sudoko board. 
		//the function is incharge to return a 3D array Domains that shows for each slot in board what are the possible options to put in the slot. 1 in the array represnts that it can be filled with the number in this index of the array+1.
		//the function automatically fills slots that have 0 and have only 1 option to fill and then check again to see if there is any new options after this implemention.
		int [][][] domainsHelp = eliminateDomainsPrivate(board);// getting a domains help fron the function 'eliminateDomainsPrivate' which is represnted as array of the optinal values for each cell in the board.
		int [][][] domains = new int [9][9][9];
		for(int row=0; row<9; row++)
		{
			for (int column=0; column<9; column++)
			{
				for(int k = 0; k<9; k++)
				{
					// translting the domainsHelp from specific values to array the his index+1 represents the optional numbers to set.
					int option = domainsHelp[row][column][k];
					if(option!=0)
					{
						//if there is any option put it in the index-1 in domain
						domains[row][column][option-1] = 1;
					}
				}
			}
		}
		for(int row=0; row<9; row++)
		{
			for (int column=0; column<9; column++)
			{
				for(int k = 0; k<9; k++)
				{
					//set all the values that optional to set from '0' to '-1'.
					if(domains[row][column][k]==0)
					{
						domains[row][column][k] = -1;
					}
				}
			}
		}
		return domains;
	}
	public static int[][][] eliminateDomainsPrivate(int[][] board){
		// This function gets a 2D array which represents the sudoko board. 
		//the function is incharge to return a 3D array Domains that shows for each slot in board what are the possible options to put in the slot. 1 in the array represnts that it can be filled with the number in this index of the array+1.
		//the function automatically fills slots that have 0 and have only 1 option to fill and then check again to see if there is any new options after this implemention.
		int [][][] domainsHelp = mapping(board);
		int [][][] domains = new int [9][9][9];
		int [][] testBoard = new int[9][9];
		boolean changed = false;
		for(int i=0; i<9; i++)
		{
			for (int j=0; j<9; j++)
			{
				//looking for the cells that contain '0'
				if(board[i][j] == 0)
				{
					//creating an array and setting him values to be the optional values to set in this cell.
					int counter = 0;
					int [] array = new int [9];
					for (int k=0; k<9; k++)
					{
						if(domainsHelp[i][j][k]==1)
						{
							array[counter]=k+1;
							counter++;
						}
					}
					if(counter == 1)
					{
						//if there have been only one option it trying to set this value in the board.
						testBoard[i][j]=array[0];
						if(valid(testBoard))
						{
							board[i][j]=array[0];
							changed = true;
						}
					}
					domains[i][j]=array;// setting the domains in the location of the cell to be this array.
				}
			}
		}
		if(changed)
		{
			//if there have been any change in the board it calls to the function again until there are no more cells that contain the value '0' and have only 1 option to set.
			domains = eliminateDomainsPrivate(board);
		}
		return domains;
	}
	public static int[][][] mapping(int[][] board){
		// This function gets a 2D array which represents the sudoko board. 
		// this function returns 3D array which represnts the available values to insert to the empty cells.
		int [][][] domains = new int [9][9][9];
		for(int row=0; row<9; row++)
		{
			for (int column=0; column<9; column++)
			{
				if(board[row][column] != 0)
				{
					for(int k = 0; k<9; k++)
					{
						//going through all the values in the board that are not '0' and marking the values in the domains of all the cells in the same column and row to be '-1'.
						domains[row][k][board[row][column]-1] = -1;
						domains[k][column][board[row][column]-1] = -1;
					}
				}
			}
		}

		for(int row=0; row<9; row++)
		{
			for (int column=0; column<9; column++)
			{
				int [][] cube = getCube(board, column, row);
				for(int cubeRow = 0; cubeRow<3; cubeRow++)
				{
					for(int cubeColumn = 0; cubeColumn<3; cubeColumn++)
					{
						if(cube[cubeRow][cubeColumn]!=0)
						{
							//going through all the values in the board that are not '0' and marking the values in the domains of all the cells in the same cube to be '-1'.
							domains[row][column][cube[cubeRow][cubeColumn]-1] = -1;
						}
					}
				}
			}
		}
		for(int row=0; row<9; row++)
		{
			for (int column=0; column<9; column++)
			{
				for(int k = 0; k<9; k++)
				{
					//set all the values that optional to set from '0' to '1'
					if(domains[row][column][k]==0)
					{
						domains[row][column][k] = 1;
					}
				}
			}
		}
		return domains;
	}
	public static int [][] getCube (int [][] board, int column, int row)
	{
		//this function gets a 2D array which represents the Sudoko board and specific column and row and uses another function "divideMatrix" 
		//to return the cube of this slot in the board as 2D array of 3X3.
		//this function calculate in which one of the cubes is this slot and then calls the other function from the top left slot of the specific cube.
		int [][] cube = new int [3][3];
		if(row%3 == 0 && column%3 == 0)
		{
			//checks if both of the them - column and row are already the top left slot, if does they are sent to function "divideMatrix".
			cube = divideMatrix(board, column, row);
		}
		else if(row%3 != 0 && column%3 == 0)
		{
			cube = divideMatrix(board, column, row - row%3);
		}
		else if(row%3 == 0 && column%3 != 0)
		{
			cube = divideMatrix(board, column - column%3, row);
		}
		else
		{
			cube = divideMatrix(board, column - column%3, row- row%3);
		}
		return cube;
	}
	public static int [][] divideMatrix (int [][] board, int column, int row)
	{
		//this function gets a 2D array which represents the Sudoko board and specific column and row that represents the top left slot of the cube.
		//the function return the cube as 2D array of 3X3 by the given location.
		int [][] cube = new int [3][3];
		for (int cubeRow=row; cubeRow<row+3 && cubeRow<9;cubeRow ++)
		{
			for (int cubeColumn=column; cubeColumn<column+3;cubeColumn ++)
			{
				//going through all the values in the cube and copy them into new 3X3 array.
				cube[cubeRow%3][cubeColumn%3]=board[cubeRow][cubeColumn];
			}
		}
		return cube;
	}
	public static void printBoard(int[][][] domains, int[][] board){
		//this function gets 3D array of domains which indicates which number can be used.
		//and prints the board and all the possible options for each slot.

		for(int i=0; i<9; i++)
		{
			//goes through the board and prints all the slots of the board and separate the board to 9 cubes.
			for (int j=0; j<9; j++)
			{
				if(j==3||j==6)
				{
					System.out.print("|");
				}
				System.out.print(board[i][j]);
			}
			System.out.println();
			if(i==2||i==5)
			{
				System.out.println("---+---+---");
			}
		}
		for(int i=0; i<9; i++)
		{
			//goes through every slot of the board in the domains and if the cell contains '0' it prints the options that he can put (where there is '1' in the domain). 
			//and if there is already value in the cell it prints the value.
			for (int j=0; j<9; j++)
			{
				System.out.print(i+","+j+" = ");
				if(board[i][j]!=0)
				{
					System.out.print(board[i][j]+",");
				}
				else
				{
					for (int k=0; k<9; k++)
					{
						if(domains[i][j][k]!=-1)
						{
							System.out.print((k+1)+",");
						}
					}
				}
				System.out.println();
			}
		}
	}

	// **************   Sudoku - Part2 (recursive)   **************
	public static boolean solveSudoku(int[][] board){
		//this function receives a 2D array which represents Sudoko board and returns true if this board is solvable, false if not. 
		//this function calls solveSudoko with copy of the board, row=0, column=0, domains and the original board.
		int [][][] domains = eliminateDomains(board);
		boolean ans = solveSudoku(copyBoard(board), 0, 0,domains,board);
		return ans;
	}
	public static boolean solveSudoku(int[][] board, int row, int column, int [][][] domains, int[][] solution){
		//this function gets a 2D array which represents the Sudoko board and specific column and row, domains which represents the optional values to insert for the cells that contains '0'. 
		//the function also receives the original array that represents the board in order to return the solved board if it does.
		//the function checks if the board is solved, if does returns true, else if the row and column are out of index returns false.
		//else if there are any cells that doesn't have any options to insert in the board or if the board is not valid it returns false.
		//else if the value in the specific cell is '0' the function is going through all the optional values of the specific cell and trying to put them, and to see if it helps to solve the board.
		//else it just going to the next cell. 
		//the function returns the result from all of the calls to this function.
		boolean ans = false;
		if(solved(board))
		{
			//returns true and copy the values from the current board to the original board.
			ans = true;
			for(int row1=0; row1<9; row1++)
			{
				for (int column1=0; column1<9; column1++)
				{
					solution[row1][column1] = board[row1][column1];
				}
			}
		}
		else if(column >= 9||row >= 9)
		{
			//if the row and column are out of index returns false.
			ans = false;
		}
		else if(!noOptionsBoard(board)||!valid(board))
		{
			//calling to 2 other functions to check if there are any cells that doesn't have any options to insert in the board or if the board is not valid it returns false.
			ans = false;
		}
		else if(board[row][column] == 0)
		{
			//if the value in the specific cell is '0' the function is going through all the optional values of the specific cell and trying to put them, and to see if it helps to solve the board.
			for(int i = 0; i < 9 && !ans; i++)
			{
				//going through all the array values as long as the answer is false, which means the board is not solved.
				if(domains[row][column][i] == 1)
				{
					//if the specific value is optional it creates copy of the current board and trying to set him for this specific value.
					int [][] newBoard = copyBoard(board);
					newBoard[row][column] = i+1;
					int [][][] newDomains = eliminateDomains(newBoard);
					if(column<8)
					{
						//going by row after row. alling the same function recursively in the next cell with the new board and domains.
						ans = solveSudoku(newBoard, row, column+1, newDomains,solution);
					}
					else
					{
						ans = solveSudoku(newBoard, row+1, 0, newDomains,solution);
					}
				}
			}
		}
		else
		{
			//if the value in the cell is already set, just calling the same function recursively in the next cell.
			if(column<8)
			{
				ans = solveSudoku(board, row, column+1, domains,solution);
			}
			else
			{
				ans = solveSudoku(board, row+1, 0, domains,solution);
			}
		}
		//returns the final answer.
		return ans;
	}
	public static boolean solved(int [][] board)
	{
		// this function receives 2D array which represents the sudoko board.
		// the function is going through all the values in the array and checks if there are values in all of the cells and also if there are no duplicates in the board row, column and cube of specific cells.
		// the function returns false if there are any duplicates or a cell that contain '0'.
		// the function returns true if there are values in all of the board and none of them returns in the same column, row and cube of specific cell.
		int [][][] domains = new int[9][9][9];
		int [][] cube = new int [3][3];
		for(int row=0; row<9; row++)
		{
			for (int column=0; column<9; column++)
			{
				if(board[row][column] != 0)
				{
					//if the cell contain '0' the function returns false, else it remember the value of this cell for this specific cell in the domains.
					if(domains[row][column][board[row][column]-1]==0)
					{
						domains[row][column][board[row][column]-1] = 1;
					}
					else
						return false;
				}
				else
				{
					return false;
				}
				cube = getCube(board, column, row);
				for(int cubeRow = 0; cubeRow<3; cubeRow++)
				{
					for(int cubeColumn = 0; cubeColumn<3; cubeColumn++)
					{
						//here the function is going through all the values in the cubes that are not this specific cell and checking their values in comparison to the specific cell.
						if(cubeRow != row%3 && cubeColumn != column%3)
						{
							if(cube[cubeRow][cubeColumn]!=0)
							{
								if(domains[row][column][cube[cubeRow][cubeColumn]-1]==0)
								{
									domains[row][column][cube[cubeRow][cubeColumn]-1] = 1;
								}
								else
									return false;
							}
						}
					}
				}
			}
		}
		//if there are no duplicates or 0 it returns true.
		return true;
	}
	public static boolean valid(int [][] board)
	{
		// this function receives 2D array which represents the sudoko board.
		// the function checks if there are no duplicates in the rows , columns and the cubes of the sudoko board except '0'.
		// if there are any duplicates the function returns false, else returns true.
		int [][] cube = new int [3][3];
		for(int row=0; row<9; row++)
		{
			for (int column=0; column<9; column++)
			{
				if(board[row][column] != 0)
				{
					for(int k = 0; k< 9; k++)
					{
						if(k!= row && k!=column)
						{
							//check if there is any value that is duplicated in the same row or column, if does return false.
							if(board[row][column] == board[k][column] || board[row][column] == board[row][k])
							{
								return false;
							}
						}
					}
				}
				//gets the cube of the cell.
				cube = getCube(board, column, row);
				for(int cubeRow = 0; cubeRow<3; cubeRow++)
				{
					for(int cubeColumn = 0; cubeColumn<3; cubeColumn++)
					{
						if(cube[cubeRow][cubeColumn] != 0)
						{
							for(int k = 0; k< 3; k++)
							{
								for(int p = 0; p<3; p++)
								{
									//check if there is any value that is duplicated in the cube, if does return false.
									if(k!= cubeRow && p!= cubeColumn && cube[cubeRow][cubeColumn] == cube[k][p])
									{
										return false;
									}
								}
							}
						}
					}
				}
			}
		}
		return true;
	}
	public static int[][] copyBoard(int [][] board)
	{
		// this function receives 2D array which represents the sudoko board.
		// the function returns a new board which is exact copy of the origin board he gets.
		int [][] boardCopy = new int [board.length][board.length];
		for (int i= 0; i< boardCopy.length; i++)
		{
			for (int j= 0; j< boardCopy.length; j++)
			{
				//put the same value for the coppied board.
				boardCopy[i][j] = board[i][j];
			}
		}
		return boardCopy;
	}
	public static boolean noOptionsBoard(int [][]board)
	{
		// this function recives 2D array which representes the sudoko board.
		// the function return false if there is any cell in the board that have value 0 and have no options for values to set instead of it. 
		// the function return true if there is at least one value for each cell that is set to 0.
		int [][][] domains = eliminateDomains(board);
		int sum =0;
		for(int row=0; row<9; row++)
		{
			for (int column=0; column<9; column++)
			{
				if(board[row][column]==0)
				{
					sum = 0;
					for(int k = 0; k<9; k++)
					{
						//sum all the values in the domains array for the specific cell that have 0 as value.
						sum = sum + domains[row][column][k];
					}
					if(sum == -9)
					{
						//if there are no options the sum is -9 and then it returns false.
						return false;
					}
				}
			}
		}
		return true;
	}
}
