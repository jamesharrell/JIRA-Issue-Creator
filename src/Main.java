import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

public class Main {

    public static void main(String[] args) {
        System.out.println("\nSending request to JIRA...");
        sendPost();
    }

    private static void sendPost() {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            String url = "https://fotlinc.atlassian.net/rest/api/2/issue";
            HttpPost post = new HttpPost(url);


            JSONObject jsonobject = new JSONObject();
            JSONObject jsonFields = new JSONObject();
            JSONObject jsonType = new JSONObject();
            JSONObject jsonProject = new JSONObject();
            JSONObject jsonMaster = new JSONObject();

            JSONArray labelsJsonArray = new JSONArray();
            labelsJsonArray.put("SupportingSystems");

            jsonProject.put("key", "SFDC");

            jsonType.put("name", "Task");

            jsonFields.put("project", jsonProject);
            jsonFields.put("summary", "(Reoccurring) Backup the ESKO Webcenter data");
            jsonFields.put("description", "Export and add to GIT");
            jsonFields.put("labels", labelsJsonArray);
            jsonFields.put("issuetype", jsonType);

            jsonMaster.put("fields", jsonFields);

            System.out.println(jsonMaster.toString());
            String jsonMasterString = new String(jsonMaster.toString());
            StringEntity se = new StringEntity(jsonMasterString);
            post.setEntity(se);
            post.setHeader(HttpHeaders.AUTHORIZATION, "Basic placeholder");
            post.setHeader("Content-Type", "application/json");

            HttpResponse response = client.execute(post);
            int responseCode = response.getStatusLine().getStatusCode();

            System.out.println("Response Code : " + responseCode);
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            System.out.println(result.toString());


        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
