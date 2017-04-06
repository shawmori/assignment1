
import java.util.*;

public class Perceptron {

	private List<Double> weights;
	private List<Feature> features;

	public Perceptron(int size, List<Feature> features){
		this.features = features;
		addDummy();
		setWeights(size);
	}

	private void addDummy() {
		Feature f = new Feature(1);
		features.add(0, f);
	}

	private void setWeights(int size) {
		weights = new ArrayList<Double>();
		Random r = new Random(10);
		for(int i = 0; i < size + 1; i++){
			weights.add(r.nextDouble());
		}

	}

	public boolean approve(Image img){
		double sum = 0;
		for(int i = 0; i < features.size(); i++){
			if(i > 0)
				features.get(i).setSign(img);
			sum += weights.get(i) * features.get(i).getSign();
		}
		if(sum > 0)
			return true;
		return false;
	}

	public void train(List<Image> images){
		for(Image img : images){
			Boolean approve = approve(img);
			if(approve && img.getCategory().equals("#other"))
				subtract();
			else if(!approve && img.getCategory().equals("#Yes")){
				add();
			}
		}
	}


	private void subtract() {
		for(int i = 0; i < features.size(); i++){
			double newValue = weights.get(i) - features.get(i).getSign();
			weights.set(i, newValue);
		}

	}

	private void add() {
		for(int i = 0; i < features.size(); i++){
			double newValue = weights.get(i) + features.get(i).getSign();
			weights.set(i, newValue);
		}

	}

	public void print() {
		System.out.println("");
		System.out.println("Final feature set:");
		System.out.println();
		for(int i = 0; i < features.size(); i++){
			if(i == 0){
				System.out.println("Dummy feature: Value 1");
				System.out.println();
			}
			else
				features.get(i).print();
			System.out.printf("Weight: %4.4f (4 d.p)\n", weights.get(i));
		}
	}
	
	public void setTestSet(List<Image> images){
		double sum = 0;
		for(Image img : images){
			sum = 0;
			for(int i = 0; i < features.size(); i++){
				sum += features.size() * weights.size();
			}
			if(sum > 0)
				img.setClassification("#Yes");
			else
				img.setClassification("#other");
		}
		double percentage = getTestPercentage(images);
		System.out.println("Cross validation accuracy: " + percentage + "%");
	}
	
	public double getTestPercentage(List<Image> images){
		double correct = 0;
		double total = 0;
		
		for(Image i : images){
			if(i.getCategory().equals(i.getClassification())){
				correct++;
			}
			total++;
		}
		System.out.println("\n" + correct + "/" + total);
		return correct / total * 100;
	}
}