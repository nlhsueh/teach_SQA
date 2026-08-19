package demo.param;

public class StringUtils {

    public static boolean isPalindrome(String s) {
        if (s == null) {
            return false;
        }

        // Convert to lowercase and remove non-alphanumeric characters
        String cleaned = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

        // Use two-pointer technique to check if the cleaned string is a palindrome
        int left = 0;
        int right = cleaned.length() - 1;

        while (left < right) {
            if (cleaned.charAt(left) != cleaned.charAt(right)) {
                return false; // Not a palindrome
            }
            left++;
            right--;
        }

        return true; // It's a palindrome
    }
}
