package mc.assign1.rockpaperscissor;

import android.provider.BaseColumns;

public class UserContract {

	public UserContract() {

	}

	public static abstract class UserEntry implements BaseColumns {

		public static final String TABLE_NAME = "user_info";
		public static final String COLUMN_USERNAME = "username";
		public static final String COLUMN_AGE = "age";
		public static final String COLUMN_SEX = "sex";
		public static final String COLUMN_WINS = "win";
		public static final String COLUMN_LOSS = "loss";
		public static final String COLUMN_DRAW = "draw";

	}

	private static final String TEXT_TYPE = " TEXT";
	private static final String INTEGER_TYPE = " INTEGER";
	private static final String COMMA_SEP = ",";
	public static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
			+ UserEntry.TABLE_NAME + " (" + UserEntry.COLUMN_USERNAME
			+ TEXT_TYPE + " INTEGER PRIMARY KEY" + COMMA_SEP
			+ UserEntry.COLUMN_AGE + INTEGER_TYPE + COMMA_SEP
			+ UserEntry.COLUMN_SEX + TEXT_TYPE + COMMA_SEP
			+ UserEntry.COLUMN_WINS + INTEGER_TYPE + COMMA_SEP
			+ UserEntry.COLUMN_DRAW + INTEGER_TYPE + COMMA_SEP
			+ UserEntry.COLUMN_LOSS + INTEGER_TYPE + " )";

	public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
			+ UserEntry.TABLE_NAME;

}
