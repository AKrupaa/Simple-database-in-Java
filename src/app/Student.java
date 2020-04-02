package app;

import java.util.Scanner;

public class Student implements Comparable<Student> {

	private String firstName;
	private String surName;
	private String address;
	private Integer age;
	private Gender gender; // true - male <-> false - female
	private Integer indexNumber;
	private Scanner scanner = new Scanner(System.in);

	// pusty konstruktor
	public Student() {
		// nothing here
	}

	// konstruktor do celów sprawdzenia błędów w bazie danych
	public Student(String firstName, String surName, String address, Integer age, Gender gender, Integer indexNumber) {
		this.setFirstName(firstName);
		this.setSurName(surName);
		this.setAddress(address);
		this.setAge(age);
		this.setGender(gender);
		this.setIndexNumber(indexNumber);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Integer getIndexNumber() {
		return indexNumber;
	}

	public void setIndexNumber(Integer indexNumber) {
		this.indexNumber = indexNumber;
	}

	public void setIndexNumber() {
		Integer indexNumber;

		while (true) {
			try {
				System.out.println("Numer indeksu:");
				indexNumber = Integer.valueOf(scanner.nextLine());
				if (indexNumber < 0) {
					throw new NoPositiveNumberException(indexNumber);
				}
				break;
			} catch (NumberFormatException e) {
				System.out.println(e.getMessage());
				System.out.println("Podczas wprowadzania liczby popełniono błąd");
				System.out.println("Spróbuj ponownie!");
			} catch (NoPositiveNumberException e) {
				System.out.println(e.getMessage());
				System.out.println("Podczas wprowadzania liczby popełniono błąd");
				System.out.println("Spróbuj ponownie!");
			}
		}

		this.indexNumber = indexNumber;
	}

	public void setFirstName() {
		String firstName = "";

		System.out.println("Imię: ");
		firstName = scanner.nextLine();
		System.out.println("Wprowadzono: " + firstName);

		this.firstName = firstName;
	}

	public void setSurName() {
		String surName = "";

		System.out.println("Nazwisko: ");
		firstName = scanner.nextLine();
		System.out.println("Wprowadzono: " + surName);

		this.surName = surName;
	}

	public void setAddress() {
		String address = "";

		System.out.println("Adres: ");
		address = scanner.nextLine();
		System.out.println("Wprowadzono: " + address);

		this.address = address;
	}

	public void setAge() {
		Integer age;

		while (true) {
			try {
				System.out.println("Wiek:");
				age = Integer.valueOf(scanner.nextLine());
				if (age < 16 || age > 122) {
					throw new WrongAgeException(age);
				}
				break;
			} catch (NumberFormatException e) {
				System.out.println(e.getMessage());
				System.out.println("Podczas wprowadzania liczby popełniono błąd");
				System.out.println("Spróbuj ponownie!");
			} catch (WrongAgeException e) {
				System.out.println(e.getMessage());
				System.out.println("Spróbuj ponownie!");
			}
		}

		this.age = age;
	}

	public void setGender() {
		Gender gender;

		while (true) {
			try {
				System.out.println("Płeć");
				System.out.println("Wpisz F dla kobiety. Wpisz M dla mężczyzny.");
				gender = Gender.valueOf(scanner.nextLine());
				break;
			} catch (NumberFormatException e) {
				System.out.println(e.getMessage());
				System.out.println("Podczas wprowadzania liczby popełniono błąd");
				System.out.println("Spróbuj ponownie!");
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				System.out.println("Wprowadzono niepoprawne dane");
				System.out.println("Spróbuj ponownie!");
			}

		}

		this.gender = gender;	// true - male <-> false - female
	}

	@Override
	public int compareTo(Student o) {
		// TODO Auto-generated method stub
		return 0;
	}

	// For an instance

	// Character.Gorgon gor = new Character.Gorgon();
	// Then do

	// gor instanceof Monster
	// For a Class instance do

	// Class<?> clazz = Character.Gorgon.class;
	// Monster.class.isAssignableFrom(clazz);

	// ALT + SHIFT + F =>> ładne formatowanie tekstu
}