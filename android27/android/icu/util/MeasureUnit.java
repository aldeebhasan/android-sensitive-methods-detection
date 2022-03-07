package android.icu.util;

import java.io.*;
import java.util.*;

public class MeasureUnit implements Serializable
{
    public static final MeasureUnit ACRE;
    public static final MeasureUnit ACRE_FOOT;
    public static final MeasureUnit AMPERE;
    public static final MeasureUnit ARC_MINUTE;
    public static final MeasureUnit ARC_SECOND;
    public static final MeasureUnit ASTRONOMICAL_UNIT;
    public static final MeasureUnit BIT;
    public static final MeasureUnit BUSHEL;
    public static final MeasureUnit BYTE;
    public static final MeasureUnit CALORIE;
    public static final MeasureUnit CARAT;
    public static final MeasureUnit CELSIUS;
    public static final MeasureUnit CENTILITER;
    public static final MeasureUnit CENTIMETER;
    public static final MeasureUnit CENTURY;
    public static final MeasureUnit CUBIC_CENTIMETER;
    public static final MeasureUnit CUBIC_FOOT;
    public static final MeasureUnit CUBIC_INCH;
    public static final MeasureUnit CUBIC_KILOMETER;
    public static final MeasureUnit CUBIC_METER;
    public static final MeasureUnit CUBIC_MILE;
    public static final MeasureUnit CUBIC_YARD;
    public static final MeasureUnit CUP;
    public static final MeasureUnit CUP_METRIC;
    public static final TimeUnit DAY;
    public static final MeasureUnit DECILITER;
    public static final MeasureUnit DECIMETER;
    public static final MeasureUnit DEGREE;
    public static final MeasureUnit FAHRENHEIT;
    public static final MeasureUnit FATHOM;
    public static final MeasureUnit FLUID_OUNCE;
    public static final MeasureUnit FOODCALORIE;
    public static final MeasureUnit FOOT;
    public static final MeasureUnit FURLONG;
    public static final MeasureUnit GALLON;
    public static final MeasureUnit GENERIC_TEMPERATURE;
    public static final MeasureUnit GIGABIT;
    public static final MeasureUnit GIGABYTE;
    public static final MeasureUnit GIGAHERTZ;
    public static final MeasureUnit GIGAWATT;
    public static final MeasureUnit GRAM;
    public static final MeasureUnit G_FORCE;
    public static final MeasureUnit HECTARE;
    public static final MeasureUnit HECTOLITER;
    public static final MeasureUnit HECTOPASCAL;
    public static final MeasureUnit HERTZ;
    public static final MeasureUnit HORSEPOWER;
    public static final TimeUnit HOUR;
    public static final MeasureUnit INCH;
    public static final MeasureUnit INCH_HG;
    public static final MeasureUnit JOULE;
    public static final MeasureUnit KARAT;
    public static final MeasureUnit KELVIN;
    public static final MeasureUnit KILOBIT;
    public static final MeasureUnit KILOBYTE;
    public static final MeasureUnit KILOCALORIE;
    public static final MeasureUnit KILOGRAM;
    public static final MeasureUnit KILOHERTZ;
    public static final MeasureUnit KILOJOULE;
    public static final MeasureUnit KILOMETER;
    public static final MeasureUnit KILOMETER_PER_HOUR;
    public static final MeasureUnit KILOWATT;
    public static final MeasureUnit KILOWATT_HOUR;
    public static final MeasureUnit KNOT;
    public static final MeasureUnit LIGHT_YEAR;
    public static final MeasureUnit LITER;
    public static final MeasureUnit LITER_PER_100KILOMETERS;
    public static final MeasureUnit LITER_PER_KILOMETER;
    public static final MeasureUnit LUX;
    public static final MeasureUnit MEGABIT;
    public static final MeasureUnit MEGABYTE;
    public static final MeasureUnit MEGAHERTZ;
    public static final MeasureUnit MEGALITER;
    public static final MeasureUnit MEGAWATT;
    public static final MeasureUnit METER;
    public static final MeasureUnit METER_PER_SECOND;
    public static final MeasureUnit METER_PER_SECOND_SQUARED;
    public static final MeasureUnit METRIC_TON;
    public static final MeasureUnit MICROGRAM;
    public static final MeasureUnit MICROMETER;
    public static final MeasureUnit MICROSECOND;
    public static final MeasureUnit MILE;
    public static final MeasureUnit MILE_PER_GALLON;
    public static final MeasureUnit MILE_PER_HOUR;
    public static final MeasureUnit MILE_SCANDINAVIAN;
    public static final MeasureUnit MILLIAMPERE;
    public static final MeasureUnit MILLIBAR;
    public static final MeasureUnit MILLIGRAM;
    public static final MeasureUnit MILLILITER;
    public static final MeasureUnit MILLIMETER;
    public static final MeasureUnit MILLIMETER_OF_MERCURY;
    public static final MeasureUnit MILLISECOND;
    public static final MeasureUnit MILLIWATT;
    public static final TimeUnit MINUTE;
    public static final TimeUnit MONTH;
    public static final MeasureUnit NANOMETER;
    public static final MeasureUnit NANOSECOND;
    public static final MeasureUnit NAUTICAL_MILE;
    public static final MeasureUnit OHM;
    public static final MeasureUnit OUNCE;
    public static final MeasureUnit OUNCE_TROY;
    public static final MeasureUnit PARSEC;
    public static final MeasureUnit PICOMETER;
    public static final MeasureUnit PINT;
    public static final MeasureUnit PINT_METRIC;
    public static final MeasureUnit POUND;
    public static final MeasureUnit POUND_PER_SQUARE_INCH;
    public static final MeasureUnit QUART;
    public static final MeasureUnit RADIAN;
    public static final MeasureUnit REVOLUTION_ANGLE;
    public static final TimeUnit SECOND;
    public static final MeasureUnit SQUARE_CENTIMETER;
    public static final MeasureUnit SQUARE_FOOT;
    public static final MeasureUnit SQUARE_INCH;
    public static final MeasureUnit SQUARE_KILOMETER;
    public static final MeasureUnit SQUARE_METER;
    public static final MeasureUnit SQUARE_MILE;
    public static final MeasureUnit SQUARE_YARD;
    public static final MeasureUnit STONE;
    public static final MeasureUnit TABLESPOON;
    public static final MeasureUnit TEASPOON;
    public static final MeasureUnit TERABIT;
    public static final MeasureUnit TERABYTE;
    public static final MeasureUnit TON;
    public static final MeasureUnit VOLT;
    public static final MeasureUnit WATT;
    public static final TimeUnit WEEK;
    public static final MeasureUnit YARD;
    public static final TimeUnit YEAR;
    
