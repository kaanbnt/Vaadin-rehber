package com.vaadin;

import java.awt.*;
import com.vaadin.ui.TextField;

public class KullaniciTextField extends TextField {

    public KullaniciTextField(){
        setInputPrompt("Input prompt");
        setNullRepresentation("Null Rep.");
    }
}
