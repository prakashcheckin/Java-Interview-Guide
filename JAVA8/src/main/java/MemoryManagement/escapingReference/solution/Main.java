package MemoryManagement.escapingReference.solution;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;


public class Main {

	public static void main(String[] args) {

		BookCollection bc = new BookCollection();
		
		//As we used collections unmodified list, this list can't be changed anywhere
		List<Book> books  = bc.getBooks();
		books.clear();
		
		//As we implemented interface. Interface don't have setprice method. so  price can't be changed
		BookReadOnly book = bc.findBookByName("Tom Jones");
		
		
		Price priceObject = book.getPrice();
		Map<String, Double> copyOfOriginalRateObject = priceObject.getRates();
		// this won't affect the original object
		copyOfOriginalRateObject.clear();
		
		//get price of book called Tom Jones in EUR
		System.out.println(bc.findBookByName("Tom Jones").getPrice().convert("EUR"));
		
	}
}
