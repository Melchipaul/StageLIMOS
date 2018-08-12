
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class ApacheLivyBase {

        private static int session_id = -1;
    private static int statement_id = -1;
    private static String stateSession = "starting";
    private static String stateExecution = "waiting";

    public static int createSession(String code) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
          JSONObject actualData = new JSONObject();
        try{
        actualData.put("kind", code);
        }catch(JSONException e){
            System.out.println(e);
        }
        RequestBody body = RequestBody.create(mediaType, actualData.toString());
        Request request = new Request.Builder()
                .url("http://localhost:8998/sessions")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .build();

        Response response = client.newCall(request).execute();
        String jsonData = response.body().string();
        JSONObject jsonObject = new JSONObject(jsonData);
        session_id = jsonObject.getInt("id");

        return getSession_id();
    }

    public static String createState(int my_session_id, String code) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        JSONObject actualData = new JSONObject();
        try{
        actualData.put("kind", code);
        }catch(JSONException e){
            System.out.println(e);
        }
        RequestBody body = RequestBody.create(mediaType, actualData.toString());
        Request request = new Request.Builder()
                .url("http://localhost:8998/sessions/" + my_session_id)
                .addHeader("cache-control", "no-cache")
                .build();

        Response response = client.newCall(request).execute();
        String jsonData = response.body().string();
        JSONObject jsonObject = new JSONObject(jsonData);
        stateSession = jsonObject.getString("state");
        return getStateSession();
    }

    public static int codeExecution(int my_session_id, String code) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        JSONObject actualData = new JSONObject();
        try{
        actualData.put("code", code);
        }catch(JSONException e){
            System.out.println(e);
        }
        RequestBody body = RequestBody.create(mediaType, actualData.toString());
        
        Request request = new Request.Builder()
                .url("http://localhost:8998/sessions/" + my_session_id + "/statements")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("X-Requested-By", "user")
                .addHeader("cache-control", "no-cache")
                .build();

        Response response = client.newCall(request).execute();
        String jsonData = response.body().string();
        JSONObject jsonObject = new JSONObject(jsonData);

        return jsonObject.getInt("id");
    }

    public static JSONObject getResults(int my_session_id, int my_statement_id) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://localhost:8998/sessions/" + my_session_id + "/statements/" + my_statement_id)
                .addHeader("cache-control", "no-cache")
                .addHeader("Content-Type", "application/json")
                .addHeader("X-Requested-By", "user")
                .build();

        Response response = client.newCall(request).execute();
        String jsonData = response.body().string();
        JSONObject jsonObject = new JSONObject(jsonData);
        stateExecution = jsonObject.getString("state");
        System.out.println(getStateExecution());

        return jsonObject;

    }

    public static void main(String[] args) throws IOException, InterruptedException {

        session_id = createSession("spark");
        while (!stateSession.equals("idle")) {
            stateSession = createState(getSession_id(),"spark");
        }

        System.out.println(getStateSession());
        String code = "def factorial(n: BigInt): BigInt =\n" +
"    if (n == 0) 1 else n * factorial(n-1)\n" +
"\n" +
"  val f50 = factorial(50); val f49 = factorial(49)\n" +
"  println(\"50! = \" + f50)\n" +
"  println(\"49! = \" + f49)\n" +
"  println(\"50!/49! = \" + (f50 / f49))";
        statement_id = codeExecution(getSession_id(), code);

        JSONObject myResult = new JSONObject();
        while (!stateExecution.equals("available")) {
            myResult = getResults(getSession_id(), getStatement_id());
        }

        System.out.println(myResult);

    }

    /**
     * @return the session_id
     */
    public static int getSession_id() {
        return session_id;
    }

    /**
     * @return the statement_id
     */
    public static int getStatement_id() {
        return statement_id;
    }

    /**
     * @return the stateSession
     */
    public static String getStateSession() {
        return stateSession;
    }

    /**
     * @return the stateExecution
     */
    public static String getStateExecution() {
        return stateExecution;
    }

}
