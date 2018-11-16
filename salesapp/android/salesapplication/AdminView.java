package phase3.cscb07.salesapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.ActionBar;

public class AdminView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view);
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF0000")));
    }
    // go back to main activity
    public void logout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // go to HireUserView
    public void toHireUserView(View view) {
        Intent intent = new Intent(this, HireUserView.class);
        startActivity(intent);
    }

    // go to RestockInventoryView
    public void toRestockInventoryView(View view) {
        Intent intent = new Intent(this, RestockInventoryView.class);
        startActivity(intent);
    }

    // go to CreateCustomerView
    public void toCreateCustomerView(View view) {
        Intent intent = new Intent(this, CreateCustomerView.class);
        startActivity(intent);
    }

    // go to EditAccountView
    public void toEditAccountView(View view) {
        Intent intent = new Intent(this, EditAccountView.class);
        startActivity(intent);
    }

    // go to PurchaseForCustomerView
    public void toPurchaseForCustomerView(View view) {
        Intent intent = new Intent(this, PurchaseForCustomerView.class);
        startActivity(intent);
    }

    // go to ViewBooksView
    public void toViewBooksView(View view) {
        Intent intent = new Intent(this, ViewBooksView.class);
        startActivity(intent);
    }

    // go to DisplayAccountEnterIdView
    public void toDisplayAccountEnterIdView(View view) {
        Intent intent = new Intent(this, DisplayAccountEnterIdView.class);
        startActivity(intent);
    }
}
