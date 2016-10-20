package com.android.smartshowclient.dao;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HeartGuardDao extends AbstractDao {
    public HeartGuardDao(Context context, OnePieceAsyncQueryResultCallback callback) {
        super(context, callback);
    }

    /**
     * 标识分为两个字节，第一个字节表示操作类型01代表查询。
     * 第二个字节代表操作, 00~FF一共有255个操作可以表示。
     */
    public static final int TOKEN_QUERY_DICTIONNARY            = 0x0110;
    public static final int TOKEN_QUERY_QUESTION_CONTENT       = 0x0101;

    @Override
    protected Object cursorToModelOrModelList(int token, Object cookie,
            Cursor cursor) {
//        switch(token) {
//            case TOKEN_QUERY_DICTIONNARY:
//                List<DataDictionary> dataDictionarys = new ArrayList<DataDictionary>();
//                DataDictionary dataDictionary;
//                try {
//                    if (cursor != null) {
//                        while (cursor.moveToNext()) {
//                            dataDictionary = new DataDictionary();
//                            dataDictionary.set_id(cursor.getString(cursor
//                                    .getColumnIndexOrThrow(OnePieceConstant.Table.COLUMNS_DATA_DICTIONARY[0])));
//                            dataDictionary.setItemName(cursor.getString(cursor
//                                    .getColumnIndexOrThrow(OnePieceConstant.Table.COLUMNS_DATA_DICTIONARY[1])));
//                            dataDictionary.setPropertyId(cursor.getString(cursor
//                                    .getColumnIndexOrThrow(OnePieceConstant.Table.COLUMNS_DATA_DICTIONARY[2])));
//                            dataDictionary.setOrderby(cursor.getInt(cursor
//                                    .getColumnIndexOrThrow(OnePieceConstant.Table.COLUMNS_DATA_DICTIONARY[3])));
//                            dataDictionarys.add(dataDictionary);
//                        }
//                    }
//                } catch (IllegalArgumentException e) {
//                    e.printStackTrace();
//                } finally {
//                    if (null != cursor && !cursor.isClosed()) {
//                        cursor.close();
//                    }
//                }
//                return dataDictionarys;
//            case TOKEN_QUERY_QUESTION_CONTENT:
//                List<QuestionContent> questions = new ArrayList<QuestionContent>();
//                try {
//                    if (cursor != null) {
//                        while(cursor.moveToNext()) {
//                            QuestionContent question = new QuestionContent();
//                            question.setId(
//                                cursor.getInt(
//                                        cursor.getColumnIndexOrThrow(OnePieceConstant.Table.COLUMNS_QUESTION_CONTENT[0])));
//                            question.setNum(
//                                cursor.getString(
//                                        cursor.getColumnIndexOrThrow(OnePieceConstant.Table.COLUMNS_QUESTION_CONTENT[1])));
//                            question.setContent(
//                                cursor.getString(
//                                        cursor.getColumnIndexOrThrow(OnePieceConstant.Table.COLUMNS_QUESTION_CONTENT[2])));
//                            String strOpts =
//                                cursor.getString(
//                                        cursor.getColumnIndexOrThrow(OnePieceConstant.Table.COLUMNS_QUESTION_CONTENT[3]));
//                            List<String> lstOpts = new ArrayList<String>();
//                            String[] opts = strOpts.split("|");
//                            Collections.addAll(lstOpts, opts);
//                            question.setOpts(lstOpts);
//                            question.setType(
//                                cursor.getInt(
//                                        cursor.getColumnIndexOrThrow(OnePieceConstant.Table.COLUMNS_QUESTION_CONTENT[4])));
//                            question.setAns(
//                                cursor.getString(
//                                        cursor.getColumnIndexOrThrow(OnePieceConstant.Table.COLUMNS_QUESTION_CONTENT[5])));
//                            questions.add(question);
//                        }
//                    }
//                }catch (IllegalArgumentException e) {
//                    e.printStackTrace();
//                } finally {
//                    if (null != cursor && !cursor.isClosed()) {
//                        cursor.close();
//                    }
//                }
//
//                return questions;
//        }
        return null;
    }

    public void getDataDictionaryByPropertyCode(String propertyCode) {
//        Uri uri = Uri.withAppendedPath(OnePieceConstant.CONTENT_URI,
//                OnePieceConstant.Table.T_DATA_DICTIONARY);
//        String selection = OnePieceConstant.Table.COLUMNS_DATA_DICTIONARY[2] + " like '" + propertyCode + "'%";
//        query(TOKEN_QUERY_DICTIONNARY, null, uri, null, selection, null, null);
    }

    public void getQuestionContentByQuestionNum(String quesNum) {
//        Uri uri = Uri.withAppendedPath(OnePieceConstant.CONTENT_URI, OnePieceConstant.Table.T_QUESTION_CONTENT);
//        String selection = OnePieceConstant.Table.COLUMNS_QUESTION_CONTENT[2] + " like '" + quesNum + "'%";
//        query(TOKEN_QUERY_QUESTION_CONTENT, null, uri, null, selection, null, null);
    }

}
