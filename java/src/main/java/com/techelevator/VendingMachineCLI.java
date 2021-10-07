package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE};

	private Menu menu;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() {

		List<Item> currentStock = restockList();

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
				Item item = new Item(categories[0], categories[1], Double.parseDouble(categories[2]), categories[3]);
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
			Scanner codeInput = new Scanner(System.in);
			System.out.println("Please enter code of item: ");
			String codeSelection = codeInput.nextLine().toUpperCase();
			System.out.println("Please enter quantity of item: ");
			int quantity = codeInput.nextInt();
			for (Item item: itemList) {
				if ((item.getCode().equals(codeSelection))){
					item.setCount(quantity);
				}
			}
		}
	}

