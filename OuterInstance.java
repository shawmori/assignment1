import java.util.*;

public class OuterInstance {
	
	String outcome;
	List<Boolean> vals;
	
	public OuterInstance(String outcome, List<Boolean> vals){
		this.outcome = outcome;
		this.vals = vals;
	}

	public List<Boolean> getVals() {
		return vals;
	}

	public String getOutcome() {
		return outcome;
	}
	
}
