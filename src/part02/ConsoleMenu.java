package part02;

import console.Console;

/**
 * The ConsoleMenu class represents a simple text-based menu for user
 * interaction using a console.
 */
public class ConsoleMenu {
	private String items[];
	private Console con;

	/**
	 * Constructs a ConsoleMenu object with the specified menu items and console.
	 *
	 * @param data    An array of strings representing the menu items.
	 * @param console The console object to use for input and output.
	 */
	public ConsoleMenu(String data[], Console console) {
		this.items = data;
		this.con = console;
	}

	/**
	 * Displays the menu items on the console.
	 */
	private void display() {
		for (int option = 1; option <= items.length; option++) {
			con.println(option + ". " + items[option - 1]);
		}
		con.println();
	}

	/**
	 * Prompts the user to enter a menu selection and returns the selected option.
	 *
	 * @return The user's menu selection.
	 */
	public int getUserChoice() {
		display();
		con.print("Enter Selection: ");
		int value = Integer.parseInt(con.readLn());
		con.clear();
		return value;
	}
}
