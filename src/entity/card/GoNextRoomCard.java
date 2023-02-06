package entity.card;
//
import java.util.ArrayList;

import Logic.GameLogic;
import entity.Counter;
import gui.Ladder;
import gui.myBoard;

//
public class GoNextRoomCard extends BaseCard {

	public GoNextRoomCard(float ratePercent) {
		super(ratePercent, "Go to the future!!");
	}

	//go to room before next ladder
	@Override
	public void cardAction(Counter counter) {
		// TODO Auto-generated method stub
		ArrayList<Ladder> ladderList = myBoard.getLadderlist();
		int nextLadderIndex = 80;
		for(Ladder ladder : ladderList) {
			if(counter.getBoardIndex() <= ladder.getLowerIndex())
				nextLadderIndex = Math.min(ladder.getLowerIndex(),nextLadderIndex);
		}
		nextLadderIndex -= 1;
		ArrayList<Integer> Pos = GameLogic.changeIndextoPos(nextLadderIndex);
		counter.setBoardIndex(nextLadderIndex);
		counter.setxPos(Pos.get(0));
		counter.setyPos(Pos.get(1));
		
	}

}
