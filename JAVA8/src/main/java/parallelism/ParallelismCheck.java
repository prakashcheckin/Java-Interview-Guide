package parallelism;

import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import streams.Java8Inaction;

/**
 * @author Prakash
 *
 */
public class ParallelismCheck {
	
	public static void main(String [] args) 
	{
		demo1();
		
	}
	
	public static void demo1() 
	{
		
		System.out.println("Sequential range sum done in: " + measureSumPerf(ParallelismCheck::sequentialSum, 10_000_000) + " msecs");
		
		System.out.println("Parallel range sum done in: " + measureSumPerf(ParallelismCheck::parallelSum, 10_000_000) + " msecs");
	
		System.out.println("Sequential range sum done in: " + measureSumPerf(ParallelismCheck::rangedSum, 10_000_000) + " msecs");

		System.out.println("Parallel range sum done in: " + measureSumPerf(ParallelismCheck::parallelRangedSum, 10_000_000) + " msecs");
		
	}
	

	//Whenever boxing and unboxing process involved, then parallel process is difficult to divide the stream in smaller chunks. 
	//That is why parallel takes more time than sequence
	
	/*
	 * iterategenerates boxed objects, which have to be unboxed to numbers before
	 * they can be added. iterateis difficult to divide into independent chunks to
	 * execute in parallel.
	 */

	public static long sequentialSum(long n) 
	{ 
		return Stream.iterate(1L, i -> i+1).limit(n).reduce(0L, Long::sum);
	}

	public static long parallelSum(long n) 
	{ 
		return Stream.iterate(1L, i -> i+1).limit(n).parallel().reduce(0L, Long::sum);
	}
	
	
	//LongSteam so no need to box and unboxing process. Parallel is much more faster than sequence 
	/*
	 * LongStream.rangeClosed works on primitive long numbers directly so there’s no
	 * boxing and unboxing overhead. LongStream.rangeClosed produces ranges of
	 * numbers, which can be easily split into independent chunks. For example, the
	 * range 1–20 can be split into 1–5, 6–10, 11–15, and 16–20.
	 */

	public static long rangedSum(long n) 
	{
		return LongStream.rangeClosed(1, n).reduce(0L, Long::sum);
	}
	
	public static long parallelRangedSum(long n) 
	{
		return LongStream.rangeClosed(1, n).parallel().reduce(0L, Long::sum); 
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
	
	
	
}

