
public class Image {

	private Boolean[][] image;
	private String category;
	private String classification = "";

	public Image(Boolean[][] image, String c){
		this.image = image;
		category = c;
	}

	public void print(){
		System.out.println(category);
		for(int i = 0; i < image.length; i++){
			for(int j = 0; j < image[0].length; j++){
				System.out.print(image[i][j]);
			}
			System.out.print("\n");
		}
	}

	public String getCategory(){
		return category;
	}
		
	public Boolean[][] getImage(){
		return image;
	}

	public void setClassification(String string) {
		this.classification = string;
		
	}
	
	public String getClassification(){
		return classification;
	}
}
