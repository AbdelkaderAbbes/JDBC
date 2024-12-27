import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Screen extends JFrame {

    private JComboBox selectcategories;
    private JTextField qstnuminput;


    public Screen() {
        super("Quiz App");
        setSize(500, 600);
        setLayout(null);

        setLocationRelativeTo(null);

        setResizable(false);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        getContentPane().setBackground(new Color(51, 153, 255)); // rgb(51, 153, 255)
        
        addComponent();

    }
    
    public void addComponent() {
        JLabel LabelTitle = new JLabel("Quiz");
        LabelTitle.setFont(new Font("Arial", Font.BOLD, 36));
        LabelTitle.setBounds(50, 20, 400, 40);
        LabelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        LabelTitle.setForeground(new Color(255, 255, 204)); // rgb(255, 255, 204)
        add(LabelTitle);

        JLabel categoryLabel = new JLabel("Choose Category: ");
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 16));
        categoryLabel.setBounds(50, 90, 400, 40);
        categoryLabel.setHorizontalAlignment(SwingConstants.LEFT);
        categoryLabel.setForeground(new Color(255, 255, 204)); // rgb(255, 255, 204)
        add(categoryLabel);

        ArrayList<String> categories = Jdbc.getCategoryList();
        selectcategories = new JComboBox(categories.toArray());
        selectcategories.setBounds(50, 130, 400, 30);
        selectcategories.setBackground(new Color(204, 238, 255));
        selectcategories.setForeground(new Color(51,153,255));
        add(selectcategories);


        JLabel qstnum = new JLabel("Number of Questions: "); 
        qstnum.setFont(new Font("Arial", Font.BOLD, 16));
        qstnum.setBounds(50, 160, 400, 40);
        qstnum.setHorizontalAlignment(SwingConstants.LEFT);
        qstnum.setForeground(new Color(255, 255, 204)); // rgb(255, 255, 204)
        add(qstnum);

        qstnuminput = new JTextField();
        qstnuminput.setFont(new Font("Arial", Font.BOLD, 16));
        qstnuminput.setBounds(50, 200, 400, 30);
        // qstnuminput.setHorizontalAlignment(SwingConstants.LEFT);
        qstnuminput.setBackground(new Color(204, 238, 255));
        qstnuminput.setForeground(new Color(51,153,255)); // rgb(255, 255, 204)
        add(qstnuminput);


        JButton startBtn = new JButton("Start");
        startBtn.setFont(new Font("Arial", Font.BOLD, 16));
        startBtn.setHorizontalAlignment(SwingConstants.CENTER);
        startBtn.setBounds(100, 260, 300, 40);
        startBtn.setBackground(new Color(204,238,255));
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(validateInput()) {
                    Category category = Jdbc.getCategory(selectcategories.getSelectedItem().toString());

                    if (category == null) {
                        return;
                    }

                    int numOfQst = Integer.parseInt(qstnuminput.getText());

                    QuizScreen quizscreen = new QuizScreen(category, numOfQst);
                    quizscreen.setLocationRelativeTo(Screen.this);

                    Screen.this.dispose();
                    quizscreen.setVisible(true);

                }
            }
        });
        add(startBtn);

        JButton exitBtn = new JButton("Exit");
        exitBtn.setFont(new Font("Arial", Font.BOLD, 16));
        exitBtn.setHorizontalAlignment(SwingConstants.CENTER);
        exitBtn.setBounds(100, 310, 300, 40);
        exitBtn.setBackground(new Color(204,238,255));
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Screen.this.dispose();
            }
        });
        add(exitBtn);
    
        JButton createqstBtn = new JButton("Create Question");
        createqstBtn.setFont(new Font("Arial", Font.BOLD, 16));
        createqstBtn.setHorizontalAlignment(SwingConstants.CENTER);
        createqstBtn.setBounds(100, 360, 300, 40);
        createqstBtn.setBackground(new Color(204,238,255));
        createqstBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateQuestion createqstscreen = new CreateQuestion();
                createqstscreen.setLocationRelativeTo(Screen.this);
                Screen.this.dispose();
                createqstscreen.setVisible(true);
            }
        });

        add(createqstBtn);
    }

    private boolean validateInput() {
        if(qstnuminput.getText().replaceAll(" ", "").length() <= 0) {
            return false;
        }
        if(selectcategories.getSelectedItem() == null) {
            return false;
        }

        return true;
    }

} 