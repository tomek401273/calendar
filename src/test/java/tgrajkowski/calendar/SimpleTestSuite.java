package tgrajkowski.calendar;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleTestSuite {
    @Test
    public void test1() {
        Long l = Long.valueOf("1550696400000");
        Date date = new Date(l);
        System.out.println(date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = simpleDateFormat.format(date);
        System.out.println(s);
    }
}
