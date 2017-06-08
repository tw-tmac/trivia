package com.adaptionsoft.games.trivia;

import com.adaptionsoft.games.trivia.runner.Player;
import com.adaptionsoft.games.uglytrivia.Game;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertTrue;

public class GameTest {

	private Game aGame;
	private Player bob;
	private int originalPosition;

	@Before
	public void setUp(){
		this.aGame = new Game();
		this.bob = new Player("Bob");
		this.originalPosition = 2;
		this.bob.currentPosition = this.originalPosition;
		this.aGame.addPlayer(bob);
	}

	@Test
	public void ShouldTakePlayerOutOfPenaltyBoxAndMove() throws Exception {

		bob.isInPenaltyBox = true;
		int rollValue = 1;

		aGame.roll(rollValue);
		assertThat(bob.isInPenaltyBox, is(equalTo(false)));
		assertTrue("Position hasn't changed", originalPosition < bob.currentPosition);
	}

	@Test
	public void PlayerInPenaltyBoxShouldNotMoveIfRollIsEvenValue() throws Exception {
		bob.isInPenaltyBox = true;
		int rollValue = 2;

		aGame.roll(rollValue);
		assertThat(originalPosition, is(equalTo(bob.currentPosition)));
	}

	@Test
	public void PlayerShouldNotGetMoneyIfInPenalty() throws Exception {
		bob.isInPenaltyBox = true;
		int rollValue = 2;

		aGame.roll(rollValue);
		aGame.wasCorrectlyAnswered();
		assertThat(bob.purseValue, is(equalTo(0)));
	}

	@Test
	public void PlayerShouldGetMoneyIfTheyGotOutOfPenaltyAndAnsweredCorrectly() throws Exception {
		bob.isInPenaltyBox = true;
		int rollValue = 1;

		aGame.roll(rollValue);
		aGame.wasCorrectlyAnswered();
		assertThat(bob.purseValue, is(equalTo(1)));
	}

	@Test
	public void PlayerShouldGetMoneyIfTheyAnswerTheQuestionCorrectly() throws Exception {
		int rollValue = 2;

		aGame.roll(rollValue);
		aGame.wasCorrectlyAnswered();
		assertThat(bob.purseValue, is(equalTo(1)));
	}

	@Test
	public void PlayerIsSentToPenaltyBoxIfAnsweredIncorrectly() throws Exception{
		int rollValue = 2;

		aGame.roll(rollValue);
		aGame.wrongAnswer();
		assertThat(bob.isInPenaltyBox, is(equalTo(true)));
	}
}
