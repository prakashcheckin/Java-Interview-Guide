package streams;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/*Lambda expressions can make your code more readable and flexible. 
 * Consider converting anonymous classes to lambda expressions, but be wary of subtle semantic differences such as the meaning of the keyword this and shadowing of variables. 
 * Method references can make your code more readable compared to lambda expressions. 
 * Consider converting iterative collection processing to use the Streams API.  Lambda expressions can help remove boilerplate code associated with several object-oriented design patterns such as strategy, template method, observer, chain of responsibility, and factory. 
 * Lambda expressions can be unit tested, but in general you should focus on testing the behavior of the methods where the lambda expressions appear.  Consider extracting complex lambda expressions into regular methods.
 * Lambda expressions can make stack traces less readable.  The peek method of a stream is useful to log intermediate values as they flow past at certain points in a stream pipeline.*/

public class Java8Inaction {

	public static void main(String[] args)
	{
		Trader raoul = new Trader("Raoul", "Cambridge");
		Trader mario = new Trader("Mario","Milan"); 
		Trader alan = new Trader("Alan","Cambridge");
		Trader brian = new Trader("Brian","Cambridge");
		List<Transaction> transactions = Arrays.asList(new Transaction(brian, 2011, 300), new Transaction(raoul, 2012, 1000), new Transaction(raoul, 2011, 400), new Transaction(mario, 2012, 710), new Transaction(mario, 2012, 700), new Transaction(alan, 2012, 950) );

		// Find all transactions in 2011 and sort by value (small to high)
		List<Transaction> transaction2011 = transactions.stream().filter(v -> v.getYear() == 2011).collect(Collectors.toList());
		Collections.sort(transaction2011, (a , b)-> a.getValue() < b.getValue() ? -1 : 1 );
		transaction2011.forEach(v -> System.out.println(v));
		
		//book approach
		List<Transaction> transaction2011Java8 = transactions.stream().filter(v -> v.getYear() == 2011).sorted(Comparator.comparing(v -> v.getValue())).collect(Collectors.toList());
		transaction2011Java8.forEach(v -> System.out.println(v));
		
		//What are all the unique cities where the traders work?
		List<String> distinctCity = transactions.stream().map(v -> v.getTrader().getCity()).distinct().collect(Collectors.toList());
		//Another way of collecting all the unique cities
		Set<String> cities = transactions.stream() .map(transaction -> transaction.getTrader().getCity()) .collect(Collectors.toSet());

		
		//Find all traders from Cambridge and sort them by name
		List<Trader> l1 = transactions.stream().map(v -> v.getTrader()).filter(v -> v.getCity().equals("Cambridge")).sorted(Comparator.comparing(v -> v.getName())).collect(Collectors.toList());
		l1.forEach(v -> System.out.println(v));
		
		//Return a string of all traders’ names sorted alphabetically
		String str1 = transactions.stream().map(v -> v.getTrader().getName()).distinct().sorted().collect(Collectors.joining(" ,"));
		System.out.println("Joining >> " + str1);
		
		//Are any traders based in Milan?
		boolean boo = transactions.stream().anyMatch(v -> v.getTrader().getCity().equals("Milan"));
		System.out.println("Are any traders based in Milan?" + boo);
		
		//Find the transaction with the smallest value
		Optional<Transaction> smallestTransaction = transactions.stream().min(Comparator.comparing(Transaction::getValue));


		//sum of all transacation values
		int totalTrans = transactions.stream().mapToInt(v -> v.getValue()).sum();
		System.out.println(totalTrans);
		
		
		//reduce method is for doing manual aggregate functions instead of using default functions like sum, average etc.. 
		// 1+2+3+4
		Optional<Integer> reduce = Arrays.asList(1,2,3,4).stream().reduce((a, b) -> a + b);
		System.out.println(reduce.get());
		
		//reduce with Identity value - identity value is a default value. if stream doesn't have any value to calculate. then reduce will return the identity value. 
		// calculation process - 0+1+2+3+4
		Integer reduceWithIdentityValue = Arrays.asList(1,2,3,4).stream().reduce(0, (a, b) -> a + b);
		System.out.println(reduceWithIdentityValue); 	
		
		
		//Grouping
		Dish d1 = new Dish("FISH", "prawns", 500);Dish d2 = new Dish("FISH", "salmon", 450); Dish d3 = new Dish("MEAT", "pork", 1000);Dish d4 = new Dish("MEAT", "beef", 800);Dish d5 = new Dish("MEAT", "chicken", 650);Dish d6 = new Dish("OTHER", "french", 500);
		Dish d7 = new Dish("OTHER", "rice", 600);Dish d8 = new Dish("OTHER", "season fruit", 200);Dish d9 = new Dish("OTHER", "pizza", 400); 
		List<Dish> menu = Arrays.asList(d1,d2,d3,d4,d5,d6,d7,d8,d9);
		
		//grouping based on dish
		Map<String, List<Dish>> dishesByType = menu.stream().collect(Collectors.groupingBy(dish -> {return dish.getType();}));
		System.out.println("Grouping based on dish >>" + dishesByType);
		
		// Dishes based on Type
		Map<String, List<String>> caloricLevelsByType = menu.stream().collect( Collectors.groupingBy(Dish::getType, Collectors.mapping( dish -> dish.getName(), Collectors.toList()) ));
		System.out.println("Dishes based on Type >>" + caloricLevelsByType);
		
		//Grouping based on CaloricLevel
		Map<CaloricLevel, List<Dish>> dishesByCalories = menu.stream().
				collect(Collectors.groupingBy(dish -> {
						if (dish.getCalories() <= 400)  return CaloricLevel.DIET; 
						else if (dish.getCalories() <= 700)	return CaloricLevel.NORMAL;
						else return CaloricLevel.FAT; 
						}));
		 System.out.println("Grouping based on CaloricLevel" + dishesByCalories);
		
		
		
		 // Another way of doing reduce using reducing method.
		 String collectName = menu.stream().collect(Collectors.reducing("", dish -> dish.getName(),  (s1, s2) -> s1 + s2 ));
		 System.out.println("collect" + collectName);
		 
		 int collectCalories = menu.stream().collect(Collectors.reducing(0 , dish -> dish.getCalories(),  (s1, s2) -> s1 + s2 ));
		 System.out.println("collect" + collectCalories);
		 
		 // Finding maximum calories using reducing
		 Optional<Dish> mostCalorieDish = menu.stream().collect(Collectors.reducing( (dd1, dd2) -> dd1.getCalories() > dd2.getCalories() ? dd1 : dd1));
		 System.out.println("mostCalorieDish" + mostCalorieDish);
		 
		 //partitioning which is greater than 3 and less than 3
		  Map<Boolean, List<Integer>> collect = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).collect(Collectors.partitioningBy(num -> num > 3)); 
		  System.out.println(collect);
		  
		  //print 1 to 10 num
		  Stream.iterate(0, n -> n + 1).limit(10).forEach(x -> System.out.println(x));
		  
		  Integer reduce2 = Stream.iterate(0, n -> n + 1).limit(4).parallel().reduce(0, (a,b) -> a+b );
		  System.out.println(reduce2);
	
	}
	
}
