package demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class MedalReader03 {

    public static void main(String[] args) {
        MedalReader03 reader = new MedalReader03();
        reader.read();
    }

    public void read() {
        try {
            // Load the JSON file
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new File("src/main/resources/medals.json"));

            // Loop through each medal entry
            for (JsonNode node : rootNode.get("medals")) {
                String[] parts = node.asText().split(" ");

                // Check if the data has exactly 4 parts
                if (parts.length != 4) {
                    System.err.println("Invalid data format: " + node.asText());
                    continue;  // Skip invalid data
                }

                try {
                    String country = parts[0].replace("-", " "); // replace hyphen with space in country names
                    int gold = Integer.parseInt(parts[1]);
                    int silver = Integer.parseInt(parts[2]);
                    int bronze = Integer.parseInt(parts[3]);

                    System.out.printf("\n%s 獲得的金銀銅分別是：%d, %d, %d", country, gold, silver, bronze);
                } catch (NumberFormatException e) {
                    System.err.println("\n獎牌的數量必須是數字: " + node.asText());
                    e.printStackTrace();
                    // Continue to the next entry even if this one fails
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
