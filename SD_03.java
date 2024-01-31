import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SD_03 {
    private static ArrayList<Contact> contacts = new ArrayList<Contact>();
    private static Scanner scanner = new Scanner(System.in);
    private static String fileName = "contacts.txt";

    public static void main(String[] args) {
        boolean quit = false;
        int choice = 0;

        loadContacts();

        while (!quit) {
            System.out.println("Enter your choice:");
            System.out.println("1. Add a new contact");
            System.out.println("2. View contact list");
            System.out.println("3. Edit a contact");
            System.out.println("4. Delete a contact");
            System.out.println("5. Quit");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addContact();
                    break;
                case 2:
                    viewContacts();
                    break;
                case 3:
                    editContact();
                    break;
                case 4:
                    deleteContact();
                    break;
                case 5:
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }

        saveContacts();
    }

    private static void addContact() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter email address: ");
        String emailAddress = scanner.nextLine();

        Contact contact = new Contact(name, phoneNumber, emailAddress);
        contacts.add(contact);
    }

    private static void viewContacts() {
        if (contacts.size() == 0) {
            System.out.println("No contacts found");
            return;
        }

        for (int i = 0; i < contacts.size(); i++) {
            System.out.println((i + 1) + ". " + contacts.get(i).getName() + " - " + contacts.get(i).getPhoneNumber() + " - " + contacts.get(i).getEmailAddress());
        }
    }

    private static void editContact() {
        if (contacts.size() == 0) {
            System.out.println("No contacts found");
            return;
        }

        System.out.print("Enter the index of the contact to edit: ");
        int index = scanner.nextInt();
        scanner.nextLine();

        if (index < 1 || index > contacts.size()) {
            System.out.println("Invalid index");
            return;
        }

        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter email address: ");
        String emailAddress = scanner.nextLine();

        Contact contact = new Contact(name, phoneNumber, emailAddress);
        contacts.set(index - 1, contact);
    }

    private static void deleteContact() {
        if (contacts.size() == 0) {
            System.out.println("No contacts found");
            return;
        }

        System.out.print("Enter the index of the contact to delete: ");
        int index = scanner.nextInt();
        scanner.nextLine();

        if (index < 1 || index > contacts.size()) {
            System.out.println("Invalid index");
            return;
        }

        contacts.remove(index - 1);
    }

    private static void loadContacts() {
        try {
            File file = new File(fileName);

            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                contacts = (ArrayList<Contact>) ois.readObject();
                ois.close();
                fis.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading contacts: " + e.getMessage());
        }
    }

    private static void saveContacts() {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(contacts);
            oos.close();
            fos.close();
        } catch (IOException e) {
            System.out.println("Error saving contacts: " + e.getMessage());
        }
    }
}

class Contact implements Serializable {
    private String name;
    private String phoneNumber;
    private String emailAddress;

    public Contact(String name, String phoneNumber, String emailAddress) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}
