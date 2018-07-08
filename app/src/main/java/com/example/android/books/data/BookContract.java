package com.example.android.books.data;

import android.provider.BaseColumns;

public class BookContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private BookContract() {}

    /**
     * Inner class that defines constant values for the books database table.
     * Each entry in the table represents a single book.
     */
    public static abstract class BookEntry implements BaseColumns {

        public static final String TABLE_NAME = "books";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_BOOK_NAME= "name";
        public static final String COLUMN_BOOK_PRICE= "price";
        public static final String COLUMN_BOOK_QUANTITY= "quantity";
        public static final String COLUMN_SUPPLIER_NAME= "supplier_name";
        public static final String COLUMN_SUPPLIER_NUMBER= "supplier_phone_number";

    }
}
