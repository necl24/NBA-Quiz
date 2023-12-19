import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizGame extends JFrame {
    private static final int NUM_QUESTIONS = 10;

    private JLabel questionLabel;
    private ButtonGroup optionGroup;
    private JRadioButton[] options;
    private JButton nextButton;

    private int currentQuestion = 0;
    private int score = 0;

    private String[] questions = {
            "Who is the NBA's Leader in All-time Points?",
            "Whose Nickname in the NBA is the Beard?",
            "What NBA Franchise Has the Most Championships (2000s)",
            "Who was the Tallest NBA Player of All Time?",
            "Who is the NBA's All-Time leader in Assists?",
            "Who is the Richest NBA Player All Time?",
            "What NBA Player Scored the Most Points in One Game Ever?",
            "Where was the NBA created??",
            "How Much Money Does the NBA Generate (In Billions)?",
            "Who is the strongest NBA Player of All Time?"
    };

    private String[][] optionsData = {
            {"LeBron James", "Kareem Abdul-Jabbar", "Michael Jordan", "Tim Duncan"},
            {"LeBron James", "Larry Bird", "Kevin Durant", "James Harden"},
            {"Boston Celtics", "Los Angeles Lakers", "Indiana Pacers", "Detroit Pistons"},
            {"Shaquille O'Neal", "Yao Ming", "Kevin Durant", "Sun Mingming"},
            {"Chris Paul", "Rajon Rondo", "Jason Kidd", "John Stockton"},
            {"LeBron James", "Michael Jordan", "Steph Curry", "Larry Bird"},
            {"Wilt Chamberlain", "Steph Curry", "Damian Lillard", "LeBron James"},
            {"New York City, New York", "Indianapolis, Indiana", "Houston, Texas", "Durham, North Carolina"},
            {"4", "1", "7", "10"},
            {"Shaquille O'Neal", "Nikola Jokic", "Wilt Chamberlain", "Dwight Howard"}
    };

    private String[] correctAnswers = {
            "LeBron James", "James Harden", "Los Angeles Lakers", "Sun Mingming", "John Stockton",
            "Michael Jordan", "Wilt Chamberlain", "New York City, New York", "10", "Wilt Chamberlain"
    };

    public QuizGame() {
        setTitle("NBA Quiz Game");
        setSize(800, 600); // Adjusted window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeComponents();

        getContentPane().setBackground(new Color(44, 62, 80));
        setForeground(new Color(236, 240, 241));

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setVisible(true);
    }

    private void initializeComponents() {
        questionLabel = new JLabel("<html>" + questions[currentQuestion] + "</html>");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setVerticalAlignment(SwingConstants.CENTER);
        questionLabel.setPreferredSize(new Dimension(700, 120)); // Adjusted preferred height

        add(Box.createVerticalGlue());
        add(questionLabel);

        JPanel optionsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        optionGroup = new ButtonGroup();
        options = new JRadioButton[4];

        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton(optionsData[currentQuestion][i]);
            options[i].setFont(new Font("Arial", Font.PLAIN, 16));
            optionGroup.add(options[i]);

            gbc.gridx = 0;
            gbc.gridy = i;
            optionsPanel.add(options[i], gbc);
        }

        optionsPanel.setPreferredSize(new Dimension(700, 120)); // Adjusted preferred height

        add(optionsPanel);

        nextButton = new JButton("Next");
        nextButton.setEnabled(false);  // Initially disable the button
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
                loadNextQuestion();
            }
        });
        nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(nextButton);

        // Add action listeners to radio buttons
        for (JRadioButton option : options) {
            option.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    nextButton.setEnabled(true);
                }
            });
        }
    }

    private void checkAnswer() {
        for (int i = 0; i < 4; i++) {
            if (options[i].isSelected() && options[i].getText().equals(correctAnswers[currentQuestion])) {
                score++;
                break;
            }
        }
    }

    private void loadNextQuestion() {
        currentQuestion++;

        if (currentQuestion < NUM_QUESTIONS) {
            questionLabel.setText("<html>" + questions[currentQuestion] + "</html>");

            for (int i = 0; i < 4; i++) {
                options[i].setText(optionsData[currentQuestion][i]);
                options[i].setSelected(false);
            }

            nextButton.setEnabled(false);  // Disable the button after loading the next question
        } else {
            askToRetry();
        }
    }

    private void askToRetry() {
        int choice = JOptionPane.showConfirmDialog(this, "This is Your Score: " + score + " out of " + NUM_QUESTIONS + "   \nDo you want to take the quiz again?",
                "Retry Quiz", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            resetQuiz();
        } else {
            showResult();
        }
    }

    private void resetQuiz() {
        currentQuestion = 0;
        score = 0;
        questionLabel.setText("<html>" + questions[currentQuestion] + "</html>");
        for (int i = 0; i < 4; i++) {
            options[i].setText(optionsData[currentQuestion][i]);
            options[i].setSelected(false);
        }
    }

    private void showResult() {
        JOptionPane.showMessageDialog(this, "Quiz completed!\nYour score: " + score + " out of " + NUM_QUESTIONS,
                "Quiz Result", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new QuizGame();
            }
        });
    }
}
