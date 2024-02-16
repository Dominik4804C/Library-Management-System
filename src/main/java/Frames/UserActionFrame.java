package Frames;

import Manager.CommonFunctions;
import Manager.Queries;
import org.example.LibraryManager.Book;
import org.example.LibraryManager.Library;
import org.example.UserManager.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.*;


public class UserActionFrame extends JFrame implements CommonFunctions {

    JMenuBar menubar;
    JMenu Options, Program;
    JMenuItem changeUser, changeLibrary, programInfo;
    JButton Sort,BorrowAbook,ReturnAbook,ConfirmChoice,returnAll,borrowALL, filter,ShowAllBook,QuickView;
    JList<String> list;
    Library flowLibrary;
    UserChooseIFrame userChooseIFrame;
    JCheckBox ascendingCheckBox;
    JCheckBox descendingCheckBox;
    JLabel booksLabel;

    private JComboBox<String> sortComboBox, categoryComboBox, SubCategoryComboBox;

    private boolean borrowButtonClicked = false;
    private boolean returnButtonClicked = false;
    private boolean informationBorrow = true;
    private boolean informationReturn = true;
    private Map<String, List<String>> subcategoriesMap = new HashMap<>();

    private String CurrentLibraryName;

    public UserActionFrame(UserChooseIFrame userChooseIFrame, Library library) {

        this.flowLibrary = library;
        this.userChooseIFrame = userChooseIFrame;
        CurrentLibraryName=userChooseIFrame.getLibraryManagementFrame().getSelectedLibrary();


        DefaultListModel<String> listOfAction = new DefaultListModel<>();
        list = new JList<>(listOfAction);
        list.setBounds(150, 20, 600, 150);
        add(list);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

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

        returnAll = new JButton("Return all");
        returnAll.setBounds(590, 190, 160, 40);
        add(returnAll);
        returnAll.setVisible(false);

        ImageIcon returnAllIcon = setIcon("/all.png");
        returnAll.setIcon(returnAllIcon);

        borrowALL = new JButton("Borrow all");
        borrowALL.setBounds(590, 190, 160, 40);
        add(borrowALL);
        borrowALL.setVisible(false);

        ImageIcon borrowAllIcon = setIcon("/all.png");
        borrowALL.setIcon(borrowAllIcon);

        sortComboBox = new JComboBox<>(new String[]{"Title", "Author", "Genre","Status"});
        sortComboBox.setBounds(590, 190, 160, 40);
        add(sortComboBox);

        categoryComboBox = new JComboBox<>(new String[]{"Author", "Genre", "Select","Status"});
        categoryComboBox.setBounds(590, 190, 160, 40);
        add(categoryComboBox);
        categoryComboBox.setSelectedItem("Select");

        SubCategoryComboBox = new JComboBox<>();
        SubCategoryComboBox.setBounds(590, 240, 180, 40);
        add(SubCategoryComboBox);

      //  booksLabel = new JLabel(RefreshListOfAvailableBook(library) + " books available in library");
     //   booksLabel.setBounds(350, -5, 200, 30);
     //   add(booksLabel);


        ascendingCheckBox = new JCheckBox("Sort Ascending");
        ascendingCheckBox.setBounds(250, 190, 150, 30);

        descendingCheckBox = new JCheckBox("Sort Descending");
        descendingCheckBox.setBounds(400, 190, 150, 30);
        add(ascendingCheckBox);
        add(descendingCheckBox);


        BorrowAbook = new JButton("Borrow");
        BorrowAbook.setBounds(10, 20, 130, 40);
        add(BorrowAbook);

        ImageIcon borrow = setIcon("/borrow.png");
        BorrowAbook.setIcon(borrow);


        ShowAllBook =new JButton("Show all");
        ShowAllBook.setBounds(10,120,130,40);
        add(ShowAllBook);

        ImageIcon showboook = setIcon("/all_5334695.png");
        ShowAllBook.setIcon(showboook);

        ReturnAbook = new JButton("Return");
        ReturnAbook.setBounds(10, 70, 130, 40);
        add(ReturnAbook);

        ImageIcon returnBook = setIcon("/return.png");
        ReturnAbook.setIcon(returnBook);

        QuickView = new JButton("Quick view");
        QuickView.setBounds(755, 20, 120, 40);
        add(QuickView);

        ImageIcon qucikView = setIcon("/view.png");
        QuickView.setIcon(qucikView);

        filter = new JButton("Filter books in library");
        filter.setBounds(10, 250, 200, 40);
        add(filter);

        ImageIcon filterIcon = setIcon("/filter.png");
        filter.setIcon(filterIcon);

        ConfirmChoice = new JButton("Confirm Choice");
        ConfirmChoice.setBounds(590, 240, 160, 40);
        add(ConfirmChoice);
        ConfirmChoice.setBackground(Color.PINK);
        ConfirmChoice.setVisible(false);

        ImageIcon confirm = setIcon("/approved.png");
        ConfirmChoice.setIcon(confirm);

        Sort = new JButton("Sort books in library");
        Sort.setBounds(10, 300, 200, 40);
        add(Sort);

        ImageIcon sortIcon = setIcon("/from-a-to-z.png");
        Sort.setIcon(sortIcon);


        QuickView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(list.getSelectedIndex()!=-1) {
                    new OverViewBookJFrame(library, list);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Choose one book from list", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        ShowAllBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                list.setEnabled(true);
                returnAll.setVisible(false);
                ConfirmChoice.setVisible(false);
                ascendingCheckBox.setVisible(false);
                descendingCheckBox.setVisible(false);
                sortComboBox.setVisible(false);
                categoryComboBox.setVisible(false);
                SubCategoryComboBox.setVisible(false);
                ConfirmChoice.setEnabled(true);
                borrowALL.setVisible(false);


                DefaultListModel<String> modifiedModel = new DefaultListModel<>();
                for (String book : Queries.getCurrentStateOfBooks(CurrentLibraryName)) {
                    modifiedModel.addElement(book);
                }
                list.setModel(modifiedModel);
            }
        });
        ReturnAbook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ascendingCheckBox.setVisible(false);
                descendingCheckBox.setVisible(false);
                sortComboBox.setVisible(false);
                categoryComboBox.setVisible(false);
                SubCategoryComboBox.setVisible(false);
                ConfirmChoice.setEnabled(true);
                categoryComboBox.setVisible(false);
                SubCategoryComboBox.setVisible(false);
                returnAll.setVisible(true);
                returnAll.setEnabled(true);
                borrowALL.setVisible(false);
                ConfirmChoice.setVisible(true);
                DefaultListModel<String> modifiedModel = new DefaultListModel<>();


                for (String book : Queries.getAllBorrowedBooksByUser(CurrentLibraryName,userChooseIFrame.getSelectedUserValue())) {
                        modifiedModel.addElement(book);
                }
                list.setModel(modifiedModel);

                if (!Queries.checkIfAnyBookIsInStatusBorrowed(CurrentLibraryName,userChooseIFrame.getSelectedUserValue())) {

                    ConfirmChoice.setEnabled(false);
                    returnAll.setEnabled(false);

                    list.setModel(modifiedModel);
                    JOptionPane.showMessageDialog(null, "No books to return", "Warning", JOptionPane.WARNING_MESSAGE);
                    borrowButtonClicked = false;
                } else {
                    ConfirmChoice.setEnabled(true);
                    list.setModel(modifiedModel);
                    if (informationReturn) {
                        JOptionPane.showMessageDialog(null, "Choose at least one book to return and confirm", "Warning", JOptionPane.INFORMATION_MESSAGE);
                    }
                    informationReturn = false;
                    borrowButtonClicked = false;
                    returnButtonClicked = true;
                }
            }
        });
        Sort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ascendingCheckBox.setVisible(true);
                descendingCheckBox.setVisible(true);
                categoryComboBox.setVisible(false);
                SubCategoryComboBox.setVisible(false);
                sortComboBox.setVisible(true);
                returnAll.setVisible(false);
                borrowALL.setVisible(false);
                ConfirmChoice.setVisible(false);
                list.setEnabled(true);
                DefaultListModel<String> modifiedModel = new DefaultListModel<>();

                if (flowLibrary.getListOfBooks().isEmpty()) {
                    for (Book books : flowLibrary.getListOfBooks()) {
                        modifiedModel.addElement(books.toString());
                    }
                    list.setModel(modifiedModel);
                    ConfirmChoice.setEnabled(false);
                    JOptionPane.showMessageDialog(null, "No books in library", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    for (Book books : flowLibrary.getListOfBooks()) {
                        modifiedModel.addElement(books.toString());
                    }
                    list.setModel(modifiedModel);
                    ConfirmChoice.setEnabled(false);
                }
            }
        });
        ConfirmChoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ascendingCheckBox.setVisible(false);
                descendingCheckBox.setVisible(false);
                sortComboBox.setVisible(false);
                categoryComboBox.setVisible(false);
                SubCategoryComboBox.setVisible(false);
                ConfirmChoice.setEnabled(true);

                int[] selectedIndices = list.getSelectedIndices();
                List<String> selectedValues = list.getSelectedValuesList();
                if (borrowButtonClicked) {

                    if (selectedIndices.length > 0) {

                        if(selectedValues.size()>1){
                            int odp = JOptionPane.showConfirmDialog(null, "Do you want to borrow all selected books ?");
                            if (odp == JOptionPane.YES_OPTION) {
                            for(int i=0; i<selectedValues.size();i++) {

                                String titleOfBookToUnassingFromUser = extractTitle(selectedValues.get(i));
                                Queries.updateBookStatusByTitle(CurrentLibraryName,"BORROWED",titleOfBookToUnassingFromUser,userChooseIFrame.getSelectedUserValue());

                            }

                                DefaultListModel<String> modifiedModel1 = new DefaultListModel<>();
                                for (String book : Queries.getAllAvailableBook(CurrentLibraryName)) {
                                    modifiedModel1.addElement(book);
                                }
                                list.setModel(modifiedModel1);
                                JOptionPane.showMessageDialog(null, "Selected books have been borrowed successfully", "Message", JOptionPane.INFORMATION_MESSAGE);

                                if (!Queries.checkIfAnyBookIsAvailable(CurrentLibraryName)) {
                                    borrowALL.setEnabled(false);
                                    ConfirmChoice.setEnabled(false);
                                }
                           //     booksLabel.setText(RefreshListOfAvailableBook(library) + " books available in library");
                            }
                        }

                        if (selectedValues.size() == 1) {
                            String titleOfBookToUnassingFromUser = extractTitle(list.getSelectedValue());

                            int odp = JOptionPane.showConfirmDialog(null, "Do you want to borrow a book: " + titleOfBookToUnassingFromUser + " ?");
                            if (odp == JOptionPane.YES_OPTION) {


                                Queries.updateBookStatusByTitle(CurrentLibraryName,"BORROWED",titleOfBookToUnassingFromUser,userChooseIFrame.getSelectedUserValue());

                                DefaultListModel<String> modifiedModel1 = new DefaultListModel<>();
                                for (String book : Queries.getAllAvailableBook(CurrentLibraryName)) {
                                    modifiedModel1.addElement(book);
                                }
                                list.setModel(modifiedModel1);

                                JOptionPane.showMessageDialog(null, "Book " + titleOfBookToUnassingFromUser + " has been borrowed successfully", "Message", JOptionPane.INFORMATION_MESSAGE);

                                if (!Queries.checkIfAnyBookIsAvailable(CurrentLibraryName)) {
                                    borrowALL.setEnabled(false);
                                    ConfirmChoice.setEnabled(false);
                                }
                             //   booksLabel.setText(RefreshListOfAvailableBook(library) + " books available in library");
                            }
                         }
                        }

                    else {
                            JOptionPane.showMessageDialog(null, "Choose at least one book", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                } else if (returnButtonClicked) {

                    if (selectedIndices.length > 0) {
                        DefaultListModel<String> modifiedModel = new DefaultListModel<>();

                        if(selectedValues.size()>1) {
                            int odp = JOptionPane.showConfirmDialog(null, "Do you want return all selected books? ");
                            if (odp == JOptionPane.YES_OPTION) {


                            for (int i = 0; i < selectedValues.size(); i++) {
                                String titleOfBookToUnassingFromUser = extractTitle(selectedValues.get(i));

                                Queries.updateBookStatusByTitle(CurrentLibraryName,"AVAILABLE",titleOfBookToUnassingFromUser,null);

                            }


                            for (String book : Queries.getAllBorrowedBooksByUser(CurrentLibraryName,userChooseIFrame.getSelectedUserValue())) {
                                modifiedModel.addElement(book);
                            }
                                list.setModel(modifiedModel);

                                if (!Queries.checkIfAnyBookIsInStatusBorrowed(CurrentLibraryName,userChooseIFrame.getSelectedUserValue())) {
                                    returnAll.setEnabled(false);
                                    ConfirmChoice.setEnabled(false);
                                }
                                JOptionPane.showMessageDialog(null, "All selected books has been returned successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
                              //  booksLabel.setText(RefreshListOfAvailableBook(library) + " books available in library");
                            }
                        }
                        if (selectedValues.size() == 1) {
                            String titleOfBookToUnassingFromUser = extractTitle(list.getSelectedValue());

                            int odp = JOptionPane.showConfirmDialog(null, "Do you want return a book: " + titleOfBookToUnassingFromUser + " ?");
                            if (odp == JOptionPane.YES_OPTION) {

                                Queries.updateBookStatusByTitle(CurrentLibraryName,"AVAILABLE",titleOfBookToUnassingFromUser,null);
                                for (String book : Queries.getAllBorrowedBooksByUser(CurrentLibraryName,userChooseIFrame.getSelectedUserValue())) {
                                    modifiedModel.addElement(book);
                                }
                                list.setModel(modifiedModel);


                                if (!Queries.checkIfAnyBookIsInStatusBorrowed(CurrentLibraryName,userChooseIFrame.getSelectedUserValue())) {
                                    returnAll.setEnabled(false);
                                    ConfirmChoice.setEnabled(false);
                                }
                                JOptionPane.showMessageDialog(null, "Book " + titleOfBookToUnassingFromUser + " has been returned successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
                               // booksLabel.setText(RefreshListOfAvailableBook(library) + " books available in library");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Choose at least one book", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }

        });
        changeUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ascendingCheckBox.setVisible(false);
                descendingCheckBox.setVisible(false);
                sortComboBox.setVisible(false);
                new UserChooseIFrame(userChooseIFrame.getFlowLibrary(), userChooseIFrame.getLibraryManagementFrame());
                setVisible(false);
            }
        });
        changeLibrary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ascendingCheckBox.setVisible(false);
                descendingCheckBox.setVisible(false);
                sortComboBox.setVisible(false);
                new LibraryManagementFrame(userChooseIFrame.getLibraryManagementFrame().getLibraryDataBase());
                setVisible(false);
            }
        });
        BorrowAbook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ascendingCheckBox.setVisible(false);
                descendingCheckBox.setVisible(false);
                sortComboBox.setVisible(false);
                categoryComboBox.setVisible(false);
                SubCategoryComboBox.setVisible(false);
                borrowButtonClicked = true;
                ConfirmChoice.setEnabled(true);
                ConfirmChoice.setVisible(true);
                borrowALL.setVisible(true);
                borrowALL.setEnabled(true);
                returnAll.setVisible(false);
                list.setEnabled(true);

                DefaultListModel<String> modifiedModel = new DefaultListModel<>();

                for (String book : Queries.getAllAvailableBook(CurrentLibraryName)) {
                        modifiedModel.addElement(book);
                }
                list.setModel(modifiedModel);

                if (Queries.checkIfAnyBookIsAvailable(CurrentLibraryName)) {
                    if (informationBorrow) {
                        JOptionPane.showMessageDialog(null, "Choose at least one book to borrow and confirm", "Warning", JOptionPane.INFORMATION_MESSAGE);
                    }
                    informationBorrow = false;
                    ConfirmChoice.setEnabled(true);
                    borrowButtonClicked = true;

                } else {
                    JOptionPane.showMessageDialog(null, "No books to borrow", "Warning", JOptionPane.INFORMATION_MESSAGE);
                    borrowALL.setEnabled(false);
                    ConfirmChoice.setEnabled(false);
                }
            }
        });
        programInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Program written by Dominik Jakubaszek. \n Version 2.0.0", "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        descendingCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        descendingCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (descendingCheckBox.isSelected()) {
                    ascendingCheckBox.setSelected(false);
                    SortingComboBox();
                }
            }
        });

        ascendingCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (ascendingCheckBox.isSelected()) {
                    descendingCheckBox.setSelected(false);
                    SortingComboBox();
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
        sortComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SortingComboBox();
            }
        });
        filter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ascendingCheckBox.setVisible(false);
                descendingCheckBox.setVisible(false);
                sortComboBox.setVisible(false);
                categoryComboBox.setVisible(true);
                SubCategoryComboBox.setVisible(true);
                borrowALL.setVisible(false);
                returnAll.setVisible(false);
                list.setEnabled(true);
                ConfirmChoice.setVisible(false);
                DefaultListModel<String> modifiedModel = new DefaultListModel<>();

                if (Queries.getCurrentStateOfBooks(CurrentLibraryName).isEmpty()) {
                    ConfirmChoice.setEnabled(false);

                    list.setModel(modifiedModel);
                    ConfirmChoice.setEnabled(false);
                    SubCategoryComboBox.setEnabled(false);
                    categoryComboBox.setEnabled(false);
                    JOptionPane.showMessageDialog(null, "No books to filter in library", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    ConfirmChoice.setEnabled(false);
                    SubCategoryComboBox.setEnabled(true);
                    categoryComboBox.setEnabled(true);

                    for (String book : Queries.getCurrentStateOfBooks(CurrentLibraryName)) {
                        modifiedModel.addElement(book);
                    }
                    list.setModel(modifiedModel);
                }
            }

        });
        borrowALL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DefaultListModel<String> modifiedModel = new DefaultListModel<>();
             //   User user = flowLibrary.getLibraryUserDataBase().returnObjectOfUserByName(userChooseIFrame.getChoosenUserName());


                    int odp = JOptionPane.showConfirmDialog(null, "Do you want to borrow all books?");
                    if (odp == JOptionPane.YES_OPTION) {
                        Queries.setAllBooksBorrowed(CurrentLibraryName,userChooseIFrame.getSelectedUserValue(),"AVAILABLE","BORROWED");

                        JOptionPane.showMessageDialog(null, "All books have been borrowed successfully", "Message", JOptionPane.INFORMATION_MESSAGE);

                        DefaultListModel<String> modifiedModel1 = new DefaultListModel<>();

                        for (String book : Queries.getAllAvailableBook(CurrentLibraryName)) {
                            modifiedModel.addElement(book);
                        }

                        list.setModel(modifiedModel1);
                     //   booksLabel.setText(RefreshListOfAvailableBook(library) + " books available in library");
                        if (!Queries.checkIfAnyBookIsAvailable(CurrentLibraryName)) {
                            borrowALL.setEnabled(false);
                            ConfirmChoice.setEnabled(false);
                        }
                    }
            }
        });
        returnAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int odp = JOptionPane.showConfirmDialog(null, "Do you want to return all books?");
                if (odp == JOptionPane.YES_OPTION) {

                    Queries.setAllBooksAvailable(CurrentLibraryName,null,"BORROWED","AVAILABLE",userChooseIFrame.getSelectedUserValue());
                    JOptionPane.showMessageDialog(null, "All books have been borrowed successfully", "Message", JOptionPane.INFORMATION_MESSAGE);


                    DefaultListModel<String> modifiedModel1 = new DefaultListModel<>();
                    for (String book : Queries.getAllBorrowedBooksByUser(CurrentLibraryName,userChooseIFrame.getSelectedUserValue())) {
                        modifiedModel1.addElement(book);
                    }
                    list.setModel(modifiedModel1);

                 //   booksLabel.setText(RefreshListOfAvailableBook(library) + " books available in library");

                    if (!Queries.checkIfAnyBookIsInStatusBorrowed(CurrentLibraryName,userChooseIFrame.getSelectedUserValue())) {
                        ConfirmChoice.setEnabled(false);
                        returnAll.setEnabled(false);
                    }
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(150, 20, 600, 150);
        add(scrollPane);
        setSize(900, 400);
        setTitle("User logged as " + userChooseIFrame.getChoosenUserName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setVisible(true);
        ascendingCheckBox.setVisible(false);
        descendingCheckBox.setVisible(false);
        sortComboBox.setVisible(false);
        categoryComboBox.setVisible(false);
        SubCategoryComboBox.setVisible(false);

    }


    public boolean checkIfAllBooksBorrowed( List<Book> books){



        for(Book book : books){
            if(book.getStatus()==Status.AVAILABLE){
                return false;
            }
        }
        return true;
    }

    public boolean checkIfAllBooksReturned( List<Book> books){

        for(Book book : books){
            if(book.getStatus()==Status.BORROWED){
                return false;
            }
        }
        return true;
    }

    public  void subCategoryFiltering(){

        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        String selectedGenre = (String) SubCategoryComboBox.getSelectedItem();

        DefaultListModel<String> modifiedModel = new DefaultListModel<>();
        if (selectedCategory.equals("Genre")) {

            modifiedModel.addAll(Queries.filterBookByGenre(CurrentLibraryName,selectedGenre));
            list.setModel(modifiedModel);

            if (modifiedModel.isEmpty()) {
                list.setModel(modifiedModel);
                JOptionPane.showMessageDialog(null,
                        "No book meets the criteria Category " + selectedCategory + " and " + selectedGenre, "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (selectedCategory.equals("Author")) {

            for (Book book : flowLibrary.getListOfBooks()) {
                if (book.getAuthor().getLastName().equals(selectedGenre)) {
                    modifiedModel.addElement(book.toString());
                    list.setModel(modifiedModel);
                }
            }
            if (modifiedModel.isEmpty()) {
                list.setModel(modifiedModel);
                JOptionPane.showMessageDialog(null,
                        "No book meets the criteria Category " + selectedCategory + " and " + selectedGenre, "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else if(selectedCategory.equals("Status")){

            for (Book book : flowLibrary.getListOfBooks()) {
                if (book.getStatus().toString().equals(selectedGenre)) {
                    modifiedModel.addElement(book.toString());
                    list.setModel(modifiedModel);
                }
            }
            if (modifiedModel.isEmpty()) {
                list.setModel(modifiedModel);
                JOptionPane.showMessageDialog(null,
                        "No book meets the criteria Category " + selectedCategory + " and " + selectedGenre, "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    public  void SortingComboBox(){
        DefaultListModel<String> modifiedModel = new DefaultListModel<>();
        List<Book> temporaryList = new ArrayList<>();


        temporaryList.addAll(flowLibrary.getListOfBooks());
        Comparator<Book> comparator = null;
        String selectedSortOption = (String) sortComboBox.getSelectedItem();


        if ("Title".equals(selectedSortOption)) {
            comparator = Comparator.comparing(Book::getTitle);
        } else if ("Author".equals(selectedSortOption)) {
            comparator = Comparator.comparing(Book::getAuthor);
        } else if ("Genre".equals(selectedSortOption)) {
            comparator = Comparator.comparing(Book::getGenre);
        }
        else if("Status".equals(selectedSortOption)){
            comparator = Comparator.comparing(Book::getStatus);
        }
        //
        if (!ascendingCheckBox.isSelected() && !descendingCheckBox.isSelected()) {
            JOptionPane.showMessageDialog(null, "Choose ascending or descending type and try again", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (comparator != null) {
                if (ascendingCheckBox.isSelected()) {
                    Collections.sort(temporaryList, comparator);
                }


                if (descendingCheckBox.isSelected()) {
                    Collections.sort(temporaryList, comparator.reversed());
                }

            }

            for (Book book : temporaryList) {
                modifiedModel.addElement(book.toString());
            }
            list.setModel(modifiedModel);
        }
    }
    public void categoryFiltering(){

        List<String> listOfAuthorSurnames = new ArrayList<>();

        for (String author: Queries.getAllAuthorsInLibrary(CurrentLibraryName)) {
            if (!listOfAuthorSurnames.contains(author)) {
                listOfAuthorSurnames.add(author);
            }
        }

        subcategoriesMap.put("Select", Arrays.asList(" "));
        subcategoriesMap.put("Status", Arrays.asList(Status.AVAILABLE.toString(),Status.BORROWED.toString()));
        subcategoriesMap.put("Author", listOfAuthorSurnames);
        subcategoriesMap.put("Genre", Arrays.asList("Przygodowa", "Akcji", "ScienceFiction", "Romans", "Historyczne", "Akademickie", "Finansowe", "Dramat"));

        String selectedCategory = (String) categoryComboBox.getSelectedItem();

        List<String> subcategories = subcategoriesMap.get(selectedCategory);
        String subcategoriesArray[] = subcategories.toArray(new String[subcategories.size()]);


        if (subcategories != null && !selectedCategory.equals("Select")) {
            SubCategoryComboBox.setEnabled(true);
            SubCategoryComboBox.setModel(new DefaultComboBoxModel<>(subcategoriesArray));
            SubCategoryComboBox.setVisible(true);
            subCategoryFiltering();
        } else {
            SubCategoryComboBox.setModel(new DefaultComboBoxModel<>(subcategoriesArray));
            SubCategoryComboBox.setEnabled(false);
        }
    }


}
