package phase3.cscb07.salesapplication;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.b07.database.DatabaseAndroidHelper;
import com.b07.sales.SaleImpl;
import com.b07.sales.SalesLogImpl;

import java.sql.SQLException;
import java.util.List;

public class ViewBooksView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_books_view);
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF0000")));
        try {
            displayBook();
        } catch (SQLException e) {

        }
    }

    public void displayBook() throws SQLException {
        // get sales log from database
        DatabaseAndroidHelper mydb = new DatabaseAndroidHelper(this);
        SalesLogImpl book = mydb.getSalesHelper();
        // get sales from sales log
        List<SaleImpl> sales = book.getSales();
        for (int i = 0; i < sales.size(); i++) {
            // get current sale
            SaleImpl currentSale = sales.get(i);
            // display current sale
            TextView currentSaleText = new TextView(this);
            currentSaleText.setText(currentSale.getItemMap().toString());
        }
    }
}
