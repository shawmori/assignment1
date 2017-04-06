import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.swing.plaf.synth.SynthSpinnerUI;

public class Main {

	private ArrayList<Iris> trainingData = new ArrayList<Iris>();
	private ArrayList<Iris> testData = new ArrayList<Iris>();

	int k;


	public Main(String trainingData, String testData, int k){
		readData(trainingData, 0);
		readData(testData, 1);
		this.k = k;

		classify();
	}

	public void classify(){
		double[] distances;
		int[] indices;

		for(Iris i : testData){
			distances = calculateDistances(i);
			if(k == 1){
				int index = getNearestNeighbour(distances);
				setClassification(i, index);
			}
			else{
				indices = getNearestNeighbours(distances);
				String classification = findMajority(indices);
				i.setClassification(classification);
			}
		}

		for(Iris i : testData){
			i.printResults();
		}

		double correct = 0;
		for(Iris i : testData){
			if(i.correct())
				correct++;
		}

		double percentage = correct / testData.size() * 100;
		System.out.println(percentage + "% correct.");

	}

	private String findMajority(int[] indices) {
		HashMap<String, Integer> labels = new HashMap<String, Integer>();

		for(int i : indices){
			String label = trainingData.get(i).getLabel();
			if(!labels.containsKey(label)){
				labels.put(label, 1);
			}else{
				labels.put(label, labels.get(label) + 1);
			}
		}
		int max = 0;
		String classification = null;

		for(String s : labels.keySet()){
			if(labels.get(s) > max){
				classification = s;
				max = labels.get(s);
			}
		}
		return classification;
	}

	private void setClassification(Iris i, int index) {
		String classification = trainingData.get(index).getLabel();
		i.setClassification(classification);
	}

	public int getNearestNeighbour(double[] distances){
		double lowest = distances[0];
		int index = 0;
		for(int i = 1; i < distances.length; i++){
			if(distances[i] < lowest){
				lowest = distances[i];
				index = i;
			}
		}
		return index;
	}

	public int[] getNearestNeighbours(double[] distances){
		int[] indices = new int[k];

		for(int i = 0; i < k; i++){
			indices[i] = getNearestNeighbour(distances);
			distances[indices[i]] = Double.MAX_VALUE;
		}

		return indices;
	}

	/**
	 * Calculates the Euclidean distance between the Iris parameter test and every training Iris in the training data.
	 * @param test
	 * @return
	 */
	public double[] calculateDistances(Iris test){
		double[] distances = new double[trainingData.size()];
		for(int i = 0; i < distances.length; i++){
				distances[i] = test.getEuclideanDistance(trainingData.get(i), getRanges());
			}
		return distances;
	}

	public double[] getRanges() {
		double min = Double.MIN_VALUE;
		double max = Double.MAX_VALUE;

		double[] highRanges = new double[]{min, min, min, min};
		double[] lowRanges = new double[]{max, max, max, max};

		for(Iris i : trainingData){

			double[] theseFeatures = new double[]{i.getSepalL(), i.getSepalW(), i.getPetalL(), i.getPetalW()};

			if(theseFeatures[0] < lowRanges[0])
				lowRanges[0] = theseFeatures[0];
			else if(theseFeatures[0] > highRanges[0])
				highRanges[0] = theseFeatures[0];

			if(theseFeatures[1] < lowRanges[1])
				lowRanges[1] = theseFeatures[1];
			else if(theseFeatures[1] > highRanges[1])
				highRanges[1] = theseFeatures[1];

			if(theseFeatures[2] < lowRanges[2])
				lowRanges[2] = theseFeatures[2];
			else if(theseFeatures[2] > highRanges[2])
				highRanges[2] = theseFeatures[2];

			if(theseFeatures[3] < lowRanges[3])
				lowRanges[3] = theseFeatures[3];
			else if(theseFeatures[3] > highRanges[3])
				highRanges[3] = theseFeatures[3];
		}

		double[] finalRanges = new double[]{highRanges[0] - lowRanges[0], highRanges[1] - lowRanges[1], highRanges[2] - lowRanges[2], highRanges[3] - lowRanges[3]};
		return finalRanges;

	}

	/**
	 * Reads data from a file and creates the training and test collections of irises.
	 *
	 * @param filename
	 * @param i (0 for training set, 1 for test set)
	 */
	public void readData(String filename, int i){
		try{
			Scanner sc = new Scanner(new File(filename));
			while(sc.hasNext()){
				double sepalL = sc.nextDouble();
				double sepalW = sc.nextDouble();
				double petalL = sc.nextDouble();
				double petalW = sc.nextDouble();
				String label = sc.next();

				if(i == 0)
					trainingData.add(new Iris(sepalL, sepalW, petalL, petalW, label));
				else if(i == 1)
					testData.add(new Iris(sepalL, sepalW, petalL, petalW, label));
			}
			sc.close();
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
	}






	public static void main(String[] args){
		try {
			Scanner sc = new Scanner(System.in);
			System.out.print("Choose a value for k: ");
			int k;
			if(sc.hasNextInt()){
				k = sc.nextInt();
			}else{
				sc.close();
				throw new IllegalArgumentException();
			}
			if((k % 2) == 0){
				sc.close();
				throw new IllegalArgumentException();
			}
			new Main(args[0], args[1], k);
			sc.close();
         } catch (IllegalArgumentException e){
        	 System.err.println("Value for k must be an odd number");
         }
	}
}
