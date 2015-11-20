package com.ga.common;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class JsonUtility {

    // This File returns JSON string excluding the fields with @Expose Annotation

    private static final Logger LOG = LoggerFactory.getLogger(JsonUtility.class);

    public static String getJson(Object obj) {
        try {
            final GsonBuilder builder = new GsonBuilder();
            builder.excludeFieldsWithoutExposeAnnotation();
            builder.serializeNulls();
            builder.setDateFormat("dd-MM-yyyy");
            final Gson g = builder.create();
            return g.toJson(obj);
        } catch (Exception e) {
            LOG.info(e.toString());
            return "";
        }
    }

    public static String getJsonWithDateFormat(Object obj, String dateFormat) {
        try {
            final GsonBuilder builder = new GsonBuilder();
            builder.excludeFieldsWithoutExposeAnnotation();
            builder.setDateFormat(dateFormat);
            final Gson g = builder.create();
            return g.toJson(obj);
        } catch (Exception e) {
            LOG.info(e.toString());
            return "";
        }
    }

    public static Object getJsonOfType(String obj, Type type) {
        try {
            final GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                // Year in 4, month in 2, day in 2
                final DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

                public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                        throws JsonParseException {
                    try {
                        return df.parse(json.getAsString());
                    } catch (final java.text.ParseException e) {
                        return null;
                    }
                }
            });
            // this code for get transion variable in json to ..
            builder.excludeFieldsWithModifiers(Modifier.STATIC);

            final Gson g = builder.create();
            return g.fromJson(obj, type);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.info(e.toString());
            return "";
        }
    }

    public static String getJsonexcludeTransient(Object obj) {
        try {
            Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.STATIC).create();
            return gson.toJson(obj);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.info(e.toString());
            return "";
        }
    }

    public static void main(String[] args) {

        // transient are by default excluded
        Gson gson = new Gson();

    }

    /**
     * 
     * @author Parakramsinh Parmar Date : 22/09/2014 Purpose : Main purpose is to remove unwanted fields from json
     * 
     * @param obj : Object witch we want to convert in to JSON
     * @param fieldExclusions : List<String> Field Exclusions
     * @param classExclusions : List<Class> Exclusions
     * @param classWithFieldsExclusions
     * @param fieldWithClassExclusions
     * @return String Converted JSON
     */

    static {

    }

}
