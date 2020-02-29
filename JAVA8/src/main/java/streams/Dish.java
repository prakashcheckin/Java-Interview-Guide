package streams;

public class Dish {
	
	private String type;
	private String name;
	private int calories;
	
	public Dish(String type, String name, int calories) 
	{
		this.type = type;
		this.name = name;
		this.calories = calories;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getCalories() {
		return calories;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}

	@Override
	public String toString() {
		return "Dish [type=" + type + ", name=" + name + ", Calories=" + calories + "]";
	}

}

