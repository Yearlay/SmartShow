
package com.android.smartshowclient.provider;

import java.util.ArrayList;
import java.util.List;

import com.android.smartshowclient.R;
import com.android.smartshowclient.provider.DatabaseModel.ColumnModel;
import com.android.smartshowclient.provider.DatabaseModel.TableModel;
import com.android.smartshowclient.utils.PreferenceUtils;
import com.project.template.utils.Constant;
import com.project.template.utils.Log;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.text.TextUtils;

/**
 * This class be used to create the database of HeartGuard.<br/>
 * There are contain DBHelper | Table | Trigger | Virtual Table.
 * 
 * @author Leon Ye
 * @see SQLiteOpenHelper
 * @since 2015-4-17
 */
public class OnePieceDatabaseHelper extends SQLiteOpenHelper implements
        SQLiteDatabaseOperation {

    static final String TAG = "OnePieceDatabase";

    private static OnePieceDatabaseHelper sInstance = null;

    private static final int ONEPIECE_DB_MODEL_RES = R.xml.database_model_1;
    private static final int ONEPIECE_DB_DATA_RES = R.xml.database_data_1;
    private static final String ONEPIECE_DB_NAME = "heartguard";

    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase;
    private DatabaseModel mDatabaseModel;
    private String mVersion;

    private OnePieceDatabaseHelper(Context context) {
        super(context, ONEPIECE_DB_NAME, null, 1);
        mContext = context;
        getWritableDatabase().enableWriteAheadLogging();
    }

    /**
     * 获得sInstance对象。
     * 
     * @param context 上下文
     * @return sInstance对象。
     */
    public static OnePieceDatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new OnePieceDatabaseHelper(context);
        }
        return sInstance;
    }

    /**
     * 重置sInstance，并置空。
     */
    public static void resetOnePieceDatabaseHelper() {
        if (sInstance != null) {
            sInstance.close();
            sInstance = null;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        mSQLiteDatabase = db;
        updateDatabase();
    }

    /**
     * Update database model and data.
     * If database version in XML is same with version saved in SharedPreferences, database will not be changed.
     */
    private void updateDatabase() {
        mVersion = PreferenceUtils.getInstance(mContext, null).getString(Constant.Preference.KEY_DATABASE_VER);
        mDatabaseModel = DatabaseParse.getDatabase(mContext);
        if (mDatabaseModel.mDatabaseVersion.equals(mVersion)) {
            Log.d(TAG, "Current database is not changed.");
            return;
        }
        try {
            mSQLiteDatabase.beginTransaction();
            initTableModel();
            initDefaultData();
            createTrigger();
            mSQLiteDatabase.setTransactionSuccessful();
            PreferenceUtils.getInstance(mContext, null).saveString(Constant.Preference.KEY_DATABASE_VER,
                    mDatabaseModel.mDatabaseVersion);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            mSQLiteDatabase.endTransaction();
        }
    }

    private void createTrigger() {
        String insertTrigger = "CREATE TRIGGER MEDICINE_FREQUENCY_INSERT AFTER INSERT ON t_medicine_dosage BEGIN UPDATE "
                + " t_treatment set frequency=(select count(_id) from t_medicine_dosage where group_id=new.group_id) where _id=new.group_id; END;";
        execSQL(insertTrigger);
        insertTrigger = "CREATE TRIGGER MEDICINE_FREQUENCY_DELETE AFTER DELETE ON t_medicine_dosage BEGIN UPDATE "
                + " t_treatment set frequency=(select count(_id) from t_medicine_dosage where group_id=old.group_id) where _id=old.group_id; END;";
        execSQL(insertTrigger);
    }

    /**
     * Read database model in xml, then make and exec sql to update database.
     */
    private void initTableModel() {
        //mDatabaseModel = DatabaseParse.getDatabase(mContext);
        for (TableModel table : mDatabaseModel.mTableList) {
            Log.d(TAG, "create table " + table.mTableName);
            createTable(mSQLiteDatabase, table);
        }
    }


    /**
     * Read database data sqls in xml and exec sql to update database.
     */
    private void initDefaultData() {
        List<String> dataSqls = DatabaseParse.getSqlFromXml(mContext);
        for (String sql : dataSqls) {
            Log.d(TAG, "exec sql " + sql);
            execSQL(sql);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(TAG, "onUpgrade -> (" + oldVersion + " --> " + newVersion + ")");
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 执行数据库的降级操作
        Log.e(TAG, "DB down grade ....");
    }

    @Override
    public long insert(String table, String nullColumnHack, ContentValues values) {
        checkDatabaseUsable();
        return mSQLiteDatabase.insertWithOnConflict(table, nullColumnHack,
                values, SQLiteDatabase.CONFLICT_IGNORE);
    }

    @Override
    public int delete(String table, String whereClause, String[] whereArgs) {
        checkDatabaseUsable();
        return mSQLiteDatabase.delete(table, whereClause, whereArgs);
    }

    @Override
    public int update(String table, ContentValues values, String whereClause,
            String[] whereArgs) {
        checkDatabaseUsable();
        return mSQLiteDatabase.updateWithOnConflict(table, values, whereClause,
                whereArgs, SQLiteDatabase.CONFLICT_IGNORE);
    }

    @Override
    public Cursor query(String table, String[] columns, String selection,
            String[] selectionArgs, String groupBy, String having,
            String orderBy) {
        checkDatabaseUsable();
        return mSQLiteDatabase.query(table, columns, selection, selectionArgs,
                groupBy, having, orderBy);
    }

    @Override
    public void beginTransaction() {
        checkDatabaseUsable();
        mSQLiteDatabase.beginTransaction();
    }

    @Override
    public void endTransaction() {
        checkDatabaseUsable();
        mSQLiteDatabase.endTransaction();
    }

    @Override
    public void setTransactionSuccessful() {
        checkDatabaseUsable();
        mSQLiteDatabase.setTransactionSuccessful();
    }

    @Override
    public void execSQL(String sql) {
        checkDatabaseUsable();
        mSQLiteDatabase.execSQL(sql);
    }

    /**
     * String sql = "select * from table_name where id=? and name=?";
     * obj.execSQL(sql, new Object[]{1, "张三"});
     * 然后将 1传给id; 将"张三"传给 name。
     */
    @Override
    public void execSQL(String sql, String[] bindArgs) {
        checkDatabaseUsable();
        mSQLiteDatabase.execSQL(sql, bindArgs);
    }

    @Override
    public void reset(String phoneNumber) {
        resetOnePieceDatabaseHelper();
    }
    
    private void checkDatabaseUsable() {
        if (mSQLiteDatabase == null) {
            mSQLiteDatabase = getReadableDatabase();
        } else if (!mSQLiteDatabase.isOpen()) {
            mSQLiteDatabase = getReadableDatabase();
        }
    }

    @Override
    public Cursor rawQuery(String sql, String[] selectionArgs) {
        checkDatabaseUsable();
        return mSQLiteDatabase.rawQuery(sql, selectionArgs);
    }

    public void createTable(SQLiteDatabase db, TableModel table) {
        if (!TextUtils.isEmpty(table.mTableName) && table.mColumns != null
                && table.mColumns.size() > 0) {
            /**
             *  IF TABLE exists, drop this table.
             */
            String dropSql = "DROP TABLE IF EXISTS " + table.mTableName + ";";
            db.execSQL(dropSql);

            StringBuilder sql = new StringBuilder();
            sql.append(" CREATE TABLE IF NOT EXISTS ");
            sql.append(table.mTableName);
            sql.append("(");
            // 数据库自增_id: 主键primary key; Auto increment.
            sql.append(BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
            for (ColumnModel column : table.mColumns) {
                sql.append(column.mName + " " + column.mType + " ");
                if (column.notNull) {
                    sql.append(" NOT NULL ");
                }
                if (column.isUnique) {
                    sql.append(" UNIQUE ");
                }
                if (!TextUtils.isEmpty(column.mDefaultValue)) {
                    sql.append(" DEFAULT " + column.mDefaultValue);
                }
                sql.append(",");
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(");");
            Log.i("test", "createTable sql = " + sql);
            db.execSQL(sql.toString());
        }
    }
    /*
     * 只允许新增列，不允许删除和修改列
     * 
    public void upgradeTable(SQLiteDatabase db, TableModel table) {
        String sql;
        for (ColumnModel column : table.mColumns) {
            sql = "ALTER TABLE " + table.mTableName + " ADD COLUMN "
                    + column.mName + " " + column.mType;
            if (column.isUnique) {
                sql += " UNIQUE ";
            }
            if (column.notNull) {
                sql += " NOT NULL ";
            }
            if (!TextUtils.isEmpty(column.mDefaultValue)) {
                sql += " DEFAULT " + column.mDefaultValue;
            }
            db.execSQL(sql + ";");
        }
    }
    */

    static class DatabaseParse {
        public static final String TAG = "DatabaseParse";

        private static final String TAG_DATABASE = "database";
        private static final String TAG_TABLE = "table";
        private static final String TAG_COLUMN = "column";
        private static final String TAG_SQL_PREV = "sql-prev";
        private static final String TAG_SQL_SUFFIX = "sql-suffix";
        private static final String ATTRIBUTE_NAME = "name";
        private static final String ATTRIBUTE_VERSION = "version";
        private static final String ATTRIBUTE_DEFAULTVALUE = "default_value";
        private static final String ATTRIBUTE_NOTNULL = "not_null";
        private static final String ATTRIBUTE_TYPE = "type";
        private static final String ATTRIBUTE_UNIQUE = "unique";
        private static final String ATTRIBUTE_UPDATE = "update";
        private static final String ATTRIBUTE_CONTENT = "content";
        private static final String VALUE_TRUE = "1";

        /**
         */
        public static DatabaseModel getDatabase(Context context) {
            DatabaseModel database = new DatabaseModel();
            TableModel table = null;
            ColumnModel column;
            XmlResourceParser xmlParser = null;

            try {
                xmlParser = context.getResources().getXml(ONEPIECE_DB_MODEL_RES);

                int eventType = xmlParser.getEventType();
                while (eventType != XmlResourceParser.END_DOCUMENT) {
                    if (eventType == XmlResourceParser.START_TAG) {
                        String strName = xmlParser.getName();
                        Log.d(TAG, "strName = " + strName);
                        if (TAG_DATABASE.equals(strName)) {
                            getDatabaseModelFromXML(database, xmlParser);
                        } else if (TAG_TABLE.equals(strName)) {
                            table = new TableModel();
                            getTableModelFromXML(table, xmlParser);
                            database.addTable(table);
                        } else if (TAG_COLUMN.equals(strName)) {
                            if (table != null) {
                                column = new ColumnModel();
                                getColumnModelFromXML(column, xmlParser);
                                table.addColumn(column);
                            } else {
                                Log.e(TAG, "parse database*.xml error! ");
                            }
                        }
                    }
                    eventType = xmlParser.next();
               }
            } catch (Exception e) {
                Log.e(TAG, "=================", e);
            }
            finally {
                if (xmlParser != null) {
                    xmlParser.close();
                }
            }
            return database;
        }

        private static void getDatabaseModelFromXML(DatabaseModel database,
                XmlResourceParser xmlParser) {
            String value;
            ///AN: What is hell!
            //int count = xmlParser.getAttributeCount();
            //for (int i = 0; i < count; i++) {
                value = xmlParser.getAttributeValue(null, ATTRIBUTE_NAME);
                if (!TextUtils.isEmpty(value)) {
                    database.mDatabaseName = value;
                }
                value = xmlParser.getAttributeValue(null, ATTRIBUTE_VERSION);
                if (!TextUtils.isEmpty(value)) {
                    database.mDatabaseVersion = value;
                }
            //}
        }

        private static void getTableModelFromXML(TableModel table, XmlResourceParser xmlParser) {
            String value;
            ///AN: What the hell cycle is doing!
            //int count = xmlParser.getAttributeCount();
            //for (int i = 0; i < count; i++) {
                value = xmlParser.getAttributeValue(null, ATTRIBUTE_NAME);
                if (!TextUtils.isEmpty(value)) {
                    table.mTableName = value;
                }
                value = xmlParser.getAttributeValue(null, ATTRIBUTE_UPDATE);
                if (!TextUtils.isEmpty(value)) {
                    table.isUpdate = VALUE_TRUE.equals(value);
                }
            //}
        }

        private static void getColumnModelFromXML(ColumnModel column, XmlResourceParser xmlParser) {
            String value;
            ///AN: While the hell cycle is here?
            //int count = xmlParser.getAttributeCount();
            //for (int i = 0; i < count; i++) {
                value = xmlParser.getAttributeValue(null, ATTRIBUTE_NAME);
                if (!TextUtils.isEmpty(value)) {
                    column.mName = value;
                }
                value = xmlParser.getAttributeValue(null, ATTRIBUTE_DEFAULTVALUE);
                if (!TextUtils.isEmpty(value)) {
                    column.mDefaultValue = value;
                }
                value = xmlParser.getAttributeValue(null, ATTRIBUTE_UNIQUE);
                if (!TextUtils.isEmpty(value)) {
                    column.isUnique = VALUE_TRUE.equals(value);
                }
                value = xmlParser.getAttributeValue(null, ATTRIBUTE_NOTNULL);
                if (!TextUtils.isEmpty(value)) {
                    column.notNull = VALUE_TRUE.equals(value);
                }
                value = xmlParser.getAttributeValue(null, ATTRIBUTE_TYPE);
                if (!TextUtils.isEmpty(value)) {
                    column.mType = value;
                }
            //}
        }

        public static List<String> getSqlFromXml(Context context){
            List<String> sqls = new ArrayList<String>();
            XmlResourceParser xmlParser = null;
            String sqlprev = null;
            String sqlsuffix = null;
            try {
                xmlParser = context.getResources().getXml(ONEPIECE_DB_DATA_RES);
                int evtType = xmlParser.getEventType();
                while (evtType != XmlResourceParser.END_DOCUMENT) {
                    if(evtType == XmlResourceParser.START_TAG) {
                        String tagName = xmlParser.getName();
                        if (TAG_SQL_PREV.equals(tagName)) {
                            sqlprev= xmlParser.getAttributeValue(null, ATTRIBUTE_CONTENT);
                        } else if(TAG_SQL_SUFFIX.equals(tagName)) {
                            sqlsuffix = xmlParser.getAttributeValue(null, ATTRIBUTE_CONTENT);
                            if(sqlprev != null) {
                                String sql = sqlprev + sqlsuffix;
                                sqls.add(sql);
                            }
                        }
                    }
                    evtType = xmlParser.next();
                }
            }catch (Exception e) {
                Log.e(TAG, "=================", e);
            } finally {
                if (xmlParser != null)
                    xmlParser.close();
            }
            return sqls;
        }

    }

}
