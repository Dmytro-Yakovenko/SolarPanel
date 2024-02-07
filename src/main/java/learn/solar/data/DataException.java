package learn.solar.data;
import java.lang.Exception;
public class DataException extends Exception {
    public DataException(String message, Throwable rootCause){
        super(message, rootCause);
    }

}
