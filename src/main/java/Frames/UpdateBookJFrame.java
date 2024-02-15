package Frames;

import Manager.CommonFunctions;
import Manager.LengthRestrictedDocument;
import org.example.LibraryManager.Book;
import org.example.LibraryManager.Genre;
import org.example.LibraryManager.Library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Pattern;

public class UpdateBookJFrame extends JFrame implements CommonFunctions {
    private Library library;
    private JList list;
    private CalendarIFrame calendar;

    public JTextField getAuthorBirthDateField() {
        return authorBirthDateField;
    }

    private JTextField authorBirthDateField = new JTextField();
    private JTextField titleField = new JTextField();
    private JTextField authorFirstNameField = new JTextField();
    private JTextField authorLastNameField = new JTextField();
    private JTextField yearField = new JTextField();
    private JComboBox<String> genreComboBox = new JComboBox<>(new String[]{"Przygodowa", "Akcji", "ScienceFiction", "Romans", "Historyczne", "Akademickie", "Finansowe", "Dramat"});
    private JTextField pagesField = new JTextField();

    public UpdateBookJFrame(Library library, JList list) {

        this.library = library;
        this.list = list;


        setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                int width = getWidth();
                int height = getHeight();

                GradientPaint gradient = new GradientPaint(0, 0, new Color(240, 248, 255), width, height, new Color(0, 191, 255));

                ((Graphics2D) g).setPaint(gradient);
                g.fillRect(0, 0, width, height);

            }
        });

        JButton updateBook = new JButton("Update book");


        JButton button = new JButton("Select");


        button.setBounds(200, 240, 100, 40);
        add(button);

        JLabel imageAddBookLabel= new JLabel(setIcon("/phonebook.png"));
        imageAddBookLabel.setBounds(100, 10, 200, 65);
        add(imageAddBookLabel);

        titleField.setBounds(100, 90, 200, 40);
        authorFirstNameField.setBounds(100, 140, 200, 40);
        authorLastNameField.setBounds(100, 190, 200, 40);
        authorBirthDateField.setBounds(100, 240,  90, 40);
        authorBirthDateField.setEditable(false);
        yearField.setBounds(100, 290, 200, 40);
        genreComboBox.setBounds(100, 340, 200, 40);
        pagesField.setBounds(100, 390, 200, 40);

        titleField.setDocument(new LengthRestrictedDocument(40));
        authorFirstNameField.setDocument(new LengthRestrictedDocument(40));
        authorLastNameField.setDocument(new LengthRestrictedDocument(40));
        authorBirthDateField.setDocument(new LengthRestrictedDocument(40));
        yearField.setDocument(new LengthRestrictedDocument(4));
        pagesField.setDocument(new LengthRestrictedDocument(6));

        int indexToUpdate = list.getSelectedIndex();
        int counter = 0;
        for (Book book : library.getListOfBooks()) {
            if (counter == indexToUpdate) {
                titleField.setText(book.getTitle());
                authorFirstNameField.setText(book.getAuthor().getFirstName());
                authorLastNameField.setText(book.getAuthor().getLastName());
                authorBirthDateField.setText(book.getAuthor().getDateOfBirth());
                yearField.setText(String.valueOf(book.getDateOfProduction()));
                genreComboBox.setSelectedItem(book.getGenre().getName());
                pagesField.setText(String.valueOf(book.getAmountOfPage()));
            }
            counter++;
        }


        add(new JLabel("Title:")).setBounds(15, 85, 100, 35);
        add(titleField);
        add(new JLabel("First Name:")).setBounds(15, 135, 100, 35);
        add(authorFirstNameField);
        add(new JLabel("Last Name:")).setBounds(15, 185, 100, 35);
        add(authorLastNameField);
        add(new JLabel("Birth Date:")).setBounds(15, 235, 100, 35);
        add(authorBirthDateField);
        add(new JLabel("Year:")).setBounds(15, 285, 100, 35);
        add(yearField);
        add(new JLabel("Genre:")).setBounds(15, 335, 100, 35);
        add(genreComboBox);
        add(new JLabel("Pages:")).setBounds(15, 385, 100, 35);
        add(pagesField);
        updateBook.setBounds(115, 440, 150, 40);
        add(updateBook);


        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    updateBook(list);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };

        titleField.addKeyListener(keyListener);
        authorFirstNameField.addKeyListener(keyListener);
        authorLastNameField.addKeyListener(keyListener);
        authorBirthDateField.addKeyListener(keyListener);
        yearField.addKeyListener(keyListener);
        genreComboBox.addKeyListener(keyListener);
        pagesField.addKeyListener(keyListener);

        updateBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBook(list);
            }
        });

        updateBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calendar = new CalendarIFrame(button, UpdateBookJFrame.this);

            }
        });


        setSize(400, 550);
        setTitle("Update book");
        setLayout(null);
        setResizable(false);
        setVisible(true);
    }

    public void updateBook(JList list) {
        String title = titleField.getText();
        String authorFirstName = authorFirstNameField.getText();
        String authorLastName = authorLastNameField.getText();
        String authorBirthDate = authorBirthDateField.getText();

        String yearText = yearField.getText();
        String description = (String) genreComboBox.getSelectedItem();

        String pagesText = pagesField.getText();

        if (title.isEmpty() || authorFirstName.isEmpty() || authorLastName.isEmpty() ||
                authorBirthDate.isEmpty() || yearText.isEmpty() || description.isEmpty() || pagesText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String regex ="^[a-zA-Z][a-zA-Z ]*$";
            String regexSurname= "^[a-zA-Z]+$";
            if (!Pattern.matches(regex, authorFirstName)) {
                JOptionPane.showMessageDialog(null, "Invalid format. Name cannot have such value", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!Pattern.matches(regexSurname, authorLastName)) {
                JOptionPane.showMessageDialog(null, "Invalid format. Surname cannot have such value", "Error", JOptionPane.ERROR_MESSAGE);
            }

            int year = 0;
            int pages = 0;
            try {
                year = Integer.parseInt(yearField.getText());
                if (year <= 0) {
                    JOptionPane.showMessageDialog(null, "Invalid year format. Year cannot have such value", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid year format. Type a valid number", "Error", JOptionPane.ERROR_MESSAGE);
            }

            try {
                pages = Integer.parseInt(pagesField.getText());
                if (pages <= 0) {
                    JOptionPane.showMessageDialog(null, "Invalid pages format. Pages cannot have such value", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid pages format. Type a valid number", "Error", JOptionPane.ERROR_MESSAGE);
            }


            if (pages != 0 && year != 0 && Pattern.matches(regex, authorFirstName) && Pattern.matches(regexSurname, authorLastName)) {

                int indexToUpdate = list.getSelectedIndex();
                int counter = 0;
                for (Book book : library.getListOfBooks()) {
                    if (counter == indexToUpdate) {
                        book.setTitle(title);
                        book.getAuthor().setFirstName(authorFirstName);
                        book.getAuthor().setLastName(authorLastName);
                        book.getAuthor().setDateOfBirth(authorBirthDate);
                        book.setDateOfProduction(year);
                        book.setGenre(new Genre(description));
                        book.setAmountOfPage(pages);
                        break;
                    }
                    counter++;
                }

                DefaultListModel<String> updatedModel = new DefaultListModel<>();
                for (Book book : library.getListOfBooks()) {
                    updatedModel.addElement(book.toString(true));
                }
                list.setModel(updatedModel);
                dispose();
            }
        }
    }


}
