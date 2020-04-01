package app;

import java.util.Scanner;

public class Student implements Comparable<Student> {

    private String firstName;
    private String surName;
    private String address;
    private Integer age;
    private Boolean gender;     // true => male | false => female
    private Integer indexNumber;
    private Scanner scanner = new Scanner(System.in);

    // pusty konstruktor
    public Student() {
        // nothing here
    }

	public Student(String firstName, String surName, String address, Integer age, Boolean gender, Integer indexNumber) {
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

	public Boolean getGender() {
		return gender;
	}

	public void setGender(Boolean gender) {
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
        while(true)
        { 
            try {
                System.out.println("Numer indeksu:");
                indexNumber = Integer.valueOf(scanner.nextLine());
                if(indexNumber < 0) {
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

	@Override
	public int compareTo(Student o) {
		// TODO Auto-generated method stub
		return 0;
	}

}