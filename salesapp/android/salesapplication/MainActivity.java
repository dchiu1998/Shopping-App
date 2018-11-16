package phase3.cscb07.salesapplication;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import java.sql.SQLException;
import android.widget.TextView;

import com.b07.database.DatabaseDriverAndroid;
import com.b07.database.DatabaseAndroidHelper;

public class MainActivity extends AppCompatActivity {
    DatabaseAndroidHelper mydb = new DatabaseAndroidHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Boolean firstRun = getSharedPreferences("PREFERENCE",MODE_PRIVATE)
                .getBoolean("firstRun", true);
        if (firstRun) {
            // initialize database ?
            DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(this);
            // go to initialization view
            Intent intent = new Intent(this, InitializationView.class);
            startActivity(intent);
        }
        getSharedPreferences("PREFERENCE",MODE_PRIVATE)
                .edit().putBoolean("firstRun",false).commit();
        setContentView(R.layout.activity_main);

    }

    // when shop_as_guest button is clicked go to the UserView activity
    public void shopAsGuest(View view) {
        // the next activity to move to : UserView
        Intent intent = new Intent(this, UserView.class);
        // start UserView
        startActivity(intent);
    }

    // Run this when the user puts in an incorrect ID/Password combination
    public void invalidInput() {
        TextView notice = findViewById(R.id.incorrect_notice);
        // Set the text
        notice.setText(R.string.invalid_pwid_text);
    }

    // Run this when the user tries to log in with a non-matching role
    public void invalidRole(){
        TextView role_notice = findViewById(R.id.invalid_role);
        // Set the message
        role_notice.setText(R.string.invalid_role_notice_text);
    }

    public void customerLogin(View view) throws SQLException{
        // get the user id and password entered by the user
        EditText userIdInput = (EditText) findViewById(R.id.accountId_Input);
        EditText passwordInput = (EditText) findViewById(R.id.password_input);
        Integer userId = Integer.parseInt(userIdInput.getText().toString());
        String password = passwordInput.getText().toString();

        // Check if the ID is in the database
        if (mydb.getUserDetailsHelper(userId) == null) {
            this.invalidInput();
        }
        // Check if the password is correct
        else if (!mydb.getPasswordHelper(userId).equals(password)) {
            this.invalidInput();
        } else {
            // Check if the user is a customer
            if (mydb.getUserRoleHelper(userId) == mydb.getRoleIdHelper("CUSTOMER")){
                Intent intent = new Intent(this, UserView.class);
                // start UserView
                startActivity(intent);
                // Stop them from logging in if they are not a customer
            } else {
                this.invalidRole();
            }
        }
    }

    public void adminLogin(View view) throws SQLException {
        // get the user id and password entered by the user
        EditText userIdInput = (EditText) findViewById(R.id.accountId_Input);
        EditText passwordInput = (EditText) findViewById(R.id.password_input);
        Integer userId = Integer.parseInt(userIdInput.getText().toString());
        String password = passwordInput.getText().toString();

        // Check if the ID is in the database
        if (mydb.getUserDetailsHelper(userId) == null) {
            this.invalidInput();
        }
        // Check if the password is correct
        else if (!mydb.getPasswordHelper(userId).equals(password)){
            this.invalidInput();
        } else {
            // Check if the user is an Admin
            if (mydb.getUserRoleHelper(userId) == mydb.getRoleIdHelper("ADMIN")){
                Intent intent = new Intent(this, AdminView.class);
                // start AdminView
                startActivity(intent);
                // Stop them from logging in if they are not an admin
            } else {
                this.invalidRole();
            }
        }
    }
}
