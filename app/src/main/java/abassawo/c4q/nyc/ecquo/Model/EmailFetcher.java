package abassawo.c4q.nyc.ecquo.Model;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by c4q-Abass on 9/16/15.
 */
public class EmailFetcher{
    public static String id;

    public static String getName(Context context) {
        Cursor CR = null;
        CR = getOwner(context);
        String id = "", name = "";
        while (CR.moveToNext())
        {
            name = CR.getString(CR.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        }
        return name;
    }


    public static String getEmailId(Context context) {
        Cursor CR = null;
        CR = getOwner(context);
        String id = "", email = "";
        while (CR.moveToNext()) {
            id = CR.getString(CR.getColumnIndex(ContactsContract.CommonDataKinds.Email.CONTACT_ID));
            email = CR.getString(CR.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
        }
        return email;
    }


    static Cursor getOwner(Context context) {
        String accountName = null;
        Cursor emailCur = null;
        AccountManager accountManager = AccountManager.get(context);
        Account[] accounts = accountManager.getAccountsByType("com.google");
        if (accounts[0].name != null) {
            accountName = accounts[0].name;
            String where = ContactsContract.CommonDataKinds.Email.DATA + " = ?";
            ArrayList<String> what = new ArrayList<String>();
            what.add(accountName);
            Log.v("Got account", "Account " + accountName);
            for (int i = 1; i < accounts.length; i++) {
                where += " or " + ContactsContract.CommonDataKinds.Email.DATA + " = ?";
                what.add(accounts[i].name);
                Log.v("Got account", "Account " + accounts[i].name);
            }
            String[] whatarr = (String[])what.toArray(new String[what.size()]);
            ContentResolver cr = context.getContentResolver();
            emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, where, whatarr, null);

        }
        return emailCur;
    }
}