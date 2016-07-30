package greg.phoenix.com.carapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


public class CreateOrEditCustomer extends AppCompatActivity implements View.OnClickListener {


    private CustomerTableDBHelper dbHelper ;
    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText makeEditText;
    EditText costEditText;

    Button saveButton;
    LinearLayout buttonLayout;
    Button editButton, deleteButton;

    int customerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        customerID = getIntent().getIntExtra(MainActivity.KEY_EXTRA_CONTACT_ID, 0);

        setContentView(R.layout.activity_edit);
        firstNameEditText = (EditText) findViewById(R.id.editTextFirstName);
        lastNameEditText = (EditText) findViewById(R.id.editTextLastName);
        makeEditText = (EditText) findViewById(R.id.editTextMake);
        costEditText = (EditText) findViewById(R.id.editTextCost);


        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);
        buttonLayout = (LinearLayout) findViewById(R.id.buttonLayout);
        editButton = (Button) findViewById(R.id.editButton);
        editButton.setOnClickListener(this);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(this);

        dbHelper = new CustomerTableDBHelper(this);

        if(customerID > 0) {
            saveButton.setVisibility(View.GONE);
            buttonLayout.setVisibility(View.VISIBLE);

            Cursor rs = dbHelper.getCustomer(customerID);
            rs.moveToFirst();
            String customerFirstName = rs.getString(rs.getColumnIndex(CustomerTableDBHelper.CUSTOMER_FIRST_NAME));
            String customerLastName = rs.getString(rs.getColumnIndex(CustomerTableDBHelper.CUSTOMER_LAST_NAME));
            String carMake = rs.getString(rs.getColumnIndex(CustomerTableDBHelper.CAR_MAKE));
            int cost = rs.getInt(rs.getColumnIndex(CustomerTableDBHelper.COST));

            if (!rs.isClosed()) {
                rs.close();
            }

            firstNameEditText.setText(customerFirstName);
            firstNameEditText.setFocusable(false);
            firstNameEditText.setClickable(false);

            lastNameEditText.setText(customerLastName);
            lastNameEditText.setFocusable(false);
            lastNameEditText.setClickable(false);

            makeEditText.setText(carMake);
            makeEditText.setFocusable(false);
            makeEditText.setClickable(false);

            costEditText.setText((CharSequence) (cost +""));
            costEditText.setFocusable(false);
            costEditText.setClickable(false);

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.saveButton:
                persistCustomer();
                return;
            case R.id.editButton:
                saveButton.setVisibility(View.VISIBLE);
                buttonLayout.setVisibility(View.GONE);
                firstNameEditText.setEnabled(true);
                firstNameEditText.setFocusableInTouchMode(true);
                firstNameEditText.setClickable(true);

                lastNameEditText.setEnabled(true);
                lastNameEditText.setFocusableInTouchMode(true);
                lastNameEditText.setClickable(true);

                makeEditText.setEnabled(true);
                makeEditText.setFocusableInTouchMode(true);
                makeEditText.setClickable(true);

                costEditText.setEnabled(true);
                costEditText.setFocusableInTouchMode(true);
                costEditText.setClickable(true);
                return;
            case R.id.deleteButton:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteCustomer)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dbHelper.deleteCustomer(customerID);
                                Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                AlertDialog d = builder.create();
                d.setTitle("Delete Customer?");
                d.show();
                return;
        }
    }

    public void persistCustomer() {
        if(customerID > 0) {
            if(dbHelper.updateCustomer(customerID, firstNameEditText.getText().toString(), lastNameEditText.getText().toString(), makeEditText.getText().toString(),
                    Integer.parseInt(costEditText.getText().toString()))) {
                Toast.makeText(getApplicationContext(), "Customer Update Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(), "Customer Update Failed", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            if(dbHelper.insertCustomer(firstNameEditText.getText().toString(),
                    lastNameEditText.getText().toString(), makeEditText.getText().toString(),
                    Integer.parseInt(costEditText.getText().toString()))) {
                Toast.makeText(getApplicationContext(), "Customer Inserted", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Could not Insert Customer", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}
