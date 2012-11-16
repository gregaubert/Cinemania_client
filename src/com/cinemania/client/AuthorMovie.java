
public class AuthorMovie extends Movie {
  private int sellingPrice;

  public AuthorMovie(String title, int year, int sellingPrice) {
		super(title, year);
		setDecreasingRate(0.8); // TODO: according to the context of the game (city and random)
		setPeopleInit(50); // TODO: according to the context of the game (city and random)
		this.sellingPrice = sellingPrice;
		
  }

  @Override
  public int sellingPrice() {
		return sellingPrice;
  }

}
