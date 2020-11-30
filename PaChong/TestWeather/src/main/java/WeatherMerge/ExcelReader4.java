package WeatherMerge;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @Auther:HuangZhiwen
 * @Date: 2020/11/19 - 11 - 8:39
 * @Description: WeatherMerge
 * @version: 1.0
 */
public class ExcelReader4 {
    private static Logger logger = Logger.getLogger(ExcelReader.class.getName()); // 日志打印类
    private int sizeLength=9;
    protected static final String XLS = "xls";
    protected static final String XLSX = "xlsx";

    /**
     * 根据文件后缀名类型获取对应的工作簿对象
     * @param inputStream 读取文件的输入流
     * @param fileType 文件后缀名类型（xls或xlsx）
     * @return 包含文件数据的工作簿对象
     * @throws IOException
     */
    public static Workbook getWorkbook(InputStream inputStream, String fileType) throws IOException {
        XSSFWorkbook workbook = null;
        if (fileType.equalsIgnoreCase(XLS)) {
//            workbook = new HSSFWorkbook(inputStream);
        } else if (fileType.equalsIgnoreCase(XLSX)) {
            workbook = new XSSFWorkbook(inputStream);
            System.out.println(inputStream);
        }
        return workbook;
    }

    /**
     * 读取Excel文件内容
     * @param fileName 要读取的Excel文件所在路径
     * @return 读取结果列表，读取失败时返回null
     */
    public List<ExcelDataVO> readExcel(String fileName) {

        XSSFWorkbook xssfWorkbook = null;
        Workbook workbook = null;
        FileInputStream inputStream = null;

        try {
            // 获取Excel后缀名
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            // 获取Excel文件
            File excelFile = new File(fileName);
            if (!excelFile.exists()) {
                logger.warning("指定的Excel文件不存在！");
                return null;
            }

            // 获取Excel工作簿
            inputStream = new FileInputStream(excelFile);
            workbook = getWorkbook(inputStream, fileType);

            // 读取excel中的数据
            List<ExcelDataVO> resultDataList = parseExcel(workbook);

            return resultDataList;
        } catch (Exception e) {
            logger.warning("解析Excel失败，文件名：" + fileName + " 错误信息：" + e.getMessage());
            return null;
        } finally {
            try {
                if (null != workbook) {
                    workbook.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (Exception e) {
                logger.warning("关闭数据流出错！错误信息：" + e.getMessage());
                return null;
            }
        }
    }

    /**
     * 解析Excel数据
     * @param workbook Excel工作簿对象
     * @return 解析结果
     */
    protected List<ExcelDataVO> parseExcel(Workbook workbook) {
        List<ExcelDataVO> resultDataList = new ArrayList<ExcelDataVO>();
        // 解析sheet
        for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
            Sheet sheet = workbook.getSheetAt(sheetNum);

            // 校验sheet是否合法
            if (sheet == null) {
                continue;
            }

            // 获取第一行数据
            int firstRowNum = sheet.getFirstRowNum();
            Row firstRow = sheet.getRow(firstRowNum);
            if (null == firstRow) {
                logger.warning("解析Excel失败，在第一行没有读取到任何数据！");
            }

            // 解析每一行的数据，构造数据对象
            int rowStart = firstRowNum + 1;
            int rowEnd = sheet.getPhysicalNumberOfRows();
            for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
                Row row = sheet.getRow(rowNum);

                if (null == row) {
                    continue;
                }

                ExcelDataVO resultData = convertRowToData(row);
                if (null == resultData) {
                    logger.warning("第 " + row.getRowNum() + "行数据不合法，已忽略！");
                    continue;
                }
                resultDataList.add(resultData);
            }
        }

        return resultDataList;
    }

    /**
     * 将单元格内容转换为字符串
     * @param cell
     * @return
     */
    protected String convertCellValueToString(Cell cell) {
        if (cell == null) {
            return null;
        }
        String returnValue = null;
        switch (cell.getCellTypeEnum()) {
            case NUMERIC:   //数字
                Double doubleValue = cell.getNumericCellValue();

                //格式化科学计数法，取一位整数
                DecimalFormat df = new DecimalFormat("0.00");
                returnValue = df.format(doubleValue);
                break;
            case STRING:    //字符串
                returnValue = cell.getStringCellValue();
                break;
            case ERROR:     // 故障
                break;
            default:
                break;
        }
        return returnValue;
    }

    /**
     * 提取每一行中需要的数据，构造成为一个结果数据对象
     *
     * 当该行中有单元格的数据为空或不合法时，忽略该行的数据
     *
     * @param row 行数据
     * @return 解析后的行数据对象，行数据错误时返回null
     */
    protected  ExcelDataVO convertRowToData(Row row) {
        ExcelDataVO resultData = new ExcelDataVO();

        Cell cell;
        int cellNum = 0;
        //获取区站号
        cell = row.getCell(cellNum++);
        String idStr = convertCellValueToString(cell);
        String[] id = idStr.split("\\.");
        String idNum = id[0];
        if (null == idNum || "".equals(idNum)) {
            resultData.setId(null);
        } else {
            resultData.setId(Integer.parseInt(idNum));
        }

        //获取年
        cell = row.getCell(cellNum++);
        String yeStr = convertCellValueToString(cell);
        String[] year = yeStr.split("\\.");
        String yearNum = year[0];
        if (null == yearNum || "".equals(yearNum)) {
            resultData.setYear(null);
        } else {
            if (yearNum.length() < sizeLength) {
                int remain = sizeLength - yearNum.length();
                for (int i = 0; i < remain; i++) {
                    yearNum = yearNum + " ";
                }
            }
            resultData.setYear(yearNum);
        }
        //获取月
        cell = row.getCell(cellNum++);
        String moStr = convertCellValueToString(cell);
        String[] month = moStr.split("\\.");
        String monthNum = month[0];
        if (null == monthNum || "".equals(monthNum)) {
            resultData.setMonth(null);
        } else {
            if (monthNum.length() < sizeLength) {
                int remain = sizeLength - monthNum.length();
                for (int i = 0; i < remain; i++) {
                    monthNum = monthNum + " ";
                }
                resultData.setMonth(monthNum);
            }
        }
        //获取日
        cell = row.getCell(cellNum++);
        String daStr = convertCellValueToString(cell);
        String[] day = daStr.split("\\.");
        String dayNum = day[0];
        if (null == dayNum || "".equals(dayNum)) {
            resultData.setDay(null);
        } else {
            if (dayNum.length() < sizeLength) {
                int remain = sizeLength - dayNum.length();
                for (int i = 0; i < remain; i++) {
                    dayNum = dayNum + " ";
                }
            }
            resultData.setDay(dayNum);
        }
        //获取太阳辐射
        cell = row.getCell(cellNum++);
        String srStr = convertCellValueToString(cell);
        if (null == srStr || "".equals(srStr)) {
            resultData.setSr(null);
        } else {
            if(srStr.length()<sizeLength){
                int remain = sizeLength-srStr.length();
                for(int i = 0; i<remain;i++){
                    srStr = srStr+" ";
                }
            }
            //String doubleMintemp=String.format("%.1f",minTempDouble);
            resultData.setSr(srStr);
        }
        return resultData;
    }
}
