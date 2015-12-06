package utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Dennis
 */
public class LoggerHandler {

    public static Logger log = Logger.getLogger("Logfile");
    private static FileHandler fh;

    public static void main(String[] args) {
        try {
            fh = new FileHandler("Logfile.log", true); // APPENDS TO EXISTING FILE
            log.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            
            log.info("First input");
        } catch (IOException ex) {
            Logger.getLogger(LoggerHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(LoggerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
