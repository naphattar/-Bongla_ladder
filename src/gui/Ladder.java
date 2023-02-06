package gui;
//
import java.util.ArrayList;
import java.util.Random;

import entity.Counter;

public class Ladder implements Teleportable {
	private int lowerIndex;
	private int upperIndex;
	
	
	public Ladder(int lowerIndex,int upperIndex) {
		this.setLowerIndex(lowerIndex);
		this.setUpperIndex(upperIndex);
	}
	public int getLowerIndex() {
		return lowerIndex;
	}
	public void setLowerIndex(int lowerIndex) {
		this.lowerIndex = lowerIndex;
	}
	public int getUpperIndex() {
		return upperIndex;
	}
	public void setUpperIndex(int upperIndex) {
		this.upperIndex = upperIndex;
	}
	@Override
	public void teleport(Counter counter) {
		if(counter.getBoardIndex() == this.getLowerIndex()) {
			counter.setBoardIndex(this.getUpperIndex());
			ArrayList<Integer> newPos = Logic.GameLogic.changeIndextoPos(this.getUpperIndex());
			counter.setxPos(newPos.get(0));
			counter.setyPos(newPos.get(1));
		}
		
	}
	

}
