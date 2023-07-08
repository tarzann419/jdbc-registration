import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class RegistrationForm extends JDialog {
    private JTextField tfName;
    private JTextField tfEmail;
    private JPasswordField tfPassword;
    private JButton registerButton;
    private JPanel RegisterPanel;
    private JPasswordField tfConfirmPass;

    public RegistrationForm(JFrame parent){
        super(parent);
        setTitle("Registration Form");
        setContentPane(RegisterPanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);



        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        setVisible(true);

    }

    private void registerUser() {
        String name = tfName.getText();
        String email = tfEmail.getText();
        String password = String.valueOf(tfPassword.getPassword());
        String confirmPassword = String.valueOf(tfConfirmPass.getPassword());


        if (name.isEmpty() || email.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(this,
                    "Please Enter All Fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);

            return;
        }

        if (!password.equals(confirmPassword)){
            JOptionPane.showMessageDialog(this,
                    "Confirm Password doesnt Match",
                    "try again",
                    JOptionPane.ERROR_MESSAGE);

            return;
        }

        user = addUserToDatabase(name, email, password);

        if (user != null){
            dispose();
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Register no do",
                    "try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public User user;
    private User addUserToDatabase(String name, String email, String password){
        User user = null;

        final String DB_URL = "jdbc.mysql://localhost/my_store?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "root";

        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement statement = connection.createStatement();

            String sql = "INSERT INTO users(name, email, password)" + "VALUES(?, ?, ?, ?)";

            // variables that we are going to add to the query
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);


            // to execute the sql query
            // insert row into to the table



            // closing all connections and ending processes
            statement.close();
            connection.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return user;
    }

    public static void main(String[] args) {
        RegistrationForm myForm = new RegistrationForm(null);

        //read user object

        User user = myForm.user;
        if (user != null){
            System.out.println("successful registration for:" + user.name);
        }
        else {
            System.out.println("registration canceled");
        }

    }
}
