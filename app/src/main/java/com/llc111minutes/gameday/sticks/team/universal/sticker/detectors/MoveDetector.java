package com.llc111minutes.gameday.sticks.team.universal.sticker.detectors;

import android.graphics.PointF;
import android.view.MotionEvent;

import static android.view.MotionEvent.ACTION_MOVE;

public class MoveDetector {
    private PointF startPoint = new PointF();
    private PointF focusPoint = new PointF();
    private PointF oldPoint = new PointF();
    private PointF diffPoint = new PointF();
    boolean isMove;
    private OnMoveListener moveListener;

    public MoveDetector(OnMoveListener moveListener) {
        this.moveListener = moveListener;
    }

    public boolean isMove() {
        return isMove;
    }

    public boolean onTouchEvent(MotionEvent event) {
        focusPoint.set(event.getX(), event.getY());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startPoint.set(focusPoint);
                oldPoint.set(focusPoint);
                isMove = true;
                if (moveListener != null) {
                    moveListener.onMoveBegin(this);
                }
                break;
            case ACTION_MOVE:
                diffPoint.set(focusPoint.x - oldPoint.x, focusPoint.y - oldPoint.y);
                oldPoint.set(focusPoint);
                focusPoint.set(event.getX(), event.getY());
                if (moveListener != null && isMove) {
                    moveListener.onMove(this);
                }
                break;
            case MotionEvent.ACTION_UP:
                isMove = false;
                if (moveListener != null) {
                    moveListener.onMoveEnd(this);
                }
                break;
        }

        return true;
    }


    public PointF getStartPoint() {
        return startPoint;
    }

    public PointF getFocusPoint() {
        return focusPoint;
    }

    public PointF getOldPoint() {
        return oldPoint;
    }

    public PointF getDiffPoint() {
        return diffPoint;
    }

    @Override
    public String toString() {
        return "MoveDetector{" +
                "difference=" + diffPoint +
                ", isMove=" + isMove +
                '}';
    }

    public interface OnMoveListener {
        boolean onMoveBegin(MoveDetector moveDetector);

        boolean onMove(MoveDetector moveDetector);

        void onMoveEnd(MoveDetector moveDetector);
    }
}
