import java.sql.*;
import java.util.ArrayList;

public class Jdbc {
    private static final String URL = "jdbc:postgresql://localhost:5432/Quiz";
    private static final String USER = "postgres";
    private static final String PASSWORD = "0000";

    public static boolean SaveQstAndAnswers(String question, String category, String[] answers, int correctIndex) {

        // try {
            // Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            // System.out.println("Connected to the PostgreSQL server successfully.");
            Category categoryObj = getCategory(category);

            if (categoryObj == null) {
                categoryObj = insertCategory(category);
            }
        
            Question questionObj = insertQst(categoryObj, question);
            
            return insertAnswers(questionObj, answers, correctIndex);

        // } catch (SQLException e) {
        //     System.out.println(e);
        // }

        // return false;

    }

    private static Question insertQst(Category category, String questionText){
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement insertQuestionQuery = connection.prepareStatement("INSERT INTO QUESTION(category_id, question_text)" + "VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS); 
            insertQuestionQuery.setInt(1, category.getCategoryId());
            insertQuestionQuery.setString(2, questionText);
            insertQuestionQuery.executeUpdate();

            ResultSet resultat = insertQuestionQuery.getGeneratedKeys();
            if(resultat.next()) {
                int questionId = resultat.getInt(1);
                return new Question(questionId, category.getCategoryId(), questionText);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return null;
    }

    public static Category getCategory(String category) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement getCategoryQuery = connection.prepareStatement("SELECT * FROM CATEGORY WHERE category_name = ?");
            getCategoryQuery.setString(1, category);
            ResultSet resultat = getCategoryQuery.executeQuery();

            if (resultat.next()) {
                int categoryId = resultat.getInt("category_id");
                return new Category(categoryId, category);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return null;
    }

    public static ArrayList<String> getCategoryList() {
        ArrayList<String> categoryList = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement getCategoryQuery = connection.createStatement();
            ResultSet resultat = getCategoryQuery.executeQuery("SELECT * FROM CATEGORY");

            while(resultat.next()) {
                String caterory = resultat.getString(2);
                categoryList.add(caterory);
            }  
            
            return categoryList;


        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    private static Category insertCategory(String category) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement insertCategoryQuery = connection.prepareStatement("INSERT INTO CATEGORY(category_name)" + "VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            insertCategoryQuery.setString(1, category);
            insertCategoryQuery.executeUpdate();
            ResultSet resultat = insertCategoryQuery.getGeneratedKeys();

            if(resultat.next()) {
                int categoryId = resultat.getInt(1);
                return new Category(categoryId, category);
            }


        } catch (SQLException e) {
            System.out.println(e);
        }

        return null;
    }

    private static boolean insertAnswers(Question question, String[] answers, int correctIndex) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement insertAnswerQuery = connection.prepareStatement("INSERT INTO ANSWER(question_id, answer_text, is_correct)" + "VALUES(?, ?, ?)"); 
            insertAnswerQuery.setInt(1, question.getQuestionId());
            
            for (int i = 0; i < answers.length; i++) {
                insertAnswerQuery.setString(2, answers[i]);
                if( i == correctIndex) {
                    insertAnswerQuery.setBoolean(3, true);
                } else {
                    insertAnswerQuery.setBoolean(3, false);
                }

                insertAnswerQuery.executeUpdate();
            }
            
            return true;

        } catch (SQLException e) {
            System.out.println(e);
        }

        return false;
    }

    public static ArrayList<Question> getQsts(Category category) {
        ArrayList<Question> questionList = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement getQuestionsQuery = connection.prepareStatement("SELECT * FROM QUESTION JOIN CATEGORY " + "ON QUESTION.CATEGORY_ID = CATEGORY.CATEGORY_ID " + "WHERE CATEGORY.CATEGORY_NAME = ? ORDER BY RANDOM()");
            getQuestionsQuery.setString(1, category.getCategoryName());
            
            ResultSet resultat = getQuestionsQuery.executeQuery();

            while(resultat.next()) {
                int questionId = resultat.getInt("question_id");
                int categoryId = resultat.getInt("category_id");
                String questionText = resultat.getString("question_text");
                questionList.add(new Question(questionId, categoryId, questionText));
            }

            

            return questionList;
        } catch (SQLException e) {
            System.out.println(e);
        }

        return null;
    }

    public static ArrayList<Answer> getAnswers(Object question) {
        ArrayList<Answer> answersList = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement getAnswersQuery = connection.prepareStatement("SELECT * FROM QUESTION JOIN ANSWER " + "ON QUESTION.QUESTION_ID = ANSWER.QUESTION_ID " + "WHERE QUESTION.QUESTION_ID = ? ORDER BY RANDOM()");
            getAnswersQuery.setInt(1, ((Question) question).getQuestionId()); // added cast to Qst
            
            ResultSet resultat = getAnswersQuery.executeQuery();

            while(resultat.next()) {
                int answerId = resultat.getInt("answer_id");
                String answerText = resultat.getString("answer_text");
                boolean isCorrect = resultat.getBoolean("is_correct");
                answersList.add(new Answer(answerId, ((Question) question).getQuestionId(), answerText, isCorrect)); // added cast to Qst
            }

            

            return answersList;
        } catch (SQLException e) {
            System.out.println(e);
        }

        return null;
    }

}
