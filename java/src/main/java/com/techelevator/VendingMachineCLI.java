package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.InputMismatchException;
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
			BigDecimal totalDollars = new BigDecimal(0);
			try {
				while (option >= 0 && option <= 2) {

					System.out.println("---------------------------");
					System.out.println("Press: ");
					System.out.println("0 to return to main menu");
					System.out.println("1 to feed money");
					System.out.println("2 to make selection");
					System.out.println("---------------------------");
					option = userInput.nextInt();
					//if (option < 0 || option > 2) {
						while (option < 0 || option > 2) {
							System.out.println("invalid input, try again");
							option = userInput.nextInt();
						}
					//}

					if (option == 0) {
						return;
					} else if (option == 1) {
						boolean moreMoney = true;

						while (moreMoney) {
							System.out.println("Please enter money in whole dollar amounts");
							BigDecimal dollars = new BigDecimal(userInput.nextInt());
							userInput.nextLine();
							totalDollars = totalDollars.add(dollars);
							System.out.println("current money provided: " + totalDollars);
							boolean isValid = false;
							while (isValid == false) {
								System.out.println("Do you have more bills?");
								String answer = userInput.nextLine().toLowerCase();
								if (answer.equals("n")) {
									moreMoney = false;
									isValid = true;
								} else if (answer.equals("y")) {
									moreMoney = true;
									isValid = true;
								} else {
									System.out.println("invalid input, try again");
								}
							}
						}
					} else if (option == 2) {
						userInput.nextLine();
						System.out.println("Please enter code of item: ");
						String codeSelection = userInput.nextLine().toUpperCase();
						System.out.println("Please enter quantity of item: ");
						int quantity = userInput.nextInt();
						for (Item item: itemList) {
							BigDecimal shoppingCart = item.getPrice();
							BigDecimal quantityConverted = new BigDecimal(quantity);
							shoppingCart.multiply(quantityConverted);
							if ((item.getCode().equals(codeSelection))){
								if (item.getCount() == 0){
									System.out.println("SOLD OUT");
								} else {
									if ((shoppingCart.compareTo(totalDollars) == 0) || (shoppingCart.compareTo(totalDollars) == -1)) {
										if (quantity <= item.getCount()) {
											item.setCount(quantity);
											if (item.getType().equals("Chip")) {
												System.out.println("Vending item! Crunch, Crunch, Yum!");
											} else if (item.getType().equals("Candy")) {
												System.out.println("Vending item! Munch, Munch, Yum!");
											} else if (item.getType().equals("Drink")) {
												System.out.println("Vending item! Glug, Glug, Yum!");
											} else {
												System.out.println("Vending item! Chew, Chew, Yum!");
											}
										} else {
											System.out.println("Sorry, item stock insufficient.");
										}
									} else {
										System.out.println("Sorry, not enough money available.");
									}
								}
							}
						}
					}
				}
			} catch (InputMismatchException e){
				System.out.println("You didn't enter a number");
			}


		}
	}

