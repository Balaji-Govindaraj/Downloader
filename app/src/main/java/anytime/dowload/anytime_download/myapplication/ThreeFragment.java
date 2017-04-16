package anytime.dowload.anytime_download.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import jim.h.common.android.lib.zxing.config.ZXingLibConfig;
import jim.h.common.android.lib.zxing.integrator.IntentIntegrator;
import jim.h.common.android.lib.zxing.integrator.IntentResult;

public class ThreeFragment extends Fragment{
    private ZXingLibConfig zxingLibConfig;
    View view;
    public ThreeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        IntentIntegrator.initiateScan(getActivity(), zxingLibConfig);

        view=inflater.inflate(R.layout.fragment_three, container, false);
        return view;

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        TextView output_data=(TextView)view.findViewById(R.id.output);
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IntentIntegrator.REQUEST_CODE:
                IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode,
                        resultCode, data);
                if (scanResult == null) {
                    return;
                }
                final String result = scanResult.getContents();
                output_data.setText(result);
                Toast.makeText(getActivity(),result,Toast.LENGTH_LONG).show();
                break;
            default:
        }
    }

}