package apichallenge;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import org.json.*;
import java.time.*;

/**
 *
 * @author Alberto Jesus Camacho
 * 
 * This project was very interesting to me mainly becuase of the http requests and 
 * JSON dictionary usage. This is the first time I use those concepts as well as the datestamp
 * stage. I've learned quite a few classes in this API Challenge and was a really fun a rewarding 
 * experience!
 * 
 * I could have used the same URL and URLConnection variable names but I opted to go with differently named
 * references for better readability.
 */
public class APIChallenge 
{
    public static void main(String[] args) throws MalformedURLException, IOException 
    {
        // String holds the returned JSON responses from the server
        String decodedString;
        
        //**********************************
        // Registering to the API Challenge*
        //**********************************
        
        // Instantiating URL objects and connecting to the server for registration
        URL registrationURL = new URL("http://challenge.code2040.org/api/register");
        URLConnection RegistrationConnection = registrationURL.openConnection();  
        RegistrationConnection.setDoOutput(true);
        
        // Writing JSON to the server to acquire the entry token
        OutputStreamWriter out = new OutputStreamWriter(RegistrationConnection.getOutputStream());                         
        out.write("{\"email\":\"acama056@fiu.edu\",\"github\":\"https://github.com/acam002/acamrepo\"}");
        out.close();
        
        // Reading the result sent from the server
        BufferedReader in = new BufferedReader(new InputStreamReader(RegistrationConnection.getInputStream()));
        // Loop will continue iterating until the server response is finished
        decodedString = in.readLine();
        in.close();
        
        //********************************
        // Stage one of the API Challenge*
        //********************************
        
        // Instantiating URL objects and connecting to the server to acquire a string to be reversed
        URL getStringURL = new URL("http://challenge.code2040.org/api/getstring");
        URLConnection getStringConnection = getStringURL.openConnection();
        getStringConnection.setDoOutput(true);
        
        // Writing JSON to the server to acquire the string to be reversed
        out = new OutputStreamWriter(getStringConnection.getOutputStream());
        out.write("{\"token\":\"567z93QjjN\"}");
        out.close();
        
        // Instantiating StringBuffer object to reverse the given string
        StringBuffer sb = null;
        
        // Reading the result sent from the server
        in = new BufferedReader(new InputStreamReader(getStringConnection.getInputStream()));
        decodedString = in.readLine();
        
        // Parsing the JSON to get the desired string
        JSONObject JSONString = new JSONObject(decodedString);
        sb = new StringBuffer(JSONString.getString("result"));
        in.close();
        
        // Sending the reversed String back to the challenge with new URL objects
        URL validateStringURL = new URL("http://challenge.code2040.org/api/validatestring"); 
        URLConnection validateStringConnection = validateStringURL.openConnection();
        validateStringConnection.setDoOutput(true);
        out = new OutputStreamWriter(validateStringConnection.getOutputStream());
        
        // Writing JSON with registration token and reversed string
        out.write("{\"token\":\"567z93QjjN\",\"string\":\"" + sb.reverse() + "\"}");
        out.close();
        
        // Reading server response
        in = new BufferedReader(new InputStreamReader(validateStringConnection.getInputStream()));
        decodedString = in.readLine();
        System.out.println(decodedString);
        in.close();
        
        
        //********************************
        // Stage two of the API Challenge*
        //********************************
    
        // Creating IRL and opening the connection to the server
        URL haystackURL = new URL("http://challenge.code2040.org/api/haystack");
        URLConnection haystackConnection = haystackURL.openConnection();
        haystackConnection.setDoOutput(true);
        
        // Sending token to the server
        out = new OutputStreamWriter(haystackConnection.getOutputStream());
        out.write("{\"token\":\"567z93QjjN\"}");
        out.close();
        
        // Reading the server request
        in = new BufferedReader(new InputStreamReader(haystackConnection.getInputStream()));
        decodedString = in.readLine();
        in.close();
        
        // Parsing the JSON object's needle and haystack fields
        JSONObject haystackParser = new JSONObject(decodedString);
        String needle = haystackParser.getJSONObject("result").getString("needle");
        JSONArray jHaystackArray = (JSONArray)haystackParser.getJSONObject("result").getJSONArray("haystack");
        
        // The integer value found will hold the index of the needle
        int found = -1;
        ArrayList<String> haystack = new ArrayList<>();
        
        // Parsing JSONArray into a String ArrayList
        for (int i = 0; i < jHaystackArray.length(); i++)
        {
            haystack.add(jHaystackArray.getString(i));
        }      
        
        // Comparing Strings to find the matching needle in the haystack
        for (int i = 0; i < haystack.size(); i++)
        {
            // If the element matches the needle then assign then assign i to found
            if (haystack.get(i).equals(needle))
            {
                found = i;
            }
        }
        
        // Creating the URL and connection to send the index of the needle
        URL validateNeedleURL = new URL("http://challenge.code2040.org/api/validateneedle");
        URLConnection validateNeedleConnection = validateNeedleURL.openConnection();
        validateNeedleConnection.setDoOutput(true);
        
        // Writing to the server
        out = new OutputStreamWriter(validateNeedleConnection.getOutputStream());
        out.write("{\"token\":\"567z93QjjN\",\"needle\":\"" + found + "\"}");
        out.close();
        
        // Reading the server response
        in = new BufferedReader(new InputStreamReader(validateNeedleConnection.getInputStream()));
        decodedString = in.readLine();
        in.close();
        System.out.println(decodedString);
         
        //**********************************
        // Stage three of the API Challenge*
        //**********************************
        
        // Creating the URL and connection for stage three to get the array and prefix
        URL prefixURL = new URL("http://challenge.code2040.org/api/prefix");
        URLConnection prefixConnection = prefixURL.openConnection();
        
        // Writing to the server
        prefixConnection.setDoOutput(true);
        out = new OutputStreamWriter(prefixConnection.getOutputStream());
        out.write("{\"token\":\"567z93QjjN\"}");
        out.close();
        
        // Reading from the server
        in = new BufferedReader(new InputStreamReader(prefixConnection.getInputStream()));
        decodedString = in.readLine();
        in.close();
        
        // Instantiating the JSON object
        JSONObject prefixParser = new JSONObject(decodedString);
        // Getting the prefix string
        String prefix = prefixParser.getJSONObject("result").getString("prefix");
        
        // JSONArray and String ArrayList being initialized
        JSONArray jPrefixArray = (JSONArray)prefixParser.getJSONObject("result").getJSONArray("array");
        ArrayList<String> prefixArrayList = new ArrayList<>();
        
        // Setting a prefix size allows the program to handle prefixes of any size
        int prefixSize = prefix.length();
        
        // Loop adding all string to the prefixArrayList not containing the prefix
        for (int i = 0; i < jPrefixArray.length(); i++)
        {
            // If the String's prefix is not equal to the prefix then add the String
            // in the current iteration to the prefixArrayList
            if(!jPrefixArray.getString(i).substring(0, prefixSize).equals(prefix))
            {
                prefixArrayList.add(jPrefixArray.getString(i));
            }
        }
        
        // Opening a connection once again to send the array with the required elements
        URL validatePrefixURL = new URL("http://challenge.code2040.org/api/validateprefix");
        URLConnection validatePrefixConnection = validatePrefixURL.openConnection();
        validatePrefixConnection.setDoOutput(true);
        
        String JSON = "";
        
        // Formating String for JSON request by concatenating all ArrayList elements
        for (int i = 0; i < prefixArrayList.size(); i++)
        {
            JSON = JSON + "\"" + prefixArrayList.get(i) + "\",";
        }
        // Removing the trailing comma from the String
        JSON = JSON.substring(0, JSON.length() - 1);
        
        // Writing to the server
        out = new OutputStreamWriter(validatePrefixConnection.getOutputStream());
        out.write("{\"token\":\"567z93QjjN\", \"array\":[" + JSON +"]}");
        out.close();
        
        // Reading the server response
        in = new BufferedReader(new InputStreamReader(validatePrefixConnection.getInputStream()));
        decodedString = in.readLine();
        in.close();
        System.out.println(decodedString);
        
        
        //*********************************
        // Stage four of the API Challenge*
        //*********************************
        
        // Instantiating URL and URLConnection objects to connect to the server
        URL timeURL = new URL("http://challenge.code2040.org/api/time");
        URLConnection timeConnection = timeURL.openConnection();
        timeConnection.setDoOutput(true);
        
        // Sending token to the server for the datestamp
        out = new OutputStreamWriter(timeConnection.getOutputStream());
        out.write("{\"token\":\"567z93QjjN\"}");
        out.close();
        
        // Reading server response
        in = new BufferedReader(new InputStreamReader(timeConnection.getInputStream()));
        decodedString = in.readLine();
        
        // Creating JSON parser
        JSONObject dateParser = new JSONObject(decodedString);
        
        // Parsing JSON object for the interval value
        int interval = dateParser.getJSONObject("result").getInt("interval");
        
        // Creating ZonedDateTime object to use the plusSeconds method which adds the interval into the datestamp
        ZonedDateTime datestamp = ZonedDateTime.parse(dateParser.getJSONObject("result").getString("datestamp"));
        datestamp = datestamp.plusSeconds(interval);
        
        // Sending last server request with the new datestamp
        URL validateTimeURL = new URL("http://challenge.code2040.org/api/validatetime");
        URLConnection validateTimeConnection = validateTimeURL.openConnection();
        validateTimeConnection.setDoOutput(true);
        
        // Writing to the server for the last time...
        out = new OutputStreamWriter(validateTimeConnection.getOutputStream());
        out.write("{\"token\":\"567z93QjjN\",\"datestamp\":\"" + datestamp +"\"}");
        out.close();
        
        // Reading server response!
        in = new BufferedReader(new InputStreamReader(validateTimeConnection.getInputStream()));
        decodedString = in.readLine();
        
        System.out.println(decodedString);
        
        // Done!
    }       
}  