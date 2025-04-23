/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * See the LICENSE file or visit <https://www.gnu.org/licenses/>.
 */


 /*  
wertuipeter
Last updated: 19.04.2025
*/ 


import java.awt.*;
import java.io.*;
import javax.swing.*;

public class UltimateDecoderGUI {

    private static JTextArea resultArea;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UltimateDecoderGUI::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Ultimate Decoder V2.1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(30, 30, 30));

        JLabel version = new JLabel("Ultimate Decoder V2.1");
        version.setFont(new Font("Monospaced", Font.BOLD, 18));
        version.setForeground(new Color(180, 180, 180));
        version.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Ultimate Decoder");
        title.setFont(new Font("SansSerif", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(title);
        mainPanel.add(version);
        mainPanel.add(Box.createVerticalStrut(20));

        String[] options = {"Caesar Cipher", "Atbash Cipher", "Vigenère Cipher", "Brute Force All"};
        JComboBox<String> cipherOptions = new JComboBox<>(options);
        cipherOptions.setFont(new Font("SansSerif", Font.PLAIN, 20));
        cipherOptions.setMaximumSize(new Dimension(400, 40));
        cipherOptions.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton decodeButton = new JButton("Decode");
        decodeButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        decodeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton clearButton = new JButton("Clear Output");
        clearButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        clearButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton saveButton = new JButton("Save Output");
        saveButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        resultArea = new JTextArea(25, 70);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        resultArea.setBackground(Color.BLACK);
        resultArea.setForeground(Color.GREEN);
        resultArea.setCaretColor(Color.WHITE);
        resultArea.setMargin(new Insets(10, 10, 10, 10));
        resultArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        mainPanel.add(cipherOptions);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(decodeButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(clearButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(saveButton);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(scrollPane);

        decodeButton.addActionListener(e -> {
            String selected = (String) cipherOptions.getSelectedItem();
            switch (selected) {
                case "Caesar Cipher" -> handleCaesar();
                case "Atbash Cipher" -> handleAtbash();
                case "Vigenère Cipher" -> handleVigenere();
                case "Brute Force All" -> handleBruteForceAll();
            }
        });

        clearButton.addActionListener(e -> resultArea.setText(""));

        saveButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showSaveDialog(null);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (PrintWriter out = new PrintWriter(file)) {
                    out.print(resultArea.getText());
                    JOptionPane.showMessageDialog(null, "File saved successfully.");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error saving file: " + ex.getMessage());
                }
            }
        });

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static void handleCaesar() {
        int option = JOptionPane.showConfirmDialog(null, "Do you have a Caesar key?", "Caesar Cipher", JOptionPane.YES_NO_OPTION);
        String input = JOptionPane.showInputDialog("Enter the string to decode:");
        if (input == null) return;

        if (option == JOptionPane.YES_OPTION) {
            String keyInput = JOptionPane.showInputDialog("Enter the key:");
            try {
                int key = Integer.parseInt(keyInput);
                resultArea.setText("Decoded:\n" + decodeCaesar(input, key));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid key. Must be an integer.");
            }
        } else {
            StringBuilder brute = new StringBuilder("Brute Force Caesar:\n");
            for (int i = 0; i < 26; i++) {
                brute.append("Key ").append(i).append(": ").append(decodeCaesar(input, i)).append("\n");
            }
            resultArea.setText(brute.toString());
        }
    }

    private static void handleAtbash() {
        String input = JOptionPane.showInputDialog("Enter the string to decode with Atbash:");
        if (input != null) {
            resultArea.setText("Decoded with Atbash:\n" + decodeAtbash(input));
        }
    }

    private static void handleVigenere() {
        String input = JOptionPane.showInputDialog("Enter the string to decode:");
        String key = JOptionPane.showInputDialog("Enter the key:");
        if (input != null && key != null) {
            resultArea.setText("Decoded with Vigenère:\n" + decodeVigenere(input, key));
        }
    }

    private static void handleBruteForceAll() {
        String input = JOptionPane.showInputDialog("Enter the string to decode with brute force:");
        if (input == null) return;

        StringBuilder output = new StringBuilder("--- Caesar Cipher ---\n");
        for (int i = 0; i < 26; i++) {
            output.append("Key ").append(i).append(": ").append(decodeCaesar(input, i)).append("\n");
        }
        output.append("\n--- Atbash Cipher ---\n");
        output.append("Atbash: ").append(decodeAtbash(input)).append("\n");

        resultArea.setText(output.toString());
    }

    private static String decodeCaesar(String s, int k) {
        StringBuilder decoded = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                c = (char) ((c - base - k + 26) % 26 + base);
            }
            decoded.append(c);
        }
        return decoded.toString();
    }

    private static String decodeAtbash(String s) {
        StringBuilder decoded = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                c = (char) (base + ('Z' - Character.toUpperCase(c)));
                if (Character.isLowerCase(base)) c = Character.toLowerCase(c);
            }
            decoded.append(c);
        }
        return decoded.toString();
    }

    private static String decodeVigenere(String text, String key) {
        StringBuilder decoded = new StringBuilder();
        key = key.toUpperCase();
        int keyIndex = 0;

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                int shift = key.charAt(keyIndex % key.length()) - 'A';
                char decodedChar = (char) ((c - base - shift + 26) % 26 + base);
                decoded.append(decodedChar);
                keyIndex++;
            } else {
                decoded.append(c);
            }
        }
        return decoded.toString();
    }
}