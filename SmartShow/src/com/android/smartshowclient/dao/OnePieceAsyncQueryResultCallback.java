
package com.android.smartshowclient.dao;

import android.net.Uri;

/**
 * 异步查询结果回调。
 * @author 叶蕾
 *
 */
public interface OnePieceAsyncQueryResultCallback {

    void onInsertComplete(int token, Object cookie, Uri uri);

    void onDeleteComplete(int token, Object cookie, int result);

    void onQueryComplete(int token, Object cookie, Object object);

    void onUpdateComplete(int token, Object cookie, int result);
}
