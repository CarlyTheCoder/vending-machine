package com.techelevator;

import com.techelevator.view.AuditInformation;

import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
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
					boolean moreMoney = true;
					//BigDecimal totalDollars = balance;
					while (moreMoney) {
						System.out.println("Please enter money in whole dollar amounts");
						BigDecimal dollars = new BigDecimal(userInput.nextInt());
						userInput.nextLine();

						balance = balance.add(dollars);
						System.out.println("current money provided: " + balance);
						AuditInformation.getTransactionDateTimeStamp("Feed Money: $" + dollars + " " + "$" +balance);
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
					displayVendingMachineItems(currentStock);
					userInput.nextLine();
					System.out.println("Please enter code of item: ");
					String codeSelection = userInput.nextLine().toUpperCase();
					System.out.println("Please enter quantity of item: ");
					int quantity = userInput.nextInt();
					for (Item item : itemList) {
						shoppingCart = item.getPrice();
						BigDecimal quantityConverted = new BigDecimal(quantity);
						shoppingCart = shoppingCart.multiply(quantityConverted);
						if ((item.getCode().equals(codeSelection))) {
							if (item.getCount() == 0) {
								System.out.println("SOLD OUT");
							} else {
								if ((shoppingCart.compareTo(balance) == 0) || (shoppingCart.compareTo(balance) == -1)) {
									if (quantity <= item.getCount()) {
										item.setCount(quantity);
										if (item.getType().equals("Chip")) {
											balance = balance.subtract(shoppingCart);
											System.out.println("Vending item! Crunch, Crunch, Yum!");
											AuditInformation.getTransactionDateTimeStamp(item.getName() + " " + item.getCode() + " " + "$" + balance.add(shoppingCart) + " " + "$" +balance);
										} else if (item.getType().equals("Candy")) {
											balance = balance.subtract(shoppingCart);
											System.out.println("Vending item! Munch, Munch, Yum!");
											AuditInformation.getTransactionDateTimeStamp(item.getName() + " " + item.getCode() + " " + "$" + balance.add(shoppingCart) + " " + "$" +balance);
										} else if (item.getType().equals("Drink")) {
											balance = balance.subtract(shoppingCart);
											System.out.println("Vending item! Glug, Glug, Yum!");
											AuditInformation.getTransactionDateTimeStamp(item.getName() + " " + item.getCode() + " " + "$" + balance.add(shoppingCart) + " " + "$" +balance);
										} else {
											balance = balance.subtract(shoppingCart);
											System.out.println("Vending item! Chew, Chew, Yum!");
											AuditInformation.getTransactionDateTimeStamp(item.getName() + " " + item.getCode() + " " + "$" + balance.add(shoppingCart) + " " + "$" +balance);
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
				} else if (option == 3) {

					int quartersReturned = (balance.divide(new BigDecimal(.25)).intValue());
					BigDecimal change = balance.subtract(new BigDecimal(quartersReturned * .25));
					int dimesReturned = 0;
					AuditInformation.getTransactionDateTimeStamp("Give Change: " + "$" + balance + " " + "$0.00");
					if (change.compareTo(new BigDecimal(.10)) >= 0) {
						dimesReturned = (change.divide(new BigDecimal(.10)).intValue());
					}

					change = change.subtract(new BigDecimal(dimesReturned * .10));
					int nickelsReturned = 0;
					if (change.compareTo(BigDecimal.ZERO) > 0) {
						nickelsReturned++;
					}
					System.out.println("quarters: " + quartersReturned);
					System.out.println("dimes: " + dimesReturned);
					System.out.println("nickels: " + nickelsReturned);
					balance = BigDecimal.ZERO;
				}
			}

		} catch(InputMismatchException e){
			System.out.println("You didn't enter a number");
		}
	}
}

