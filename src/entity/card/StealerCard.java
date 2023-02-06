 package entity.card;
import java.util.ArrayList;

//
import Logic.GameLogic;
import entity.BackwardCounter;
import entity.BaseCounter;
import entity.Counter;
import entity.DiagonalCounter;
import entity.DoubleDiceCounter;
import entity.NeverGobackCounter;

public class StealerCard extends BaseCard {

	public StealerCard(float ratePercent) {
		super(ratePercent, "Steal stole stolen");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void cardAction(Counter counter) {
		// TODO Auto-generated method stub
		boolean isPlayer1got = counter.hashCode() == GameLogic.getPlayer1Counter().hashCode();
		ArrayList<Integer> Pos1 = GameLogic.changeIndextoPos(GameLogic.getPlayer1Counter().getBoardIndex());
		ArrayList<Integer> Pos2 = GameLogic.changeIndextoPos(GameLogic.getPlayer2Counter().getBoardIndex());
		
		if(isPlayer1got) {
			if(GameLogic.getPlayer2Counter() instanceof BackwardCounter) {
				GameLogic.setPlayer1Counter(new BackwardCounter(Pos1.get(0), Pos1.get(1)));
			}else if(GameLogic.getPlayer2Counter() instanceof BaseCounter) {
				GameLogic.setPlayer1Counter(new BaseCounter(Pos1.get(0), Pos1.get(1)));
			}else if(GameLogic.getPlayer2Counter() instanceof DiagonalCounter) {
				GameLogic.setPlayer1Counter(new DiagonalCounter(Pos1.get(0), Pos1.get(1)));
			}else if(GameLogic.getPlayer2Counter() instanceof DoubleDiceCounter) {
				GameLogic.setPlayer1Counter(new DoubleDiceCounter(Pos1.get(0), Pos1.get(1)));
			}else if(GameLogic.getPlayer2Counter() instanceof NeverGobackCounter) {
				GameLogic.setPlayer1Counter(new NeverGobackCounter(Pos1.get(0), Pos1.get(1)));
			}
			GameLogic.getPlayer1Counter().setxPos(Pos1.get(0));
			GameLogic.getPlayer1Counter().setyPos(Pos1.get(1));
			GameLogic.setPlayer2Counter(new BaseCounter(Pos2.get(0),Pos2.get(1)));
		}else {
			if(GameLogic.getPlayer1Counter() instanceof BackwardCounter) {
				GameLogic.setPlayer2Counter(new BackwardCounter(Pos2.get(0), Pos2.get(1)));
			}else if(GameLogic.getPlayer1Counter() instanceof BaseCounter) {
				GameLogic.setPlayer2Counter(new BaseCounter(Pos2.get(0), Pos2.get(1)));
			}else if(GameLogic.getPlayer1Counter() instanceof DiagonalCounter) {
				GameLogic.setPlayer2Counter(new DiagonalCounter(Pos2.get(0), Pos2.get(1)));
			}else if(GameLogic.getPlayer1Counter() instanceof DoubleDiceCounter) {
				GameLogic.setPlayer2Counter(new DoubleDiceCounter(Pos2.get(0), Pos2.get(1)));
			}else if(GameLogic.getPlayer1Counter() instanceof NeverGobackCounter) {
				GameLogic.setPlayer2Counter(new NeverGobackCounter(Pos2.get(0), Pos2.get(1)));
			}
			GameLogic.setPlayer2Counter((Counter)GameLogic.getPlayer1Counter());
			GameLogic.getPlayer2Counter().setxPos(Pos2.get(0));
			GameLogic.getPlayer2Counter().setyPos(Pos2.get(1));
			GameLogic.setPlayer1Counter(new BaseCounter(Pos1.get(0),Pos1.get(1)));
		}
	}

}
