package phase3.cscb07.salesapplication;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DisplayInactiveAccountsView extends AppCompatActivity {
    TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_inactive_accounts_view);
        String idList = getIntent().getStringExtra("List of Accounts");
        display = findViewById(R.id.inactive_list_text);
        display.setText(idList);
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF0000")));
    }

    // Go back to user id enter view
    public void toDisplayAccountEnterIdView() {
        Intent intent = new Intent(this, DisplayAccountEnterIdView.class);
        startActivity(intent);
    }
}
