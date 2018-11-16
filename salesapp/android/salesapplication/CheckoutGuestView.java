package phase3.cscb07.salesapplication;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.b07.database.DatabaseAndroidHelper;

public class CheckoutGuestView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_guest_view);
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF0000")));
        // get the shopping cart passed in from ShoppingCartView
       // Intent intent = getIntent();
    }

    // go back to shopping cart
    public void backToCart(View view) {
        finish();
    }

    // display items in shopping cart
    public void display_shoppingCart() {

    }

    public void toUserInfoConfirmation(View view) {
        // the next activity to move to: CustomerInfoConfirmationView
        Intent intent = new Intent(this, CustomerInfoConfirmationView.class);
        // get the name of the customer from the editText field
        EditText nameText = (EditText) findViewById(R.id.name_input);
        String name = nameText.getText().toString();
        // get the age of the customer from the editText field
        EditText ageText = (EditText) findViewById(R.id.age_input);
        String age = ageText.getText().toString();
        // get the address of the customer from the editText field
        EditText addressText = (EditText) findViewById(R.id.address_input);
        String address = addressText.getText().toString();
        // pass the inputs to CustomerInfoConfirmationView
        intent.putExtra("customerName", name);
        intent.putExtra("customerAge", age);
        intent.putExtra("customerAddress", address);
        // start CustomerInfoConfirmationView
        startActivity(intent);
    }
}
