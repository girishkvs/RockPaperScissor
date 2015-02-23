package mc.assign1.rockpaperscissor;

import mc.assign1.rockpaperscissor.UserContract.UserEntry;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;

public class DBDelegate {

	private UserDbHelper userDbHelper;
	private SQLiteDatabase db;
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "User.db";

	public DBDelegate(Context context) {
		userDbHelper = new UserDbHelper(context, DATABASE_NAME, null,
				DATABASE_VERSION);
	}

	public boolean isUserExists(String userName) {
		db = userDbHelper.getReadableDatabase();

		String[] projection = {UserContract.UserEntry.COLUMN_USERNAME};
		String selection = UserContract.UserEntry.COLUMN_USERNAME + " LIKE ?";
		String[] selectionArgs = {userName};
		Cursor cursor = db.query(UserContract.UserEntry.TABLE_NAME, projection,
				selection, selectionArgs, null, null, null);
		if (cursor.getCount() == 1)
			return true;
		return false;
	}

	public boolean isAgeSexValid(String userName, int age, String sex) {
		db = userDbHelper.getReadableDatabase();

		String[] projection = {UserContract.UserEntry.COLUMN_AGE,
				UserContract.UserEntry.COLUMN_SEX};
		String selection = UserContract.UserEntry.COLUMN_USERNAME + " LIKE ?";
		String[] selectionArgs = {userName};
		Cursor cursor = db.query(UserContract.UserEntry.TABLE_NAME, projection,
				selection, selectionArgs, null, null, null);
		if (cursor.moveToFirst()) {
			if (cursor.getInt(0) != age)
				return false;
			if (!cursor.getString(1).equals(sex))
				return false;
		}
		return true;
	}

	public long insert(String userName, int age, String sex) {
		db = userDbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(UserContract.UserEntry.COLUMN_USERNAME, userName);
		values.put(UserContract.UserEntry.COLUMN_AGE, age);
		values.put(UserContract.UserEntry.COLUMN_SEX, sex);
		values.put(UserContract.UserEntry.COLUMN_WINS, 0);
		values.put(UserContract.UserEntry.COLUMN_DRAW, 0);
		values.put(UserContract.UserEntry.COLUMN_LOSS, 0);

		return db.insert(UserEntry.TABLE_NAME, null, values);

	}

	public void getStats(String userName, TextView win, TextView loss,
			TextView draw) {
		db = userDbHelper.getReadableDatabase();

		String[] projection = {UserContract.UserEntry.COLUMN_WINS,
				UserContract.UserEntry.COLUMN_LOSS,
				UserContract.UserEntry.COLUMN_DRAW};
		String selection = UserContract.UserEntry.COLUMN_USERNAME + " LIKE ?";
		String[] selectionArgs = {userName};
		Cursor cursor = db.query(UserContract.UserEntry.TABLE_NAME, projection,
				selection, selectionArgs, null, null, null);
		if (cursor.moveToFirst()) {
			win.setText(String.valueOf(cursor.getInt(0)));
			loss.setText(String.valueOf(cursor.getInt(1)));
			draw.setText(String.valueOf(cursor.getInt(2)));
		}

	}

	public void updateDB(String userName, String result) {

		String[] projection = {UserContract.UserEntry.COLUMN_WINS,
				UserContract.UserEntry.COLUMN_LOSS,
				UserContract.UserEntry.COLUMN_DRAW};
		String selection = UserContract.UserEntry.COLUMN_USERNAME + " LIKE ?";
		String[] selectionArgs = {userName};

		db = userDbHelper.getReadableDatabase();
		Cursor cursor = db.query(UserContract.UserEntry.TABLE_NAME, projection,
				selection, selectionArgs, null, null, null);

		ContentValues values = new ContentValues();

		if (cursor.getCount() == 1 && cursor.moveToFirst()) {

			if (result.equals("win"))
				values.put(
						UserContract.UserEntry.COLUMN_WINS,
						cursor.getInt(cursor
								.getColumnIndex(UserContract.UserEntry.COLUMN_WINS)) + 1);
			else if (result.equals("loss"))
				values.put(
						UserContract.UserEntry.COLUMN_LOSS,
						cursor.getInt(cursor
								.getColumnIndex(UserContract.UserEntry.COLUMN_LOSS)) + 1);
			else if (result.equals("draw"))
				values.put(
						UserContract.UserEntry.COLUMN_DRAW,
						cursor.getInt(cursor
								.getColumnIndex(UserContract.UserEntry.COLUMN_DRAW)) + 1);

			db.update(UserContract.UserEntry.TABLE_NAME, values, selection,
					selectionArgs);
		}

	}
}
