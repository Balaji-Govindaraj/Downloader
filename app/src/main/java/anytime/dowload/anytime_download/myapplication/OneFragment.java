package anytime.dowload.anytime_download.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import org.w3c.dom.Text;

import static android.content.Context.CLIPBOARD_SERVICE;
import java.net.MalformedURLException;
import java.net.URL;
import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class OneFragment extends Fragment{
    public OneFragment() {
        // Required empty public constructor
    }
    IntentFilter intentFilter;

    private MyService serviceBinder;
    Intent i;

    private ServiceConnection connection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            //---called when the connection is made---
            serviceBinder = ((MyService.MyBinder)service).getService();
            try {
                URL[] urls = new URL[] {
                        new URL("http://www.ieee-pes.org/images/files/pdf/pg4-sample-conference-paper.pdf"),
//                        new URL("http://www.coe.utah.edu/~cs4640/slides/Lecture0.pdf"),
//                        new URL("http://www.coe.utah.edu/~cs4640/slides/Lecture0.pdf"),
//                        new URL("http://www.coe.utah.edu/~cs4640/slides/Lecture0.pdf")
                };
                //---assign the URLs to the service through the serviceBinder object---
                serviceBinder.urls = urls;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            getContext().startService(i);
        }
        public void onServiceDisconnected(ComponentName className) {
            //---called when the service disconnects---
            serviceBinder = null;
        }
    };
    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getContext(), "File downloaded!",
                    Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=null;
        view=inflater.inflate(R.layout.fragment_one, container, false);
        Button start=(Button)view.findViewById(R.id.save);
        ClipboardManager myClipboard = (ClipboardManager) getContext().getSystemService(getContext().CLIPBOARD_SERVICE);
        if(myClipboard.hasPrimaryClip()) {
            TextView clipdata=(TextView)view.findViewById(R.id.timer_url);

            ClipData abc = myClipboard.getPrimaryClip();
            ClipData.Item item = abc.getItemAt(0);
            String text = item.getText().toString();
            clipdata.setText(text);
            intentFilter = new IntentFilter();
            intentFilter.addAction("FILE_DOWNLOADED_ACTION");
            getContext().registerReceiver(intentReceiver, intentFilter);
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i = new Intent(getContext(), MyService.class);
                    getContext().bindService(i, connection, Context.BIND_AUTO_CREATE);
                }
            });
        }
        else
        {
            //clipdata.setText("Paste the url");
        }
//        toolbar = (Toolbar)view.findViewById(R.id.toolbar);
//        //toolbar.setTitle("TIME");
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        return view;
    }

}