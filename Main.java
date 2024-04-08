class Animal {
    int age;

    Animal() {
        this(0);
    }

    Animal(int years) {
        this.age = years;
    }

    Animal(String sound) {
        System.out.println("Animal sound: " + sound);
    }

    void makeSound() {
        System.out.println("Animal makes a sound");
    }

    void eat() {
        System.out.println("Animal eats food");
    }
}

class Dog extends Animal {
    String breed;

    Dog() {
        super();
    }

    Dog(int years) {
        super(years);
        this.breed = "Unknown";
    }

    Dog(String breed) {
        this("Bark", breed); // Utilizăm constructorul din clasa derivată
    }

    Dog(String sound, String breed) {
        super(sound);
        this.breed = breed;
    }

    void wagTail() {
        System.out.println("Dog wags its tail");
    }

    void bark() {
        System.out.println("Dog barks");
    }
}

public class Main {
    public static void main(String[] args) {
        Animal animal1 = new Animal();
        animal1.makeSound();
        animal1.eat();

        Dog dog1 = new Dog();
        dog1.makeSound();
        dog1.wagTail();
        dog1.eat();

        Dog dog2 = new Dog("Labrador");
        dog2.makeSound();
        dog2.wagTail();
        dog2.bark();
        dog2.eat();

        Animal animal2 = new Dog(3);
        animal2.makeSound();
        animal2.eat();

        Dog dog3 = new Dog("Golden Retriever");
        dog3.makeSound();
        dog3.wagTail();
        dog3.bark();
        dog3.eat();
    }
}

