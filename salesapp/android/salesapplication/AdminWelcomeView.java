package phase3.cscb07.salesapplication;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class AdminWelcomeView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_welcome_view);
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF0000")));
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String adminId = bundle.getString("adminId");
        TextView admin_display = (TextView) findViewById(R.id.user_id_display);
        admin_display.setText(adminId);
    }

    public void logout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // go to AdminView
    public void toAdminView(View view) {
        Intent intent = new Intent(this, AdminView.class);
        startActivity(intent);
    }
}
