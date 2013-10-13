package com.vgdc.merge.assets;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

//Test Methods

public class JsonTest {
	HashMap<String, Person> people = new HashMap<String, Person>();
	Json json = new Json();

	public void runTest() {
		loadPersonData("data/Nate.json");
		loadPersonData("data/Kate.json");
		loadPersonData("data/Archie.txt");

<<<<<<< .mine
	loadAndPrintPathArray("data/paths.txt");
	loadAndPrintPathMap("data/pathMap.json");
	printPerson(people.get("Nate"));
	printPerson(people.get("Kate"));
	printPerson(people.get("Archie"));
    }
=======
		loadData("data/paths.txt");
		print(people.get("Nate"));
		print(people.get("Kate"));
		print(people.get("Archie"));
	}
>>>>>>> .r5

<<<<<<< .mine
    private void loadAndPrintPathArray(String path) {
	FileHandle file = Gdx.files.internal(path);
=======
	private void loadData(String path) {
		FileHandle file = Gdx.files.internal(path);
>>>>>>> .r5

<<<<<<< .mine
	AssetList l = new AssetList();
	l.paths = new ArrayList<String>();
	l.paths.add("1");
	l.paths.add("2");
	l.paths.add("3");
	System.out.println("==json==");
	System.out.println(json.prettyPrint(l));
=======
		AssetList l = new AssetList();
		l.paths = new ArrayList<String>();
		l.paths.add("1");
		l.paths.add("2");
		l.paths.add("3");
		System.out.println(json.prettyPrint(l));
>>>>>>> .r5

		AssetList data = json.fromJson(AssetList.class, file);

<<<<<<< .mine
	System.out.println("==result==");
	for (String s : data.paths) {
	    System.out.println(s);
=======
		for (String s : data.paths) {
			System.out.println(s);
		}
>>>>>>> .r5
	}
<<<<<<< .mine
	System.out.println();
    }
=======
>>>>>>> .r5

<<<<<<< .mine
    private void loadAndPrintPathMap(String path) {
	FileHandle file = Gdx.files.internal(path);

	AssetMap m = new AssetMap();
	m.pathMap = new HashMap<String, String>();
	m.pathMap.put("FILE1", "1.png");
	m.pathMap.put("FILE2", "2.png");
	m.pathMap.put("FILE2", "3.png");
	System.out.println("==json==");
	System.out.println(json.prettyPrint(m));

	AssetMap data = json.fromJson(AssetMap.class, file);

	System.out.println("==result==");
	for (String s : data.pathMap.keySet()) {
	    System.out.println(s + ", " + data.pathMap.get(s));
	}
	System.out.println();
    }

    public void printPerson(Person person) {
	System.out.println(person.name + " (" + person.age + "):");
	for (PhoneNumber pn : person.numbers) {
	    System.out.println("\t" + pn.name + " - " + pn.number);
=======
	public void print(Person person) {
		System.out.println(person.name + " (" + person.age + "):");
		for (PhoneNumber pn : person.numbers) {
			System.out.println("\t" + pn.name + " - " + pn.number);
		}
>>>>>>> .r5
	}

	public void loadPersonData(String path) {
		FileHandle file = Gdx.files.internal(path);

		Person data = json.fromJson(Person.class, file);

		people.put(data.name, data);
	}

<<<<<<< .mine
    private static class AssetList {
	public ArrayList<String> paths;
    }
=======
	static class AssetList {
		public ArrayList<String> paths;
	}
>>>>>>> .r5
    
    private static class AssetMap {
	public HashMap<String, String> pathMap;
    }

<<<<<<< .mine
    private static class Person {
	String name;
	int age;
	ArrayList<PhoneNumber> numbers;
    }

    private static class PhoneNumber {
	String name;
	String number;
    }
=======
	static class Person {
		String name;
		int age;
		ArrayList<PhoneNumber> numbers;
	}

	static class PhoneNumber {
		public PhoneNumber() {
			// TODO Auto-generated constructor stub
		}

		public PhoneNumber(String name, String number) {
			this.name = name;
			this.number = number;
		}

		String name;
		String number;
	}
>>>>>>> .r5
}