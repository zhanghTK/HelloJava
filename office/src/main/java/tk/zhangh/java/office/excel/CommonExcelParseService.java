package tk.zhangh.java.office.excel;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用Excel解析，最终解析为键值对形式
 * 键为首行表头内容，值为单元格内容
 * 文件所有内容均为字符形式,不包含其它引用,不包含特殊单元格格式
 * 如果Excel文件中包含了日期,需要指定具体的日期格式化形式,默认提供了"yyyy-MM-dd HH:mm"形式.
 * Created by ZhangHao on 2017/1/6.
 */
public class CommonExcelParseService implements ExcelParseService {
    private final static String XLS = "xls";
    private final static String XLSX = "xlsx";
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public void setDateFormat(String format) {
        this.dateFormat = new SimpleDateFormat(format);
    }

    @Override
    public List<Map<String, String>> convertToList(File file, int sheetNum) throws IOException {
        if (sheetNum < 0) {
            throw new IllegalArgumentException("sheet编号不能为负");
        }
        List<Map<String, String>> list;
        try (InputStream inStream = new FileInputStream(file)) {
            list = convertToList(inStream, file.getName(), sheetNum);
        }
        return list;
    }

    @Override
    public List<Map<String, String>> convertToList(InputStream is, String filename, int sheetNum) throws IOException {
        Workbook workbook;
        String[] names = filename.split("\\.");
        String extension = names[names.length - 1];
        if (extension.toLowerCase().equals(XLS)) {
            workbook = new HSSFWorkbook(is);
        } else if (extension.toLowerCase().equals(XLSX)) {
            workbook = new XSSFWorkbook(is);
        } else {
            throw new IllegalArgumentException("上传文件格式不正确");
        }
        return evaluate(workbook.getSheetAt(sheetNum), workbook.getCreationHelper().createFormulaEvaluator());
    }

    /**
     * 使用给定的evaluator解析sheet
     */
    private List<Map<String, String>> evaluate(Sheet sheet, FormulaEvaluator evaluator) {
        List<String> heads = evaluateHead(sheet.getRow(0), evaluator);
        return evaluateBody(sheet, heads, evaluator);
    }

    /**
     * 解析excel首行,作为最终结果的key值
     */
    private List<String> evaluateHead(Row row, FormulaEvaluator evaluator) {
        List<String> heads = new ArrayList<>();
        for (short colIx = row.getFirstCellNum(); colIx <= row.getLastCellNum(); colIx++) {
            CellValue cellValue = evaluator.evaluate(row.getCell(colIx));
            if (cellValue != null) {
                heads.add(cellValue.getStringValue().trim());
            }
        }
        return heads;
    }

    /**
     * 根据首行作为key值解析excel
     */
    private List<Map<String, String>> evaluateBody(Sheet sheet, List<String> heads, FormulaEvaluator evaluator) {
        List<Map<String, String>> resultList = new ArrayList<>();
        for (int rowIx = sheet.getFirstRowNum() + 1; rowIx <= sheet.getLastRowNum(); rowIx++) {
            resultList.add(evaluateRow(sheet.getRow(rowIx), heads, evaluator));
        }
        return resultList;
    }

    /**
     * 解析excel行
     */
    private Map<String, String> evaluateRow(Row row, List<String> heads, FormulaEvaluator evaluator) {
        Map<String, String> rowMapping = new LinkedHashMap<>();
        for (short colIx = row.getFirstCellNum(); colIx <= row.getLastCellNum() && colIx < heads.size(); colIx++) {
            Cell cell = row.getCell(colIx);
            String head = heads.get(colIx);
            CellValue cellValue = evaluator.evaluate(cell);
            if (cellValue != null) {
                rowMapping.put(head, getCellValue(cell, cellValue).trim());
            }
            if ("".equals(cell.toString())) {
                rowMapping.put(head, "");
            }
        }
        return rowMapping;
    }

    /**
     * 根据具体类型,格式,解析字符串形式的内容
     */
    private String getCellValue(Cell cell, CellValue cellValue) {
        switch (cellValue.getCellType()) {
            case Cell.CELL_TYPE_BOOLEAN:
                return Boolean.toString(cellValue.getBooleanValue());
            case Cell.CELL_TYPE_NUMERIC:
                return DateUtil.isCellDateFormatted(cell)
                        ? dateFormat.format(cell.getDateCellValue())
                        : Long.toString((long) cellValue.getNumberValue());
            case Cell.CELL_TYPE_STRING:
                return cellValue.getStringValue();
            default:
                return "";
        }
    }
}
