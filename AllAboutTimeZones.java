package Utilities;

import java.time.*;
/**
 * @author: Jessica Thomas
 * C 195 Appointment Scheduling project
 * */


/**
 * Class AllAboutTimeZones.java converts a LocalDateTime object to Eastern Standard Time
 * */
public class AllAboutTimeZones {

    /**
     * Method converts a LocalDateTime object to Eastern Standard Time
     * @param ldt is the LocalDateTime object to convert
     * @return the converted LocalDateTime
     * */
    public static LocalDateTime getTimeInESTTimeZone(LocalDateTime ldt){

        ZoneId myZoneID = ZoneId.systemDefault();
        ZonedDateTime zdt = ZonedDateTime.of(ldt, myZoneID);

        ZoneId est = ZoneId.of("America/New_York");
        ZonedDateTime estZDT = zdt.withZoneSameInstant(est);
        LocalDateTime newLDT = estZDT.toLocalDateTime();

        return newLDT;
    }

}
