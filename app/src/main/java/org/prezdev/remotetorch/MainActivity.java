package org.prezdev.remotetorch;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {
    private FlashlightProvider flashlightProvider;
    private ImageView torchImage;
    private InetAddress address;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        torchImage = findViewById(R.id.torchImage);
        flashlightProvider = new FlashlightProvider(getApplicationContext());

        torchImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Client client = new Client();
                client.start();
                try {
                    client.connect(5000, address.getHostAddress(), 54555, 54777);
                    client.sendTCP("hola");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);




    }

    public void onClickListener(View v){
        switch (v.getId()){
            case R.id.startServerButton:{
                Server server = new Server();
                server.start();
                try {
                    server.bind(54555, 54777);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                server.addListener(new Listener(){
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    public void received (Connection connection, Object object) {
                        if (object instanceof String) {

                            boolean isLightOn = flashlightProvider.toggleLight();
                            if(isLightOn){
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    torchImage.setColorFilter(
                                            getColor(R.color.lightOn),
                                            PorterDuff.Mode.SRC_IN
                                    );
                                }
                            }else{
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    torchImage.setColorFilter(getColor(
                                            R.color.lightOff),
                                            PorterDuff.Mode.SRC_IN
                                    );
                                }
                            }

                        }
                    }
                });
                break;
            }

            case R.id.discoverButton: {
                Client client = new Client();
                address = client.discoverHost(54777, 5000);
                System.out.println("ADDRESS!!!: "+ address);
                break;
            }
        }
    }
}
