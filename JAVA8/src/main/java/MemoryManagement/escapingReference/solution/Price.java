package MemoryManagement.escapingReference.solution;

import java.util.HashMap;
import java.util.Map;

// this class is immutable has all the values are protected
public class Price {

	private Map<String,Double> rates;
	private Double value;
	
	public Price(Double value) {
		this.value = value;
		rates = new HashMap<String,Double>();
		rates.put("USD", 1d);
		rates.put("GBP", 0.6);
		rates.put("EUR", 0.8);
	}
		
	public Double convert(String toCurrency) {
		
		if (toCurrency.equals("USD")) {
			return value;
		}
		else {
			Double conversion = rates.get("USD") / rates.get(toCurrency);
			return conversion * value;
		}
	}
	
	public String toString() {
		return this.value.toString();
	}
	
	// Original map copies data to new hash map and return new hashmap
	public Map<String,Double> getRates() {
		return new HashMap<String,Double>(this.rates);
	}
	
}
