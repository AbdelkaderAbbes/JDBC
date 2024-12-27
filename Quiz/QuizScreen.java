import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;


public class QuizScreen extends JFrame implements ActionListener{
    private JLabel scoreLabel; 
    private JTextArea qstTextArea;
    private JButton[] answerButton;
    private JButton nextBtn;

    private Category category;
    private ArrayList<Question> questions;
    private Question currentQst;
    public int CurrentQstNum;
    private int numofqsts;
    private int score;
    private boolean firstChoiceMade;

    QuizScreen(Category category, int numofqsts) {
        super("Quiz Game");
        setSize(500, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(51, 153, 255));
        answerButton = new JButton[4];

        this.category = category;


        questions = Jdbc.getQsts(category);

        this.numofqsts = Math.min(numofqsts, questions.size());

        for (Question question : questions) {
            ArrayList<Answer> answers = Jdbc.getAnswers(question);
            question.setAnswers(answers);
        }


        currentQst = questions.get(CurrentQstNum);

        addComponent();

    }

    private void addComponent() {

        JLabel topicLabel = new JLabel("Topic: " + category.getCategoryName());
        topicLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topicLabel.setBounds(15, 15, 250, 20);
        add(topicLabel);

        score = 0;

        scoreLabel = new JLabel("Score: " + score + "/" + numofqsts);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        scoreLabel.setBounds(385, 15, 250, 20);
        add(scoreLabel);
    
        qstTextArea = new JTextArea(currentQst.getQuestionText());
        qstTextArea.setFont(new Font("Arial", Font.BOLD, 24));
        qstTextArea.setBounds(50, 50, 400, 60);
        qstTextArea.setBackground(null);
        qstTextArea.setLineWrap(true);
        qstTextArea.setWrapStyleWord(true);
        qstTextArea.setEditable(false);
        qstTextArea.setMargin(new Insets(10,10,10,10));
        add(qstTextArea);


        loadAnswerComponent();



        nextBtn = new JButton("Next Question");
        nextBtn.setFont(new Font("Arial", Font.BOLD, 16));
        nextBtn.setBounds(125, 360, 250, 50);
        nextBtn.setVisible(false);
        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextBtn.setVisible(false);
                firstChoiceMade = false;

                currentQst = questions.get(++CurrentQstNum);
                qstTextArea.setText(currentQst.getQuestionText());

                for (int i = 0; i < currentQst.getAnswers().size(); i++) {
                    Answer answer = currentQst.getAnswers().get(i);
        
                    answerButton[i].setBackground(new Color(255,255, 255));
                    
                    answerButton[i].setForeground(new Color(0, 0, 0));
                    answerButton[i].setText(answer.getAnswerText());
                    add(answerButton[i]);
                }
            }
        });
        add(nextBtn);

        JButton returnScreen = new JButton("Return to Main Screen");
        returnScreen.setFont(new Font("Arial", Font.BOLD, 16));
        returnScreen.setBounds(125, 420, 250, 50);
        returnScreen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Screen screen = new Screen();
                screen.setLocationRelativeTo(QuizScreen.this);

                QuizScreen.this.dispose();

                screen.setVisible(true);
            }
        });
        add(returnScreen);

    }

    private void loadAnswerComponent() {
        for (int i = 0; i < currentQst.getAnswers().size(); i++) {
            Answer answer = currentQst.getAnswers().get(i);

            JButton answerBtn = new JButton(answer.getAnswerText());
            answerBtn.setBounds(125, 120 + (60 * i), 250, 50);
            answerBtn.setFont(new Font("Arial", Font.BOLD, 18));
            // answerBtn.setHorizontalAlignment(SwingConstants.LEFT);
            answerBtn.setBackground(new Color(255, 255, 255));
            answerBtn.setForeground(new Color(0, 0, 0));
            answerBtn.addActionListener(this);
            answerButton[i] = answerBtn;
            add(answerButton[i]);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton answerBtn = (JButton) e.getSource();

        Answer correctAnswer = null;

        for (Answer answer : currentQst.getAnswers()) {
            if(answer.getIsCorrect()) {
                correctAnswer = answer;
                break;
            }
        }

        if(answerBtn.getText().equals(correctAnswer.getAnswerText())) {
            answerBtn.setBackground(new Color(0, 255, 0));

            if(!firstChoiceMade) {
                scoreLabel.setText("Score: " + (++score) + "/" + numofqsts);
            }

            if(CurrentQstNum == numofqsts - 1) {
                JOptionPane.showMessageDialog(QuizScreen.this, "Final Score: " + (score) + "/" + numofqsts);
            } else {
                nextBtn.setVisible(true);
            }
        }else {
            answerBtn.setBackground(new Color(255, 0, 0));
            answerBtn.setForeground(new Color(0, 0, 0)); // changed this
        }

        firstChoiceMade = true;

    }
    
}
