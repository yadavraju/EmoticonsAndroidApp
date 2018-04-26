package com.raju.emogi.data.model;

import java.util.HashMap;

/**
 * Created by Raju Yadav
 */

public class EmogiContent {
    public HashMap<String, EmogiContentValue> dataValues;

    public EmogiContent() {
        this.dataValues = new HashMap<>();
    }
}
