package phase3.cscb07.salesapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.b07.database.DatabaseAndroidHelper;
import com.b07.database.DatabaseDriverAndroid;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.users.Admin;
import com.b07.users.User;

import java.sql.SQLException;

public class InitializationView extends AppCompatActivity {

    DatabaseDriverAndroid mydb = new DatabaseDriverAndroid(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialization_view);
    }

    private long initializeAdmin(User admin, DatabaseDriverAndroid connection)
            throws SQLException, DatabaseInsertException {
        // get the name of the user from the editText field
        EditText nameText = (EditText) findViewById(R.id.name_input);
        String name = nameText.getText().toString();
        // get the age of the user from the editText field
        EditText ageText = (EditText) findViewById(R.id.age_input);
        Integer age = Integer.getInteger(ageText.getText().toString());
        // get the address of the user from the editText field
        EditText addressText = (EditText) findViewById(R.id.address_input);
        String address = addressText.getText().toString();
        // get the password of the user from the editText field
        EditText passwordText = (EditText) findViewById(R.id.password_input);
        String password = passwordText.getText().toString();
        DatabaseAndroidHelper mydb = null;
        long adminId = mydb.insertNewUserHelper(name, age, address, password);
        // fix hard coded userId
        //mydb.insertUserRole(adminId, helper.getUserRole(0));
        return adminId;
    }

    // TODO: validate user input for initializing new administrator before going to next view
    public void toAdminWelcomeView(View view) {

        // create a new admin object
        Admin admin = null;
        // initalize the admin
        try {
            long adminId = initializeAdmin(admin, mydb);
        } catch (SQLException e){
            e.printStackTrace();
        } catch (DatabaseInsertException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, AdminWelcomeView.class);
        intent.putExtra("adminId", admin.getId());
        startActivity(intent);
    }
}
