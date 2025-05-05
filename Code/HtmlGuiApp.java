import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class HtmlGuiApp {
    private static HtmlRoot root = new HtmlRoot();
    private static JTextArea outputArea;
    private static JButton undoButton;
    private static JButton redoButton;
    private static JComboBox<String> fontFamilyCombo;
    private static JComboBox<String> fontSizeCombo;
    private static JComboBox<String> alignmentCombo;
    private static JButton colorPickerButton;
    private static JButton bgColorPickerButton;
    private static JButton pageBgColorButton; // New button for page background color
    private static JToggleButton darkModeToggle;
    private static JButton previewButton;
    private static Color currentColor = Color.BLACK;
    private static Color currentBgColor = Color.WHITE;
    private static Color pageBackgroundColor = Color.WHITE; // New field for page background color
    private static boolean darkMode = false;

    public static void main(String[] args) {
        JFrame frame = new JFrame("CodeWeaver HTML Studio");
        frame.setSize(1200, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        // Control Panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        controlPanel.setBackground(new Color(245, 245, 245)); // Light gray for modern look

        // Preview Panel
        JPanel previewPanel = new JPanel(new BorderLayout());
        previewPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        previewPanel.setBackground(new Color(245, 245, 245));

        // Styling constants for sleeker GUI
        Font buttonFont = new Font("Segoe UI", Font.PLAIN, 14);
        Color primaryColor = new Color(33, 150, 243); // Modern blue
        Color textColor = Color.WHITE;
        Color accentColor = new Color(255, 87, 34); // Orange for clear button
        Color secondaryColor = new Color(117, 117, 117); // Gray for undo/redo
        Color successColor = new Color(76, 175, 80); // Green for save

        // Create formatting controls
        JPanel formatPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formatPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(189, 189, 189)),
            "Text Formatting",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new Font("Segoe UI", Font.PLAIN, 12)
        ));
        formatPanel.setBackground(new Color(245, 245, 245));

        fontFamilyCombo = new JComboBox<>(new String[]{"Arial", "Times New Roman", "Courier New", "Verdana", "Georgia"});
        fontFamilyCombo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        fontSizeCombo = new JComboBox<>(new String[]{"12px", "14px", "16px", "18px", "20px", "24px", "28px", "32px"});
        fontSizeCombo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        alignmentCombo = new JComboBox<>(new String[]{"left", "center", "right"});
        alignmentCombo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        colorPickerButton = createStyledButton("Text Color", buttonFont, primaryColor, textColor);
        bgColorPickerButton = createStyledButton("Element BG Color", buttonFont, primaryColor, textColor);
        pageBgColorButton = createStyledButton("Page BG Color", buttonFont, primaryColor, textColor); // New button
        darkModeToggle = new JToggleButton("Dark Mode");
        darkModeToggle.setFont(buttonFont);
        darkModeToggle.setBackground(primaryColor);
        darkModeToggle.setForeground(textColor);
        darkModeToggle.setBorder(new RoundedBorder(10, primaryColor));
        previewButton = createStyledButton("Preview", buttonFont, primaryColor, textColor);

        formatPanel.add(createStyledLabel("Font Family:"));
        formatPanel.add(fontFamilyCombo);
        formatPanel.add(createStyledLabel("Font Size:"));
        formatPanel.add(fontSizeCombo);
        formatPanel.add(createStyledLabel("Alignment:"));
        formatPanel.add(alignmentCombo);
        formatPanel.add(createStyledLabel("Text Color:"));
        formatPanel.add(colorPickerButton);
        formatPanel.add(createStyledLabel("Element Background:"));
        formatPanel.add(bgColorPickerButton);
        formatPanel.add(createStyledLabel("Page Background:"));
        formatPanel.add(pageBgColorButton);
        formatPanel.add(createStyledLabel("Theme:"));
        formatPanel.add(darkModeToggle);

        // Create buttons
        JButton h1Button = createStyledButton("Add H1 Heading", buttonFont, primaryColor, textColor);
        JButton h2Button = createStyledButton("Add H2 Heading", buttonFont, primaryColor, textColor);
        JButton pButton = createStyledButton("Add Paragraph", buttonFont, primaryColor, textColor);
        JButton ulButton = createStyledButton("Add Unordered List", buttonFont, primaryColor, textColor);
        JButton olButton = createStyledButton("Add Ordered List", buttonFont, primaryColor, textColor);
        JButton aButton = createStyledButton("Add Link", buttonFont, primaryColor, textColor);
        JButton iButton = createStyledButton("Add Image", buttonFont, primaryColor, textColor);
        undoButton = createStyledButton("Undo", buttonFont, secondaryColor, textColor);
        redoButton = createStyledButton("Redo", buttonFont, secondaryColor, textColor);
        JButton clearButton = createStyledButton("Clear All", buttonFont, accentColor, textColor);
        JButton saveButton = createStyledButton("Save to File", buttonFont, successColor, textColor);
        previewButton = createStyledButton("Preview", buttonFont, primaryColor, textColor);

        // Add components to control panel
        controlPanel.add(formatPanel);
        controlPanel.add(Box.createVerticalStrut(20)); // Increased spacing
        controlPanel.add(h1Button);
        controlPanel.add(h2Button);
        controlPanel.add(pButton);
        controlPanel.add(ulButton);
        controlPanel.add(olButton);
        controlPanel.add(aButton);
        controlPanel.add(iButton);
        controlPanel.add(Box.createVerticalStrut(20));
        controlPanel.add(undoButton);
        controlPanel.add(redoButton);
        controlPanel.add(Box.createVerticalStrut(20));
        controlPanel.add(previewButton);
        controlPanel.add(clearButton);
        controlPanel.add(saveButton);

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        outputArea.setBackground(Color.WHITE);
        outputArea.setForeground(new Color(33, 33, 33));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(189, 189, 189)),
            "HTML Preview",
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new Font("Segoe UI", Font.PLAIN, 12)
        ));

        // Add action listeners
        h1Button.addActionListener(e -> addElement("H1", "Enter heading text:"));
        h2Button.addActionListener(e -> addElement("H2", "Enter heading text:"));
        pButton.addActionListener(e -> addElement("P", "Enter paragraph text:"));
        ulButton.addActionListener(e -> addList(false));
        olButton.addActionListener(e -> addList(true));
        aButton.addActionListener(e -> addLink());
        iButton.addActionListener(e -> addImage());
        undoButton.addActionListener(e -> undo());
        redoButton.addActionListener(e -> redo());
        clearButton.addActionListener(e -> clearAll(frame));
        saveButton.addActionListener(e -> saveToFile(frame));
        colorPickerButton.addActionListener(e -> chooseColor());
        bgColorPickerButton.addActionListener(e -> chooseBgColor());
        pageBgColorButton.addActionListener(e -> choosePageBgColor()); // New action listener
        darkModeToggle.addActionListener(e -> toggleDarkMode());
        previewButton.addActionListener(e -> showPreview());

        // Add components to frame
        previewPanel.add(scrollPane, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.WEST);
        frame.add(previewPanel, BorderLayout.CENTER);

        // Update buttons initially
        updateUndoRedoButtons();
        updateOutput();

        frame.setVisible(true);
    }

    private static JButton createStyledButton(String text, Font font, Color bgColor, Color textColor) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(bgColor);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setBorder(new RoundedBorder(10, bgColor));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private static JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setForeground(new Color(33, 33, 33));
        return label;
    }

    private static void chooseColor() {
        currentColor = JColorChooser.showDialog(null, "Choose Text Color", currentColor);
    }

    private static void chooseBgColor() {
        currentBgColor = JColorChooser.showDialog(null, "Choose Background Color", currentBgColor);
    }

    private static void choosePageBgColor() {
        pageBackgroundColor = JColorChooser.showDialog(null, "Choose Page Background Color", pageBackgroundColor);
        updateOutput();
    }

    private static void toggleDarkMode() {
        darkMode = !darkMode;
        if (darkMode) {
            outputArea.setBackground(new Color(60, 63, 65));
            outputArea.setForeground(Color.WHITE);
        } else {
            outputArea.setBackground(Color.WHITE);
            outputArea.setForeground(new Color(33, 33, 33));
        }
    }

    private static void showPreview() {
        JFrame previewFrame = new JFrame("HTML Preview");
        previewFrame.setSize(800, 600);

        JEditorPane editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        editorPane.setEditable(false);
        editorPane.setText(root.render());

        JScrollPane scrollPane = new JScrollPane(editorPane);
        previewFrame.add(scrollPane);
        previewFrame.setVisible(true);
    }

    private static void addList(boolean ordered) {
        String items = JOptionPane.showInputDialog("Enter list items (comma separated):");
        if (items != null && !items.trim().isEmpty()) {
            ListElement list = new ListElement(ordered);
            for (String item : items.split(",")) {
                list.addItem(item.trim());
            }
            applyCurrentStyles(list);
            root.addElement(list);
            updateOutput();
            updateUndoRedoButtons();
        }
    }

    private static void applyCurrentStyles(HtmlElement element) {
        String fontFamily = (String) fontFamilyCombo.getSelectedItem();
        String fontSize = (String) fontSizeCombo.getSelectedItem();
        String alignment = (String) alignmentCombo.getSelectedItem();
        String hexColor = String.format("#%02x%02x%02x",
            currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue());
        String bgHexColor = String.format("#%02x%02x%02x",
            currentBgColor.getRed(), currentBgColor.getGreen(), currentBgColor.getBlue());

        element.addStyle("font-family", fontFamily);
        element.addStyle("font-size", fontSize);
        element.addStyle("color", hexColor);
        element.addStyle("background-color", bgHexColor);

        if (element instanceof Heading) {
            ((Heading) element).setAlignment(alignment);
        } else if (element instanceof Paragraph) {
            ((Paragraph) element).setAlignment(alignment);
        } else if (element instanceof HtmlImage) {
            ((HtmlImage) element).setAlignment(alignment);
        }
    }

    private static void addElement(String type, String prompt) {
        String text = JOptionPane.showInputDialog(prompt);
        if (text != null && !text.trim().isEmpty()) {
            HtmlElement element = null;
            switch (type) {
                case "H1":
                    element = new Heading(text, 1);
                    break;
                case "H2":
                    element = new Heading(text, 2);
                    break;
                case "P":
                    element = new Paragraph(text);
                    ((Paragraph) element).setAlignment((String) alignmentCombo.getSelectedItem());
                    break;
            }
            if (element != null) {
                applyCurrentStyles(element);
                root.addElement(element);
                updateOutput();
                updateUndoRedoButtons();
            }
        }
    }

    private static void addLink() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JTextField urlField = new JTextField();
        JTextField textField = new JTextField();
        JCheckBox newTabCheckbox = new JCheckBox("Open in new tab");
        JTextField titleField = new JTextField();

        panel.add(new JLabel("URL*:"));
        panel.add(urlField);
        panel.add(new JLabel("Link Text*:"));
        panel.add(textField);
        panel.add(new JLabel("Title (optional):"));
        panel.add(titleField);
        panel.add(new JLabel(""));
        panel.add(newTabCheckbox);

        int result = JOptionPane.showConfirmDialog(null, panel,
            "Enter Link Details", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String url = urlField.getText();
            String text = textField.getText();
            String title = titleField.getText();

            if (!url.isEmpty() && !text.isEmpty()) {
                Link link = new Link(url, text);

                if (newTabCheckbox.isSelected()) {
                    link.setTarget("_blank");
                }

                if (!title.isEmpty()) {
                    link.setTitle(title);
                }

                applyCurrentStyles(link);
                root.addElement(link);
                updateOutput();
                updateUndoRedoButtons();
            } else {
                JOptionPane.showMessageDialog(null,
                    "URL and Link Text are required fields",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void addImage() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JTextField urlField = new JTextField();
        JTextField altField = new JTextField();
        JTextField widthField = new JTextField();
        JTextField heightField = new JTextField();

        panel.add(new JLabel("Image URL:"));
        panel.add(urlField);
        panel.add(new JLabel("Alt Text:"));
        panel.add(altField);
        panel.add(new JLabel("Width (optional):"));
        panel.add(widthField);
        panel.add(new JLabel("Height (optional):"));
        panel.add(heightField);

        int result = JOptionPane.showConfirmDialog(null, panel,
            "Enter Image Details", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String url = urlField.getText();
            String alt = altField.getText();
            String width = widthField.getText();
            String height = heightField.getText();

            if (!url.isEmpty()) {
                HtmlImage image = new HtmlImage(url);
                image.setAlignment((String) alignmentCombo.getSelectedItem());

                if (!alt.isEmpty()) {
                    image.setAltText(alt);
                }

                if (!width.isEmpty() || !height.isEmpty()) {
                    image.setDimensions(width, height);
                }

                applyCurrentStyles(image);
                root.addElement(image);
                updateOutput();
                updateUndoRedoButtons();
            } else {
                JOptionPane.showMessageDialog(null,
                    "Image URL cannot be empty",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void undo() {
        if (root.undo()) {
            updateOutput();
            updateUndoRedoButtons();
        }
    }

    private static void redo() {
        if (root.redo()) {
            updateOutput();
            updateUndoRedoButtons();
        }
    }

    private static void clearAll(JFrame frame) {
        int confirm = JOptionPane.showConfirmDialog(frame,
            "Are you sure you want to clear all elements?",
            "Confirm Clear", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            root.clearAll();
            updateOutput();
            updateUndoRedoButtons();
        }
    }

    private static void saveToFile(JFrame parent) {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new File("output.html"));
        if (chooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            try (PrintWriter out = new PrintWriter(chooser.getSelectedFile())) {
                out.println(renderWithPageBackground());
                JOptionPane.showMessageDialog(parent, "File saved successfully.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent, "Error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static String renderWithPageBackground() {
        String originalHtml = root.render();
        String hexColor = String.format("#%02x%02x%02x",
            pageBackgroundColor.getRed(), pageBackgroundColor.getGreen(), pageBackgroundColor.getBlue());
        return originalHtml.replace(
            "<style>body { margin: 20px; }</style>",
            "<style>body { margin: 20px; background-color: " + hexColor + "; }</style>"
        );
    }

    private static void updateOutput() {
        String html = renderWithPageBackground();
        if (html.equals("<!DOCTYPE html>\n<html>\n<head>\n  <title>Generated HTML</title>\n  <meta charset=\"UTF-8\">\n  <style>body { margin: 20px; background-color: #FFFFFF; }</style>\n</head>\n<body>\n</body>\n</html>")) {
            outputArea.setText("<!-- No elements added yet -->");
        } else {
            outputArea.setText(html);
        }
    }

    private static void updateUndoRedoButtons() {
        undoButton.setEnabled(root.canUndo());
        redoButton.setEnabled(root.canRedo());
    }

    // Custom border for rounded buttons
    static class RoundedBorder implements javax.swing.border.Border {
        private int radius;
        private Color color;

        RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius / 2, this.radius, this.radius / 2, this.radius);
        }

        public boolean isBorderOpaque() {
            return true;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(color);
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
}