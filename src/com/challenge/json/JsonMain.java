package com.challenge.json;

import java.util.Scanner;

/**
 * JsonMain contains main(), which initializes the JSON string,
 * and prompts the user for an element name to search for in the JSON object.
 */
public class JsonMain {

    public static void main(String[] args) {

        // JSON Input
        String itemList = "{\"itemList\": {\"items\": ["
                + "{\"id\": \"item1\"},"
                + "{\"id\": \"item2\",\"label\": \"Item 2\"},"
                + "{\"id\": \"item3\"},"
                + "{\"id\": \"item4\"},"
                + "{\"id\": \"item5\"},"
                + "{\"id\": \"subItem1\","
                + "\"subItems\": ["
                + "{\"id\": \"subItem1Item1\",\"label\": \"SubItem 1\"},"
                + "{\"id\": \"subItem1Item2\",\"label\": \"SubItem 2\"},"
                + "{\"id\": \"subItem1Item3\",\"label\": \"SubItem 3\"}"
                + "]"
                + "},"
                + "{\"id\": \"item6\"},"
                + "{\"id\": \"item7\",\"label\": \"Item 7\"},"
                + "{\"id\": \"subItem2\","
                + "\"subItems\": {\"id\": \"item1\",\"label\": \"SubItem 2 item1\"}"
                + "}"
                + "]}}";

        // Run a loop to prompt the user for names to search for,
        // until they enter "exit".
        Scanner scanner = new Scanner(System.in);
        String element;
        try {
            while (true) {
                System.out.print("Enter an element name to search for (\"exit\" to close): ");
                element = scanner.nextLine();

                if (element != null) {
                    // Trim any possible quotes.
                    element = element.replaceAll("^\"|\"$", "");

                    // Check whether to exit.
                    if (element.equalsIgnoreCase("exit"))
                        break;

                    // Use the JsonParser to find the element's path.
                    System.out.println(String.format("\"%s\": %s\n",
                            element,
                            JsonParser.getFullPath(itemList, element)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (scanner != null)
                scanner.close();
        }
        System.out.println("\nGoodbye!");
    }
}
