package com.project.template.requestbean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.project.template.utils.GsonUtil;

/**
 * <b>Description:</b> </br>
 * 请求的基类Bean。</br>
 * 当前有考虑到文件传输的问题，添加了fileList。后面待优化。
 * @author 叶蕾
 *
 */
public abstract class RequestBean {
    public static final int DOCTOR_TYPE = 1;
    public static final int PATIENT_TYPE = 0;
    
    // 考虑到文件传输的情况
    private List<File> fileList;

    public void addFilePath(String filePath) {
        if (fileList == null) {
            fileList = new ArrayList<File>();
        }
        fileList.add(new File(filePath));
    }
    
    public void addFile(File file) {
        if (fileList == null) {
            fileList = new ArrayList<File>();
        }
        fileList.add(file);
    }

    public List<File> getFileList() {
        return fileList;
    }
    
    public int getFileListSize() {
        return (fileList != null) ? fileList.size() : 0;
    }
    
    public File getFile(int index) {
        File file = null;
        if (fileList != null) {
            file = fileList.get(index);
        }
        return file;
    }
    
    /**
     * 将RequestBean对象转换成Json字符串。
     * @return Json格式的字符串。
     */
    public String toGsonString() {
        return GsonUtil.instance().getJsonFromObject(this);
    }
}
