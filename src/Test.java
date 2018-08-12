
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nduwayo
 */
public class Test {
     private int session_id = -1;
    private int statement_id = -1;
    private String stateSession = "starting";
    private String stateExecution = "waiting";

    public  int createSession() throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"kind\": \"spark\"}");
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

    public  String createState(int my_session_id) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"kind\": \"spark\"}");
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

    public  int codeExecution(int my_session_id, String code) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"code\":\"25+98\"}");
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

    public  JSONObject getResults(int my_session_id, int my_statement_id) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://localhost:8998/sessions/" + getSession_id() + "/statements/" + getStatement_id())
                .addHeader("cache-control", "no-cache")
                .addHeader("Content-Type", "application/json")
                .addHeader("X-Requested-By", "user")
                .build();

        Response response = client.newCall(request).execute();
        String jsonData = response.body().string();
        JSONObject jsonObject = new JSONObject(jsonData);
        stateExecution = jsonObject.getString("state");
        //System.out.println(getStateExecution());

        return jsonObject;

    }

    public  void main(String[] args) throws IOException, InterruptedException {

        session_id = createSession();
        while (!stateSession.equals("idle")) {
            stateSession = createState(session_id);
        }

        System.out.println(stateSession);
        String code = "\"25+98\"";
        statement_id = codeExecution(session_id, code);

        JSONObject myResult = new JSONObject();
        while (!stateExecution.equals("available")) {
            myResult = getResults(session_id, statement_id);
        }

        System.out.println(myResult);

    }

    /**
     * @return the session_id
     */
    public int getSession_id() {
        return session_id;
    }

    /**
     * @return the statement_id
     */
    public int getStatement_id() {
        return statement_id;
    }

    /**
     * @return the stateSession
     */
    public String getStateSession() {
        return stateSession;
    }

    /**
     * @return the stateExecution
     */
    public String getStateExecution() {
        return stateExecution;
    }
    
}
