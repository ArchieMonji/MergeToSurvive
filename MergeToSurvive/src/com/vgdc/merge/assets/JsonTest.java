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

		loadData("data/paths.txt");
		print(people.get("Nate"));
		print(people.get("Kate"));
		print(people.get("Archie"));
	}

	private void loadData(String path) {
		FileHandle file = Gdx.files.internal(path);

		AssetList l = new AssetList();
		l.paths = new ArrayList<String>();
		l.paths.add("1");
		l.paths.add("2");
		l.paths.add("3");
		System.out.println(json.prettyPrint(l));

		AssetList data = json.fromJson(AssetList.class, file);

		for (String s : data.paths) {
			System.out.println(s);
		}
	}

	public void print(Person person) {
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

	static class AssetList {
		public ArrayList<String> paths;
	}

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
}
