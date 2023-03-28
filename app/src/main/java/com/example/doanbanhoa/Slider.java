package com.example.doanbanhoa;

import com.denzcoskun.imageslider.models.SlideModel;

import java.util.List;

public class Slider {
    private List<SlideModel> imageSlider;

    public Slider(List<SlideModel> imageSlider) {
        this.imageSlider = imageSlider;
    }

    public List<SlideModel> getImageSlider() {
        return imageSlider;
    }

    public void setImageSlider(List<SlideModel> imageSlider) {
        this.imageSlider = imageSlider;
    }
}
