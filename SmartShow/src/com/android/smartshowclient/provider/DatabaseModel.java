package com.android.smartshowclient.provider;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库模型：</br>
 * DatabaseModel </br>
 * DatabaseModel.TableModel </br>
 * DatabaseModel.TableModel.ColumnModel </br>
 * @author 叶蕾
 */
public class DatabaseModel {
    public String mDatabaseName;

    public String mDatabaseVersion;

    List<TableModel> mTableList = new ArrayList<TableModel>();

    public void addTable(TableModel table) {
        mTableList.add(table);
    }

    @Override
    public String toString() {
        return "DatabaseModel [mDatabaseName=" + mDatabaseName + ", mDatabaseVersion="
                + mDatabaseVersion + ", mTableList=" + mTableList + "]";
    }

    public static class TableModel {

        public String mTableName;

        List<ColumnModel> mColumns = new ArrayList<ColumnModel>();

        public boolean isUpdate;

        public void addColumn(ColumnModel column) {
            mColumns.add(column);
        }

        @Override
        public String toString() {
            return "TableModel [mTableName=" + mTableName + ", mColumns=" + mColumns
                    + ", isUopdate=" + isUpdate + "]";
        }
    }

    public static class ColumnModel {
        /** 整数 */
        public static final String INTEGER = "INTEGER";

        /** 浮点值 */
        public static final String REAL = "REAL";

        /** 文本字符串，存储使用的编码方式为UTF-8、UTF-16BE、UTF-16LE。 */
        public static final String TEXT = "TEXT";

        /** 存储Blob数据，该类型数据和输入数据完全相同。 */
        public static final String BLOB = "BLOB";

        /** 表示该值为NULL值。 */
        public static final String NULL = "NULL";

        /** 数据库列名 */
        public String mName;

        /** 该列的数据类型 */
        public String mType;

        /** 默认值 */
        public String mDefaultValue;

        /** 是否增加唯一性标识 */
        public boolean isUnique;

        /** 是否定义非空 */
        public boolean notNull;

        @Override
        public String toString() {
            return "ColumnModel [mName=" + mName + ", mType=" + mType + ", mDefaultValue="
                    + mDefaultValue + ", isUnique=" + isUnique + ", notNull=" + notNull + "]";
        }

    }

}
