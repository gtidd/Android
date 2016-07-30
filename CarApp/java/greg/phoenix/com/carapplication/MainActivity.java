package greg.phoenix.com.carapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity
{
    public final static String KEY_EXTRA_CONTACT_ID = "KEY_EXTRA_CONTACT_ID";

    private ListView listView;
    CustomerTableDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.addNew);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateOrEditCustomer.class);
                intent.putExtra(KEY_EXTRA_CONTACT_ID, 0);
                startActivity(intent);
            }
        });

        dbHelper = new CustomerTableDBHelper(this);

        final Cursor cursor = dbHelper.getAllCustomers();
        String [] columns = new String[] {
                CustomerTableDBHelper.CUSTOMER_COLUMN_ID,
                CustomerTableDBHelper.CUSTOMER_FIRST_NAME,
                CustomerTableDBHelper.CUSTOMER_LAST_NAME,

                CustomerTableDBHelper.COST
        };
        int [] widgets = new int[] {
                R.id.customerID,
                R.id.customerFirstName,
                R.id.customerLastName,
                R.id.customerCost
        };

        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.customer_info,
                cursor, columns, widgets, 0);
        listView = (ListView)findViewById(R.id.listView1);
        listView.setAdapter(cursorAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView listView, View view,
                                    int position, long id) {
                Cursor itemCursor = (Cursor) MainActivity.this.listView.getItemAtPosition(position);
                int personID = itemCursor.getInt(itemCursor.getColumnIndex(CustomerTableDBHelper.CUSTOMER_COLUMN_ID));
                Intent intent = new Intent(getApplicationContext(), CreateOrEditCustomer.class);
                intent.putExtra(KEY_EXTRA_CONTACT_ID, personID);
                startActivity(intent);
            }
        });

    }

}

