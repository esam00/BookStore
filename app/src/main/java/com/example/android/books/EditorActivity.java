package com.example.android.books;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.books.data.BookContract.BookEntry;
import com.example.android.books.data.BookDbHelper;

/**
 * Allows user to create a new book or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity {

    /** EditText field to enter the book's name */
    private EditText mNameEditText;

    /** EditText field to enter the book's price */
    private  EditText mPriceEditText;

    /** EditText field to enter the book's quantity */
    private EditText mQuantityEditText;

    /** EditText field to enter the supplier name */
    private EditText mSupplierNameEditText;

    /** EditText field to enter the supplier phone number */
    private EditText mSupplierNumberEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mNameEditText = findViewById(R.id.edit_book_name);
        mPriceEditText = findViewById(R.id.edit_book_price);
        mQuantityEditText = findViewById(R.id.edit_book_quantity);
        mSupplierNameEditText = findViewById(R.id.edit_supplier_name);
        mSupplierNumberEditText = findViewById(R.id.edit_supplier_number);
    }

    /**
     * Helper method to insert hardcoded book data into the database.
     */
    private void insertBook(){

        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = mNameEditText.getText().toString().trim();
        String priceString= mPriceEditText.getText().toString().trim();
        int price = Integer.parseInt(priceString);
        String quantityString = mQuantityEditText.getText().toString().trim();
        int quantity = Integer.parseInt(quantityString);
        String supplierNameString = mSupplierNameEditText.getText().toString().trim();
        String supplierNumberString = mSupplierNumberEditText.getText().toString().trim();
        int supplierNumber = Integer.parseInt(supplierNumberString);

        // Create database helper
        BookDbHelper mDbHelper = new BookDbHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and book attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_BOOK_NAME,nameString);
        values.put(BookEntry.COLUMN_BOOK_PRICE,price);
        values.put(BookEntry.COLUMN_BOOK_QUANTITY,quantity);
        values.put(BookEntry.COLUMN_SUPPLIER_NAME,supplierNameString);
        values.put(BookEntry.COLUMN_SUPPLIER_NUMBER,supplierNumber);

        // Insert a new row for pet in the database, returning the ID of that new row.
      long newRowId = db.insert(BookEntry.TABLE_NAME,null,values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, R.string.error_saving_book, Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, getString(R.string.book_saved_successfully) + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save book to database
                insertBook();
                // Exit activity
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
