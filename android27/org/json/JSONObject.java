package org.json;

import java.util.*;

public class JSONObject
{
    public static final Object NULL;
    
    public JSONObject() {
        throw new RuntimeException("Stub!");
    }
    
    public JSONObject(final Map copyFrom) {
        throw new RuntimeException("Stub!");
    }
    
    public JSONObject(final JSONTokener readFrom) throws JSONException {
        throw new RuntimeException("Stub!");
    }
    
    public JSONObject(final String json) throws JSONException {
        throw new RuntimeException("Stub!");
    }
    
    public JSONObject(final JSONObject copyFrom, final String[] names) throws JSONException {
        throw new RuntimeException("Stub!");
    }
    
    public int length() {
        throw new RuntimeException("Stub!");
    }
    
    public JSONObject put(final String name, final boolean value) throws JSONException {
        throw new RuntimeException("Stub!");
    }
    
    public JSONObject put(final String name, final double value) throws JSONException {
        throw new RuntimeException("Stub!");
    }
    
    public JSONObject put(final String name, final int value) throws JSONException {
        throw new RuntimeException("Stub!");
    }
    
    public JSONObject put(final String name, final long value) throws JSONException {
        throw new RuntimeException("Stub!");
    }
    
    public JSONObject put(final String name, final Object value) throws JSONException {
        throw new RuntimeException("Stub!");
    }
    
    public JSONObject putOpt(final String name, final Object value) throws JSONException {
        throw new RuntimeException("Stub!");
    }
    
    public JSONObject accumulate(final String name, final Object value) throws JSONException {
        throw new RuntimeException("Stub!");
    }
    
    public Object remove(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isNull(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean has(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public Object get(final String name) throws JSONException {
        throw new RuntimeException("Stub!");
    }
    
    public Object opt(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getBoolean(final String name) throws JSONException {
        throw new RuntimeException("Stub!");
    }
    
    public boolean optBoolean(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean optBoolean(final String name, final boolean fallback) {
        throw new RuntimeException("Stub!");
    }
    
    public double getDouble(final String name) throws JSONException {
        throw new RuntimeException("Stub!");
    }
    
    public double optDouble(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public double optDouble(final String name, final double fallback) {
        throw new RuntimeException("Stub!");
    }
    
    public int getInt(final String name) throws JSONException {
        throw new RuntimeException("Stub!");
    }
    
    public int optInt(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public int optInt(final String name, final int fallback) {
        throw new RuntimeException("Stub!");
    }
    
    public long getLong(final String name) throws JSONException {
        throw new RuntimeException("Stub!");
    }
    
    public long optLong(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public long optLong(final String name, final long fallback) {
        throw new RuntimeException("Stub!");
    }
    
    public String getString(final String name) throws JSONException {
        throw new RuntimeException("Stub!");
    }
    
    public String optString(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public String optString(final String name, final String fallback) {
        throw new RuntimeException("Stub!");
    }
    
    public JSONArray getJSONArray(final String name) throws JSONException {
        throw new RuntimeException("Stub!");
    }
    
    public JSONArray optJSONArray(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public JSONObject getJSONObject(final String name) throws JSONException {
        throw new RuntimeException("Stub!");
    }
    
    public JSONObject optJSONObject(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public JSONArray toJSONArray(final JSONArray names) throws JSONException {
        throw new RuntimeException("Stub!");
    }
    
    public Iterator<String> keys() {
        throw new RuntimeException("Stub!");
    }
    
    public JSONArray names() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public String toString(final int indentSpaces) throws JSONException {
        throw new RuntimeException("Stub!");
    }
    
    public static String numberToString(final Number number) throws JSONException {
        throw new RuntimeException("Stub!");
    }
    
    public static String quote(final String data) {
        throw new RuntimeException("Stub!");
    }
    
    public static Object wrap(final Object o) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        NULL = null;
    }
}
