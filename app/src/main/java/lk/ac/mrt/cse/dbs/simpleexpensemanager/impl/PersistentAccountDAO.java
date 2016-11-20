package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

/**
 * Created by siva on 11/20/16.
 */
public class PersistentAccountDAO implements AccountDAO {
    private SQLiteDatabase database;

    //We need to store the database in the constructor to prevent reopening it everytime
    public PersistentAccountDAO(SQLiteDatabase db){
        this.database = db;
    }
    @Override
    public List<String> getAccountNumbersList() {
        //To loop through results, we first acquire a cursor to the result set.
        //Cursor is just an iterator
        Cursor resultSet = database.rawQuery("SELECT Account_no FROM Account",null);
        //We point the cursor to the first record before looping

        //Initialize a list to store the relevant data
        List<String> accounts = new ArrayList<String>();

        //Loop the iterator and add data to the List
        if(resultSet.moveToFirst()) {
            do {
                accounts.add(resultSet.getString(resultSet.getColumnIndex("Account_no")));
            } while (resultSet.moveToNext());
        }
        //Return the list
        return accounts;
    }

    @Override
    public List<Account> getAccountsList() {
        Cursor resultSet = database.rawQuery("SELECT * FROM Account",null);
        List<Account> accounts = new ArrayList<Account>();

        if(resultSet.moveToFirst()) {
            do {
                Account account = new Account(resultSet.getString(resultSet.getColumnIndex("AccountNo")),
                        resultSet.getString(resultSet.getColumnIndex("Bank")),
                        resultSet.getString(resultSet.getColumnIndex("Holder")),
                        resultSet.getDouble(resultSet.getColumnIndex("InitialAmountt")));
                accounts.add(account);
            } while (resultSet.moveToNext());
        }

        return accounts;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        Cursor resultSet = database.rawQuery("SELECT * FROM Account WHERE AccountNo = " + accountNo,null);
        Account account = null;

        if(resultSet.moveToFirst()) {
            do {
                account = new Account(resultSet.getString(resultSet.getColumnIndex("AccountNo")),
                        resultSet.getString(resultSet.getColumnIndex("Bank")),
                        resultSet.getString(resultSet.getColumnIndex("Holder")),
                        resultSet.getDouble(resultSet.getColumnIndex("InitialAmount")));
            } while (resultSet.moveToNext());
        }

        return account;
    }

    @Override
    public void addAccount(Account account) {
        //For inserting we use prepared statements
        //First we prepare the sql with the variables to be hold
        String sql = "INSERT INTO Account (AccountNo,Bank,Holder,InitialAmount) VALUES (?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);


        //Bind the values correctly. First holder is index 1
        statement.bindString(1, account.getAccountNo());
        statement.bindString(2, account.getBankName());
        statement.bindString(3, account.getAccountHolderName());
        statement.bindDouble(4, account.getBalance());

        //Execute it
        statement.executeInsert();


    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        String sql = "DELETE FROM Account WHERE AccountNo = ?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1,accountNo);

        statement.executeUpdateDelete();
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        String sql = "UPDATE Account SET InitialAmount = InitialAmount + ?";
        SQLiteStatement statement = database.compileStatement(sql);
        if(expenseType == ExpenseType.EXPENSE){
            statement.bindDouble(1,-amount);
        }else{
            statement.bindDouble(1,amount);
        }

        statement.executeUpdateDelete();
    }
}
