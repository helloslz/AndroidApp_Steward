package luzheng.Steward;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class ArticlesContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public ArticlesContract() {}

    /* Inner class that defines the table contents */
    public static abstract class ArticlesEntry implements BaseColumns {
        public static final String TABLE_NAME = "articles";
        public static final String COLUMN_NAME_ARTICLE_ID = "articleid";
        public static final String COLOUM_NAME_PHOTO = "photo";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
    }
    
    public class ArticlesDbHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "Articles.db";
        private static final String TEXT_TYPE = " TEXT";
        private static final String IMAGE_TYPE = " BLOB";
        private static final String COMMA_SEP = ",";
        private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ArticlesEntry.TABLE_NAME + " (" +
            ArticlesEntry._ID + " INTEGER PRIMARY KEY," +
            ArticlesEntry.COLUMN_NAME_ARTICLE_ID + TEXT_TYPE + COMMA_SEP +
            ArticlesEntry.COLOUM_NAME_PHOTO + IMAGE_TYPE + COMMA_SEP +
            ArticlesEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP + 
            ArticlesEntry.COLUMN_NAME_PRICE + TEXT_TYPE + COMMA_SEP +
            ArticlesEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE +
            " )";
        private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ArticlesEntry.TABLE_NAME;

        public ArticlesDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        
        /** create table "articles" */
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }
        
        @Override
        /** this is a must-implemented method */
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
        
        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
        
        public void reset(SQLiteDatabase db) {
        	db.execSQL(SQL_DELETE_ENTRIES);
        }
    }

}
