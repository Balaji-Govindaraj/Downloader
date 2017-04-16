package anytime.dowload.anytime_download.myapplication;
import java.net.MalformedURLException;
import java.net.URL;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class MyIntentService extends IntentService{

    public MyIntentService() {
        super("MyIntentServiceName");
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // TODO Auto-generated method stub

        try {
            int result = DownloadFile(new URL
                    ("http://www.ieee-pes.org/images/files/pdf/pg4-sample-conference-paper.pdf"));
            Log.d("IntentService", "Downloaded " + result + " bytes");

            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("FILE_DOWNLOADED_ACTION");
            getBaseContext().sendBroadcast(broadcastIntent);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private int DownloadFile(URL url) {

        try {
            //---simulate taking some time to download a file---
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 100;
    }
}
