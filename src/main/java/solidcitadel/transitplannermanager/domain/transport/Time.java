package solidcitadel.transitplannermanager.domain.transport;

import lombok.Data;

@Data
public class Time {
    private Integer hour;
    private Integer minute;

    public Time(Integer hour, Integer minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public Time(Integer minute) {
        this.hour = minute/60;
        this.minute = minute - hour;
    }

    public Time diff(Time time) {
        return new Time(hour - time.getHour(), minute - time.getMinute());
    }

    public Integer toTime(){
        return hour*60 + minute;
    }

    public String toString(){

        String stringHour = hour.toString();
//        if (stringHour.length() == 1)
//            stringHour = '0' + stringHour;

        String stringMinute = minute.toString();
        if (stringMinute.length() == 1) {
            stringMinute = '0' + stringMinute;
        }

        return stringHour + ':' + stringMinute;
    }
}
