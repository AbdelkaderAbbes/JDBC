import javax.swing.*;
import java.awt.*;
import java.awt.event.*;;

public class CreateQuestion extends JFrame {
    private JTextArea qstTextArea;
    private JTextField categorytext;
    private JTextField[] answerTextField;
    private JRadioButton[] answerRadioBtn;
    private ButtonGroup btnGrp;

    public CreateQuestion() {
        super("Create a Quetion");
        setSize(500, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(51, 153, 255)); // rgb(51, 153, 255)
        answerRadioBtn = new JRadioButton[4];
        answerTextField = new JTextField[4];
        btnGrp = new ButtonGroup();
        addComponent();
    }

    private void addComponent() {
        JLabel LabelTitle = new JLabel("Creating Question");
        LabelTitle.setFont(new Font("Arial", Font.BOLD, 36));
        LabelTitle.setBounds(50, 15, 400, 40);
        LabelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        LabelTitle.setForeground(new Color(255, 255, 204)); // rgb(255, 255, 204)
        add(LabelTitle);


        JLabel qstLabel = new JLabel("Question");
        qstLabel.setFont(new Font("Arial", Font.BOLD, 16));
        qstLabel.setBounds(50, 45, 400, 40);
        qstLabel.setHorizontalAlignment(SwingConstants.LEFT);
        qstLabel.setForeground(new Color(255, 255, 204));
        add(qstLabel);


        qstTextArea = new JTextArea();
        qstTextArea.setFont(new Font("Arial", Font.BOLD, 16));
        qstTextArea.setBounds(50, 85, 400, 60);
        qstTextArea.setMargin(new Insets(10,10,10,10));
        qstTextArea.setLineWrap(true);
        qstTextArea.setWrapStyleWord(true);
        // qstTextArea.setHorizontalAlignment(SwingConstants.LEFT);
        qstTextArea.setForeground(new Color(0, 0, 0));
        add(qstTextArea);

        JLabel categoryLabel = new JLabel("Choose Category: ");
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 16));
        categoryLabel.setBounds(50, 150, 400, 40);
        categoryLabel.setHorizontalAlignment(SwingConstants.LEFT);
        categoryLabel.setForeground(new Color(255, 255, 204)); // rgb(255, 255, 204)
        add(categoryLabel);

        categorytext = new JTextField();
        categorytext.setFont(new Font("Arial", Font.BOLD, 16));
        categorytext.setBounds(50, 190, 400, 40);
        categorytext.setHorizontalAlignment(SwingConstants.LEFT);
        categorytext.setForeground(new Color(0, 0, 0)); // rgb(255, 255, 204)
        categorytext.setMargin(new Insets(10,10,10,10));
        add(categorytext);
    
        
        addAnswers();

        JButton submitBtn = new JButton("Submit");
        submitBtn.setFont(new Font("Arial", Font.BOLD, 16));
        submitBtn.setHorizontalAlignment(SwingConstants.CENTER);
        submitBtn.setBounds(100, 515, 150, 40);
        submitBtn.setBackground(new Color(204,238,255));
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(validateInput()) {
                    String question = qstTextArea.getText();
                    String category = categorytext.getText();
                    String[] answers = new String[answerTextField.length];
                    int correctIndex = 0;
                    for (int i = 0; i < answerTextField.length; i++) {
                        answers[i] = answerTextField[i].getText();
                        if(answerRadioBtn[i].isSelected()) {
                            correctIndex = i;
                        }
                    }

                    if(Jdbc.SaveQstAndAnswers(question, category, answers, correctIndex)) {
                        JOptionPane.showMessageDialog(CreateQuestion.this, "Successfully Added Question");
                        resetFields();
                    } else {
                        JOptionPane.showMessageDialog(CreateQuestion.this, "Failed to Add Question");

                    }

                } else {
                    JOptionPane.showMessageDialog(CreateQuestion.this, "Invalid Input");

                }
            } 
        });
        add(submitBtn);

        JButton goBack = new JButton("Go Back");
        goBack.setFont(new Font("Arial", Font.BOLD, 16));
        goBack.setHorizontalAlignment(SwingConstants.CENTER);
        goBack.setBounds(250, 515, 150, 40);
        goBack.setBackground(new Color(204,238,255));

        goBack.addMouseListener(new MouseAdapter() {
            // @Override
            public void mouseClicked(MouseEvent e) {
                Screen screen = new Screen();
                screen.setLocationRelativeTo(CreateQuestion.this);
            
                CreateQuestion.this.dispose();
                screen.setVisible(true);
            }
        });
        add(goBack);
    }


    private void addAnswers() {
        // Panel to hold the grid layout for choices
        // JPanel answerPanel = new JPanel();
        // answerPanel.setBounds(50, 240, 400, 200);
        // answerPanel.setLayout(new GridLayout(4, 1, 10, 10)); // 4 rows, 1 column with gaps
        // answerPanel.setBackground(new Color(51, 153, 255));
    
        // ButtonGroup to enforce single selection for radio buttons
        // ButtonGroup group = new ButtonGroup();
    
        for (int i = 0; i < 4; i++) {
            
            JLabel answerLabel = new JLabel("Answer N°" + (i+1));
            answerLabel.setFont(new Font("Arial", Font.BOLD, 16));
            answerLabel.setBounds(50, 225 + (70 * i), 400, 40);
            answerLabel.setHorizontalAlignment(SwingConstants.LEFT);
            answerLabel.setForeground(new Color(255, 255, 204)); // rgb(255, 255, 204)
            add(answerLabel);

            answerRadioBtn[i] = new JRadioButton();
            answerRadioBtn[i].setBounds(50, 265 + (70 * i), 20, 20);
            answerRadioBtn[i].setBackground(null);
            btnGrp.add(answerRadioBtn[i]);
            add(answerRadioBtn[i]);

            answerTextField[i] = new JTextField();
            answerTextField[i].setBounds(75, 255+(70 * i), 380, 40);
            answerTextField[i].setMargin(new Insets(10,10,10,10));
            add(answerTextField[i]);
        }
    }

    private boolean validateInput() {
        if(qstTextArea.getText().replaceAll(" ", "").length() <= 0) {
            return false;
        }
        if(categorytext.getText().replaceAll(" ", "").length() <= 0) {
            return false;
        }

        for (int i = 0; i < 4; i++) {
            if (answerTextField[i].getText().replaceAll(" ", "").length() <= 0) {
                return false;
            }
        }

        return true;
    }
    
    
    private void resetFields() {
        qstTextArea.setText("");
        categorytext.setText("");
        for (int i = 0; i < 4; i++) {
            answerTextField[i].setText("");
        }
    }
    
}
