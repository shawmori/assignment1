import java.util.*;

public class TreeBuilder {
	
	private List<String> allAttributes, outcomes;
	private List<OuterInstance> allInstances;
	private Node root;
	
	private double bProbability;
	private String baseline;
	
	
	public TreeBuilder(List<String> atts, List<OuterInstance> instances, List<String> outcomes){
		allAttributes = atts;
		allInstances = instances;
		this.outcomes = outcomes;
		
		baseline = getMajority(instances);
		bProbability = getBaselineProbability(instances);
		
		root = buildTree(allInstances, allAttributes);
		
		System.out.println("Decision Tree succesfully created.");
	}

	

	public Node buildTree(List<OuterInstance> instances, List<String> attributesO){
		List<String> attributes = new ArrayList<String>(attributesO);
		int number = getInstances(instances);
		if(instances.isEmpty()){
			return new Node(null, null, null, true, baseline, bProbability, 0);
		}else if(pure(instances)){
			return new Node(null, null, null, true, instances.get(0).getOutcome(), 1, number);
		}else if(attributes.isEmpty()){
			String outcome = getMajority(instances);
			double prob = getProbablity(instances);
			return new Node(null, null, null, true, outcome, prob, number);
		}else{
			String bestAtt = "";
			int index = 0;
			List<OuterInstance> bestInstsTrue = null;
			List<OuterInstance> bestInstsFalse = null;
			double lowestPurity = Double.MAX_VALUE;
			for(int i = 0; i < attributes.size(); i++){
				double purity = calculatePurity(attributes.get(i), instances);
				if(purity < lowestPurity){
					bestAtt = attributes.get(i);
					bestInstsTrue = splitTrue(attributes.get(i), instances);
					bestInstsFalse = splitFalse(attributes.get(i), instances);
					lowestPurity = purity;
					index = i;
				}
			}
			attributes.remove(index);
			Node left = buildTree(bestInstsTrue, attributes);
			Node right = buildTree(bestInstsFalse, attributes);
			return new Node(bestAtt, left, right, false, null, 0, 0);
		}
	}
	
	private int getInstances(List<OuterInstance> instances) {
		int count = 0;
		for(OuterInstance i : instances)
			count++;
		return count;
	}

	private double getProbablity(List<OuterInstance> instances) {
		double outcomeOneCount = 0;
		double outcomeTwoCount = 0;
		for(OuterInstance i : instances){
			if(i.getOutcome().equals(outcomes.get(0))){
				outcomeOneCount++;
			}else{
				outcomeTwoCount++;
			}
		}
		double total = outcomeOneCount + outcomeTwoCount;
		if(outcomeOneCount > outcomeTwoCount){
			return outcomeOneCount / total;
		}else if(outcomeOneCount < outcomeTwoCount){
			return outcomeTwoCount / total;
		}else 
			return 0.5;
		
	}
	
	private double getBaselineProbability(List<OuterInstance> instances) {
		double outcomeOneCount = 0;
		double outcomeTwoCount = 0;
		for(OuterInstance i : instances){
			if(i.getOutcome().equals(baseline)){
				outcomeOneCount++;
			}else{
				outcomeTwoCount++;
			}
		}
		double total = outcomeOneCount + outcomeTwoCount;
		if(outcomeOneCount > outcomeTwoCount){
			return outcomeOneCount / total;
		}else if(outcomeOneCount < outcomeTwoCount){
			return outcomeTwoCount / total;
		}else 
			return 0.5;
	}

	private String getMajority(List<OuterInstance> instances) {
		int outcomeOneCount = 0;
		int outcomeTwoCount = 0;
		for(OuterInstance i : instances){
			if(i.getOutcome().equals(outcomes.get(0))){
				outcomeOneCount++;
			}else{
				outcomeTwoCount++;
			}
		}
		if(outcomeOneCount > outcomeTwoCount){
			return outcomes.get(0);
		}else if(outcomeOneCount < outcomeTwoCount){
			return outcomes.get(1);
		}else{
			Random r = new Random();
			int i = r.nextInt(2);
			if(i == 0){
				return outcomes.get(0);
			}else{
				return outcomes.get(1);
			}
		}
	}

	public double calculatePurity(String attribute, List<OuterInstance> ints){
		
		int index = 0; 
		for(int i = 0; i < allAttributes.size(); i++){
			if(allAttributes.get(i).equals(attribute))
				index = i;
		}
		
		int outcomeOneTrue = 0;
		int outcomeTwoTrue = 0;
		int outcomeOneFalse = 0;
		int outcomeTwoFalse = 0;
		
		for(int i = 0; i < ints.size(); i++){
			Boolean instanceValue = ints.get(i).getVals().get(index);
			String instanceOutcome = ints.get(i).getOutcome();
			if(instanceValue && instanceOutcome.equals(outcomes.get(0))){
				outcomeOneTrue++;
			}else if(instanceValue && instanceOutcome.equals(outcomes.get(1))){
				outcomeTwoTrue++;
			}else if(!instanceValue && instanceOutcome.equals(outcomes.get(0))){
				outcomeOneFalse++;
			}else{
				outcomeTwoFalse++;
			}
		}

		double trueTotal = outcomeOneTrue + outcomeTwoTrue;
		double falseTotal = outcomeOneFalse + outcomeTwoFalse;
		if(trueTotal == 0 || falseTotal == 0)
			return 1;
		double total = trueTotal + falseTotal;
		double truePurity = (outcomeOneTrue * outcomeTwoTrue) / (trueTotal * trueTotal);
		double falsePurity = (outcomeOneFalse * outcomeTwoFalse) / (falseTotal * falseTotal);
		double purity = (trueTotal / total * truePurity) + (falseTotal / total * falsePurity);
		return purity;
	}	
	
	public List<OuterInstance> splitTrue(String att, List<OuterInstance> ints){
		List<OuterInstance> split = new ArrayList<OuterInstance>();
		int index = 0;
		for(int i = 0; i < allAttributes.size(); i++){
			if(allAttributes.get(i).equals(att))
				index = i;
		}
		
		for(OuterInstance i : ints){
			if(i.getVals().get(index))
				split.add(i);
		}
		return split;
	}
	
	public List<OuterInstance> splitFalse(String att, List<OuterInstance> ints){
		List<OuterInstance> split = new ArrayList<OuterInstance>();
		int index = 0;
		for(int i = 0; i < allAttributes.size(); i++){
			if(allAttributes.get(i).equals(att))
				index = i;
		}
		
		for(OuterInstance i : ints){
			if(!(i.getVals().get(index)))
				split.add(i);
		}
		return split;
	}
	
	public Boolean pure(List<OuterInstance> ints){
		int count = 1;
		for(int i = 1; i < ints.size(); i++){
			if(ints.get(i).getOutcome().equals(ints.get(0).getOutcome()))
				count++;
		}
		if(count == ints.size())
			return true;
		return false;
	}

	public Boolean test(OuterInstance instance, Node node) {
		String att = node.getAttribute();
		if(node.isLeaf()){
			if(node.getOutcome().equals(instance.getOutcome())){
				return true;
			}else{
				return false;
			}
		}
		int index = 0;
		for(int i = 0; i < allAttributes.size(); i++){
			if(allAttributes.get(i).equals(att)){
				index = i;
				break;
			}
		}
		if(instance.getVals().get(index)){
			return test(instance, node.getLeft());
		}else{
			return test(instance, node.getRight());
		}
	}
	
	public Node getRoot(){
		return root;
	}
	
	public void display(){
		root.display("");
	}



	public double getBProbability() {
		return bProbability;
	}



	public String getBaseline() {
		return baseline;
	}
}
