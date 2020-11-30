package WeatherMerge;

/**
 * @Auther:HuangZhiwen
 * @Date: 2020/11/19 - 11 - 8:35
 * @Description: WeatherMerge
 * @version: 1.0
 */
public class ExcelDataVO {
    private String province;
    private String city;
    private Integer id;
    private Integer w;
    private Integer h;
    private Integer high;
    private String year;
    private String month;
    private String day;
    private String atemp;
    private String maxtemp;
    private String mintemp;
    private double firstRain;
    private double midRain;
    private String rainData;
    private String radnData;
    private String vp;
    private String sr;

    public ExcelDataVO() {
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getW() {
        return this.w;
    }

    public void setW(Integer w) {
        this.w = w;
    }

    public Integer getH() {
        return this.h;
    }

    public void setH(Integer h) {
        this.h = h;
    }

    public Integer getHigh() {
        return this.high;
    }

    public void setHigh(Integer high) {
        this.high = high;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) { this.year = year; }

    public String getMonth() {
        return this.month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return this.day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getAtemp() {
        return this.atemp;
    }

    public void setAtemp(String atemp) { this.atemp = atemp; }

    public String getMaxtemp() {
        return this.maxtemp;
    }

    public void setMaxtemp(String maxtemp) {
        this.maxtemp = maxtemp;
    }

    public String getMintemp() { return this.mintemp; }

    public void setMintemp(String mintemp) { this.mintemp = mintemp; }

    public double getFirstRain() { return firstRain; }

    public void setFirstRain(double firstRain) { this.firstRain = firstRain; }

    public double getMidRain() { return midRain; }

    public void setMidRain(double midRain) { this.midRain = midRain; }

    public String getRainData() { return rainData; }

    public void setRainData(String rainData) { this.rainData = rainData; }

    public String getRadnData() {
        return radnData;
    }

    public void setRadnData(String radnData) {
        this.radnData = radnData;
    }

    public void setVp(String vp) {
        this.vp = vp;
    }

    public String getVp() {
        return vp;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    public String getSr() {
        return sr;
    }
}
