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

import com.b07.userinterface.ShoppingCart;

public class ShoppingCartView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart_view);
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00FF00")));
    }

    // back to store
    public void backToStore(View view) {
        finish();
    }

    // check out a guest customer
    public void toGuestCheckout(View view) {
        // the next activity to move to : CheckoutGuestView
        Intent intent = new Intent(this, CheckoutGuestView.class);
        // get shopping cart of the user
       // ShoppingCart shoppingCart;
        // pass shopping cart to next activity
        //intent.putExtra(shoppingCart);
        // start CheckoutGuestView
        startActivity(intent);
    }
}
