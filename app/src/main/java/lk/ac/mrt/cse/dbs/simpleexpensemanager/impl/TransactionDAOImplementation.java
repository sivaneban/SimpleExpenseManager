package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.content.Context;
import android.database.Cursor;

import java.text.ParseException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

import java.util.Locale;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.Database.DatabaseInfo;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;

/**
 * Created by siva on 11/20/16.
 */

public class TransactionDAOImplementation implements TransactionDAO {


    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {

    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        return null;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        return null;
    }

    protected SQLiteDatabase database;
    private DatabaseInfo dbHelper;
    private Context mContext;
    private Log log;
    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "dd-yyyy-MM", Locale.ENGLISH);

    public TransactionDAOImplementation(Context context) {
        this.mContext = context;
        dbHelper = DatabaseInfo.getHelper(mContext);
        open();

    }
    public ArrayList<Transaction> getTransaction() throws ParseException {
        ArrayList<Transaction> transaction_list = new ArrayList<Transaction>();

        Cursor cursor = database.query(DatabaseInfo.TRANSACTION_TABLE,
                new String[] { DatabaseInfo.DATE_ATTRIBUTE,
                        DatabaseInfo.ACCOUNT_NO_ATTRIBUTE,
                        DatabaseInfo.EXPENSE_TYPE_ATTRIBUTE,
                        DatabaseInfo.AMOUNT_ATTRIBUTE}, null, null, null,
                null, null);

        while (cursor.moveToNext()) {

            Transaction transaction = new Transaction(formatter.parse(cursor.getString(0)),cursor.getString(1),ExpenseType.valueOf(cursor.getString(2).toUpperCase()),cursor.getDouble(3));

            transaction_list.add(transaction);
        }
        return transaction_list;
    }


    public void addTransaction(Transaction transaction) {
        ContentValues values = new ContentValues();

        values.put(DatabaseInfo.ACCOUNT_NO_ATTRIBUTE, transaction.getAccountNo());
        values.put(DatabaseInfo.EXPENSE_TYPE_ATTRIBUTE, transaction.getExpenseType());
        values.put(DatabaseInfo.DATE_ATTRIBUTE,formatter.format(transaction.getDate()));
        values.put(DatabaseInfo.AMOUNT_ATTRIBUTE, transaction.getAmount());
        database.insert(DatabaseInfo.TRANSACTION_TABLE, null, values);
    }
    public void open() throws SQLException {
        if(dbHelper == null)
            dbHelper = DatabaseInfo.getHelper(mContext);
        database = dbHelper.getWritableDatabase();
    }

}

