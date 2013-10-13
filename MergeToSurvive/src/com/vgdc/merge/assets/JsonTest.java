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
		loadPersonDataList("data/People.json");
		printPerson(people.get("Nate"));
		printPerson(people.get("Kate"));
		printPerson(people.get("Archie"));		
	}

	private void loadPersonDataList(String path) {
		AssetList list = json.fromJson(AssetList.class, Gdx.files.internal(path));
		
		for(String personPath: list.paths){
			loadPersonData(personPath);
		}
	}

	private void loadAndPrintPathArray(String path) {
		FileHandle file = Gdx.files.internal(path);

		AssetList l = new AssetList();
		l.paths = new ArrayList<String>();
		l.paths.add("1");
		l.paths.add("2");
		l.paths.add("3");
		System.out.println("==json==");
		System.out.println(json.prettyPrint(l));

		AssetList data = json.fromJson(AssetList.class, file);

		System.out.println("==result==");
		for (String s : data.paths) {
			System.out.println(s);
		}
		System.out.println();
	}

	private void loadAndPrintPathMap(String path) {
		FileHandle file = Gdx.files.internal(path);

		AssetMap m = new AssetMap();
		m.pathMap = new HashMap<String, String>();
		m.pathMap.put("FILE1", "1.png");
		m.pathMap.put("FILE2", "2.png");
		m.pathMap.put("FILE3", "3.png");
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
		}
	}

	public void loadPersonData(String path) {
		FileHandle file = Gdx.files.internal(path);

		Person data = json.fromJson(Person.class, file);

		people.put(data.name, data);
	}

	private static class AssetList {
		public ArrayList<String> paths;
	}

	private static class AssetMap {
		public HashMap<String, String> pathMap;
	}

	private static class Person {
		String name;
		int age;
		ArrayList<PhoneNumber> numbers;
	}

	private static class PhoneNumber {
		String name;
		String number;
	}
}