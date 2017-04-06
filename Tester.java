import java.util.List;

public class Tester {
	
	private TreeBuilder tb;
	private List<OuterInstance> allInstances;
	private List<String> outcomes;
	
	private double outcomeOneTotal = 0;
	private double outcomeTwoTotal = 0;
	private double outcomeOneCorrect = 0;
	private double outcomeTwoCorrect = 0;
	
	public Tester(TreeBuilder tb, List<OuterInstance> instances, List<String> outcomes){
		this.tb = tb;
		this.allInstances = instances;
		this.outcomes = outcomes;
		test();
		printResults();
	}

	private void printResults() {
		System.out.println("");
		System.out.println("Accuracy:");
		System.out.println(outcomes.get(0) + ": " + outcomeOneCorrect + " out of " + outcomeOneTotal);
		System.out.println(outcomes.get(1) + ": " + outcomeTwoCorrect + " out of " + outcomeTwoTotal);
		System.out.println("");
		double percentage = (outcomeOneCorrect + outcomeTwoCorrect) / (outcomeOneTotal + outcomeTwoTotal) * 100;
		System.out.printf("Decision Tree percentage correct = %.2f%%\n", percentage);
		System.out.printf("Baseline percentage correct (%s) = %.2f%%\n\n", tb.getBaseline(), tb.getBProbability()*100);
		tb.display();
		
	}

	private void test() {
		for(OuterInstance i : allInstances){
			if(i.getOutcome().equals(outcomes.get(0))){
				outcomeOneTotal++;
				if(tb.test(i, tb.getRoot())){
					outcomeOneCorrect++;
				}
			}else{
				outcomeTwoTotal++;
				if(tb.test(i, tb.getRoot())){
					outcomeTwoCorrect++;
				}
			}
		}
		
	}
	
}
