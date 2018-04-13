package com.llc111minutes.gameday.sticks.team.universal.sticker;

/**
 * Created by Yurii on 2/3/17.
 */

public class RectCoordinate {

    private float x1, y1;
    private float x2, y2;
    private float x3, y3;
    private float x4, y4;
    private double Rad, TAng, RAng;

    public void init(int with, int height, float rotate, float centerX, float centerY) {
        Rad  =  Math.sqrt(Math.pow(with*0.5d, 2) + Math.pow(height*0.5d, 2));
        TAng = Math.asin(height/(2*Rad));
        RAng = Math.toRadians(rotate)*0.0174532925D; /*rotate*0.0174532925;*/

        x1 = (float) (centerX + Math.cos(RAng + Math.PI + TAng)*Rad);
        y1 = (float) (centerY + Math.sin(RAng + Math.PI + TAng)*Rad);

        //X2 := X + Cos(RAng - TAng)*Rad;
        //Y2 := Y + Sin(RAng - TAng)*Rad;

        x2 = (float) (centerX + Math.cos(RAng - TAng)*Rad);
        y2 = (float) (centerY + Math.sin(RAng - TAng)*Rad);

        /*X3 := X + Cos(RAng + TAng)*Rad;
        Y3 := Y + Sin(RAng + TAng)*Rad;
*/
        x3 = (float) (centerX + Math.cos(RAng + TAng) * Rad);
        y3 = (float) (centerY + Math.sin(RAng + TAng) * Rad);

        /*X4 := X + Cos(RAng + 3.1415 - TAng)*Rad;
        Y4 := Y + Sin(RAng + 3.1415 - TAng)*Rad;*/

        x4 = (float) (centerX + Math.cos(RAng + Math.PI + TAng)*Rad);
        y4 = (float) (centerY + Math.sin(RAng + Math.PI - TAng) * Rad);
    }

    public float getX1() {
        return x1;
    }

    public float getY1() {
        return y1;
    }

    public float getX2() {
        return x2;
    }

    public float getY2() {
        return y2;
    }

    public float getX3() {
        return x3;
    }

    public float getY3() {
        return y3;
    }

    public float getX4() {
        return x4;
    }

    public float getY4() {
        return y4;
    }

    public void setX1(float x1) {
        this.x1 = x1;
    }

    public void setY1(float y1) {
        this.y1 = y1;
    }

    public void setX2(float x2) {
        this.x2 = x2;
    }

    public void setY2(float y2) {
        this.y2 = y2;
    }

    public void setX3(float x3) {
        this.x3 = x3;
    }

    public void setY3(float y3) {
        this.y3 = y3;
    }

    public void setX4(float x4) {
        this.x4 = x4;
    }

    public void setY4(float y4) {
        this.y4 = y4;
    }
}
