package com.secres.colormatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String currentColor = "";
    private String[] currentColors = new String[4];
    private Random rn;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView colorBg = findViewById(R.id.textView);
        TextView scoreLabel = findViewById(R.id.scoreLabel);
        Button[] buttons = new Button[4];
        buttons[0] = findViewById(R.id.topLeft);
        buttons[1] = findViewById(R.id.topRight);
        buttons[2] = findViewById(R.id.bottomLeft);
        buttons[3] = findViewById(R.id.bottomRight);

        Map<String, Integer> colorMap = initColorMap();
        List<String> keysAsArray = new ArrayList<>(colorMap.keySet());

        currentColor = "Green";
        colorBg.setBackgroundColor(colorMap.get(currentColor));
        scoreLabel.setTypeface(scoreLabel.getTypeface(), Typeface.BOLD);
        scoreLabel.setText("Score: " + score);

        for(Button i : buttons) {
            i.setOnClickListener(e -> {
                if(i.getText().equals(currentColor)) {
                    score++;
                    scoreLabel.setText("Score: " + score);

                    rn = new Random();
                    int range = buttons.length;
                    int randomNum = rn.nextInt(range) + 1;

                    currentColor = keysAsArray.get(rn.nextInt(keysAsArray.size()));
                    currentColors[randomNum - 1] = currentColor;
                    for(int j = 0; j < buttons.length; j++) {
                        if(buttons[randomNum - 1].equals(buttons[j])) {
                            buttons[randomNum - 1].setText(currentColor);
                        }
                        else {
                            checkFlavorExists(keysAsArray, buttons, j);
                        }
                    }

                    colorBg.setBackgroundColor(colorMap.get(currentColor));
                }
                else {
                    score--;
                    scoreLabel.setText("Score: " + score);
                }
            });
        }
    }

    private Map<String, Integer> initColorMap() {
        Map<String, Integer> colorMap = new LinkedHashMap<>();
        colorMap.put("Black", Color.argb(255, 0,0,0));
        colorMap.put("Gray", Color.argb(255, 128, 128, 128));
        colorMap.put("Red", Color.argb(255, 255, 0, 0));
        colorMap.put("Lime", Color.argb(255, 0, 255, 0));
        colorMap.put("Blue", Color.argb(255, 0, 0, 255));
        colorMap.put("Yellow", Color.argb(255, 255, 255, 0));
        colorMap.put("Cyan", Color.argb(255, 0, 255, 255));
        colorMap.put("Magenta", Color.argb(255, 255, 0, 255));
        colorMap.put("Silver", Color.argb(255, 192, 192, 192));
        colorMap.put("Maroon", Color.argb(255, 128, 0, 0));
        colorMap.put("Olive", Color.argb(255, 128, 128, 0));
        colorMap.put("Green", Color.argb(255, 0, 128, 0));
        colorMap.put("Purple", Color.argb(255, 128, 0, 128));
        colorMap.put("Teal", Color.argb(255, 0, 128, 128));
        colorMap.put("Navy", Color.argb(255, 0, 0, 128));

        return colorMap;
    }

    private void checkFlavorExists(List<String> keysAsArray, Button[] buttons, int j) {
        String flavor = keysAsArray.get(rn.nextInt(keysAsArray.size()));
        boolean contains = Arrays.asList(currentColors).contains(flavor);
        if(!contains) {
            buttons[j].setText(flavor);
            currentColors[j] = flavor;
        }
        else {
            checkFlavorExists(keysAsArray, buttons, j);
        }
    }

}