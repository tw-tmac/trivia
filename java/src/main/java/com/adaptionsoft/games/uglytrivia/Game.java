package com.adaptionsoft.games.uglytrivia;

import com.adaptionsoft.games.trivia.runner.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

public class Game {

    private ArrayList<Player> players = new ArrayList<>();

    //make it a question class
    private LinkedList<String> popQuestions = new LinkedList<>();
    private LinkedList<String> scienceQuestions = new LinkedList<>();
    private LinkedList<String> sportsQuestions = new LinkedList<>();
    private LinkedList<String> rockQuestions = new LinkedList<>();
    
    private int currentPlayerPosition = 0;
    private Player currentPlayer;
    public  Game(){

    	//Create questions outside of the class
    	for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast("Rock Question " + i);
    	}
    }

	public boolean addPlayer(Player player) {
	    players.add(player);
	    System.out.println(player.Name + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}

	//complicated logic with nested ifs
	public void roll(int roll) {
        this.currentPlayer = players.get(currentPlayerPosition);
		System.out.println(  this.currentPlayer.Name + " is the current player");
		System.out.println("They have rolled a " + roll);
		if(this.currentPlayer.isInPenaltyBox){
			//odd number gets player out of penalty box
			if (roll % 2 != 0) {
				System.out.println(this.currentPlayer.Name + " is getting out of the penalty box");
				this.currentPlayer.isInPenaltyBox = false;
				this.currentPlayer.move(roll);
			} else {
				System.out.println(this.currentPlayer.Name + " is not getting out of the penalty box");
				}
		} else {

			this.currentPlayer.move(roll);
			System.out.println(this.currentPlayer.Name + "'s new location is " + this.currentPlayer.currentPosition);
			System.out.println("The category is " + currentCategory());
		}
		askQuestion();


	}

	private void askQuestion() {

		//removeFirst is the same for each linked list. Make a class out of it
		if (Objects.equals(currentCategory(), "Pop"))
			System.out.println(popQuestions.removeFirst());
		if (Objects.equals(currentCategory(), "Science"))
			System.out.println(scienceQuestions.removeFirst());
		if (Objects.equals(currentCategory(), "Sports"))
			System.out.println(sportsQuestions.removeFirst());
		if (Objects.equals(currentCategory(), "Rock"))
			System.out.println(rockQuestions.removeFirst());
	}

	private String currentCategory() {
		//repeated return statements, use case/switch or something better in Java 8
		if (this.currentPlayer.currentPosition == 0 || this.currentPlayer.currentPosition == 4 || this.currentPlayer.currentPosition == 8) {
			return "Pop";
		} else if (this.currentPlayer.currentPosition == 1 || this.currentPlayer.currentPosition == 5 || this.currentPlayer.currentPosition == 9) {
			return "Science";
		} else if (this.currentPlayer.currentPosition == 2 || this.currentPlayer.currentPosition == 6 || this.currentPlayer.currentPosition == 10) {
			return "Sports";
		} else {
			return "Rock";
		}
	}

	public boolean wasCorrectlyAnswered() {
		if (!this.currentPlayer.isInPenaltyBox){
			System.out.println("Answer was correct!!!!");
			this.currentPlayer.purseValue++;
			System.out.println(this.currentPlayer.Name + " now has " + this.currentPlayer.purseValue + " Gold Coins.");
		}

		currentPlayerPosition = ++currentPlayerPosition % players.size();

		return !this.currentPlayer.isAWinner();

	}
	
	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(this.currentPlayer.Name + " was sent to the penalty box");
		this.currentPlayer.isInPenaltyBox = true;
		currentPlayerPosition = ++currentPlayerPosition % players.size();
		return true;
	}

}
