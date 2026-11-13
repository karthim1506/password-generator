import java.awt.*;
import java.awt.event.*;
import java.security.SecureRandom;

public class PasswordGenerator extends Frame implements ActionListener {

    Label title;
    Label lengthLabel;
    Label resultLabel;

    TextField lengthField;
    TextField resultField;

    Button generateButton;
    Button againButton;
    Button clearButton;

    SecureRandom random = new SecureRandom();

    // Meaningful words
    String[] words = {
        "Tiger", "Cloud", "River", "Eagle",
        "Apple", "Light", "Green", "Ocean",
        "Happy", "Dream", "Brave", "Storm",
        "Magic", "Smart", "Flame", "Earth",
        "Stone", "Royal", "Music", "Night",
        "Flower", "Silver", "Golden", "Planet"
    };

    public PasswordGenerator() {

        setTitle("Password Generator");
        setSize(550, 350);
        setLayout(new GridLayout(6, 2, 10, 10));

        // Title
        title = new Label("PASSWORD GENERATOR", Label.CENTER);

        // Password Length
        lengthLabel = new Label("Password Length:");
        lengthField = new TextField();

        // Output
        resultLabel = new Label("Generated Password:");
        resultField = new TextField();
        resultField.setEditable(false);

        // Automatic Validation
        lengthField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {

                String input = lengthField.getText();

                if (input.isEmpty()) {
                    resultField.setText("");
                    return;
                }

                try {
                    int length = Integer.parseInt(input);

                    if (length < 8 || length > 12) {
                        resultField.setText(
                            "Password length must be 8 to 12!"
                        );
                    } else {
                        resultField.setText("");
                    }

                } catch (NumberFormatException ex) {
                    resultField.setText("Enter numbers only!");
                }
            }
        });

        // Buttons
        generateButton = new Button("Generate Password");
        againButton = new Button("Generate Again");
        clearButton = new Button("Clear");

        // Add Components
        add(title);
        add(new Label(""));
        add(lengthLabel);
        add(lengthField);
        add(generateButton);
        add(againButton);
        add(clearButton);
        add(new Label(""));
        add(resultLabel);
        add(resultField);
        add(new Label(""));
        add(new Label(""));

        // Button Actions
        generateButton.addActionListener(this);
        againButton.addActionListener(this);
        clearButton.addActionListener(this);

        // Close Window
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    // Password Generation Method
    public String generatePassword(int length) {

        // Select meaningful word
        String word;

        do {
            word = words[random.nextInt(words.length)];
        } while (word.length() + 1 > length - 1);

        String special = "@#$%&*!";

        StringBuilder password = new StringBuilder();

        // Add meaningful word
        password.append(word);

        // Add special character
        password.append(
            special.charAt(random.nextInt(special.length()))
        );

        // Add random numbers
        String numbers = "0123456789";

        while (password.length() < length) {
            password.append(
                numbers.charAt(random.nextInt(numbers.length()))
            );
        }

        return password.toString();
    }

    public void actionPerformed(ActionEvent e) {

        // Clear Button
        if (e.getSource() == clearButton) {
            lengthField.setText("");
            resultField.setText("");
            return;
        }

        try {
            int length = Integer.parseInt(lengthField.getText());

            // Validation
            if (length < 8 || length > 12) {
                resultField.setText(
                    "Password length must be 8 to 12!"
                );
                return;
            }

            // Generate Password
            String password = generatePassword(length);

            // Display Output
            resultField.setText(password);

        } catch (NumberFormatException ex) {
            resultField.setText("Enter numbers only!");
        }
    }

    public static void main(String[] args) {
        new PasswordGenerator();
    }
}
