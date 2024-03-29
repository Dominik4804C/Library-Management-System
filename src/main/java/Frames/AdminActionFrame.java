package Frames;

import Manager.AdminQueries;
import Manager.CommonFunctions;
import Manager.Queries;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.util.List;
import java.util.*;


public class AdminActionFrame extends JFrame implements CommonFunctions {

    private JMenuBar menubar;
    private JMenu Options, Program;
    private JMenuItem changeUser, changeLibrary, programInfo;
    private JButton AddBook, ReturnInfoAllBooks, ReturnInfoOfBook, DeleteBook, UpdateBook, AddUser, DeleteUser, ShowAllUsers, ReturnBookOfAGivenUser, BorrowBookOfGivenUser, ConfirmChoice, QuickView, ConfirmChoiceOfGivenUser, returnAll, borrowALL, AllLogs;

    public JList<String> getList() {
        return list;
    }

    private JList<String> list;
    private UserChooseIFrame userChooseIFrame;
    private JLabel booksLabel;
    private JCheckBox descendingCheckBoxFiltering, ascendingCheckBoxFiltering;

    boolean deleteBookClicked = false;
    boolean updateBookClicked = false;

    private boolean informationUpdate = true;
    private boolean informationDelete = true;
    private boolean returnBookOfAGivenUser = false;

    private boolean borrowBookOfAGivenUser = false;
    private JComboBox<String> categoryComboBox, SubCategoryComboBox, UserSelectionComboBox, UserSelectionComboBoxToReturnABook, UserSelectionComboBoxToBorrow, UserSelectionComboBoxToFilterTransaction;
    private Map<String, List<String>> subcategoriesMap = new HashMap<>();

    private String CurrentLibraryName;


