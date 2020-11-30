package com.maven.java;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther:HuangZhiwen
 * @Date: 2020/11/13 - 11 - 10:24
 * @Description: com.maven.java
 * @version: 1.0
 */
public class ExcelReader1 extends ExcelReader {
    private int sizeLength=10;
      @Override
      public  ExcelDataVO convertRowToData(Row row) {
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
              resultData.setYear(yearStr);
          }
          //获取月
          cell = row.getCell(cellNum++);
          String monthStr = convertCellValueToString(cell);
          if (null == monthStr || "".equals(monthStr)) {
              resultData.setMonth(null);
          } else {
              resultData.setMonth(monthStr);
          }
          //获取日
          cell = row.getCell(cellNum++);
          String dayStr = convertCellValueToString(cell);
          if (null == dayStr || "".equals(dayStr)) {
              resultData.setDay(null);
          } else {
              resultData.setDay(dayStr);
          }

          //获取平均气温
          cell = row.getCell(cellNum++);
          String atempStr = convertCellValueToString(cell);
          if (null == atempStr || "".equals(atempStr)) {
              resultData.setAtemp(null);
          } else {
              Integer atemp = Integer.valueOf(atempStr);
              DecimalFormat df = new DecimalFormat("0.00");//格式化小数
              String atempDouble = df.format((float) atemp / 100 * 10);//返回的是String类型
              Double getAtemp = Double.parseDouble(atempDouble);
              //System.out.println(atempDouble);
              //String doubleAtemp=String.format("%.1f",atempDouble);
              resultData.setAtemp(getAtemp);
          }
          //获取最高气温
          cell = row.getCell(cellNum++);
          String maxTempStr = convertCellValueToString(cell);
          if (null == maxTempStr || "".equals(maxTempStr)) {
              resultData.setMaxtemp(null);
          } else {
              Double maxTemp = Double.valueOf(maxTempStr);
              Double maxTempDouble = maxTemp / 10;
              DecimalFormat atempD= new DecimalFormat("0.00");//格式化小数
              String maxTempStrr = atempD.format(maxTempDouble);//返回的是String类型
              //String doubleMintemp=String.format("%.1f",minTempDouble);
              if(maxTempStrr.length()<sizeLength){
                  int remain = sizeLength-maxTempStrr.length();
                  for(int i = 0; i<remain;i++){
                      maxTempStrr = maxTempStrr+" ";
                  }
              }
              //String doubleMaxtemp=String.valueOf(maxTempDouble);
              resultData.setMaxtemp(maxTempStrr);
          }
          //获取最低气温
          cell = row.getCell(cellNum++);
          String minTempStr = convertCellValueToString(cell);
          if (null == minTempStr || "".equals(minTempStr)) {
              resultData.setMintemp(null);
          } else {
              Double minTemp = Double.valueOf(minTempStr);
              double minTempDouble = minTemp / 10;
              DecimalFormat atempD= new DecimalFormat("0.00");//格式化小数
              String minTempStrr = atempD.format(minTempDouble);//返回的是String类型
              //String doubleMintemp=String.format("%.1f",minTempDouble);
              if(minTempStrr.length()<sizeLength){
                  int remain = sizeLength-minTempStrr.length();
                  for(int i = 0; i<remain;i++){
                      minTempStrr = minTempStrr+" ";
                  }
              }
              //String doubleMaxtemp=String.valueOf(maxTempDouble);
              //String doubleMintemp=String.format("%.1f",minTempDouble);
              resultData.setMintemp(minTempStrr);
          }
          return resultData;
      }

}

