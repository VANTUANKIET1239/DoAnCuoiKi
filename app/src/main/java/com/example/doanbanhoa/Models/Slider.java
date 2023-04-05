package com.example.doanbanhoa.Models;

import com.denzcoskun.imageslider.models.SlideModel;

import java.util.List;

public class Slider {
    private SlideModel imageSlider;

    public Slider(SlideModel imageSlider) {
        this.imageSlider = imageSlider;
    }
    public Slider() {
        this.imageSlider = null;
    }

    public SlideModel getImageSlider() {
        return imageSlider;
    }

    public void setImageSlider(SlideModel imageSlider) {
        this.imageSlider = imageSlider;
    }
}
