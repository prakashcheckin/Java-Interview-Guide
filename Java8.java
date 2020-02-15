import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Demo {

	public static void main(String[] args) {
		
		
		//create streams
		Stream<String> song = Stream.of("gently", "down", "the", "stream");
		
		List<Employee> list = new ArrayList<Employee>();
		
		list.add(new Employee("Prakash", 11));
		list.add(new Employee("Nithya", 12));
		list.add(new Employee("z", 13));
		list.add(new Employee("Josh", 14));
		list.add(new Employee("Aksh", 15));
		list.add(new Employee("Priya", 15));
		list.add(new Employee("Prakash", 11));
		
		//filter
		Stream<Employee> streamsWithEmployeeforFilter = list.stream();
		Stream<Employee>  streamsWithEmployeeWithFilteredResult= streamsWithEmployeeforFilter.filter(v -> v.getName() != null);
		List<Employee> listAftreFiltered  = streamsWithEmployeeWithFilteredResult.collect(Collectors.toList());
		listAftreFiltered.forEach(v -> System.out.println("Filtered result>>> " + v.toString()));
		
		//collect as list
		List<Employee> lsfiltered = list.stream().filter(v -> v.getName().startsWith("P")).collect(Collectors.toList());
		lsfiltered.forEach(v -> System.out.println(v.getName()));
		
		//collect as set
		Set<Employee> result = list.stream().filter(v -> v.getName().startsWith("P")).collect(Collectors.toSet());
		result.forEach(v -> System.out.println("Set result >> " +  v.getName()));
		
		//map
	    Stream<Employee> streamsWithEmployee= list.stream();
		Stream<String> steamWithString = streamsWithEmployee.map(v -> v.getName());
		steamWithString.forEach(v -> System.out.println("Map convertion >>> " + v));
		
	
		//HashMap iteration
		Map<Integer, String> map = new HashMap<>();
		
		map.put(1, "prakash");
		map.put(2, "nith");
		map.put(3, "proj");
		map.put(4, "par");
		
		map.keySet().stream().forEach(v -> System.out.println(map.get(v)));
		map.forEach((k, v) -> System.out.println((k + ":" + v)));
		
		//Insert object into hashmap
		Map<String, Integer> maptest = new HashMap<>();
		Stream<Employee> stream = list.stream().filter(v -> v.getName() != null);
		stream.forEach(v -> maptest.put(v.getName(), v.getId()));
		maptest.forEach((k, v) -> System.out.println((k + ":::::" + v)));
		
		//String join
		String joinResult = String.join("-", "2015", "10", "31" );
		System.out.println(joinResult);
		
		List<Employee> ls = list.stream().filter(v -> v.getName() != null).collect(Collectors.toList());
		Collections.sort(ls, (obj1, obj2) -> obj1.getName().compareTo(obj2.getName()));
		ls.forEach(v -> System.out.println(v.toString()));
		
	}

}
