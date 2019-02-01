package pesonalFinanceApp;

import pl.zankowski.iextrading4j.api.stocks.Chart;

import javax.swing.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import static java.lang.Math.toIntExact;

public class Main {
    public static void main(String[] args) {

        GUI frame = new GUI();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(3);

    }
}