package org.prezdev.remotetorch;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private FlashlightProvider flashlightProvider;
    private ImageView torchImage;

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
                boolean isLightOn = flashlightProvider.toggleLight();
                if(isLightOn){
                    torchImage.setColorFilter(
                        getColor(R.color.lightOn),
                        PorterDuff.Mode.SRC_IN
                    );
                }else{
                    torchImage.setColorFilter(getColor(
                        R.color.lightOff),
                        PorterDuff.Mode.SRC_IN
                    );
                }
            }
        });
    }
}
