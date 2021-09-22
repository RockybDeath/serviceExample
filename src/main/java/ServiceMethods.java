import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.*;

public class ServiceMethods {
    private String url;
    private int port;
    CloseableHttpClient httpClient;
    public ServiceMethods(String url, int port){
        this.url = url;
        this.port = port;
        init();
    }
    public void init(){
        httpClient = HttpClients.createDefault();
    }
    public String getAll(){
        try {
            HttpGet httpGet = new HttpGet(url + ":" + port + "/data");
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
    public String setElement(int id, String name, long timeLife){
        try {
            HttpPost httpPost = new HttpPost(url + ":" + port + "/data");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", name);
            jsonObject.put("id", id);
            jsonObject.put("timelife", timeLife);
            StringEntity stringEntity = new StringEntity(jsonObject.toString());
            httpPost.setEntity(stringEntity);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Accept", "application/json");
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
    public String getById(int id){
        try {
            HttpGet httpGet = new HttpGet(url + ":" + port + "/data/"+id);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
    public String removeById(int id){
        try{
            HttpDelete httpDelete = new HttpDelete(url + ":" + port + "/data/"+id);
            CloseableHttpResponse response = httpClient.execute(httpDelete);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
    public String dump(){
        try{
            HttpGet httpGet = new HttpGet(url + ":" + port + "/dump");
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream inputStream = entity.getContent();
            OutputStream outputStream = new FileOutputStream("dump.bin");
            IOUtils.copy(inputStream, outputStream);
            return "Записано в файл - dump.bin";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
    public String load(){
        try{
            HttpPost httpPost = new HttpPost(url + ":" + port + "/load");
            File file = new File("dump.bin");
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            multipartEntityBuilder.addBinaryBody("file", file, ContentType.DEFAULT_BINARY, "file.bin");
            httpPost.setEntity(multipartEntityBuilder.build());
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
    public void setPort(int port) {
        this.port = port;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPort() {
        return port;
    }

    public String getUrl() {
        return url;
    }
}
