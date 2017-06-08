package com.adaptionsoft.games.trivia;

import com.adaptionsoft.games.trivia.runner.Player;
import com.adaptionsoft.games.uglytrivia.Game;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertTrue;

public class GameTest {

	@Test
	public void ShouldTakePlayerOutOfPenaltyBoxAndMove() throws Exception {
		Game aGame = new Game();
		Player bob = new Player("Bob");
		int originalPosition = 2;

		bob.currentPosition = originalPosition;
		bob.isInPenaltyBox = true;
		int rollValue = 1;

		aGame.addPlayer(bob);
		aGame.roll(rollValue);
		assertThat(bob.isInPenaltyBox, is(equalTo(false)));
		assertTrue("Position hasn't changed", originalPosition < bob.currentPosition);
	}

	@Test
	public void PlayerInPenaltyBoxShouldNotMoveIfRollIsEvenValue() throws Exception {
		Game aGame = new Game();
		Player bob = new Player("Bob");
		bob.isInPenaltyBox = true;
		int originalPosition = 2;
		int rollValue = 2;
		bob.currentPosition = originalPosition;

		aGame.addPlayer(bob);
		aGame.roll(rollValue);
		assertThat(originalPosition, is(equalTo(bob.currentPosition)));
	}

	@Test
	public void PlayerShouldNotGetMoneyIfInPenalty() throws Exception {
		Game aGame = new Game();
		Player bob = new Player("Bob");
		bob.isInPenaltyBox = true;
		int rollValue = 2;
		aGame.addPlayer(bob);
		aGame.roll(rollValue);
		aGame.wasCorrectlyAnswered();
		assertThat(bob.purseValue, is(equalTo(0)));
	}

	@Test
	public void PlayerShouldGetMoneyIfTheyGotOutOfPenaltyAndAnsweredCorrectly() throws Exception {
		Game aGame = new Game();
		Player bob = new Player("Bob");
		bob.isInPenaltyBox = true;
		int rollValue = 1;
		aGame.addPlayer(bob);
		aGame.roll(rollValue);
		aGame.wasCorrectlyAnswered();
		assertThat(bob.purseValue, is(equalTo(1)));
	}

	@Test
	public void PlayerShouldGetMoneyIfTheyAnswerTheQuestionCorrectly() throws Exception {
		Game aGame = new Game();
		Player bob = new Player("Bob");
		int rollValue = 2;
		aGame.addPlayer(bob);
		aGame.roll(rollValue);
		aGame.wasCorrectlyAnswered();
		assertThat(bob.purseValue, is(equalTo(1)));
	}

	@Test
	public void PlayerIsSentToPenaltyBoxIfAnsweredIncorrectly() throws Exception{
		Game aGame = new Game();
		Player bob = new Player("Bob");
		int rollValue = 2;
		aGame.addPlayer(bob);
		aGame.roll(rollValue);
		aGame.wrongAnswer();
		assertThat(bob.isInPenaltyBox, is(equalTo(true)));
	}
}
