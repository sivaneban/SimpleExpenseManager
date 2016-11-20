package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.Database;


import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;


/**
 * Created by siva on 11/20/16.
 */

public class DatabaseInfo extends SQLiteOpenHelper {
    
    public static  String ACCOUNT_TABLE = "Account";
    public static  String ACCOUNT_NO_ATTRIBUTE = "AccountNo";
    public static  String BANK_NAME_ATTRIBUTE = "BankName";
    public static  String ACCOUNT_HOLDER_ATTRIBUTE = "AccountHolderName";
    public static  String BALANCE_ATTRIBUTE = "Balance";
    
    public static final String CREATE_ACCOUNT_TABLE = "CREATE TABLE "
            + ACCOUNT_TABLE + "(" + ACCOUNT_NO_ATTRIBUTE + " INTEGER PRIMARY KEY, "
            + BANK_NAME_ATTRIBUTE + " TEXT, " +ACCOUNT_HOLDER_ATTRIBUTE + " TEXT, "+ BALANCE_ATTRIBUTE+ " DOUBLE "
            + ")";

    public static String TRANSACTION_TABLE = "TransactionTable";
    public static String DATE_ATTRIBUTE = "Date";
    public static String EXPENSE_TYPE_ATTRIBUTE = "ExpenseType";
    public static String AMOUNT_ATTRIBUTE = "Amount";

    public static final String CREATE_TRANSACTION_TABLE = "CREATE TABLE "
            + TRANSACTION_TABLE +"(" +"ID" + " INTEGER PRIMARY KEY  AUTOINCREMENT, "
            + DATE_ATTRIBUTE + " DATE, " + ACCOUNT_NO_ATTRIBUTE + " TEXT, "
            + EXPENSE_TYPE_ATTRIBUTE + " TEXT, "
            + AMOUNT_ATTRIBUTE  + " DOUBLE " +")";

    private static DatabaseInfo instance;

    public static synchronized DatabaseInfo getHelper(Context context) {
//        if (instance == null)
            instance = new DatabaseInfo(context);
        return instance;
    }

    public DatabaseInfo(Context context) {
        super(context, "140593F", null, 1);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ACCOUNT_TABLE);
        db.execSQL(CREATE_TRANSACTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
