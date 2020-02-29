package parallelism;

import java.util.function.Function;
import java.util.stream.LongStream;

public class ParallelismCheck2 {

	public static void main(String[] args) {
		
		demo2();
	}
	
	public static void demo2() 
	{
		System.out.println("SideEffect sum done in: " + measureSumPerf(ParallelismCheck2::sideEffectSum, 10_000_000L) + " msecs" );
		
		System.out.println("SideEffect Parallel sum done in: " + measureSumPerf(ParallelismCheck2::sideEffectParallelSum, 10_000_000L) + " msecs" );
	}
	
	public static long measureSumPerf(Function<Long, Long> adder, long n) 
	{
		long fastest = Long.MAX_VALUE;
		for (int i = 0; i < 10; i++) 
		{
			long start = System.nanoTime();
			long sum = adder.apply(n);
			long duration = (System.nanoTime() - start) / 1_000_000;
			System.out.println("Result: " + sum); 
			if (duration < fastest) 
				fastest = duration; 
		}
		return fastest; 
	}
	
	/*
	 * Iterative code is quite common to write this sort of code, especially for developers who
	 * are familiar with imperative programming paradigms. This code closely
	 * resembles what you’re used to doing when iterating imperatively a list of
	 * numbers: you initialize an accumulator and traverse the elements in the list
	 * one by one, adding them on the accumulator. What’s wrong with this code?
	 * Unfortunately, it’s irretrievably broken because it’s fundamentally
	 * sequential. You have a data race on every access of total. And if you try to
	 * fix that with synchronization, you’ll lose all your parallelism. To
	 * understand this, let’s try to turn the Stream into a parallel one:
	 */
	public static long sideEffectSum(long n) 
	{ 
		Accumulator accumulator = new Accumulator(); 
		LongStream.rangeClosed(1, n).forEach(accumulator::add); 
		return accumulator.total; 
	}

	/*
	 * When use parallel stream output will be difference for each, all very distant
	 * from the correct value of 50000005000000. This is caused by the fact that
	 * multiple threads are concurrently accessing the accumulator and in particular
	 * executing total += value, which, despite its appearance, isn’t an atomic
	 * operation. The origin of the problem is that the method invoked inside the
	 * forEach block has the side effect of changing the mutable state of an object
	 * shared among multiple threads. It’s mandatory to avoid these kinds of
	 * situations if you want to use parallel Streams without incurring similar bad
	 * surprises.
	 */
	/*
	 * Now you know that shared mutable state doesn’t play well with parallel
	 * streams and with parallel computations in general. Keep in mind that avoiding shared
	 * mutable state ensures that your parallel Stream will produce the right
	 * result.
	 */ 
	public static long sideEffectParallelSum(long n) 
	{ 
		Accumulator accumulator = new Accumulator(); 
		LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add); 
		return accumulator.total; 
	}

}

class Accumulator 
	{ 
	public long total = 0; 
	
	public void  add(long value) 
	{ 
		total += value; 
	} 
	
	/*
	 * Stream sources and decomposability Source Decomposability 
	 * ArrayList - Excellent
	 * LinkedList - Poor 
	 * IntStream.range - Excellent 
	 * Stream.iterate - Poor 
	 * HashSet - Good
	 * TreeSet - Good
	 */

}
