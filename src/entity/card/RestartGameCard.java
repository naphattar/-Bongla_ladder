package entity.card;
//
import Logic.GameLogic;
import entity.Counter;

public class RestartGameCard extends BaseCard {

	public RestartGameCard(float ratePercent) {
		super(ratePercent, "Take a rest");
	}

	@Override
	public void cardAction(Counter counter) {
		GameLogic.endgame(1);
	}

}
