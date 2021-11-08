package com.example.bottomapp.helpers;

public class AddBeatHelper {

    public String checkBeat (boolean beat, String beatTitle, boolean cover) {

        if (beat) { return "Upload mp3";}
        else if (beatTitle.isEmpty()) {return "Write Title";}
        else if (cover) { return "Upload Cover";}
        else { return "OK";}

    }
}
