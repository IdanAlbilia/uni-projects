//Author: Itai Dagan 

public class Mastermind
{
	public static int N = 4; 					// Number of digits in a tuple
	public static int BASE = 10;				// Base of the digits
	public static int MAX_ROUND_NUMBER = 6;		// Number of rounds in a game

	public static void main(String[] args) {
		//Frame.play();


		// TO TEST YOUR CODE MARK THE LINE  "Frame.play();"  AS A REMRAK AND UNMARK THE REQUESTED TEST:



		//+++++++++++++++++++++++++ test for areArraysEqual +++++++++++++++++++++++++++++++

		/*int[]arr1 = {1,2,3,9};
		int[]arr2 = {1,2,3,9};
		int[]arr3 = {1,2,4,0};
		int[]arr4 = null;
		System.out.println(areArraysEqual(arr1,arr2));
		System.out.println(areArraysEqual(arr1, arr3));
		System.out.println(areArraysEqual(arr1, arr4));

		// expected output: true
		//				    false
		// 			 	    false


		//+++++++++++++++++++++++++ test for randomizeSequence +++++++++++++++++++++++++++++++

		int[]arr16 = randomizeSequence();
		System.out.print("Randomize :");
		for (int i = 0; i < arr16.length; i++) {
			System.out.print(arr16[i]+ " ");}
		System.out.println();

		int[]arr5 ={9,90,9,9};
		for (int i = 0; i < arr5.length; i++) {
			System.out.print(arr5[i]+ " ");}
		System.out.println();
		int[]arr6 = null;
		arr6 = incrementOdometer(arr5);
		for (int i = 0; i < arr6.length; i++) {
			System.out.print(arr6[i]+ " ");}
		System.out.println();

		// expected output: 0 1 0 0

		//*/

		//+++++++++++++++++++++++++ test for isRightfulGuess +++++++++++++++++++++++++++

		//int[]arr7 = null;
		//int[]arr8 = {9,1,2,10};

		//System.out.println(isRightfulGuess(arr7));
		//System.out.println(isRightfulGuess(arr8));

		// expected output: true
		//			 	    false

		/*

		int[] arr2 = {1,2,3,9};
		int[] arr1 = getNextRightfulGuess(arr2);
		for (int i = 0; i < arr1.length; i++) {
			System.out.print(arr1[i]+ " ");
		}
		System.out.println();

		// expected output: 1 2 4 0

		 */

		/*
		int[] guess1 = {2,1,3,4};
		int[] guess2 = {4,1,3,2};
		int[] hitFitAnswer = judgeBetweenGuesses(guess1, guess2);
		System.out.print(hitFitAnswer[0] + " " + hitFitAnswer[1]);
		System.out.println();

		// expected output: 2 2

		//*/



		//int[] arr13 = randomizeSequence();
		//printGame(arr13, play(arr13));

		//expected output:  the secret is 1 2 3 4
		//			  		the guess 0 1 2 3  gives (h/f): 0 3
		//			  		the guess 1 0 3 4  gives (h/f): 3 0
		//			 		 the guess 1 0 3 5  gives (h/f): 2 0
		//			 		 the guess 1 2 3 4  gives (h/f): 4 0
		//			 		 you guessed the secret within 4  rounds

	} // end of main


	/**
	 * TASK 1
	 * This task recives 2 arrays and returns true if they are equal, false if they are not.
	 **/
	public static boolean areArraysEqual(int[] firstArr, int[] secArr) {
		//checking if at least one of the arrays is null or if the lengths aren't equal and return false
		if (firstArr == null || secArr == null || firstArr.length != secArr.length)
		{
			return false;
		}
		//if they are in the same length comapre between the values in the arrays
		for(int i=0; i<firstArr.length; i++)
		{
			// if it will found one value that is in the same index and the value is not equal return false
			if(firstArr[i]!=secArr[i])
			{
				return false;
			}
		}
		//if all the values were the same return true
		return true;
	}

