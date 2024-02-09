package Frames;

import org.example.LibraryManager.Library;
import org.example.LibraryManager.LibraryDataBase;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LibraryManagementFrame extends JFrame implements ActionListener {

    JLabel label,welcome;
    JLabel selectedLibrary;
    JButton buttonConfirm;
    JComboBox<String> comboBox;
    LibraryDataBase libraryDataBase;
    Library flowLibrary;
    public LibraryManagementFrame(LibraryDataBase libraryDataBase){

        this.libraryDataBase=libraryDataBase;

        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
        for(Library Singlelibrary : libraryDataBase.getListOfLibrary()){
            comboBoxModel.addElement(Singlelibrary.getNameOfLibrary());
        }

        setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                int width = getWidth();
                int height = getHeight();

                GradientPaint gradient = new GradientPaint(0, 0, new Color(240,248,255), width, height, new Color(0,191, 255));

                ((Graphics2D) g).setPaint(gradient);
                g.fillRect(0, 0, width, height);

            }
        });

        comboBox = new JComboBox<>(comboBoxModel);
        comboBox.setBounds(70,150, 120,40);

        label = new JLabel("Select library");
        label.setBounds(82,120, 125,40);

        welcome= new JLabel("Welcome!");
        welcome.setBounds(82,30, 125,40);
        welcome.setFont(new Font("Forte",Font.ITALIC,20));


        selectedLibrary = new JLabel("Selected library");
        selectedLibrary.setBounds(120,220, 150,20);

        buttonConfirm= new JButton("Confirm");
        buttonConfirm.setBounds(70,200, 120,40);

        buttonConfirm.addActionListener(this);

        add(comboBox);
        add(label);
        add(buttonConfirm);
        add(welcome);

        setSize(270,350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setVisible(true);
        setTitle("Select library");

    }

    public void IfButtonClicked(){
        String selectedLibraryText = (String) comboBox.getSelectedItem();
        if(selectedLibraryText == null || selectedLibraryText.isEmpty()){
        JOptionPane.showMessageDialog(LibraryManagementFrame.this,"You have to choose at least 1 library","Error",JOptionPane.ERROR_MESSAGE);
        }
        else {
            selectedLibrary.setText("Selected library is " + selectedLibraryText);

            for (Library librarySpec : libraryDataBase.getListOfLibrary()) {
                if (librarySpec.getNameOfLibrary().equalsIgnoreCase(selectedLibraryText)) {
                    flowLibrary = librarySpec;
                }
            }
            UserChooseIFrame userChooseIFrame = new UserChooseIFrame(flowLibrary,LibraryManagementFrame.this);  // przekazujesz klase IFrame z zainicjowanym obiektem UserDataBase
            dispose();
            userChooseIFrame.setVisible(true);
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        Object source= e.getSource();
        if(source==buttonConfirm) {
            IfButtonClicked();
        }
    }
    }


