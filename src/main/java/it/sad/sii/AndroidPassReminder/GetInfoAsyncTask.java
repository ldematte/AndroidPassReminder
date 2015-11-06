package it.sad.sii.AndroidPassReminder;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * Created by lconcli on 22/09/15.
 */
public class GetInfoAsyncTask extends AsyncTask<Void, Void, String> {

    public interface GetInfoListener {
        void onPersonalInfoReceived(String data);
        void onError(String error);
    }

    private final GetInfoListener infoListener;
    private final String cnumber;
    private final String apiUsername;
    private final String apiPassword;

    private Exception exception;

    public GetInfoAsyncTask(GetInfoListener infoListener, String cnumber, String apiUsername, String apiPassword) {
        this.infoListener = infoListener;
        this.cnumber = cnumber;
        this.apiUsername = apiUsername;
        this.apiPassword = apiPassword;
    }

    protected String doInBackground(Void... params) {
        DefaultHttpClient httpClient = new DefaultHttpClient();

        SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
        httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", socketFactory, 443));

        HttpRequestBase request = new HttpGet("https://www.sii.bz.it/api/altoadigepass/tickets/" + cnumber + "/personal");
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(apiUsername, apiPassword);
        request.addHeader(BasicScheme.authenticate(credentials, "UTF-8", false));
        request.getParams().setBooleanParameter("http.protocol.expect-continue", false);
        request.addHeader(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        request.addHeader(new BasicHeader("Accept", "application/json"));

        exception = null;
        try {
            HttpResponse response = httpClient.execute(request);
            int errorCode = response.getStatusLine().getStatusCode();
            if (errorCode == 200) {
                return EntityUtils.toString(response.getEntity());
            } else {
                throw new Exception("Http status: " + errorCode);
            }
        } catch (Exception e) {
            Log.e(GetInfoAsyncTask.class.getName(), e.getMessage());
            exception = e;
        }
        return null;
    }

    protected void onPostExecute(String data) {
        if (exception != null) {
            infoListener.onError(exception.getMessage());
        } else {
            if (data != null) {
                Log.d(GetInfoAsyncTask.class.getName(), data);
                infoListener.onPersonalInfoReceived(data);
            } else {
                Log.d(GetInfoAsyncTask.class.getName(), "null");
            }
        }
    }
}
