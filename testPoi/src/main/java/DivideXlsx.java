import com.maven.java.*;
import org.apache.poi.util.SystemOutLogger;
import org.etsi.uri.x01903.v13.SignaturePolicyIdentifierType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLOutput;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther:HuangZhiwen
 * @Date: 2020/11/12 - 11 - 10:33
 * @Description: PACKAGE_NAME
 * @version: 1.0
 */
public class DivideXlsx {

    public static void main(String[] args) throws IOException {
        DivideXlsx obj = new DivideXlsx();

        String tempFileName = "F:\\javaProject\\WorkSpace\\weather\\weatherdata\\jx\\temp.xlsx";
        String rainFileName = "F:\\javaProject\\WorkSpace\\weather\\weatherdata\\jx\\rain.xlsx";
        String radnFileName = "F:\\javaProject\\WorkSpace\\weather\\weatherdata\\jx\\radn.xlsx";
        ExcelReader1 excelReader1 = new ExcelReader1();
        ExcelReader22 excelReader2 = new ExcelReader22();
        ExcelReader33 excelReader3 = new ExcelReader33();
        List<ExcelDataVO> readResult1 = excelReader1.readExcel(tempFileName);
        List<ExcelDataVO> readResult2 = excelReader2.readExcel(rainFileName);
        List<ExcelDataVO> readResult3 = excelReader3.readExcel(radnFileName);

        List<String> cityArea = new ArrayList<>();
        for(int i=0;i<readResult1.size();i++){
            String city = readResult1.get(i).getCity();
            if(cityArea.size()==0){
                cityArea.add(city);

            }
            String temCity =cityArea.get(cityArea.size()-1);
            if(temCity!=city){
                cityArea.add(city);
            }
        }
        System.out.println(cityArea);
        Integer num =0;
        Integer count = 0;
        for(int i =0;i<readResult1.size();i++){
            String lineWeather = null;

            String evap = "-99.00    ";
            lineWeather = readResult2.get(i).getDay()+
                    readResult2.get(i).getMonth()+readResult2.get(i).getYear()
                    +readResult1.get(i).getMaxtemp()+readResult1.get(i).getMintemp()+readResult2.get(i).getRainData()+evap+readResult3.get(i).getRadnData();
            if(readResult1.get(i).getCity()==cityArea.get(count)){
                File writeName =  new File(cityArea.get(count)+count+".prn");
                if(!writeName.exists()){
                    writeName.createNewFile();
                    num =1;
                    FileWriter writer = new FileWriter(writeName);
                    BufferedWriter out = new BufferedWriter(writer);
                    String title = "    Dcum      Day       Month     Year      T.Max     T.Min     Rain      " +
                            "Evap      Radn      VP        ";
                    out.write(title+"\n");
                    out.write( "    "+num+"         "+lineWeather+"\n");
                    out.flush();
                }

                else{
                    FileWriter writer = new FileWriter(writeName,true);
                    BufferedWriter out = new BufferedWriter(writer);
                    num++;
                    String numStr = num+" ";
                    while (numStr.length()<10){
                        numStr = numStr+" ";
                    }
                    out.write("    "+numStr+lineWeather+"\n");
                    out.flush();

                }

            }
            else{
                count = count+1;
                File writeName =  new File(cityArea.get(count)+count+".prn");
                if(!writeName.exists()){
                    writeName.createNewFile();
                    num=1;
                    FileWriter writer = new FileWriter(writeName);
                    BufferedWriter out = new BufferedWriter(writer);
                    String title = "    Dcum      Day       Month     Year      T.Max     T.Min     Rain      " +
                            "Evap      Radn      VP        ";
                    out.write(title+"\n");
                    out.write("    "+num+"         "+lineWeather+"\n");
                    out.flush();
                }

                else{

                    FileWriter writer = new FileWriter(writeName,true);
                    BufferedWriter out = new BufferedWriter(writer);
                    num++;
                    String numStr = num+" ";
                    while (numStr.length()<10){
                        numStr = numStr+" ";
                    }
                    out.write("    "+numStr+lineWeather+"\n");
                    out.flush();

                }

            }

        }
    }


}