    MeasureUnit() {
        throw new RuntimeException("Stub!");
    }
    
    public String getType() {
        throw new RuntimeException("Stub!");
    }
    
    public String getSubtype() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object rhs) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public static synchronized Set<String> getAvailableTypes() {
        throw new RuntimeException("Stub!");
    }
    
    public static synchronized Set<MeasureUnit> getAvailable(final String type) {
        throw new RuntimeException("Stub!");
    }
    
    public static synchronized Set<MeasureUnit> getAvailable() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        ACRE = null;
        ACRE_FOOT = null;
        AMPERE = null;
        ARC_MINUTE = null;
        ARC_SECOND = null;
        ASTRONOMICAL_UNIT = null;
        BIT = null;
        BUSHEL = null;
        BYTE = null;
        CALORIE = null;
        CARAT = null;
        CELSIUS = null;
        CENTILITER = null;
        CENTIMETER = null;
        CENTURY = null;
        CUBIC_CENTIMETER = null;
        CUBIC_FOOT = null;
        CUBIC_INCH = null;
        CUBIC_KILOMETER = null;
        CUBIC_METER = null;
        CUBIC_MILE = null;
        CUBIC_YARD = null;
        CUP = null;
        CUP_METRIC = null;
        DAY = null;
        DECILITER = null;
        DECIMETER = null;
        DEGREE = null;
        FAHRENHEIT = null;
        FATHOM = null;
        FLUID_OUNCE = null;
        FOODCALORIE = null;
        FOOT = null;
        FURLONG = null;
        GALLON = null;
        GENERIC_TEMPERATURE = null;
        GIGABIT = null;
        GIGABYTE = null;
        GIGAHERTZ = null;
        GIGAWATT = null;
        GRAM = null;
        G_FORCE = null;
        HECTARE = null;
        HECTOLITER = null;
        HECTOPASCAL = null;
        HERTZ = null;
        HORSEPOWER = null;
        HOUR = null;
        INCH = null;
        INCH_HG = null;
        JOULE = null;
        KARAT = null;
        KELVIN = null;
        KILOBIT = null;
        KILOBYTE = null;
        KILOCALORIE = null;
        KILOGRAM = null;
        KILOHERTZ = null;
        KILOJOULE = null;
        KILOMETER = null;
        KILOMETER_PER_HOUR = null;
        KILOWATT = null;
        KILOWATT_HOUR = null;
        KNOT = null;
        LIGHT_YEAR = null;
        LITER = null;
        LITER_PER_100KILOMETERS = null;
        LITER_PER_KILOMETER = null;
        LUX = null;
        MEGABIT = null;
        MEGABYTE = null;
        MEGAHERTZ = null;
        MEGALITER = null;
        MEGAWATT = null;
        METER = null;
        METER_PER_SECOND = null;
        METER_PER_SECOND_SQUARED = null;
        METRIC_TON = null;
        MICROGRAM = null;
        MICROMETER = null;
        MICROSECOND = null;
        MILE = null;
        MILE_PER_GALLON = null;
        MILE_PER_HOUR = null;
        MILE_SCANDINAVIAN = null;
        MILLIAMPERE = null;
        MILLIBAR = null;
        MILLIGRAM = null;
        MILLILITER = null;
        MILLIMETER = null;
        MILLIMETER_OF_MERCURY = null;
        MILLISECOND = null;
        MILLIWATT = null;
        MINUTE = null;
        MONTH = null;
        NANOMETER = null;
        NANOSECOND = null;
        NAUTICAL_MILE = null;
        OHM = null;
        OUNCE = null;
        OUNCE_TROY = null;
        PARSEC = null;
        PICOMETER = null;
        PINT = null;
        PINT_METRIC = null;
        POUND = null;
        POUND_PER_SQUARE_INCH = null;
        QUART = null;
        RADIAN = null;
        REVOLUTION_ANGLE = null;
        SECOND = null;
        SQUARE_CENTIMETER = null;
        SQUARE_FOOT = null;
        SQUARE_INCH = null;
        SQUARE_KILOMETER = null;
        SQUARE_METER = null;
        SQUARE_MILE = null;
        SQUARE_YARD = null;
        STONE = null;
        TABLESPOON = null;
        TEASPOON = null;
        TERABIT = null;
        TERABYTE = null;
        TON = null;
        VOLT = null;
        WATT = null;
        WEEK = null;
        YARD = null;
        YEAR = null;
    }
}
