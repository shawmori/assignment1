import java.io.File;
import java.io.IOException;
import java.util.*;

public class Setup {

	private List<Image> images;
	private List<Feature> features;
	private int size;
	private Perceptron perceptron;

	public Setup(String fname, int numFeatures){
		images = new ArrayList<Image>();
		load(fname);
		setFeatures(numFeatures);
		perceptron = new Perceptron(numFeatures, features);
		train(1000, images, perceptron);
		crossValidate();

	}

	public void train(int epochs, List<Image> images, Perceptron p){
		for(int i = 0; i < epochs; i++){
			if(epoch(p, images, i))
				return;
		}
	}

	public boolean epoch(Perceptron p, List<Image> images, int i){
		int correct = 0;
		int count = images.size();

		p.train(images);

		for(Image img : images){
			boolean approve = p.approve(img);
			if(img.getCategory().equals("#Yes") == approve)
				correct++;
		}

		if(correct == count){
			System.out.println("Epochs before converging: " + i);
			p.print();
			return true;
		}
		return false;
	}

	private void setFeatures(int amount) {
		Random r = new Random(1);
		features = new ArrayList<Feature>();
		for(int i = 0; i < amount; i++){
			Feature f = new Feature(size, 4, r);
			features.add(f);
		}

	}

	public void load(String fname){
		Boolean[][] image;
		String category;
		int x, y;
		try{
			java.util.regex.Pattern bit = java.util.regex.Pattern.compile("[01]");
			File f = new File(fname);
			Scanner sc = new Scanner(f);
			while(sc.hasNext()){
				if(!(sc.next().equals("P1"))){
					sc.close();
					throw new IOException();
				}
				category = sc.next();
				x = sc.nextInt();
				y = sc.nextInt();
				size = x;
				image = new Boolean[x][y];
				for(int i = 0; i < y; i++){
					for(int j = 0; j < x; j++){
						image[i][j] = (sc.findWithinHorizon(bit,0).equals("1"));
					}
				}
				images.add(new Image(image, category));
			}
			sc.close();
		}catch(IOException e){
			System.out.println("File image not a P1 PBM image");
		}

	}

	public void crossValidate(){
		System.out.println("==================================\n"
						+ "          CROSS VALIDATION\n"	
						+ "=================================");
		Random r = new Random(10);
		List<Image> allImages = new ArrayList<Image>(images);
		Collections.shuffle(allImages, r);
		List<Image> testImages = new ArrayList<Image>();
		List<Image> trainingImages = new ArrayList<Image>();

		for(int i = 0; i < allImages.size(); i++){
			if(i < allImages.size() / 2)
				trainingImages.add(allImages.get(i));
			else
				testImages.add(allImages.get(i));
		}
		features.remove(0);
		Perceptron crossP = new Perceptron(50, features);
		train(1000, trainingImages, crossP);
		crossP.setTestSet(testImages);
	}
	

	public static void main(String[] args){
		new Setup(args[0], Integer.valueOf(args[1]));
	}
}