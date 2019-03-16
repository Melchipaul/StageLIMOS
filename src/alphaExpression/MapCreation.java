
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nduwayo
 */
public class MapCreation {
    private static Map<String, AlphaExpression> myMap = new HashMap<>();

    public  MapCreation(HttpServletRequest request) {
         this.myMap.put(request.getParameter("functionName"), new AlphaExpression(request.getParameter("drag")));
        
    }

    /**
     * @return the myMap
     */
    public static Map<String, AlphaExpression> getMyMap() {
        return myMap;
    }
    
    
    
}
