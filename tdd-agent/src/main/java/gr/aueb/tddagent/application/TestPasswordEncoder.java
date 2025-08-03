package gr.aueb.tddagent.application;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Utility program to generate and validate BCrypt hashes for password "User123!"
 * This program uses Spring Security's BCryptPasswordEncoder to ensure compatibility
 * with the application's security configuration.
 */
public class TestPasswordEncoder {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "User123!";
        
        System.out.println("=".repeat(60));
        System.out.println("BCrypt Password Hash Generator for Spring Security");
        System.out.println("=".repeat(60));
        System.out.println("Password: " + password);
        System.out.println();
        
        // Generate a fresh hash
        String freshHash = encoder.encode(password);
        System.out.println("✓ Fresh BCrypt hash generated:");
        System.out.println(freshHash);
        System.out.println();
        
        // Verify the fresh hash works
        boolean freshHashMatches = encoder.matches(password, freshHash);
        System.out.println("✓ Verification test: " + (freshHashMatches ? "PASSED" : "FAILED"));
        System.out.println();
        
        // Generate a few more hashes to show they're unique each time
        System.out.println("Additional valid hashes (each hash is unique):");
        for (int i = 1; i <= 3; i++) {
            String additionalHash = encoder.encode(password);
            boolean matches = encoder.matches(password, additionalHash);
            System.out.println(i + ". " + additionalHash + " (Valid: " + matches + ")");
        }
        System.out.println();
        
        System.out.println("=".repeat(60));
        System.out.println("SQL SEED DATA - Copy the hash below:");
        System.out.println("=".repeat(60));
        System.out.println("INSERT INTO users (username, password) VALUES ('testuser', '" + freshHash + "');");
        System.out.println();
        System.out.println("Or just the hash value:");
        System.out.println(freshHash);
        System.out.println("=".repeat(60));
    }
}