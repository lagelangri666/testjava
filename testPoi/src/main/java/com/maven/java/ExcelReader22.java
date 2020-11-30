package com.maven.java;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Provider;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @Auther:HuangZhiwen
 * @Date: 2020/11/15 - 11 - 16:36
 * @Description: com.maven.java
 * @version: 1.0
 */
public class ExcelReader22 {
    protected static Logger logger = Logger.getLogger(ExcelReader.class.getName()); // 日志打印类
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
            System.out.println(workbook);

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
        List<ExcelDataVO> resultDataList = new ArrayList<>();
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
        if(cell==null){
            return null;
        }
        String returnValue = null;
        switch (cell.getCellTypeEnum()) {
            case NUMERIC:   //数字
                Double doubleValue = cell.getNumericCellValue();

                // 格式化科学计数法，取一位整数
                DecimalFormat df = new DecimalFormat("0");
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
    protected ExcelDataVO convertRowToData(Row row){
        ExcelDataVO resultData = new ExcelDataVO();

        Cell cell;
        int cellNum = 0;
        // 获取省
        cell = row.getCell(cellNum++);
        String province = convertCellValueToString(cell);
        resultData.setProvince(province);
        // 获取城市
        cell = row.getCell(cellNum++);
        String city = convertCellValueToString(cell);
        resultData.setCity(city);
        //获取区站号
        cell = row.getCell(cellNum++);
        String idStr = convertCellValueToString(cell);
        if (null == idStr || "".equals(idStr)) {
            resultData.setId(null);
        } else {
            resultData.setId(Integer.parseInt(idStr));
        }
        //获取纬度
        cell = row.getCell(cellNum++);
        String wStr = convertCellValueToString(cell);
        if (null == wStr || "".equals(wStr)) {
            resultData.setW(null);
        } else {
            resultData.setW(Integer.parseInt(wStr));
        }
        //获取经度
        cell = row.getCell(cellNum++);
        String hStr = convertCellValueToString(cell);
        if (null == hStr || "".equals(hStr)) {
            resultData.setH(null);
        } else {
            resultData.setH(Integer.parseInt(hStr));
        }
        //获取海拔
        cell = row.getCell(cellNum++);
        String highStr = convertCellValueToString(cell);
        if (null == highStr || "".equals(highStr)) {
            resultData.setH(null);
        } else {
            resultData.setHigh(Integer.parseInt(highStr));
        }
        //获取年
        cell = row.getCell(cellNum++);
        String yearStr = convertCellValueToString(cell);
        if (null == yearStr || "".equals(yearStr)) {
            resultData.setYear(null);
        } else {
            if(yearStr.length()<sizeLength){
                int remain = sizeLength-yearStr.length();
                for(int i = 0; i<remain;i++){
                    yearStr = yearStr+" ";
                }
            }
            resultData.setYear(yearStr);
        }
        //获取月
        cell = row.getCell(cellNum++);
        String monthStr = convertCellValueToString(cell);
        if (null == monthStr || "".equals(monthStr)) {
            resultData.setMonth(null);
        } else {
            if(monthStr.length()<sizeLength){
                int remain = sizeLength-monthStr.length();
                for(int i = 0; i<remain;i++){
                    monthStr = monthStr+" ";
                }
            }
            resultData.setMonth(monthStr);
        }
        //获取日
        cell = row.getCell(cellNum++);
        String dayStr = convertCellValueToString(cell);
        if (null == dayStr || "".equals(dayStr)) {
            resultData.setDay(null);
        } else {
            if(dayStr.length()<sizeLength){
                int remain = sizeLength-dayStr.length();
                for(int i = 0; i<remain;i++){
                    dayStr = dayStr+" ";
                }
            }
            resultData.setDay(dayStr);
        }

        //获取20-8气温
        cell = row.getCell(cellNum++);
        String firstRainStr = convertCellValueToString(cell);
        if (null == firstRainStr || "".equals(firstRainStr)) {
            resultData.setFirstRain(0.00);
        } else {
            Double firstRain = Double.valueOf(firstRainStr);
            double rain = firstRain / 10;
            //String doubleMintemp=String.format("%.1f",minTempDouble);
            resultData.setFirstRain(rain);
        }
        //获取20-8气温
        cell = row.getCell(cellNum++);
        String midRainStr = convertCellValueToString(cell);
        if (null == midRainStr || "".equals(midRainStr)) {
            resultData.setMidRain(0.00);
        } else {
            Double midRain = Double.valueOf(midRainStr);
            double rain = midRain / 10;
            //String doubleMintemp=String.format("%.1f",minTempDouble);
            resultData.setMidRain(rain);
        }
        //获取最终气温
        cell = row.getCell(cellNum++);
        String rainStr = convertCellValueToString(cell);
        if (null == rainStr || "".equals(rainStr)) {
            resultData.setAtemp(null);
        } else {
            Double rain = Double.valueOf(rainStr);
            if(rain==32700.0){
                rain =0.00;
            }
            double rainDouble = rain / 10;
            DecimalFormat atempD= new DecimalFormat("0.00");//格式化小数
            String rainStrr = atempD.format(rainDouble);//返回的是String类型
            //String doubleMintemp=String.format("%.1f",minTempDouble);
            if(rainStrr.length()<sizeLength){
                int remain = sizeLength-rainStrr.length();
                for(int i = 0; i<remain;i++){
                    rainStrr = rainStrr+" ";
                }
            }
            resultData.setRainData(rainStrr);
        }
        return resultData;
    }


}
