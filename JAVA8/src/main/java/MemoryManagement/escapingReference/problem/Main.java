package MemoryManagement.escapingReference.problem;

import java.util.List;
import java.util.Map;

public class Main {

	public static void main(String[] args) {

		BookCollection bc = new BookCollection();
		
		//Below are the all possibilities of escaping reference. Refer solutions package to all this problem solutions
		//Escaping reference - List is private variable but still user able to modify the list
		List<Book> books = bc.getBooks();
		books.clear();
		
		//User has access to change price value
		Book book = bc.findBookByName("Tom Jones");
		book.setPrice(200.0);
		
		 // convert method has access to change currency value directly
		 bc.findBookByName("Tom Jones").getPrice().convert("EUR");
		
		 // User has access to the private variable rates map
		 Price priceObject = book.getPrice();
		 Map<String,Double> rates = priceObject.getRates();
		 rates.clear();
		
		//get price of book called Tom Jones in EUR
		System.out.println(bc.findBookByName("Tom Jones").getPrice().convert("EUR"));
		
	}
}
