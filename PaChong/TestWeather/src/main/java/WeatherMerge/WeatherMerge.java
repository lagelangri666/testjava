package WeatherMerge;

import java.awt.geom.Area;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther:HuangZhiwen
 * @Date: 2020/11/18 - 11 - 12:59
 * @Description: WeatherMerge
 * @version: 1.0
 */
public class WeatherMerge {
    public static List<String> getAllFile(String directoryPath, boolean isAddDirectory) {
        List<String> list = new ArrayList<String>();
        File baseFile = new File(directoryPath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return list;
        }
        File[] files = baseFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if (isAddDirectory) {
                    list.add(file.getAbsolutePath());
                }
                list.addAll(getAllFile(file.getAbsolutePath(), isAddDirectory));//递归
            } else {
                list.add(file.getAbsolutePath());
            }
        }
        return list;
    }
    public static Integer GetMinSize(int a,int b, int c,int d){
        int min =0;
        if((a<=b)&&(a<=c)&&(a<=d)){
             min = a;
        }
        else if((b<=a)&&(b<=c)&&(b<=d)){
            min = b;
        }
        else if((c<=a)&&(c<=b)&&(c<=d)){
            min = c;
        }
        else if((d<=a)&&(d<=b)&&(d<=c)){
            min = d;
        }

        return min;
    }

    public static void main(String[] args) throws Exception {
        String dataFileName = "F:\\javaProject\\meteorologyDataDownload\\云南";
        //String zhanDianName = dataFileName+
        boolean isAddDirectory = true;
        List<String> dataPath = getAllFile(dataFileName, isAddDirectory);
        String diQU = dataPath.get(0);
        List<String> diQuList = new ArrayList<>();
        int diQu = 1;
        for (int i = 1; i < dataPath.size(); i++) {
            if (dataPath.get(i).contains(diQU)) {
                if (i == 1) {
                    diQuList.add(diQU);
                }
            } else {
                diQU = dataPath.get(i);
                diQuList.add(diQU);
            }
            //System.out.println(dataPath.get(i));
        }
        for (int i = 0; i < diQuList.size(); i++) {
            AllYearWeather allYearWeather = new AllYearWeather();
            List<List<String>> weather = new ArrayList<>();
            weather = allYearWeather.yearWeather(dataPath, diQuList.get(i));
            for (int j = 0; j < weather.size(); j++) {
                List<ExcelDataVO> readResult1 = new ArrayList<ExcelDataVO>();
                List<ExcelDataVO> readResult2 = new ArrayList<ExcelDataVO>();
                List<ExcelDataVO> readResult3 = new ArrayList<ExcelDataVO>();
                List<ExcelDataVO> readResult4 = new ArrayList<ExcelDataVO>();
                Integer finished=0;
                for (int k = 0; k < weather.get(j).size(); k++) {
                    //System.out.println(weather.get(j));
                    if (weather.get(j).get(k).contains("avg_tem-max_tem-min_tem")) {
                        String tempFileName = weather.get(j).get(k);
                        ExcelReader excelReader1 = new ExcelReader();
                        readResult1 = excelReader1.readExcel(tempFileName);
                    }
                    else if (weather.get(j).get(k).contains("_pre_20_20")) {
                        String rain = weather.get(j).get(k);
                        ExcelReader2 excelReader2 = new ExcelReader2();
                        readResult2 = excelReader2.readExcel(rain);
                    }
                    else if (weather.get(j).get(k).contains("_min_evp-max_evp_")) {
                        String vp = weather.get(j).get(k);
                        ExcelReader3 excelReader3 = new ExcelReader3();
                        readResult3 = excelReader3.readExcel(vp);

                    }
                    else if (weather.get(j).get(k).contains("_sr_")) {
                        String sr = weather.get(j).get(k);
                        ExcelReader4 excelReader4 = new ExcelReader4();
                        readResult4 = excelReader4.readExcel(sr);
                    }
                    if((readResult1.size() != 0) && (readResult2.size() != 0) && (readResult3.size() != 0) &&
                            (readResult4.size() != 0)&&finished ==0){
                        int num = 0;
                        String[] theSplit = weather.get(j).get(0).split("\\\\");
                        String fileNamePath = theSplit[0]+"\\"+theSplit[1]+"\\"+theSplit[2]+"\\"+theSplit[3]+
                                "\\"+theSplit[4]+"\\"+theSplit[6];
                        Integer size = GetMinSize(readResult1.size(),readResult2.size(),readResult3.size(),readResult4.size());
                        System.out.println(size);
                        for (int leng = 0; leng < size; leng++) {
                            String lineWeather = null;

                            String evap = "-99.00    ";
                            lineWeather = readResult2.get(leng).getDay() +
                                    readResult2.get(leng).getMonth() + readResult2.get(leng).getYear()
                                    + readResult1.get(leng).getMaxtemp() + readResult1.get(leng).getMintemp() +
                                    readResult2.get(leng).getRainData() + evap + readResult4.get(leng).getSr() + readResult3.get(leng).getVp();
                            //String[] splitStr = weather.get(j).get(0).split()

                            File writeName = new File(fileNamePath + ".prn");
                            if (!writeName.exists()) {
                                writeName.createNewFile();
                                num = 1;
                                FileWriter writer = new FileWriter(writeName);
                                BufferedWriter out = new BufferedWriter(writer);
                                String title = "    Dcum      Day       Month     Year      T.Max     T.Min     Rain      " +
                                        "Evap      Radn      VP        ";
                                out.write(title + "\n");
                                out.write("    " + num + "         " + lineWeather + "\n");
                                out.flush();
                            } else {
                                FileWriter writer = new FileWriter(writeName, true);
                                BufferedWriter out = new BufferedWriter(writer);
                                num++;
                                String numStr = num + " ";
                                while (numStr.length() < 10) {
                                    numStr = numStr + " ";
                                }
                                out.write("    " + numStr + lineWeather + "\n");
                                out.flush();

                            }

                        }
                        finished = 1;
                    }

                }

            }
            System.out.println(diQuList.get(i));

            //System.out.println(dataPath);

        }

    }
}
