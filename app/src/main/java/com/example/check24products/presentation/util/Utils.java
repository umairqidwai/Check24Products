package com.example.check24products.presentation.util;

import com.example.check24products.data.model.Price;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String getFormattedDate(long seconds){
        long timeInMilliSeconds = seconds * 1000;

        String dateString = new SimpleDateFormat("MM/dd/yyyy").format(new Date(timeInMilliSeconds));
        return dateString;
    }

    public static String getPriceWithCurrency(Price price){
        return price.getValue() +" "+price.getCurrency();
    }


}
