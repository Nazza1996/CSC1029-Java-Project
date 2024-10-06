package part01;

import java.util.Scanner;

/**
 * The Menu class represents a text-based menu for user interaction.
 */
public class Menu {
	private String items[];
	private String title;
	private Scanner input;

	/**
	 * Constructs a Menu object with the specified title and menu items.
	 *
	 * @param title The title of the menu.
	 * @param data  An array of strings representing the menu items.
	 */
	public Menu(String title, String data[]) {
		this.title = title;
		this.items = data;
		this.input = new Scanner(System.in);
	}

	/**
	 * Displays the menu with its title and items.
	 */
	private void display() {
		System.out.println(title);
		for (int count = 0; count < title.length(); count++) {
			System.out.print("+");
		}
		System.out.println();
		for (int option = 1; option <= items.length; option++) {
			System.out.println(option + ". " + items[option - 1]);
		}
		System.out.println();
	}

	/**
	 * Prompts the user to enter a menu selection.
	 *
	 * @return The user's menu selection.
	 */
	public int getUserChoice() {
		display();
		System.out.print("Enter Selection: ");
		int value = input.nextInt();
		return value;
	}

	/**
	 * Clears the input buffer.
	 */
	public void clear() {
		input.nextLine();
	}
}
