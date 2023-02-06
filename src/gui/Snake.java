package gui;
//
import java.util.ArrayList;
import java.util.Random;

import entity.Counter;
import entity.NeverGobackCounter;

public class Snake implements Teleportable{
	private int headIndex;
	private int tailIndex;
	
	public Snake(int headIndex,int tailIndex) {
		this.setHeadIndex(headIndex);
		this.setTailIndex(tailIndex);
	}
	public int getHeadIndex() {
		return headIndex;
	}
	public void setHeadIndex(int headIndex) {
		this.headIndex = headIndex;
	}
	public int getTailIndex() {
		return tailIndex;
	}
	public void setTailIndex(int tailIndex) {
		this.tailIndex = tailIndex;
	}
	
	@Override
	public void teleport( Counter counter) {
		// TODO Auto-generated method stub
		if(counter.getBoardIndex() == this.getHeadIndex()) {
			if(counter instanceof NeverGobackCounter ) {
				// No effect and change counter backto class BaseCounter
				
			}else {
				counter.setBoardIndex(this.getTailIndex());
				ArrayList<Integer> newPos = Logic.GameLogic.changeIndextoPos(this.getTailIndex());
				counter.setxPos(newPos.get(0));
				counter.setyPos(newPos.get(1));
			}
		}
		
	}
	
}
