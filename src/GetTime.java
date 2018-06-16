import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This is a get time class, where I get difference between today date and championship date
 * @author ZipFileDesu
 */

public class GetTime {

    /**
     * This variable is a format date like 09:00:00 19.06.2018
     */
    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy.hh.mm.ss");

    /**
     * This variable is today date
     */
    public Date date = null;

    /**
     * This variable is championsip date
     */
    public Date date_1 = null;

    String chmp_date = "19.06.2018.9.00.00";

    /**
     * This is method which return difference between today date and championship date
     * @return difference between today date and championship date
     * @throws ParseException if something goes wrong
     */
    public long GetTime() throws ParseException {
        return GetChmpDate() - GetNewDate();
    }

    /**
     * This is method which returns championship date
     * @return championship date
     * @throws ParseException if something goes wrong
     */
    public long GetChmpDate() throws ParseException {
        date_1 = format.parse(chmp_date);
        return date_1.getTime();
    }

    /**
     * This is method which returns today date
     * @return today date
     */
    public long GetNewDate(){
        date = new Date();
        return date.getTime();
    }
}
