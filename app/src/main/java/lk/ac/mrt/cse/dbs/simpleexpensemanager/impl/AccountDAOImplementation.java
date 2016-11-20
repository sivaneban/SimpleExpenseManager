package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.Database.DatabaseInfo;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

/**
 * Created by siva on 11/20/16.
 */

public class AccountDAOImplementation implements AccountDAO {
    @Override
    public List<String> getAccountNumbersList() {
        return null;
    }

    @Override
    public List<Account> getAccountsList() {
        return null;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        return null;
    }
    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
    }
    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
    }
    protected SQLiteDatabase database;
    private DatabaseInfo dbHelper;
    private Context mContext;

    public AccountDAOImplementation(Context context) {
        this.mContext = context;
        dbHelper = DatabaseInfo.getHelper(mContext);
        open();
    }
    public List<String> get_ID_List() {
        List<String> account_ID_list = new ArrayList<>();
        String query = "select "+DatabaseInfo.ACCOUNT_NO_ATTRIBUTE+" from "+DatabaseInfo.ACCOUNT_TABLE;
        Cursor cursor = database.rawQuery(query, null);
        try {
            while (cursor.moveToNext()) {
                account_ID_list.add(cursor.getString(cursor.getColumnIndex(DatabaseInfo.ACCOUNT_NO_ATTRIBUTE)));
            }
        }catch(Exception ex){
            Log.e("Error in getting IDs ",ex.toString());
        }
        return account_ID_list;
    }

    @Override
    public void addAccount(Account account) {
        ContentValues values = new ContentValues();
        values.put(DatabaseInfo.ACCOUNT_NO_ATTRIBUTE, account.getAccountNo());
        values.put(DatabaseInfo.BANK_NAME_ATTRIBUTE, account.getBankName());
        values.put(DatabaseInfo.ACCOUNT_HOLDER_ATTRIBUTE,account.getAccountHolderName());
        values.put(DatabaseInfo.BALANCE_ATTRIBUTE, account.getBalance());
        database.insert(DatabaseInfo.ACCOUNT_TABLE, null, values);
    }
    public void open() throws SQLException {
        if(dbHelper == null)
            dbHelper = DatabaseInfo.getHelper(mContext);
        database = dbHelper.getWritableDatabase();
    }


}
