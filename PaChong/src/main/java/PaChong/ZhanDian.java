package PaChong;

import java.io.*;
import java.net.URL;
import java.util.List;

/**
 * @Auther:HuangZhiwen
 * @Date: 2020/11/17 - 11 - 9:37
 * @Description: PaChong
 * @version: 1.0
 */
public class ZhanDian {

    ZhanDian(){

    }

    public void run(String type){
        String zhanFileName = "F:\\javaProject\\WorkSpace\\weather\\weatherdata\\zhandian.xls";
        ExcelReader excelReader1 = new ExcelReader();
        List<ExcelDataVO> readResult1 = excelReader1.readExcel(zhanFileName);
        try {
            readUrl("江西","南昌",2019,57598,type);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
//    public void start(){
//        t = new Thread(this,threadName);
//        t.start();
//    }
    public void readUrl(String province,String city,int year,int id,String type) throws Exception {
        String fileName1 = "F:\\javaProject\\WorkSpace\\weather\\weatherdata\\jiangxi\\"+city+year+"_tem_"+id+".xlsx";
        String fileName2 = "F:\\javaProject\\WorkSpace\\weather\\weatherdata\\jiangxi\\"+city+year+"_20_20"+id+".xlsx";
        String fileName3 = "F:\\javaProject\\WorkSpace\\weather\\weatherdata\\jiangxi\\"+city+year+"_rhu"+id+".xlsx";
        String fileName4 = "F:\\javaProject\\WorkSpace\\weather\\weatherdata\\jiangxi\\"+city+year+"_sr"+id+".xlsx";
        File fp1 = new File(fileName1);
        File fp2 = new File(fileName2);
        File fp3 = new File(fileName3);
        File fp4 = new File(fileName4);
        OutputStream os1 = new FileOutputStream(fp1);
        OutputStream os2 = new FileOutputStream(fp2);
        OutputStream os3 = new FileOutputStream(fp3);
        OutputStream os4 = new FileOutputStream(fp4);
        if(type=="max_temp-min_temp"){
            URL url1 = new URL("http://data.sheshiyuanyi.com/WeatherData/datafile/" +id+"_"+"max_tem-min_tem_" +year
                    +"_0.xlsx");
            int ls =0;
            InputStream input1 = url1.openStream();
            byte b1[] = new byte[204800];
            while ((ls=input1.read(b1,0,204800))>-1){
                //System.out.println(in);
                os1.write(b1,0,ls);
                os1.flush();
            }
            os1.flush();
            os1.close();
        }
        else if(type=="_20_20"){
            URL url2 = new URL("http://data.sheshiyuanyi.com/WeatherData/datafile/" +id+"_pre"+"_20_20_" +year
                    +"_0.xlsx");
            int ls =0;
            InputStream input2 = url2.openStream();
            byte b2[] = new byte[204800];
            while ((ls=input2.read(b2,0,204800))>-1){
                //System.out.println(in);
                os2.write(b2,0,ls);
                os2.flush();
            }
            os2.flush();
            os2.close();

        }
        else if(type=="avg_rhu"){
            URL url3 = new URL("http://data.sheshiyuanyi.com/WeatherData/datafile/" +id+"_"+"avg_rhu_" +year
                    +"_0.xlsx");
            int ls = 0;
            InputStream input3 = url3.openStream();
            byte b3[] = new byte[204800];
            while ((ls=input3.read(b3,0,204800))>-1){
                //System.out.println(in);
                os3.write(b3,0,ls);
                os3.flush();
            }
            os3.flush();
            os3.close();
        }
        else if(type=="sr"){
            URL url4 = new URL("http://data.sheshiyuanyi.com/WeatherData/datafile/" +id+"_"+"sr_" +year
                    +"_0.xlsx");
            int ls = 0;
            InputStream input4 = url4.openStream();
            byte b4[] = new byte[204800];
            while ((ls=input4.read(b4,0,204800))>-1){
                //System.out.println(in);
                os4.write(b4,0,ls);
                os4.flush();
            }
            os4.flush();
            os4.close();
        }


    }

}
