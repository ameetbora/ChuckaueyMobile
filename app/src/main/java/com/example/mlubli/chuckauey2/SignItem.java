package com.example.mlubli.chuckauey2;

public class SignItem {

    private int sImageResource;
    private String sImageID;
    private String sName;
    private String sColor1;
    private String sColor2;
    private String sShape;
    private String sDescription;





    public SignItem(int imageResource, String imageId, String name, String color1, String color2, String shape, String description){

       sImageResource = imageResource;
       sImageID = imageId;
       sName = name;
       sColor1 = color1;
       sColor2 = color2;
       sShape = shape;
       sDescription = description;

    }

    public int getImageResource (){
        return sImageResource;
    }

    public void setImageResource (int imageResource){

        sImageResource = imageResource;
    }

    public String getsImageID() {
        return sImageID;
    }

    public String getsName() {
        return sName;
    }

    public String getsColor1() {
        return sColor1;
    }

    public String getsColor2() {
        return sColor2;
    }

    public String getsShape() {
        return sShape;
    }

    public String getsDescription() {
        return sDescription;
    }
}
