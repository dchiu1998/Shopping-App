package phase3.cscb07.salesapplication;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.b07.database.DatabaseAndroidHelper;
import android.widget.Button;
import android.widget.EditText;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DisplayAccountEnterIdView extends AppCompatActivity {
    EditText userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_account_enter_id_view);
        userId = findViewById(R.id.user_id_text);
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF0000")));
    }

    // Go to a list of active accounts
    public void toAdminView (View view){
        Intent intent = new Intent(this, AdminView.class);
        startActivity(intent);
    }

    // If the check Active button is clicked
    public void toActiveAccountsView (View view) throws SQLException {
        // Get the ID entered
        int id;
        id = Integer.parseInt(userId.getText().toString());
        // Make sure the ID is valid
        DatabaseAndroidHelper mydb = new DatabaseAndroidHelper(this);
        if (mydb.getUserDetailsHelper(id) == null) {
            throw new SQLException();
        } else {
            // If valid, go to the DisplayActiveAccountsView
            Intent intent = new Intent(this, DisplayActiveAccountsView.class);
            List<Integer> idList = new ArrayList<Integer>();
            // Pass a list of the user's active account ID's to the new activity
            idList = mydb.getUserActiveAccountsHelper(id);
            intent.putExtra("List of Accounts", idList.toString());
            startActivity(intent);
        }
    }

    // If the check Inactive button is clicked
    public void toInactiveAccountsView (View view) throws SQLException {
        // Get the ID entered
        int id;
        id = Integer.parseInt(userId.getText().toString());
        // Make sure the ID is valid
        DatabaseAndroidHelper mydb = new DatabaseAndroidHelper(this);
        if (mydb.getUserDetailsHelper(id) == null) {
            throw new SQLException();
        } else {
            // If valid, go to the DisplayInactiveAccountsView
            Intent intent = new Intent(this, DisplayInactiveAccountsView.class);
            List<Integer> idList = new ArrayList<Integer>();
            // Pass a list of the user's inactive account ID's to the new activity
            idList = mydb.getUserInactiveAccountsHelper(id);
            intent.putExtra("List of Accounts", idList.toString());
            startActivity(intent);
        }
    }
}
