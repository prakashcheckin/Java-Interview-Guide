package com.in28minutes.java.object.constructors;

class Animal {
	String name;

	public Animal(String name) {
		this.name = name;
		System.out.println("Animal Constructor with name"+ name);
	}
}

class Dog extends Animal {
	public Dog(String name) {
		super(name);
	}

	public Dog() {
		super("Default Dog Name");
	}
}

public class ConstructorExamples {
	public static void main(String[] args) {
		Dog dog = new Dog("Terry");
		Dog dog1 = new Dog();
	}
}
