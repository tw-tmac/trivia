package com.adaptionsoft.games.uglytrivia;

import com.adaptionsoft.games.trivia.runner.Player;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {

    private ArrayList<Player> players = new ArrayList();

    //make it a question class
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    int currentPlayer = 0;

    public  Game(){

    	//Create questions outside of the class
    	for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast(createRockQuestion(i));
    	}
    }

	//function is not needed as it does the same as the other
	public String createRockQuestion(int index){
		return "Rock Question " + index;
	}


	public boolean addPlayer(Player player) {
	    players.add(player);
	    System.out.println(player.Name + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}

	//complicated logic with nested ifs
	public void roll(int roll) {
		Player player = players.get(currentPlayer);

		System.out.println(  player.Name + " is the current player");
		System.out.println("They have rolled a " + roll);
		if(player.isInPenaltyBox){
			//odd number gets player out of penalty box
			if (roll % 2 != 0) {
				System.out.println(player.Name + " is getting out of the penalty box");
				player.isInPenaltyBox = false;
				player.move(roll);
			} else {
				System.out.println(player.Name + " is not getting out of the penalty box");
				}
		} else {

			player.move(roll);
			System.out.println(player.Name + "'s new location is " + player.currentPosition);
			System.out.println("The category is " + currentCategory());
		}
		askQuestion();


	}

	private void askQuestion() {

		//removeFirst is the same for each linked list. Make a class out of it
		if (currentCategory() == "Pop")
			System.out.println(popQuestions.removeFirst());
		if (currentCategory() == "Science")
			System.out.println(scienceQuestions.removeFirst());
		if (currentCategory() == "Sports")
			System.out.println(sportsQuestions.removeFirst());
		if (currentCategory() == "Rock")
			System.out.println(rockQuestions.removeFirst());		
	}

	private String currentCategory() {
		//repeated return statements, use case/switch or something better in Java 8
		Player player = players.get(currentPlayer);
		switch (player.currentPosition){
			case 0:
			case 4:
			case 8:
				return "Pop";
			case 1:
			case 5:
			case 9:
				return "Science";
			case 2:
			case 6:
			case 10:
				return "Sports";
			default:
				return "Rock";
		}
	}

	public boolean wasCorrectlyAnswered() {
		Player player = players.get(currentPlayer);
		if (!player.isInPenaltyBox){
			System.out.println("Answer was correct!!!!");
			player.purseValue++;
			System.out.println(player.Name + " now has " + player.purseValue + " Gold Coins.");
		}

		currentPlayer = ++currentPlayer% players.size();

		return !player.isAWinner();

	}
	
	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		Player player = players.get(currentPlayer);
		System.out.println(player.Name + " was sent to the penalty box");
		player.isInPenaltyBox = true;
		currentPlayer = ++currentPlayer% players.size();
		return true;
	}

}
