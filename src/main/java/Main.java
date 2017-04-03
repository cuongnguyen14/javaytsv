import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.net.*;
import java.io.*;

import javax.net.ssl.HttpsURLConnection;

import static spark.Spark.*;

public class Main {

  public static void main(String[] args) {

    port(Integer.valueOf(System.getenv("PORT")));
    staticFileLocation("/public");

      get("/get/:quality/:ytid", (req, res) -> {
          String quality = req.params(":quality");
          String ytid = req.params(":ytid");

          try {
              String url = "https://api.trending.fm/media-cache/" + quality + "/" + ytid;
              
              URL obj = new URL(url);
              HttpURLConnection con = (HttpURLConnection) obj.openConnection();
              
              // optional default is GET
              con.setRequestMethod("GET");
              //
              con.setRequestProperty("User-Agent", "Mozilla/5.0");
              
              int responseCode = con.getResponseCode();
              System.out.println("\nSending 'GET' request to URL : " + url);
              System.out.println("Response Code : " + responseCode);
              
              BufferedReader in = new BufferedReader(
                                                     new InputStreamReader(con.getInputStream()));
              String inputLine;
              StringBuffer response = new StringBuffer();
              
              while ((inputLine = in.readLine()) != null) {
                  response.append(inputLine);
              }
              in.close();
              
              //print result
              return response.toString();
          } catch(Exception e) {
              return "";
          }

      });

      

  }

}
