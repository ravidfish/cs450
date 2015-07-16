/** 
 * This file has been auto-generated by REST Compile. 
 * 
 * You should not modify it, unless you know what you do. Any modification 
 * might cause serious damage, or even destroy your computer. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE. 
 */ 

package riot_api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/**
 * This class has been auto-generated by REST Compile.
 *
 * @author REST Compile 
 */
public class RestRequest {

  // provide user and password for HTTP AUTH 
  private static final String user = ""; 
  private static final String password = ""; 

  /**
   * Class for placing a GET call
   * 
   * @param urlString the URL as a String value
   * @return the response of the call as a String
   */
  public static String doGetCall(String urlString) {
    String response = "";
    try {
      // connect to the web service via HTTP GET
      URL url = new URL(urlString);
      HttpURLConnection connection =
        (HttpURLConnection) url.openConnection();
      // provide credentials if they're established 
      if (user.length() > 0 && password.length() > 0) { 
        String userPassword = user + ":" + password;
        String encoding = 
          new sun.misc.BASE64Encoder().encode(userPassword.getBytes()); 
        connection.setRequestProperty ("Authorization", "Basic " + encoding); 
      } 
      connection.setRequestMethod("GET");
      connection.connect();

      response = receiveResponse(connection);

      connection.disconnect();
    }
    catch (IOException e) {
      System.err.println(e.toString());
    }
    return response;
  }

  /**
   * Class for placing a POST call
   * 
   * @param urlString the URL as a String value 
   * @param postArgs the POST arguments as a URL-encoded String 
   * @return the response of the call as a String
   */
  public static String doPostCall(String urlString, String postArgs) {
    String response = "";

    try {
      // connect to the web service via HTTP POST
      URL url = new URL(urlString);
      HttpURLConnection connection =
        (HttpURLConnection) url.openConnection();
      // provide credentials if they're established 
      if (user.length() > 0 && password.length() > 0) { 
        String userPassword = user + ":" + password;
        String encoding = 
          new sun.misc.BASE64Encoder().encode(userPassword.getBytes()); 
        connection.setRequestProperty ("Authorization", "Basic " + encoding); 
      } 
      connection.setDoOutput(true);
      connection.setRequestMethod("POST");
      OutputStreamWriter outputStream =
        new OutputStreamWriter(connection.getOutputStream());
      outputStream.write(postArgs);
      outputStream.flush();

      response = receiveResponse(connection);

      connection.disconnect();
      outputStream.close();
    }
    catch (IOException e) {
      System.err.println(e.toString());
    }
    return response;
  }
  
  /**
   * Internal method for encoding-type-based input stream creation.
   * The goal is to accept compressed data if possible.
   *
   * @param connection the HTTP URL connection
   * @return the response as a String
   * @throws IOException 
   */
  private static String receiveResponse(HttpURLConnection connection) throws IOException {
    String response = "";
    try {
      // create the appropriate stream wrapper based on the encoding type
      InputStream inputStream = null;
      String contentEncoding = connection.getContentEncoding();
      if (contentEncoding != null) {
        if (contentEncoding.equalsIgnoreCase("gzip")) {
          inputStream =
            new GZIPInputStream(connection.getInputStream());
        }
        else if (contentEncoding.equalsIgnoreCase("deflate")) {
          inputStream =
            new InflaterInputStream(
              connection.getInputStream(),
              new Inflater(true));
        }
      }
      else {
        inputStream = connection.getInputStream();
      }

      // read the contents
      byte[] buffer = new byte[1024];
      int length;
      while ((length = inputStream.read(buffer)) > 0) {
        for (int i = 0; i < length; i++) {
          response += (char) buffer[i];
        }
      }
      inputStream.close();
    }
    catch (IOException e) {
      int responseCode = connection.getResponseCode();
      switch (responseCode) {
      }
    }
    return response;
  }
}