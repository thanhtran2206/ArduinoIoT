package ledcontrol.arduino.net.wifiledcontrol;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/*
    Dev by Thanh Tran 2016
 */

public class MainActivity extends AppCompatActivity {
    //UI Elements
    Button btnLED1, btnLED2, btnLED3, btnLED4, btnLED5;
    EditText txtAddressAndPort;

    Socket myAppSocket = null;
    public static String wifiModuleIP = "";
    public static int wifiModulePort = 0;
    public static String LEDID = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLED1 = (Button) findViewById(R.id.btnLED1);
        btnLED2 = (Button) findViewById(R.id.btnLED2);
        btnLED3 = (Button) findViewById(R.id.btnLED3);
        btnLED4 = (Button) findViewById(R.id.btnLED4);
        btnLED5 = (Button) findViewById(R.id.btnLED5);

        txtAddressAndPort = (EditText) findViewById(R.id.txtAddress);

        btnLED1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIPandPort();
                LEDID = "1";
                Socket_AsyncTask turnLED1 = new Socket_AsyncTask();
                turnLED1.execute();
            }
        });

        btnLED2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIPandPort();
                LEDID = "2";
                Socket_AsyncTask turnLED2 = new Socket_AsyncTask();
                turnLED2.execute();
            }
        });

        btnLED3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIPandPort();
                LEDID = "3";
                Socket_AsyncTask turnLED3 = new Socket_AsyncTask();
                turnLED3.execute();
            }
        });

        btnLED4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIPandPort();
                LEDID = "4";
                Socket_AsyncTask turnLED4 = new Socket_AsyncTask();
                turnLED4.execute();
            }
        });

        btnLED5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIPandPort();
                LEDID = "5";
                Socket_AsyncTask turnLED5 = new Socket_AsyncTask();
                turnLED5.execute();
            }
        });
    }

    //helper functions
    private void getIPandPort()
    {
        String iPAndPort = txtAddressAndPort.getText().toString();
        Log.d("MYTEST", "IP String:" + iPAndPort);
        String tmp[] = iPAndPort.split(":");
        wifiModuleIP = tmp[0];
        wifiModulePort = Integer.valueOf(tmp[1]);

        Log.d("MYTEST", "IP:" + wifiModuleIP);
        Log.d("MYTEST", "Port:" + wifiModulePort);
    }

    //helper class
    public class Socket_AsyncTask extends AsyncTask<Void, Void, Void>
    {
        Socket socket;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                InetAddress inetAddress = InetAddress.getByName(MainActivity.wifiModuleIP);
                socket = new java.net.Socket(inetAddress, MainActivity.wifiModulePort);
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                //dataOutputStream.writeChar(MainActivity.LEDID);
                dataOutputStream.writeBytes(LEDID);
                dataOutputStream.close();
                socket.close();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
