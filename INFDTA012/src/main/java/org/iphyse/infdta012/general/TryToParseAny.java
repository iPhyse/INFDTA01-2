package org.iphyse.infdta012.general;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/**
 *
 * @author RICKRICHTER
 */
public class TryToParseAny {
    
    public static int ttpaInt(String str, int fallback){
        int result;
        try{
            result = parseInt(str);
        }catch(NumberFormatException e){
            System.out.println("Couldn't read input as number, fallback to '" + fallback + "'");
            result = fallback;
        }
        return result;
    }
    
    public static double ttpaDouble(String str, double fallback){
        double result;
        try{
            result = parseDouble(str);
        }catch(NumberFormatException e){
            System.out.println("Couldn't read input as Double, fallback to " + fallback);
            result = fallback;
        }
        
        return result;
    }
    
    public static boolean ttpaBool(String str, boolean fallback){
        boolean result;
        try{
            result = parseBoolean(str);
        }catch(NumberFormatException e){
            System.out.println("Couldn't read input as Boolean, fallback to " + fallback);
            result = fallback;
        }
        
        return result;
    }
    
}