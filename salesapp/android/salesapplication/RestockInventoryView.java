package phase3.cscb07.salesapplication;

import android.app.ActionBar;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.b07.database.DatabaseAndroidHelper;
import com.b07.database.DatabaseDriverAndroid;
import com.b07.inventory.ItemImpl;
import com.b07.inventory.ItemTypes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RestockInventoryView extends AppCompatActivity {

    DatabaseAndroidHelper mydb;
    //Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restock_inventory_view);
        fillSpinner();
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF0000")));
        mydb = new DatabaseAndroidHelper(this);
    }

    // fill spinner with item name
    public void fillSpinner() {
        Spinner spinner = (Spinner)findViewById(R.id.restock_item_selection);
        ArrayAdapter<ItemTypes> adapter;
        adapter = new ArrayAdapter<ItemTypes>
                (this, android.R.layout.simple_spinner_item, ItemTypes.values());
        spinner.setAdapter(adapter);

    }

    public void restockInventory(View view) {
        int itemId;
        // get item to restock
        Spinner restockSelection = (Spinner)findViewById(R.id.restock_item_selection);
        String item = restockSelection.getSelectedItem().toString();
        EditText quantityText = (EditText)findViewById(R.id.quantity_input);
        Integer quantity = Integer.getInteger(quantityText.getText().toString());
        List<ItemImpl> itemsInDatabase = mydb.getAllItemsHelper();
        if (itemsInDatabase.isEmpty()) {
            //insert item into database
            itemId = mydb.insertItemHelper(item, new BigDecimal(2));
            mydb.updateInventoryQuantityHelper(quantity, itemId);
            finish();
        } else {
            // get quantity of the item to restock
        }
         //  mydb.updateInventoryQuantityHelper(quantity, itemId);
         }

    public void backToAdminView(View view) {
        finish();
    }
}
