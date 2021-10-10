package com.techelevator;

import com.techelevator.view.VendingMachine;

import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE};

	private Menu menu;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	static BigDecimal balance = new BigDecimal(0);
	static List<Item> currentStock = restockList();

	public void run() {


		while (true) {


			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				displayVendingMachineItems(currentStock);
				// display vending machine items
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				purchaseItem(currentStock);
				// do purchase
			}
		}
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}

	public static List<Item> restockList() {
		String path = "vendingmachine.csv";
		File inputFile = new File(path);

		List<Item> itemList = new ArrayList<>();

		try (Scanner fileScanner = new Scanner(inputFile)) {
			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				String[] categories = line.split(Pattern.quote("|"));
				BigDecimal bigDecimal = new BigDecimal(categories[2]);
				Item item = new Item(categories[0], categories[1], bigDecimal, categories[3]);
				itemList.add(item);
			}
		} catch (FileNotFoundException e) {
		}
		return itemList;
	}

	public static void displayVendingMachineItems(List<Item> itemList) {
		for (Item item : itemList) {
			System.out.println(item);
			if (item.getCount() == 0) {
				System.out.println("Sorry, item is sold out.");
			}

		}
	}

	public static void purchaseItem(List<Item> itemList) {
		Scanner userInput = new Scanner(System.in);
		int option = 0;
		BigDecimal shoppingCart = new BigDecimal(0);

		try {
			while (option >= 0 && option <= 3) {
				System.out.println(balance);
				System.out.println("---------------------------");
				System.out.println("Press: ");
				System.out.println("0 to return to main menu");
				System.out.println("1 to feed money");
				System.out.println("2 to make selection");
				System.out.println("3 to finish transaction");
				System.out.println("---------------------------");
				option = userInput.nextInt();
				while (option < 0 || option > 3) {
					System.out.println("invalid input, try again");
					option = userInput.nextInt();
				}

				if (option == 0) {
				} else if (option == 1) {
					balance = VendingMachine.feedMoney(balance, userInput);
				} else if (option == 2) {
					balance = VendingMachine.makeSelection(currentStock, userInput, shoppingCart, itemList, balance);
				} else if (option == 3) {

					balance = VendingMachine.finishTransaction(balance);
				}
			}

		} catch(InputMismatchException e){
			System.out.println("You didn't enter a number");
		}
	}
}

