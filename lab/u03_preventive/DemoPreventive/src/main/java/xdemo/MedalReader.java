package xdemo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * LAB: use assert / exception to achieve preventive programming; make the program robust
 */

public class MedalReader {

    public static void main(String[] args) {
        MedalReader reader = new MedalReader();
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

                // ? Is this assert good?
                assert parts.length == 4: "part number must be 4 for each medal line";

                // ? 有考慮例外嗎
                String country = parts[0].replace("-", " "); // replace hyphen with space in country names
                int gold = Integer.parseInt(parts[1]);
                int silver = Integer.parseInt(parts[2]);
                int bronze = Integer.parseInt(parts[3]);

                System.out.printf("\n%s 獲得的金銀銅分別是：%d, %d, %d", country, gold, silver, bronze);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
