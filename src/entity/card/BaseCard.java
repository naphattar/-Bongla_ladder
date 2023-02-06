package entity.card;
//
import entity.Counter;

//
public abstract class BaseCard {
	private String name;
	private float ratePercent;
	
	public BaseCard(float ratePercent,String name) {
		this.setName(name);
		this.setRatePercent(ratePercent);
	}
	public BaseCard(float ratePercent) {
		this.setName("BaseCard");
		this.setRatePercent(ratePercent);
	}
	
	public String getName() {
		return name;
	}
	public float getRatePercent() {
		return ratePercent;
	}
	public void setRatePercent(float ratePercent) {
		this.ratePercent = Math.max(0,ratePercent);
	}
		
	public void setName(String name) {
		this.name = name;
		if(name == null) this.name = "BaseCard";
	}
	//Method
	public abstract void cardAction(Counter counter);
	
}