	/**
	 * TASK 2
	 * this task create new array size of N and put numbers from 0 to BASE not including in random order and the number doesn't appear twice.
	 **/
	public static int[] randomizeSequence() {
		int [] randomSequence = new int[N];// local array that will be the random with N length	
		int rand = (int) (Math.random() * BASE); //Random variable to get random number
		boolean correct = false;// boolean number that indicates if number has been found
		//run for the length of the array in order to find correct value for every index in the array
		if (N <= 0 || N > BASE)
			return null;
		randomSequence[0]= rand;//for the first index in location 0 just insert the random value
		for (int i=1; i<randomSequence.length; i++)
		{
			//if its not the 0 location then get into while loop and find matching number that has not been used yet
			correct = false;
			while(!correct)
			{
				rand = (int) (Math.random() * BASE);
				correct = true;
				for(int j=i-1; j>=0; j--)
				{
					if(rand == randomSequence[j])
					{
						correct = false;
					}
				}
			}
			randomSequence[i]= rand; 
		}
		//return the random sequence 
		return randomSequence;
	}

	/**
	 * TASK 3
	 * recives an array with valid digits from 0 to BASE-1 and the function needs to increase the array in the most significant sign by 1.
	 **/
	public static int[] incrementOdometer(int[] odometer) {
		int[] increasedOdometer = new int[odometer.length];//local array in order to copy the values
		boolean added=false;//boolean that indicates if it's the first time in the loop
		//run from the end of the array to the start in order to add 1 to base numbers
		for (int i=odometer.length-1; i>=0; i--)
		{
			if(odometer[i] == BASE -1 && !added)
			{
				increasedOdometer[i] = 0;
			}
			else if(!added){
				increasedOdometer[i] = odometer[i] + 1;
				added = true;
			}
			else{
				increasedOdometer[i] = odometer[i];
			}
		}
		// return the increased array
		return increasedOdometer;
	}