    public AdminActionFrame(UserChooseIFrame userChooseIFrame) {

        this.userChooseIFrame = userChooseIFrame;

        CurrentLibraryName = userChooseIFrame.getLibraryManagementFrame().getSelectedLibrary();

        DefaultListModel<String> listOfAction = new DefaultListModel<>();
        list = new JList<>(listOfAction);
        list.setBounds(150, 20, 700, 150);
        add(list);

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
        menubar = new JMenuBar();
        Options = new JMenu("Options");
        Program = new JMenu("Info");
        changeUser = new JMenuItem("Log out");
        changeLibrary = new JMenuItem("Change library");
        programInfo = new JMenuItem("About");
        Options.add(changeUser);
        Options.add(changeLibrary);
        Program.add(programInfo);

        setJMenuBar(menubar);
        menubar.add(Options);
        menubar.add(Program);

        QuickView = new JButton("Quick view");
        QuickView.setBounds(855, 20, 120, 40);
        add(QuickView);

        ImageIcon qucikView = setIcon("/view.png");
        QuickView.setIcon(qucikView);

        AddBook = new JButton("Add a book");
        AddBook.setBounds(10, 20, 130, 40);
        add(AddBook);

        ImageIcon add = setIcon("/add.png");
        AddBook.setIcon(add);

        ReturnInfoAllBooks = new JButton("Show all ");
        ReturnInfoAllBooks.setBounds(10, 70, 130, 40);
        add(ReturnInfoAllBooks);

        ImageIcon showboook = setIcon("/all_5334695.png");
        ReturnInfoAllBooks.setIcon(showboook);

        DeleteBook = new JButton("Delete book");
        DeleteBook.setBounds(10, 120, 130, 40);
        add(DeleteBook);

        ImageIcon deleteBook = setIcon("/dislike_9250694.png");
        DeleteBook.setIcon(deleteBook);

        ConfirmChoice = new JButton("Confirm Choice");
        ConfirmChoice.setBounds(680, 180, 160, 40);
        add(ConfirmChoice);
        ConfirmChoice.setVisible(false);
        ConfirmChoice.setBackground(Color.PINK);

        ImageIcon confirm = setIcon("/approved.png");
        ConfirmChoice.setIcon(confirm);

        UpdateBook = new JButton("Update book");
        UpdateBook.setBounds(10, 170, 130, 40);
        add(UpdateBook);

        ImageIcon updateBook = setIcon("/recycle_9250683.png");
        UpdateBook.setIcon(updateBook);

        AddUser = new JButton("Add user");
        AddUser.setBounds(855, 70, 120, 40);
        add(AddUser);

        ImageIcon addUserIcon = setIcon("/add-user_8924229 (1).png");
        AddUser.setIcon(addUserIcon);

        DeleteUser = new JButton("Delete user");
        DeleteUser.setBounds(855, 120, 120, 40);
        add(DeleteUser);

        ImageIcon deleteUser = setIcon("/delete-user.png");
        DeleteUser.setIcon(deleteUser);

        ReturnBookOfAGivenUser = new JButton("Return book of a given user");
        ReturnBookOfAGivenUser.setBounds(10, 220, 240, 40);
        add(ReturnBookOfAGivenUser);

        AllLogs = new JButton("All transaction logs");
        AllLogs.setBounds(10, 320, 240, 40);
        add(AllLogs);

        ImageIcon transaction = setIcon("/transaction (1).png");
        AllLogs.setIcon(transaction);

        ImageIcon returnBook = setIcon("/return.png");
        ReturnBookOfAGivenUser.setIcon(returnBook);

        returnAll = new JButton("Return all by user");
        returnAll.setBounds(680, 280, 200, 40);
        add(returnAll);
        returnAll.setVisible(false);

        ImageIcon returnAllIcon = setIcon("/all.png");
        returnAll.setIcon(returnAllIcon);

        categoryComboBox = new JComboBox<>(new String[]{"Author", "Genre", "Select", "Status", "Assigned to"});
        categoryComboBox.setBounds(680, 200, 160, 40);
        add(categoryComboBox);
        categoryComboBox.setSelectedItem("Select");

        SubCategoryComboBox = new JComboBox<>();
        SubCategoryComboBox.setBounds(680, 250, 160, 40);
        add(SubCategoryComboBox);


        UserSelectionComboBox = new JComboBox<>();
        UserSelectionComboBox.setBounds(680, 180, 160, 40);
        add(UserSelectionComboBox);

        UserSelectionComboBoxToReturnABook = new JComboBox<>();
        UserSelectionComboBoxToReturnABook.setBounds(680, 180, 160, 40);
        add(UserSelectionComboBoxToReturnABook);
        UserSelectionComboBoxToReturnABook.setVisible(false);

        UserSelectionComboBoxToFilterTransaction = new JComboBox<>();
        UserSelectionComboBoxToFilterTransaction.setBounds(680, 180, 160, 40);
        add(UserSelectionComboBoxToFilterTransaction);
        UserSelectionComboBoxToFilterTransaction.setVisible(false);

        UserSelectionComboBoxToBorrow = new JComboBox<>();
        UserSelectionComboBoxToBorrow.setBounds(680, 180, 160, 40);
        add(UserSelectionComboBoxToBorrow);
        UserSelectionComboBoxToBorrow.setVisible(false);

        ConfirmChoiceOfGivenUser = new JButton("Confirm choice by user");
        ConfirmChoiceOfGivenUser.setBounds(680, 230, 200, 40);
        add(ConfirmChoiceOfGivenUser);
        ConfirmChoiceOfGivenUser.setVisible(false);
        ConfirmChoiceOfGivenUser.setBackground(Color.PINK);

        ShowAllUsers = new JButton("All users");
        ShowAllUsers.setBounds(855, 170, 120, 40);
        add(ShowAllUsers);
        ImageIcon allUsers = setIcon("/team.png");
        ShowAllUsers.setIcon(allUsers);

        ImageIcon confirmChoiceByUser = setIcon("/approved.png");
        ConfirmChoiceOfGivenUser.setIcon(confirmChoiceByUser);


        BorrowBookOfGivenUser = new JButton("Assign book to given user");
        BorrowBookOfGivenUser.setBounds(10, 270, 240, 40);
        add(BorrowBookOfGivenUser);
        ImageIcon borroBookOfGivenUserIcon = setIcon("/borrow.png");
        BorrowBookOfGivenUser.setIcon(borroBookOfGivenUserIcon);


        categoryComboBox.setVisible(false);
        SubCategoryComboBox.setVisible(false);
        UserSelectionComboBox.setVisible(false);
        UserSelectionComboBoxToReturnABook.setVisible(false);


        borrowALL = new JButton("Borrow all by user");
        borrowALL.setBounds(680, 280, 200, 40);
        add(borrowALL);
        ImageIcon borrowAllIcon = setIcon("/all.png");
        borrowALL.setIcon(borrowAllIcon);
        borrowALL.setVisible(false);
        borrowALL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String UserWithBorrowedBook = (String) UserSelectionComboBoxToBorrow.getSelectedItem();


                int odp = JOptionPane.showConfirmDialog(null, "Do you want to assign all books to " + UserWithBorrowedBook + " ?");
                if (odp == JOptionPane.YES_OPTION) {

                    for (String book : Queries.getAllAvailableBook(CurrentLibraryName)) {
                        String title = extractTitle(book);
                        Queries.insertBorrowedBookToHistory(UserWithBorrowedBook, title, CurrentLibraryName, "is borrowed");
                    }
                    Queries.setAllBooksBorrowed(CurrentLibraryName, UserWithBorrowedBook, "AVAILABLE", "BORROWED");

                    JOptionPane.showMessageDialog(null, "All books have been borrowed successfully", "Message", JOptionPane.INFORMATION_MESSAGE);

                    DefaultListModel<String> modifiedModel1 = new DefaultListModel<>();

                    for (String book : Queries.getAllAvailableBook(CurrentLibraryName)) {
                        modifiedModel1.addElement(book);
                    }

                    list.setModel(modifiedModel1);

                    JOptionPane.showMessageDialog(null, "All books have been assigned successfully", "Message", JOptionPane.INFORMATION_MESSAGE);

                    if (!Queries.checkIfAnyBookIsAvailable(CurrentLibraryName)) {
                        borrowALL.setEnabled(false);
                        ConfirmChoiceOfGivenUser.setEnabled(false);
                        UserSelectionComboBoxToBorrow.setEnabled(false);
                    }
                }
            }
        });
        UserSelectionComboBoxToBorrow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultListModel<String> modifiedModel = new DefaultListModel<>();
                String UserWithBorrowedBook = (String) UserSelectionComboBoxToBorrow.getSelectedItem();

                if (!UserWithBorrowedBook.equals("Select")) {
                    for (String book : Queries.getAllAvailableBook(CurrentLibraryName)) {
                        modifiedModel.addElement(book);
                    }
                    list.setModel(modifiedModel);
                    ConfirmChoiceOfGivenUser.setVisible(true);
                    ConfirmChoiceOfGivenUser.setEnabled(true);
                    list.setEnabled(true);
                    QuickView.setEnabled(true);
                    borrowBookOfAGivenUser = true;
                    borrowALL.setVisible(true);
                    borrowALL.setEnabled(true);
                } else {
                    ConfirmChoiceOfGivenUser.setVisible(false);
                    returnAll.setVisible(false);
                    borrowALL.setVisible(false);
                    list.setModel(modifiedModel);
                }

                if (!Queries.checkIfAnyBookIsAvailable(CurrentLibraryName)) {
                    ConfirmChoiceOfGivenUser.setEnabled(false);
                    returnAll.setEnabled(false);

                    list.setModel(modifiedModel);
                    JOptionPane.showMessageDialog(null, "No books to borrow", "Warning", JOptionPane.WARNING_MESSAGE);
                    //  borrowButtonClicked = false;
                }
            }
        });
        BorrowBookOfGivenUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                categoryComboBox.setVisible(true);
                SubCategoryComboBox.setVisible(true);
                UserSelectionComboBox.setVisible(false);
                SubCategoryComboBox.setVisible(false);
                categoryComboBox.setVisible(false);
                ConfirmChoice.setVisible(false);
                UserSelectionComboBoxToReturnABook.setVisible(false);
                ConfirmChoiceOfGivenUser.setVisible(false);
                returnAll.setVisible(false);
                borrowALL.setVisible(false);
                descendingCheckBoxFiltering.setVisible(false);
                ascendingCheckBoxFiltering.setVisible(false);

                returnBookOfAGivenUser = false;
                borrowBookOfAGivenUser = true;
                UserSelectionComboBoxToFilterTransaction.setVisible(false);
                DefaultListModel<String> modifiedModel = new DefaultListModel<>();

                if (Queries.getAllAvailableBook(CurrentLibraryName).isEmpty()) {
                    list.setModel(modifiedModel);
                    ConfirmChoiceOfGivenUser.setEnabled(false);
                    returnAll.setEnabled(false);
                    UserSelectionComboBoxToReturnABook.setVisible(false);
                    UserSelectionComboBoxToBorrow.setVisible(false);
                    UserSelectionComboBoxToBorrow.setEnabled(false);
                    borrowALL.setVisible(false);
                    JOptionPane.showMessageDialog(null, "No book possible to borrow", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    UserSelectionComboBoxToBorrow.setEnabled(true);

                    Queries.getAllAvailableBook(CurrentLibraryName).stream()
                            .map(String::toString)
                            .forEach(modifiedModel::addElement);
                    list.setModel(modifiedModel);
                    list.setEnabled(false);
                    QuickView.setEnabled(false);
                    UserSelectionComboBoxToBorrow.setVisible(true);


                    if (AdminQueries.readAllUsersAssignedToLibraryWithoutAdmin(CurrentLibraryName).size() != 1) {
                        String UserNameArray[] = AdminQueries.readAllUsersAssignedToLibraryWithoutAdmin(CurrentLibraryName).toArray(new String[AdminQueries.readAllUsersAssignedToLibraryWithoutAdmin(CurrentLibraryName).size()]);
                        UserSelectionComboBoxToBorrow.setModel(new DefaultComboBoxModel<>(UserNameArray));
                    } else {
                        UserSelectionComboBoxToBorrow.setModel(new DefaultComboBoxModel<>(new String[]{"No users"}));
                        UserSelectionComboBoxToBorrow.setEnabled(false);
                    }
                }
            }
        });
        ShowAllUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                categoryComboBox.setVisible(false);
                SubCategoryComboBox.setVisible(false);
                UserSelectionComboBox.setVisible(false);
                ConfirmChoice.setVisible(false);
                ConfirmChoiceOfGivenUser.setVisible(false);
                returnAll.setVisible(false);
                UserSelectionComboBoxToReturnABook.setVisible(false);
                QuickView.setEnabled(false);
                UserSelectionComboBoxToBorrow.setVisible(false);
                borrowALL.setVisible(false);

                ascendingCheckBoxFiltering.setVisible(false);
                descendingCheckBoxFiltering.setVisible(false);
                UserSelectionComboBoxToFilterTransaction.setVisible(false);
                DefaultListModel<String> modifiedModel = new DefaultListModel<>();
                AdminQueries.getAllUsersInfo(CurrentLibraryName).stream()
                        .map(String::toString)
                        .forEach(modifiedModel::addElement);
                list.setModel(modifiedModel);
                list.setEnabled(false);
            }
        });
        ConfirmChoiceOfGivenUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                int[] selectedIndices = list.getSelectedIndices();
                List<String> selectedValues = list.getSelectedValuesList();


                if (returnBookOfAGivenUser) {
                    if (selectedIndices.length > 0) {
                        DefaultListModel<String> modifiedModel = new DefaultListModel<>();
                        String UserToReturnBook = (String) UserSelectionComboBoxToReturnABook.getSelectedItem();

                        if (selectedValues.size() > 1) {


                            int odp = JOptionPane.showConfirmDialog(null, "Do you want return all selected books ?");

                            if (odp == JOptionPane.YES_OPTION) {


                                for (int i = 0; i < selectedValues.size(); i++) {
                                    String titleOfBookToUnassingFromUser = extractTitle(selectedValues.get(i));
                                    Queries.updateBookStatusByTitle(CurrentLibraryName, "AVAILABLE", titleOfBookToUnassingFromUser, null);
                                    Queries.insertBorrowedBookToHistory(UserToReturnBook, titleOfBookToUnassingFromUser, CurrentLibraryName, "is returned");
                                }


                                for (String book : AdminQueries.getAllBorrowedBooksByUserOrder(CurrentLibraryName, UserToReturnBook, "Admin", "username")) {
                                    modifiedModel.addElement(book);
                                }

                                list.setModel(modifiedModel);

                                JOptionPane.showMessageDialog(null, "All selected books has been returned successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
                                if (!Queries.checkIfAnyBookIsInStatusBorrowed(CurrentLibraryName, UserToReturnBook)) {
                                    ConfirmChoiceOfGivenUser.setEnabled(false);
                                    returnAll.setEnabled(false);;
                                }
                                if (modifiedModel.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, UserToReturnBook + " does not have more book to return", "Message", JOptionPane.INFORMATION_MESSAGE);
                                    ConfirmChoiceOfGivenUser.setEnabled(false);
                                    returnAll.setEnabled(false);
                                }

                            }
                        }
                        if (selectedValues.size() == 1) {
                            String titleOfBookToUnassingFromUser = extractTitle(list.getSelectedValue());

                            int odp = JOptionPane.showConfirmDialog(null, "Do you want return a book: " + titleOfBookToUnassingFromUser + " ?");
                            if (odp == JOptionPane.YES_OPTION) {

                                Queries.updateBookStatusByTitle(CurrentLibraryName, "AVAILABLE", titleOfBookToUnassingFromUser, null);
                                Queries.insertBorrowedBookToHistory(UserToReturnBook, titleOfBookToUnassingFromUser, CurrentLibraryName, "is returned");

                                for (String book : AdminQueries.getAllBorrowedBooksByUserOrder(CurrentLibraryName, UserToReturnBook, "Admin", "username")) {
                                    modifiedModel.addElement(book);
                                }

                                JOptionPane.showMessageDialog(null, "Book " + titleOfBookToUnassingFromUser + " has been returned successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
                                list.setModel(modifiedModel);

                                if (!Queries.checkIfAnyBookIsInStatusBorrowed(CurrentLibraryName, UserToReturnBook)) {
                                    ConfirmChoiceOfGivenUser.setEnabled(false);
                                    returnAll.setEnabled(false);
                                }
                                if (modifiedModel.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, UserToReturnBook + " does not have any book to borrow", "Message", JOptionPane.INFORMATION_MESSAGE);
                                    ConfirmChoiceOfGivenUser.setEnabled(false);
                                    returnAll.setEnabled(false);
                                }

                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Choose at least one book", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (borrowBookOfAGivenUser) {
                    if (selectedIndices.length > 0) {
                        String UserWithBorrowedBook = (String) UserSelectionComboBoxToBorrow.getSelectedItem();
                        if (selectedValues.size() > 1) {
                            borrowALL.setEnabled(true);
                            ConfirmChoice.setEnabled(true);

                            for (int i = 0; i < selectedValues.size(); i++) {

                                String titleOfBookToAssignUser = extractTitle(selectedValues.get(i));
                                Queries.updateBookStatusByTitle(CurrentLibraryName, "BORROWED", titleOfBookToAssignUser, UserWithBorrowedBook);
                                Queries.insertBorrowedBookToHistory(UserWithBorrowedBook, titleOfBookToAssignUser, CurrentLibraryName, "is borrowed");

                            }
                            int odp = JOptionPane.showConfirmDialog(null, "Do you want to borrow all selected books ?");
                            if (odp == JOptionPane.YES_OPTION) {

                                DefaultListModel<String> modifiedModel1 = new DefaultListModel<>();
                                modifiedModel1.addAll(Queries.getAllAvailableBook(CurrentLibraryName));
                                list.setModel(modifiedModel1);
                                JOptionPane.showMessageDialog(null, "Selected books have been borrowed successfully", "Message", JOptionPane.INFORMATION_MESSAGE);

                                if (!Queries.checkIfAnyBookIsAvailable(CurrentLibraryName)) {
                                    borrowALL.setEnabled(false);
                                    ConfirmChoiceOfGivenUser.setEnabled(false);
                                    UserSelectionComboBoxToBorrow.setModel(new DefaultComboBoxModel<>(new String[]{"No users"}));
                                }
                            }
                        } else if (selectedValues.size() == 1) {
                            borrowALL.setEnabled(true);
                            ConfirmChoice.setEnabled(true);
                            String titleOfBookToAssignToUser = extractTitle(list.getSelectedValue());

                            int odp = JOptionPane.showConfirmDialog(null, "Do you want to borrow a book: " + titleOfBookToAssignToUser + " ?");
                            if (odp == JOptionPane.YES_OPTION) {


                                Queries.updateBookStatusByTitle(CurrentLibraryName, "BORROWED", titleOfBookToAssignToUser, UserWithBorrowedBook);
                                Queries.insertBorrowedBookToHistory(UserWithBorrowedBook, titleOfBookToAssignToUser, CurrentLibraryName, "is borrowed");

                                DefaultListModel modifiedModel1 = new DefaultListModel();
                                modifiedModel1.addAll(Queries.getAllAvailableBook(CurrentLibraryName));
                                list.setModel(modifiedModel1);
                                JOptionPane.showMessageDialog(null, "Book " + titleOfBookToAssignToUser + " has been borrowed successfully", "Message", JOptionPane.INFORMATION_MESSAGE);

                                if (!Queries.checkIfAnyBookIsAvailable(CurrentLibraryName)) {
                                    borrowALL.setEnabled(false);
                                    ConfirmChoiceOfGivenUser.setEnabled(false);
                                    UserSelectionComboBoxToBorrow.setModel(new DefaultComboBoxModel<>(new String[]{"No users"}));
                                }
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Choose at least one book", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        UserSelectionComboBoxToReturnABook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultListModel<String> modifiedModel = new DefaultListModel<>();
                String UserToReturn = (String) UserSelectionComboBoxToReturnABook.getSelectedItem();

                if (!UserToReturn.equals("Select")) {

                    ConfirmChoiceOfGivenUser.setVisible(true);
                    returnAll.setVisible(true);
                    returnAll.setEnabled(true);
                    modifiedModel.addAll(AdminQueries.getAllBorrowedBooksByUserOrder(CurrentLibraryName, UserToReturn, "Admin", "username"));
                    list.setModel(modifiedModel);
                    list.setEnabled(true);
                    QuickView.setEnabled(true);
                } else {
                    ConfirmChoiceOfGivenUser.setVisible(false);
                    returnAll.setVisible(false);
                    list.setModel(modifiedModel);
                }

                if (!Queries.checkIfAnyBookIsInStatusBorrowed(CurrentLibraryName, UserToReturn)) {

                    ConfirmChoiceOfGivenUser.setEnabled(false);
                    returnAll.setEnabled(false);

                    list.setModel(modifiedModel);
                    JOptionPane.showMessageDialog(null, "No books to return", "Warning", JOptionPane.WARNING_MESSAGE);

                } else {
                    ConfirmChoiceOfGivenUser.setEnabled(true);
                    list.setModel(modifiedModel);
                }
            }
        });
        returnAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DefaultListModel<String> modifiedModel = new DefaultListModel<>();

                String UserToReturn = (String) UserSelectionComboBoxToReturnABook.getSelectedItem();

                int odp = JOptionPane.showConfirmDialog(null, "Do you want to return all books?");
                if (odp == JOptionPane.YES_OPTION) {

                    for (String book : Queries.getAllBorrowedBooksByUser(CurrentLibraryName, UserToReturn, "User")) {
                        String title = extractTitle(book);
                        Queries.insertBorrowedBookToHistory(UserToReturn, title, CurrentLibraryName, "is returned");
                    }

                    Queries.setAllBooksAvailable(CurrentLibraryName, null, "BORROWED", "AVAILABLE", UserToReturn);
                    JOptionPane.showMessageDialog(null, "All books have been borrowed successfully", "Message", JOptionPane.INFORMATION_MESSAGE);

                    DefaultListModel<String> modifiedModel1 = new DefaultListModel<>();
                    for (String book : Queries.getAllBorrowedBooksByUser(CurrentLibraryName, UserToReturn, "User")) {
                        modifiedModel1.addElement(book);
                    }
                    list.setModel(modifiedModel1);

                    if (modifiedModel.isEmpty()) {
                        ConfirmChoiceOfGivenUser.setEnabled(false);
                        returnAll.setEnabled(false);
                    }
                    if (!Queries.checkIfAnyBookIsInStatusBorrowed(CurrentLibraryName, UserToReturn)) {
                        ConfirmChoiceOfGivenUser.setEnabled(false);
                        returnAll.setEnabled(false);
                    }
                }
            }
        });
        ReturnBookOfAGivenUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                categoryComboBox.setVisible(true);
                SubCategoryComboBox.setVisible(true);
                UserSelectionComboBox.setVisible(false);
                SubCategoryComboBox.setVisible(false);
                categoryComboBox.setVisible(false);
                ConfirmChoice.setVisible(false);
                ConfirmChoiceOfGivenUser.setVisible(false);
                UserSelectionComboBoxToBorrow.setVisible(false);
                UserSelectionComboBoxToReturnABook.setEnabled(true);
                borrowALL.setVisible(false);
                returnAll.setVisible(false);
                returnBookOfAGivenUser = true;
                borrowBookOfAGivenUser = false;
                ascendingCheckBoxFiltering.setVisible(false);
                descendingCheckBoxFiltering.setVisible(false);
                UserSelectionComboBoxToFilterTransaction.setVisible(false);
                DefaultListModel<String> modifiedModel = new DefaultListModel<>();


                if (AdminQueries.getAllBorrowedBooks(CurrentLibraryName).isEmpty()) {
                    list.setModel(modifiedModel);
                    ConfirmChoiceOfGivenUser.setEnabled(false);
                    returnAll.setEnabled(false);
                    UserSelectionComboBoxToReturnABook.setEnabled(false);
                    UserSelectionComboBoxToReturnABook.setVisible(false);
                    JOptionPane.showMessageDialog(null, "No borrowed books in library", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {


                    modifiedModel.addAll(AdminQueries.getAllBorrowedBooks(CurrentLibraryName));
                    list.setModel(modifiedModel);
                    list.setEnabled(false);
                    QuickView.setEnabled(false);
                    UserSelectionComboBoxToReturnABook.setVisible(true);


                    String UserNameArray[] = AdminQueries.readAllUsersAssignedToLibraryWithoutAdmin(CurrentLibraryName).toArray(new String[AdminQueries.readAllUsersAssignedToLibraryWithoutAdmin(CurrentLibraryName).size()]);
                    UserSelectionComboBoxToReturnABook.setModel(new DefaultComboBoxModel<>(UserNameArray));
                }
            }
        });
        QuickView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (list.getSelectedIndex() != -1) {
                    new OverViewBookJFrame(CurrentLibraryName, list);
                } else {
                    JOptionPane.showMessageDialog(null, "Choose one book from list", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        DeleteUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                categoryComboBox.setVisible(false);
                SubCategoryComboBox.setVisible(false);
                UserSelectionComboBox.setVisible(true);
                UserSelectionComboBox.setEnabled(true);
                ConfirmChoiceOfGivenUser.setVisible(false);
                returnAll.setVisible(false);
                UserSelectionComboBoxToReturnABook.setVisible(false);
                ConfirmChoice.setVisible(false);
                UserSelectionComboBoxToBorrow.setVisible(false);
                list.setEnabled(false);
                QuickView.setEnabled(false);
                ascendingCheckBoxFiltering.setVisible(false);
                descendingCheckBoxFiltering.setVisible(false);
                UserSelectionComboBoxToFilterTransaction.setVisible(false);

                if (AdminQueries.getAllUsersInfo(CurrentLibraryName).size() == 1) {
                    UserSelectionComboBox.setEnabled(false);
                    UserSelectionComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"No users"}));
                    JOptionPane.showMessageDialog(null,
                            "No additional users assigned to library", "Message", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    List<String> listOfUserName = new ArrayList<>();
                    listOfUserName.addAll(AdminQueries.readAllUsersAssignedToLibraryWithoutAdmin(CurrentLibraryName));
                    String UserNameArray[] = listOfUserName.toArray(new String[listOfUserName.size()]);
                    UserSelectionComboBox.setModel(new DefaultComboBoxModel<>(UserNameArray));
                }
            }
        });
        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                categoryFiltering();
            }

        });
        SubCategoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                subCategoryFiltering();
            }
        });
        changeUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new UserChooseIFrame(userChooseIFrame.getLibraryManagementFrame());
                setVisible(false);
            }
        });
        changeLibrary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LibraryManagementFrame();
                setVisible(false);
            }
        });
        ReturnInfoAllBooks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                categoryComboBox.setVisible(true);
                SubCategoryComboBox.setVisible(true);
                UserSelectionComboBox.setVisible(false);
                ConfirmChoice.setVisible(false);
                ConfirmChoiceOfGivenUser.setVisible(false);
                returnAll.setVisible(false);
                UserSelectionComboBoxToReturnABook.setVisible(false);
                UserSelectionComboBoxToBorrow.setVisible(false);
                QuickView.setEnabled(true);
                borrowALL.setVisible(false);
                ascendingCheckBoxFiltering.setVisible(false);
                descendingCheckBoxFiltering.setVisible(false);
                UserSelectionComboBoxToFilterTransaction.setVisible(false);

                DefaultListModel<String> modifiedModel = new DefaultListModel<>();
                if (Queries.getCurrentStateOfBooks(CurrentLibraryName, "Admin").isEmpty()) {
                    list.setModel(modifiedModel);
                    SubCategoryComboBox.setEnabled(false);
                    categoryComboBox.setEnabled(false);
                    JOptionPane.showMessageDialog(null, "No books in library", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    for (String book : Queries.getCurrentStateOfBooks(CurrentLibraryName, "Admin")) {
                        modifiedModel.addElement(book);
                    }
                    list.setModel(modifiedModel);
                    SubCategoryComboBox.setEnabled(true);
                    categoryComboBox.setEnabled(true);
                    list.setEnabled(true);
                }
            }
        });
        ConfirmChoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (deleteBookClicked) {
                    if (list.getSelectedIndex() != -1) {

                        String selectedBookToRemove = (String) extractTitle(list.getSelectedValue());
                        int odp = JOptionPane.showConfirmDialog(null, "Do you want to remove a book: " + selectedBookToRemove + " ?");

                        if (odp == JOptionPane.YES_OPTION) {

                            AdminQueries.deleteBookByTitle(selectedBookToRemove, CurrentLibraryName);

                            DefaultListModel<String> modifiedModelOverall = new DefaultListModel<>();
                            for (String book : Queries.getAllAvailableBook(CurrentLibraryName)) {
                                modifiedModelOverall.addElement(book);
                            }
                            list.setModel(modifiedModelOverall);
                        }
                        if (Queries.getAllAvailableBook(CurrentLibraryName).isEmpty()) {
                            ConfirmChoice.setEnabled(false);
                            JOptionPane.showMessageDialog(null,
                                    "No book to delete in " + CurrentLibraryName, "Message", JOptionPane.INFORMATION_MESSAGE);
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Choose one book", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                if (updateBookClicked) {
                    if (list.getSelectedIndex() != -1) {
                        new UpdateBookJFrame(list, userChooseIFrame);
                    } else {
                        JOptionPane.showMessageDialog(null, "Choose one book", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        DeleteBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBookClicked = false;
                deleteBookClicked = true;
                categoryComboBox.setVisible(false);
                SubCategoryComboBox.setVisible(false);
                UserSelectionComboBox.setVisible(false);
                ConfirmChoice.setVisible(true);
                list.setEnabled(true);
                ConfirmChoiceOfGivenUser.setVisible(false);
                returnAll.setVisible(false);
                UserSelectionComboBoxToReturnABook.setVisible(false);
                UserSelectionComboBoxToBorrow.setVisible(false);
                QuickView.setEnabled(true);
                ascendingCheckBoxFiltering.setVisible(false);
                descendingCheckBoxFiltering.setVisible(false);
                UserSelectionComboBoxToFilterTransaction.setVisible(false);
                if (Queries.getAllAvailableBook(CurrentLibraryName).isEmpty()) {
                    ConfirmChoice.setEnabled(false);
                    JOptionPane.showMessageDialog(null,
                            "No book to delete in " + CurrentLibraryName, "Message", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    ConfirmChoice.setEnabled(true);
                    DefaultListModel<String> modifiedModelOverall = new DefaultListModel<>();
                    for (String book : Queries.getAllAvailableBook(CurrentLibraryName)) {
                        modifiedModelOverall.addElement(book);
                    }
                    list.setModel(modifiedModelOverall);
                    SubCategoryComboBox.setEnabled(true);
                    categoryComboBox.setEnabled(true);
                    if (informationDelete) {
                        JOptionPane.showMessageDialog(null,
                                "Choose one book from list and click confirm", "Message", JOptionPane.INFORMATION_MESSAGE);
                        informationDelete = false;
                    }
                }
            }
        });
        UserSelectionComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String selectedUserToDelete = (String) UserSelectionComboBox.getSelectedItem();
                if (!selectedUserToDelete.equals("Select")) {

                    int odp = JOptionPane.showConfirmDialog(null, "Do you want to remove " + selectedUserToDelete + "?\nIt will delete all transaction history and returns book assigned to user", "Message", JOptionPane.YES_NO_OPTION);
                    if (odp == JOptionPane.YES_OPTION) {

                        AdminQueries.DeleteUser(CurrentLibraryName, selectedUserToDelete);


                        if (AdminQueries.readAllUsersAssignedToLibraryWithoutAdmin(CurrentLibraryName).isEmpty()) {
                            UserSelectionComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"No users"}));
                            UserSelectionComboBox.setEnabled(false);
                        } else {
                            String modifiedUserArray[] = AdminQueries.readAllUsersAssignedToLibraryWithoutAdmin(CurrentLibraryName).toArray(new String[AdminQueries.readAllUsersAssignedToLibraryWithoutAdmin(CurrentLibraryName).size()]);
                            UserSelectionComboBox.setModel(new DefaultComboBoxModel<>(modifiedUserArray));
                        }
                    }
                }
            }
        });
        AddUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QuickView.setEnabled(false);
                list.setEnabled(false);
                UserSelectionComboBox.setVisible(false);
                UserSelectionComboBoxToBorrow.setVisible(false);
                ascendingCheckBoxFiltering.setVisible(false);
                descendingCheckBoxFiltering.setVisible(false);
                ConfirmChoice.setVisible(false);
                UserSelectionComboBoxToFilterTransaction.setVisible(false);
                AddUserAdminFrame addUserFrame = new AddUserAdminFrame(userChooseIFrame, true);
                addUserFrame.setVisible(true);
                addUserFrame.setAdminActionFrame(AdminActionFrame.this);
            }
        });
        UpdateBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBookClicked = false;
                updateBookClicked = true;
                list.setEnabled(true);
                categoryComboBox.setVisible(false);
                SubCategoryComboBox.setVisible(false);
                UserSelectionComboBox.setVisible(false);
                ConfirmChoice.setVisible(true);
                ConfirmChoiceOfGivenUser.setVisible(false);
                returnAll.setVisible(false);
                UserSelectionComboBoxToReturnABook.setVisible(false);
                UserSelectionComboBoxToBorrow.setVisible(false);
                QuickView.setEnabled(true);
                ascendingCheckBoxFiltering.setVisible(false);
                descendingCheckBoxFiltering.setVisible(false);
                UserSelectionComboBoxToFilterTransaction.setVisible(false);
                if (Queries.getAllAvailableBook(CurrentLibraryName).isEmpty()) {
                    ConfirmChoice.setEnabled(false);
                    JOptionPane.showMessageDialog(null,
                            "No book to update in " +CurrentLibraryName, "Message", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    updateBookClicked = true;

                    DefaultListModel<String> modifiedModel = new DefaultListModel<>();
                    Queries.getAllAvailableBook(CurrentLibraryName).stream()
                            .map(String::toString)
                            .forEach(modifiedModel::addElement);
                    list.setModel(modifiedModel);
                    if (informationUpdate) {
                        JOptionPane.showMessageDialog(null,
                                "Choose one book from list and click confirm", "Message", JOptionPane.INFORMATION_MESSAGE);
                        informationUpdate = false;
                    }

                }
            }
        });
        AddBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfirmChoiceOfGivenUser.setVisible(false);
                returnAll.setVisible(false);
                UserSelectionComboBoxToReturnABook.setVisible(false);
                SubCategoryComboBox.setVisible(false);
                categoryComboBox.setVisible(false);
                UserSelectionComboBoxToBorrow.setVisible(false);
                QuickView.setEnabled(true);
                ascendingCheckBoxFiltering.setVisible(false);
                descendingCheckBoxFiltering.setVisible(false);
                UserSelectionComboBoxToFilterTransaction.setVisible(false);
                DefaultListModel<String> updatedModel = new DefaultListModel<>();

                Queries.getAllAvailableBook(CurrentLibraryName).stream()
                        .map(String::toString)
                        .forEach(updatedModel::addElement);

                list.setModel(updatedModel);
                new AddBookJFrame(list, booksLabel, userChooseIFrame);

            }
        });
        programInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Program written by Dominik Jakubaszek. \n Version 2.0.0", "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        AllLogs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DefaultListModel model = new DefaultListModel();
                deleteBookClicked = false;
                updateBookClicked = true;
                list.setEnabled(true);
                categoryComboBox.setVisible(false);
                SubCategoryComboBox.setVisible(false);
                UserSelectionComboBox.setVisible(false);
                ConfirmChoice.setVisible(false);
                ConfirmChoiceOfGivenUser.setVisible(false);
                returnAll.setVisible(false);
                borrowALL.setVisible(false);
                UserSelectionComboBoxToReturnABook.setVisible(false);
                UserSelectionComboBoxToBorrow.setVisible(false);
                QuickView.setEnabled(true);
                ascendingCheckBoxFiltering.setVisible(false);
                descendingCheckBoxFiltering.setVisible(false);
                UserSelectionComboBoxToFilterTransaction.setVisible(true);

                if (AdminQueries.getAllTransactions(CurrentLibraryName).isEmpty()) {
                    list.setModel(model);
                    UserSelectionComboBoxToFilterTransaction.setVisible(false);
                    JOptionPane.showMessageDialog(null, "No transactions saved", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    model.addAll(AdminQueries.getAllTransactions(CurrentLibraryName));
                    list.setModel(model);
                    list.setEnabled(true);
                    QuickView.setEnabled(true);
                    UserSelectionComboBoxToFilterTransaction.setVisible(true);

                    String UserNameArray[] = AdminQueries.readAllUsersAssignedToLibraryWithoutAdmin(CurrentLibraryName).toArray(new String[AdminQueries.readAllUsersAssignedToLibraryWithoutAdmin(CurrentLibraryName).size()]);
                    UserSelectionComboBoxToFilterTransaction.setModel(new DefaultComboBoxModel<>(UserNameArray));
                }
            }
        });

        UserSelectionComboBoxToFilterTransaction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DefaultListModel<String> modifiedModel = new DefaultListModel<>();
                String User = (String) UserSelectionComboBoxToFilterTransaction.getSelectedItem();

                if (!User.equals("Select")) {
                    for (String transaction : AdminQueries.getAllTransactionByUser(CurrentLibraryName, User)) {
                        modifiedModel.addElement(transaction);
                    }
                    list.setModel(modifiedModel);
                    list.setEnabled(true);
                    QuickView.setEnabled(true);

                } else {
                    ConfirmChoiceOfGivenUser.setVisible(false);
                    returnAll.setVisible(false);
                    borrowALL.setVisible(false);
                    QuickView.setEnabled(false);
                    list.setModel(modifiedModel);
                }

            }
        });

        ascendingCheckBoxFiltering = new JCheckBox("Sort filter \uD83E\uDC79");
        ascendingCheckBoxFiltering.setBounds(560, 205, 150, 30);

        descendingCheckBoxFiltering = new JCheckBox("Sort filter \uD83E\uDC7B");
        descendingCheckBoxFiltering.setBounds(560, 230, 150, 30);
        add(ascendingCheckBoxFiltering);
        add(descendingCheckBoxFiltering);

        descendingCheckBoxFiltering.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (descendingCheckBoxFiltering.isSelected()) {
                    ascendingCheckBoxFiltering.setSelected(false);
                    subCategoryFiltering();
                } else if (!ascendingCheckBoxFiltering.isSelected()) {
                    ascendingCheckBoxFiltering.setSelected(true);
                    subCategoryFiltering();
                }
            }
        });
        ascendingCheckBoxFiltering.setSelected(true);
        ascendingCheckBoxFiltering.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (ascendingCheckBoxFiltering.isSelected()) {
                    descendingCheckBoxFiltering.setSelected(false);
                    subCategoryFiltering();
                } else if (!descendingCheckBoxFiltering.isSelected()) {
                    descendingCheckBoxFiltering.setSelected(true);
                    subCategoryFiltering();
                }
            }
        });

        ascendingCheckBoxFiltering.setVisible(false);
        descendingCheckBoxFiltering.setVisible(false);

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(150, 20, 700, 150);
        add(scrollPane);

        setSize(1000, 460);
        setTitle("Admin Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setVisible(true);

    }

    public void subCategoryFiltering() {

        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        String selectedValue = (String) SubCategoryComboBox.getSelectedItem();
        DefaultListModel<String> modifiedModel = new DefaultListModel<>();
        if (selectedCategory.equals("Genre")) {


            if (ascendingCheckBoxFiltering.isSelected()) {
                modifiedModel.addAll(Queries.filterBookByGenre(CurrentLibraryName, selectedValue, "ASC"));

            }
            if (descendingCheckBoxFiltering.isSelected()) {
                modifiedModel.addAll(Queries.filterBookByGenre(CurrentLibraryName, selectedValue, "DESC"));
            }
            list.setModel(modifiedModel);

            if (modifiedModel.isEmpty()) {
                list.setModel(modifiedModel);
                JOptionPane.showMessageDialog(null,
                        "No book meets the criteria Category " + selectedCategory + " and " + selectedValue, "Message", JOptionPane.INFORMATION_MESSAGE);
            }

        } else if (selectedCategory.equals("Author")) {
            String[] partsOfAuthors = selectedValue.split(" ");

            if (ascendingCheckBoxFiltering.isSelected()) {
                modifiedModel.addAll(Queries.filterBookByAuthorNameAndSurname(CurrentLibraryName, partsOfAuthors[1], partsOfAuthors[0], "ASC"));
            }
            if (descendingCheckBoxFiltering.isSelected()) {
                modifiedModel.addAll(Queries.filterBookByAuthorNameAndSurname(CurrentLibraryName, partsOfAuthors[1], partsOfAuthors[0], "DESC"));
            }
            list.setModel(modifiedModel);

            if (modifiedModel.isEmpty()) {
                list.setModel(modifiedModel);
                JOptionPane.showMessageDialog(null,
                        "No book meets the criteria Category " + selectedCategory + " and " + selectedValue, "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (selectedCategory.equals("Status")) {

            if (ascendingCheckBoxFiltering.isSelected()) {
                modifiedModel.addAll(Queries.filterBookByStatus(CurrentLibraryName, selectedValue, "ASC"));
            }
            if (descendingCheckBoxFiltering.isSelected()) {
                modifiedModel.addAll(Queries.filterBookByStatus(CurrentLibraryName, selectedValue, "DESC"));
            }
            list.setModel(modifiedModel);

            if (modifiedModel.isEmpty()) {
                list.setModel(modifiedModel);
                JOptionPane.showMessageDialog(null,
                        "No book meets the criteria Category " + selectedCategory + " and " + selectedValue, "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (selectedCategory.equals("Assigned to")) {

            if (ascendingCheckBoxFiltering.isSelected()) {
                modifiedModel.addAll(AdminQueries.getAllBookFilteredByAssignedTo(CurrentLibraryName, selectedValue, "ASC"));
            }
            if (descendingCheckBoxFiltering.isSelected()) {
                modifiedModel.addAll(AdminQueries.getAllBookFilteredByAssignedTo(CurrentLibraryName, selectedValue, "DESC"));
            }
            list.setModel(modifiedModel);

            if (modifiedModel.isEmpty()) {
                list.setModel(modifiedModel);
                JOptionPane.showMessageDialog(null,
                        "No book meets the criteria Category " + selectedCategory + " and " + selectedValue, "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public void categoryFiltering() {

        List<String> listOfUsers = new ArrayList<>();

        List<String> listOfAuthorSurnames = new ArrayList<>();

        for (String author : Queries.getAllAuthorsInLibrary(CurrentLibraryName)) {
            if (!listOfAuthorSurnames.contains(author)) {
                listOfAuthorSurnames.add(author);
            }
        }

        ResultSet set = AdminQueries.getAllAssignedToUsers(CurrentLibraryName);
        try {
            while (set.next()) {

                listOfUsers.add(set.getString("username"));
            }
        } catch (Exception e) {

        }
        listOfUsers.add("None");

        subcategoriesMap.put("Select", Arrays.asList(" "));
        subcategoriesMap.put("Status", Arrays.asList(Status.AVAILABLE.toString(), Status.BORROWED.toString()));
        subcategoriesMap.put("Author", listOfAuthorSurnames);
        subcategoriesMap.put("Genre", Arrays.asList("Przygodowa", "Akcji", "Science Fiction", "Romans", "Historyczne", "Akademickie", "Finansowe", "Dramat"));
        subcategoriesMap.put("Assigned to", listOfUsers);

        String selectedCategory = (String) categoryComboBox.getSelectedItem();

        List<String> subcategories = subcategoriesMap.get(selectedCategory);
        String subcategoriesArray[] = subcategories.toArray(new String[subcategories.size()]);


        if (subcategories != null && !selectedCategory.equals("Select")) {
            ascendingCheckBoxFiltering.setVisible(true);
            descendingCheckBoxFiltering.setVisible(true);
            SubCategoryComboBox.setEnabled(true);
            SubCategoryComboBox.setModel(new DefaultComboBoxModel<>(subcategoriesArray));
            SubCategoryComboBox.setVisible(true);
            subCategoryFiltering();
        } else {
            SubCategoryComboBox.setModel(new DefaultComboBoxModel<>(subcategoriesArray));
            SubCategoryComboBox.setEnabled(false);
            ascendingCheckBoxFiltering.setVisible(false);
            descendingCheckBoxFiltering.setVisible(false);
        }
    }

}
