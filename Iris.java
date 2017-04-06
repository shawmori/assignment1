
public class Iris {

	private double sepalL, sepalW, petalL, petalW;
	private String label;
	private String classification;

	public Iris(double sepalL, double sepalW, double petalL, double petalW, String label){
		this.sepalL = sepalL;
		this.sepalW = sepalW;
		this.petalL = petalL;
		this.petalW = petalW;
		this.label = label;
	}

	public double getEuclideanDistance(Iris i, double[] ranges){
		double w1 = sepalL;				//
		double x1 = sepalW;				//
		double y1 = petalL;				//
		double z1 = petalW;				//	Simple variable names for easier readability

		double w2 = i.getSepalL();		//
		double x2 = i.getSepalW();		//
		double y2 = i.getPetalL();		//
		double z2 = i.getPetalW();		//

		double w3 = Math.pow(w1 - w2, 2);
		double x3 = Math.pow(x1 - x2, 2);
		double y3 = Math.pow(y1 - y2, 2);
		double z3 = Math.pow(z1 - z2, 2);
		
		double r0 = Math.pow(ranges[0], 2);
		double r1 = Math.pow(ranges[1], 2);
		double r2 = Math.pow(ranges[2], 2);
		double r3 = Math.pow(ranges[3], 2);

		double eucDist = Math.sqrt((w3/r0) + (x3/r1) + (y3/r2) + (z3/r3));

		return eucDist;
	}

	public boolean correct(){
		if(label.equals(classification))
			return true;
		return false;
	}

	public void setClassification(String s){
		classification = s;
	}

	public double getPetalW() {
		return petalW;
	}

	public double getPetalL() {
		return petalL;
	}

	public double getSepalW() {
		return sepalW;
	}

	public double getSepalL() {
		return sepalL;
	}

	public String getLabel(){
		return this.label;
	}

	public void printResults() {

		System.out.println("Label: " + label);
		System.out.println("Classification: " + classification);
		System.out.println("");
	}
}
