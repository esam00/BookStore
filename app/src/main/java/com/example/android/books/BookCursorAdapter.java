package com.example.android.books;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.content.ContentResolver;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.books.data.BookContract.BookEntry;
import com.example.android.books.data.BookDbHelper;

/**
 * {@link BookCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of book data as its data source. This adapter knows
 * how to create list items for each row of book data in the {@link Cursor}.
 */
public class BookCursorAdapter extends CursorAdapter {


    /**
     * Constructs a new {@link BookCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public BookCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the book data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current book can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = view.findViewById(R.id.name);
        TextView summaryTextView = view.findViewById(R.id.summary);
        TextView priceTextView = view.findViewById(R.id.price);
        TextView quantityTextView = view.findViewById(R.id.quantity);
        ImageButton reduceTextView = view.findViewById(R.id.reduce);

        // Find the columns of book attributes that we're interested in
        //final int idColumnIndex = cursor.getColumnIndex(BookEntry._ID);
        int idColumnIndex = cursor.getColumnIndex(BookEntry._ID);
        int nameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_NAME);
        int categoryColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_CATEGORY);
        int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_QUANTITY);

        // Read the book attributes from the Cursor for the current book
        String nameString = cursor.getString(nameColumnIndex);
        String categoryString = cursor.getString(categoryColumnIndex);
        String priceString = Integer.toString(cursor.getInt(priceColumnIndex));
        final String quantityString = Integer.toString(cursor.getInt(quantityColumnIndex));
        final int bookId = cursor.getInt(idColumnIndex);

        // If the book category is empty string or null, then use some default text
        // that says "Unknown category", so the TextView isn't blank.
        if (TextUtils.isEmpty(categoryString)) {
            categoryString = context.getString(R.string.unknown_category);
        }

        // Update the TextViews with the attributes for the current book
        nameTextView.setText(nameString);
        summaryTextView.setText(categoryString);
        priceTextView.setText(priceString);
        quantityTextView.setText(quantityString);

        reduceTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(quantityString);
                if (quantity >= 2) {
                    quantity -= 1;
                    Uri uri = ContentUris.withAppendedId(BookEntry.CONTENT_URI, bookId);
                    ContentValues values = new ContentValues();
                    values.put(BookEntry.COLUMN_BOOK_QUANTITY, quantity);
                    String selection = BookEntry._ID + "=?";
                    String[] selectionArgs = new String[]{String.valueOf(bookId)};

                    int rowsAffected = view.getContext().getContentResolver().update(uri, values, selection, selectionArgs);

                }
            }
        });

    }
}
