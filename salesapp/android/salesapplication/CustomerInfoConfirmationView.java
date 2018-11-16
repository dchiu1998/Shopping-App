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

public class CustomerInfoConfirmationView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_info_confirmation_view);
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00FF00")));

        // get the intent passed from CheckoutGuestView
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        // get the inputs passed from CheckOutGuestView
        String name = bundle.getString("customerName");
        String age = bundle.getString("customerAge");
        String address = bundle.getString("customerAddress");
        // set textView with the given inputs
        TextView name_display = (TextView) findViewById(R.id.name_display);
        name_display.setText("Full name: " + name);
        TextView age_display = (TextView) findViewById(R.id.age_display);
        age_display.setText("Age : " + age);
        TextView address_display = (TextView) findViewById(R.id.address_display);
        address_display.setText("Address : " + address);
    }

    // back to user information view
    public void backToUserInfo(View view) {
        finish();
    }

    public void toOrderConfirmation(View view) {
        Intent intent = new Intent(this,OrderComfirmationView.class);
        startActivity(intent);
    }
}
