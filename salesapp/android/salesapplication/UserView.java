package phase3.cscb07.salesapplication;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.b07.userinterface.ShoppingCart;

public class UserView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00FF00")));
    }

    // go back to main activity
    public void logout(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void toShoppingCart(View view) {
        // the next activity to move to: ShoppingCartView
        Intent intent = new Intent(this, ShoppingCartView.class);
        // get shopping cart
        //ShoppingCart shoppingCart;
        // pass shopping cart to ShoppingCartView
        //intent.putExtra("shoppingCart", shoppingCart);
        // start ShoppingCartView
        startActivity(intent);
    }

}
