package com.xlsx.java;



import java.io.*;
import java.util.List;

/**
 * @Auther:HuangZhiwen
 * @Date: 2020/11/10 - 11 - 15:55
 * @Description: com.xlsx.java
 * @version: 1.0
 */

public class DivideXlsx {

    public static void main(String[] args) {
        DivideXlsx obj = new DivideXlsx();

        String excelFileName = "F:\\javaProject\\WorkSpace\\weather\\weatherdata\\jx\\temp.xlsx";
//        ExcelReader readTest = new ExcelReader();
        List<ExcelDataVO> readResult = ExcelReader.readExcel(excelFileName);

        System.out.println(readResult);


    }



}


