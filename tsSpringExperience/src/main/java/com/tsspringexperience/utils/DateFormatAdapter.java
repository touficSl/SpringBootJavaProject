package com.tsspringexperience.utils;

import java.text.ParseException;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateFormatAdapter extends XmlAdapter<String, Date> {
	
    @Override
    public String marshal(Date date) {
        return date == null ? null : Utils.returnDateFormatByDate(date, Constants.DateFormat);
    }

    @Override
    public Date unmarshal(String date) throws ParseException {
        return date == null ? null : Utils.returnDateByFormat(date, Constants.DateFormat);
    }

}