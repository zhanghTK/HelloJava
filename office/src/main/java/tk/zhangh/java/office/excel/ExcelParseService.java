package tk.zhangh.java.office.excel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Excel解析
 * Created by ZhangHao on 2017/1/6.
 */
public interface ExcelParseService {
    /**
     * 由Excel文件的Sheet导出至List
     *
     * @param sheetNum 从零开始
     */
    List<Map<String, String>> convertToList(File file, int sheetNum) throws IOException;

    /**
     * 由Excel文件的Sheet导出至List
     *
     * @param sheetNum 从零开始
     */
    List<Map<String, String>> convertToList(InputStream is, String filename, int sheetNum) throws IOException;
}
