package nl.jeroen_nieuwenhuis.AndroidToArduino;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;


public class MainActivity extends ActionBarActivity {
    Button button;
    EditText textVeld;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothSocket btSocket;
    private static final String macAdres = "98:D3:31:20:25:88";
    private static final UUID MY_UUID =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new VerstuurListener());
        textVeld = (EditText) findViewById(R.id.editText);

        if(bluetoothEnabled()){
            connect();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private boolean bluetoothEnabled() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (!mBluetoothAdapter.isEnabled()) {
            Toast.makeText(getApplicationContext(), "Bluetooth Disabled !",
                    Toast.LENGTH_SHORT).show();
                   /* It tests if the bluetooth is enabled or not, if not the app will show a message. */
            return false;
        }

        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(),
                    "Bluetooth null !", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        return true;
    }
    public void connect() {
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(macAdres);
        Log.d("", "Connecting to ... " + device);
        mBluetoothAdapter.cancelDiscovery();

        try {
            btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);

            btSocket.connect();
            Log.d("", "Connection made.");
        } catch (IOException e) {
            e.printStackTrace();
//            try {
//                btSocket.close();
//            } catch (IOException e2) {
//                Log.d("", "Unable to end the connection");
//                e2.printStackTrace();
//            }
            Log.d("", "Socket creation failed");
        }
    }
    private void writeData(String data) {
        OutputStream outStream = null;
        Log.d("SENDING:", data);

        try {
            outStream = btSocket.getOutputStream();
        } catch (IOException e) {
            Log.d("", "Bug BEFORE Sending stuff", e);
        }

        String message = data;
        byte[] msgBuffer = message.getBytes();

        try {
            outStream.write(msgBuffer);
        } catch (IOException e) {
            Log.d("", "Bug while sending stuff", e);
        }
    }

    private class VerstuurListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            writeData(textVeld.getText().toString()+"\n");
        }
    }
}
