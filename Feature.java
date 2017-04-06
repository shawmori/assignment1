import java.util.*;

public class Feature {

	private int[] x, y;
	private Boolean[] vals;
	private int size;
	private int sign;


	public Feature(int size, int pixels, Random rand){
		this.x = new int[pixels];
		this.y = new int[pixels];
		vals = new Boolean[pixels];
		this.size = size;
		setPixels(rand);
		setVals(rand);
	}

	public Feature(int i){
		sign = i;
	}

	public void setSign(Image img) {
		int count = 0;
		Boolean[][] image = img.getImage();
		for(int i = 0; i < vals.length; i++){
			if(image[x[i]][y[i]] == vals[i])
				count++;
		}
		if(count > 2)
			sign = 1;
		else
			sign = 0;
	}

	public void setVals(Random rand){
		for(int i = 0; i < vals.length; i++){
			vals[i] = rand.nextBoolean();
		}
	}

	public void setPixels(Random rand){
		for(int i = 0; i < x.length; i++){
			int newX = rand.nextInt(size);
			int newY = rand.nextInt(size);
			while(duplicate(newX, newY)){
				newX = rand.nextInt(size);
				newY = rand.nextInt(size);
			}
			x[i] = newX;
			y[i] = newY;
		}
	}

	private boolean duplicate(int newX, int newY) {
		for(int i = 0; i < x.length; i++){
			if(x[i] == newX && y[i] == newY){
				return true;
			}
		}
		return false;
	}

	public void print(){
		System.out.println();
		System.out.println("Sign: " + sign);
		for(int i = 0; i < x.length; i++){
			System.out.println("[" + x[i] + ", " + y[i] + "]" + " == " + vals[i]);
		}
		
	}

	public int getSign() {
		return sign;
	}
}