	/**
	 * TASK 4
	 * this function recives an array and return true if all the digits are between 0 to BASE-1 and there are no duplicated numbers. false if not.
	 **/
	public static boolean isRightfulGuess(int[] guessArray) {
		// if the length of the array is bigger than N return false
		if (guessArray.length != N)
		{
			return false;
		}
		//check the values of the array to see if it stands for the standards of rightful guess which means there are no duplicated digits in the array indexes values
		for( int i=0; i<guessArray.length;i++)
		{
			if(guessArray[i] > (BASE-1))
			{
				return false;
			}
			for (int j=i+1; j<guessArray.length&&i<guessArray.length-1; j++)
			{
				if(guessArray[i] == guessArray[j])
				{
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * TASK 5
	 * this function receives an array and returns the next rightful guess that is bigger than the previous one
	 **/
	public static int[] getNextRightfulGuess(int[] guessArray) {
		int [] rightfulGuess = incrementOdometer(guessArray);//local array in order to copy the values from guessArray
		//this while loop is increasing the guess array until true value is returned from isRightfulGuess 
		while(!isRightfulGuess(rightfulGuess))
		{
			rightfulGuess = incrementOdometer(rightfulGuess);
		}
		
		return rightfulGuess;	
	}


	/**
	 * TASK 6
	 * this function recives 2 arrays and returns array of size 2 that the array[0] defines the number of exact match which means the same number in the same location and in the array[1] the number of the same numbers which are not in the same location.
	 **/
	public static int[] judgeBetweenGuesses(int[] guess1, int[] guess2) {
		int[] comparison = {0,0};// array that stands for the score of the comaprison
		//going through the array with 2 index and comparing between the values, 
		//if it's the same value and same index then it add 1 to comparison[0]
		//if it's the same value in different index it add 1 to comparison[1]
		for(int i =0; i<guess1.length; i++)
		{
			if(guess1[i]==guess2[i])
			{
				comparison[0]= comparison[0] + 1;
			}
			for(int j=0;j<guess1.length; j++)
			{
				if(guess1[i]==guess2[j] && i!=j)
				{
					comparison[1]= comparison[1] + 1;
				}
			}
		}
		return comparison;
	}

	/**
	 * TASK 7
	 * this function recives round, guess array and game History and checks if the curent guess gets the same judgemnt with all the previous guesses. which means if this number was the secret number it for the previous guesses.
	 **/
	public static boolean settleGuessInHistory(int round, int[] currentGuess, int[][][] gameHistory) {
		int [] judgmentResult = new int [2];//local array to get the new score for the currentGuess and all the previous guesses
		//goes through all the previous guesses in the game history and check if the score matches to the score in gameHistory
		if(!isRightfulGuess(currentGuess))
		{
			return false;
		}
		for(int i=0; i<round; i++)
		{
			judgmentResult = judgeBetweenGuesses(currentGuess, gameHistory[i][0]);
			if(judgmentResult[0]!=gameHistory[i][1][0] || judgmentResult[1]!=gameHistory[i][1][1])
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * TASK 8
	 * this function recives gameHistory, round, new guess array and score. the function update in gameHistory the guess and score in the relevant round
	 **/
	public static void update(int[][][] gameHistory, int round, int[] newGuess, int[] score) {
		// adds a newGuess and answer (hits/fits) to the gameHistory at
		// "line" round. We assume that round < MAX_ROUND_NUMBER.
		int [] currentScore = new int[score.length]; //local array that is a copy of score
		int [] currentGuess = new int[newGuess.length]; //local array that is a copy of newGuess
		//copy the values from newGuess to currentGuess
		for (int i= 0; i<currentGuess.length; i++)
		{
			currentGuess[i] = newGuess[i];
		}
		//copy the values from score to currentScore
		for (int i= 0; i<currentScore.length; i++)
		{
			currentScore[i] = score[i];
		}
		gameHistory[round][0] = currentGuess;//set current guess in the gameHistory
		gameHistory[round][1] = currentScore;//set current score in the gameHistory
	}  

	/**
	 * TASK 9
	 * this function recives secret number and informs the game for this secret number with himself by the given N and BASE numbers with no more than MAX ROUND NUMBER rounds.
	 **/
	public static int[][][] play(int[] secret) {
		int[][][] gameHistory = new int[MAX_ROUND_NUMBER][2][];// set the array of gameHistory
		int[] currentGuessArray = new int[N]; //set the array of current guess
		int round = 0;// counter for how many rounds have been played
		//initalize the current guess values to 0
		for (int i=0; i<N; i=i+1) {
			currentGuessArray[i] = 0;
		}
		//get into the loop of the game as long as the round counter is smaller than MAX_ROUND_NUMBER and the secret code hasn't been found yet
		while (round < MAX_ROUND_NUMBER) {
			//for the first round - get the next rightful guess and then get the score of this guess compared to secret code and update it in the gameHistory
			if(round == 0)
			{
				currentGuessArray = getNextRightfulGuess(currentGuessArray);
				update(gameHistory,round,currentGuessArray,judgeBetweenGuesses(secret, currentGuessArray));
			}
			else
			{
				currentGuessArray = getNextRightfulGuess(currentGuessArray);
				while(!settleGuessInHistory(round, currentGuessArray, gameHistory))
				{
					currentGuessArray = getNextRightfulGuess(currentGuessArray);
				}
				update(gameHistory,round,currentGuessArray,judgeBetweenGuesses(secret, currentGuessArray));	
			}
			if(areArraysEqual(secret, currentGuessArray))
			{
				break;
			}
			round++;
		}
		return gameHistory;
	}

	/**
	 * TASK 10
	 * this function recives secret number and gameHistory. the function in charge to print the gameHistory for the relevant secret number
	 **/
	public static void printGame(int[] secret, int[][][] gameHistory) {
		int counterValidRounds = 0;// counts how many rounds with gusses have been in the gameHistory
		//prints the secret code
		System.out.print("The secret is ");
		for (int i=0; i<secret.length; i++)
		{
			System.out.print(secret[i]+" ");
		}
		System.out.println();
		//this loop is going through the gameHistory and prints it to the screen as long as there are still gusess in the 
		//gameHistory and the number of round is smaller than MAX_ROUND_NUMBER
		for (int i=0; i<gameHistory.length && gameHistory[i][0] != null; i++)
		{
			//prints the Guess
			System.out.print("The guess ");
			for(int j=0;j<gameHistory[i][0].length;j++)
			{
				System.out.print(gameHistory[i][0][j]+" ");
			}
			//prints the score
			System.out.print("gives (h/f): " + gameHistory[i][1][0]+" " + gameHistory[i][1][1]+" ");
			System.out.println();
			counterValidRounds = i;
		}
		//if the number of hits is equal to N it means he guessed it, if not it means it failed.
		if(areArraysEqual(secret, gameHistory[counterValidRounds][0]))
		{
			System.out.println("You guessed the secret within "+ (counterValidRounds +1) +" rounds");
		}
		else
		{
			System.out.println("You failed guessing the secret within MAX_ROUND_NUMBER="+ MAX_ROUND_NUMBER +" rounds");
		}
	}

}// end of class Mastermind
