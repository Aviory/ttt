package com.llc111minutes.gameday.sticks.team.universal.sticker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.events.Events;
import com.llc111minutes.gameday.interfaces.Callback;
import com.llc111minutes.gameday.model.enums.ContentType;
import com.llc111minutes.gameday.sticks.team.universal.sticker.detectors.MoveDetector;
import com.llc111minutes.gameday.sticks.team.universal.sticker.interfaces.ContainerInterface;
import com.llc111minutes.gameday.sticks.team.universal.sticker.interfaces.ContentListenerInterface;
import com.llc111minutes.gameday.sticks.team.universal.sticker.interfaces.ParentInterface;
import com.llc111minutes.gameday.sticks.team.universal.sticker.interfaces.SelectEventListener;
import com.llc111minutes.gameday.sticks.team.universal.sticker.utils.LogUtil;
import com.llc111minutes.gameday.util.GeometryUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class ParentView extends FrameLayout implements ParentInterface,
        MoveDetector.OnMoveListener {

    private ArrayList<ContainerInterface> containers;
    private ContainerInterface selected;
    private SelectEventListener selectEventListener;
    private ContentListenerInterface contentListener;

    private PointF coordinatesAddPoint = new PointF();
    private PointF coordinatesDelPoint = new PointF();
    private PointF coordinatesResizePoint = new PointF();

    protected Bitmap controlDelBitmap;
    protected Bitmap controlTransformBitmap;
    protected Bitmap controlPencilBitmap;
    protected Bitmap controlPlusBitmap;

    protected Bitmap overlayBitmap;
    protected int mOverlayColorFilter = Integer.MIN_VALUE;

    // My dimension
    private float controlSize;
    private float controlLineWidth;

    private double time;

    //private BorderRunnable borderRunnable;
    private boolean isDrawBorder = false;
    private boolean isRotate = false;
    private boolean isRotateEnd = true;
    private boolean isPreview = false;
    private boolean isActive = false;

    protected final Paint paintCircle = new Paint();
    protected final Paint paintBorder = new Paint();
    protected final Paint paintSelectedBorder = new Paint();

    float marginDott;
    float minContentWidth, minContentHeight;
    float[] dottedArray = new float[]{marginDott, marginDott};

    private MoveDetector moveDetector;

    private int borderColor = Color.WHITE;
    private int selectedBorderColor = Color.RED;

    private PointF differencePoint = new PointF();
    private float floatDiffAngle, floatDiffDistance;
    private Callback<Integer> changeContentCallback;
    private Callback<Boolean> changeParentViewStatusCallback;

    float centerX, centerY, startDiff, startScale, startX, startY;
    private RectF workSpace = new RectF();
    private int onBoundsTouchMargin = 30;
    float EPSILON = 6.0f;
    private float angleRounding = 45.0f;
    private float roundingBounder = 3.0f;
    private static final String VERTICAL = "VERTICAL";
    private static final String HORIZONTAL = "HORIZONTAL";
    private float guideLineRounding;
    PointF[] arrayCoordinatesStickerGuideLineV;
    PointF[] arrayCoordinatesStickerGuideLineH;
    PointF[] arrayCoordinatesGlobalGuideLineV;
    PointF[] arrayCoordinatesGlobalGuideLineH;
    private boolean isShowGlobalGuideLineV = false;
    private boolean isShowGlobalGuideLineH = false;
    private boolean isShowStickerGuideLineV = false;
    private boolean isShowStickerGuideLineH = false;

    public ParentView(Context context) {
        super(context);
        init();
    }

    public ParentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ParentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        paintBorder.setColor(borderColor);
    }

    public void setSelectedBorderColor(int selectedBorderColor) {
        this.selectedBorderColor = selectedBorderColor;
        paintSelectedBorder.setColor(selectedBorderColor);
    }

    protected void init() {
        //controlSize = getContext().getResources().getDimensionPixelSize(R.dimen.circle_radius);
        //paintBorder.setStrokeWidth(getResources().getDimensionPixelSize(R.dimen.dip_width));
        paintBorder.setStyle(Paint.Style.STROKE);
        paintBorder.setColor(borderColor);

        //paintSelectedBorder.setStrokeWidth(getResources().getDimensionPixelSize(R.dimen.dip_width));
        paintSelectedBorder.setStyle(Paint.Style.STROKE);
        paintSelectedBorder.setColor(selectedBorderColor);

        paintCircle.setColor(Color.DKGRAY);
        paintCircle.setAlpha(128);

        controlTransformBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_control_transform);
        controlPencilBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_control_edit);
        controlPlusBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_control_plus);
        controlDelBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_control_cancel);

        containers = new ArrayList<>();
        moveDetector = new MoveDetector(this);
    }

    public void setSelectEventListener(SelectEventListener selectEventListener) {
        this.selectEventListener = selectEventListener;
    }

    @Override
    public ArrayList<ContainerInterface> getContainers() {
        return containers;
    }

    @Override
    public ContainerInterface getSelectedContainer() {
        return selected;
    }

    @Override
    public ContentListenerInterface getChangeContentInterface() {
        return contentListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //LogUtil.info(this, "Rotate: "+isRotate);
        if (isRotate) return true;
        moveDetector.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        for (ContainerInterface container : containers) {
            container.release();
        }
        super.onDetachedFromWindow();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void addContainer(@NonNull ContainerInterface containerInterface) {
        containers.add(containerInterface);
        containerInterface.setParent(this);
        containerInterface.init(getContext());
        notifyView();
        if (changeContentCallback != null) {
            changeContentCallback.onResult(containers.size());
        }
    }

    public void clear() {
        selected = null;
        containers.clear();
        setBackgroundResource(R.color.transparent);
        overlayBitmap = null;
        notifyView();
        if (changeContentCallback != null) {
            changeContentCallback.onResult(containers.size());
        }
    }

    public void setPreview(boolean preview) {
        isPreview = preview;
    }

    @Override
    public ContainerInterface findEntityAtPoint(float x, float y) {
        for (ContainerInterface containerInterface : containers) {
            if (containerInterface.contains(x, y)) {
                LogUtil.info("Find: " + containerInterface);
                return containerInterface;
            }
        }
        LogUtil.info("Not found");
        return null;
    }

    @Override
    public void notifyView() {
        invalidate();
    }

    @Override
    public void setCoordinatesResizePoint(float x, float y) {
        coordinatesResizePoint.set(x, y);
    }

    @Override
    public void setCoordinatesDelPoint(float x, float y) {
        coordinatesDelPoint.set(x, y);
    }

    @Override
    public void setCoordinatesAddPoint(float x, float y) {
        coordinatesAddPoint.set(x, y);
    }

    @Override
    public int getColorForFilter() {
        return mOverlayColorFilter;
    }

    @Override
    public void setColorForFilter(int color) {
        this.mOverlayColorFilter = color;
    }

    @Override
    public float getMarginControl() {
        return (float) (controlSize * 0.3);
    }

    @Override
    public float getMinContainerWidth() {
        return minContentWidth;
    }

    @Override
    public float getMinContainerHeight() {
        return minContentHeight;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public PointF getCoordinatesResizePoint() {
        return coordinatesResizePoint;
    }

    public PointF getCoordinatesDelPoint() {
        return coordinatesDelPoint;
    }

    public PointF getCoordinatesAddPoint() {
        return coordinatesAddPoint;
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        drawAllStickers(canvas);
        if (selected != null && !isPreview) {
            drawControls(canvas);
        }
        drawParentBorder(canvas);
        // draw global guide line
        drawGuideLines(canvas, isShowGlobalGuideLineV, isShowGlobalGuideLineH,
                arrayCoordinatesGlobalGuideLineV, arrayCoordinatesGlobalGuideLineH, 3);
        //draw sticker guide line relative to other stickers
        drawGuideLines(canvas, isShowStickerGuideLineV, isShowStickerGuideLineH,
                arrayCoordinatesStickerGuideLineV, arrayCoordinatesStickerGuideLineH, 1);
    }

    private void drawGuideLines(Canvas canvas, boolean isShowVerticalLine, boolean isShowHorizontalLine,
                                PointF[] lineCoordinatesV, PointF[] lineCoordinatesH, int strokeWidth) {
        Paint paint = new Paint();
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(Color.CYAN);
        //draw vertical guides line
        if (isShowVerticalLine) {
            PointF startLine = lineCoordinatesV[0];
            PointF endLine = lineCoordinatesV[1];
            canvas.drawLine(startLine.x, startLine.y, endLine.x, endLine.y, paint);
        }
        //draw horizontal guides line
        if (isShowHorizontalLine) {
            PointF startLine = lineCoordinatesH[0];
            PointF endLine = lineCoordinatesH[1];
            canvas.drawLine(startLine.x, startLine.y, endLine.x, endLine.y, paint);
        }
    }

    @Override
    public void drawParentBorder(Canvas canvas) {
        if (isPreview) return;
        float oldWidth = 1;
        if (isActive) {
            oldWidth = paintSelectedBorder.getStrokeWidth();
            paintSelectedBorder.setStrokeWidth(oldWidth * 4);
            drawPointOnRect(canvas, paintSelectedBorder, getLeft(), getTop(), getRight(), getBottom());
            canvas.drawRect(getLeft(), getTop(), getRight(), getBottom(), paintSelectedBorder);
            paintSelectedBorder.setStrokeWidth(oldWidth);
        } else {
            oldWidth = paintBorder.getStrokeWidth();
            paintBorder.setStrokeWidth(oldWidth * 4);
            drawPointOnRect(canvas, paintBorder, getLeft(), getTop(), getRight(), getBottom());
            canvas.drawRect(getLeft(), getTop(), getRight(), getBottom(), paintBorder);
            paintBorder.setStrokeWidth(oldWidth);
        }
    }

    @Override
    public void setActiveContainer(ContainerInterface activeContainer) {
        if (selected != null) {
            selected.setActive(false);
            selected.setIsSelected(false);
            selected.setMotion(false);
        }
        selected = activeContainer;
        selected.setActive(true);
        notifyView();
    }

    protected void drawPointOnRect(@NonNull Canvas canvas, @NonNull Paint paint,
                                   float left, float top, float right, float bottom) {
        canvas.drawPoint(left, top, paint);
        canvas.drawPoint(right, top, paint);
        canvas.drawPoint(right, bottom, paint);
        canvas.drawPoint(left, bottom, paint);
    }

    private void drawControls(Canvas canvas) {
        RectF rectF = new RectF(coordinatesResizePoint.x - controlSize / 2, coordinatesResizePoint.y - controlSize / 2,
                coordinatesResizePoint.x + controlSize / 2, coordinatesResizePoint.y + controlSize / 2);
        canvas.drawBitmap(controlTransformBitmap, null, rectF, null);

        rectF.set(coordinatesDelPoint.x - controlSize / 2, coordinatesDelPoint.y - controlSize / 2,
                coordinatesDelPoint.x + controlSize / 2, coordinatesDelPoint.y + controlSize / 2);
        canvas.save();
        canvas.rotate(selected.getRotate(), coordinatesDelPoint.x, coordinatesDelPoint.y);
        canvas.drawBitmap(controlDelBitmap, null, rectF, null);
        canvas.restore();

        rectF.set(coordinatesAddPoint.x - controlSize / 2, coordinatesAddPoint.y - controlSize / 2,
                coordinatesAddPoint.x + controlSize / 2, coordinatesAddPoint.y + controlSize / 2);
        canvas.save();
        canvas.rotate(selected.getRotate(), coordinatesAddPoint.x, coordinatesAddPoint.y);
        if (selected.getContent().isDefault()) {
            canvas.drawBitmap(controlPlusBitmap, null, rectF, null);
        } else {
            canvas.drawBitmap(controlPencilBitmap, null, rectF, null);
        }
        canvas.restore();
    }

    @Override
    public boolean onMoveBegin(MoveDetector detector) {
        if (selected != null) {
            if (isCircle(detector.getFocusPoint().x, detector.getFocusPoint().y,
                    coordinatesResizePoint.x, coordinatesResizePoint.y)) {
                LogUtil.info(this, "ResizePoint touch");
                selected.setIsSelected(true);

                centerX = selected.absoluteCenterX();
                centerY = selected.absoluteCenterY();

                startX = detector.getFocusPoint().x - coordinatesResizePoint.x + centerX;
                startY = detector.getFocusPoint().y - coordinatesResizePoint.y + centerY;

                startDiff = (float) Math.hypot(detector.getFocusPoint().x - startX, detector.getFocusPoint().y - startY);

                startScale = selected.getScale();

                differencePoint.set(detector.getStartPoint().x - coordinatesResizePoint.x,
                        detector.getStartPoint().y - coordinatesResizePoint.y);

                floatDiffAngle = GeometryUtils.getAngle(selected.absoluteCenter(),
                        detector.getFocusPoint().x - differencePoint.x,
                        detector.getFocusPoint().y - differencePoint.y) + selected.getRotate();
                return true;
            } else {
                selected.setIsSelected(false);
            }

            if (isCircle(detector.getFocusPoint().x, detector.getFocusPoint().y,
                    coordinatesDelPoint.x, coordinatesDelPoint.y)) {
                LogUtil.info(this, "DelPoint touch");
                containers.remove(selected);
                if (selectEventListener != null) {
                    selectEventListener.unSelected(selected);
                }
                selected = null;
                notifyView();
                return true;
            }

            if (isCircle(detector.getFocusPoint().x, detector.getFocusPoint().y,
                    coordinatesAddPoint.x, coordinatesAddPoint.y)) {
                LogUtil.info(this, "AddPoint touch" + selected.getContent().getClass().getSimpleName());
                selectEventListener.showDialog(selected);
                return true;
            }
        }

        ContainerInterface sticker =
                findEntityAtPoint(detector.getFocusPoint().x, detector.getFocusPoint().y);
        if (sticker != null) {
            setActive(false);
            sticker.setMotion(true);
            if (System.currentTimeMillis() - time < 500 && sticker == selected && !isRotate) {
                selectEventListener.showDialogEdit(selected);
                return true;
            }
            time = System.currentTimeMillis();
            if (selected == sticker) {
                selected.onTap(getContext());
                selectEventListener.onSelected(sticker);
            } else {
                if (selected != null) {
                    selected.setActive(false);
                }
                containers.remove(sticker);
                containers.add(sticker);
                sticker.setActive(true);
            }

            if (selected != null && selected != sticker) {
                if (selectEventListener != null) {
                    selectEventListener.reSelected(sticker);
                }
                selected.setIsSelected(false);
                selected = null;
            }

            if (selected == null || (selected != null && selected != sticker)) {
                if (selectEventListener != null) {
                    selectEventListener.onSelected(sticker);
                }
            }

            selected = sticker;
            selected.invalidate();
            notifyView();
        } else {
            if (selected != null) {
                selected.setMotion(false);
                selected.setActive(false);
                selected.invalidate();
                selectEventListener.unSelected(selected);
                selected = null;
            }
            setActive(true);
            notifyView();
        }
        return true;
    }

    @Override
    public boolean onMove(MoveDetector detector) {
        if (isRotateEnd) {
            isRotateEnd = false;
            return false;
        }
        if (selected == null || isRotate) {
            return false;
        }

        if (selected.isSelected()) {

            float angle = GeometryUtils.getAngle(selected.absoluteCenter(),
                    detector.getFocusPoint().x - differencePoint.x,
                    detector.getFocusPoint().y - differencePoint.y);

            float mAngle = floatDiffAngle - angle;
            selected.setRotate(mAngle);

            handleRotateSticker();

            floatDiffDistance = selected.getDiffDiagonal(detector.getFocusPoint().x - differencePoint.x,
                    detector.getFocusPoint().y - differencePoint.y);

            float newDiff = (float) Math.hypot(detector.getFocusPoint().x - startX, detector.getFocusPoint().y - startY);
            float newScale = (newDiff / startDiff) * startScale;

            selected.setScale(newScale);

            selectionSizeSticker();

            selected.invalidate();

        } else if (selected.isMotion()) {
            if (!workSpace.contains(detector.getFocusPoint().x, detector.getFocusPoint().y))
                return true;
            selected.offsetTo(selected.getX() + detector.getDiffPoint().x,
                    selected.getY() + detector.getDiffPoint().y);
            hideAllGuideLines();
            handleGlobalGuideLinesDisplaying();
            handleStickersGuideLinesDisplaying();
            selected.invalidate();
        }
        notifyView();

        return true;
    }

    @Override
    public void onMoveEnd(MoveDetector detector) {
        if (selected != null && selected.isSelected()) {
            selected.setIsSelected(false);
        }
        hideAllGuideLines();
        if (selected != null) selected.invalidate();
        notifyView();
    }

    /**
     * Displaying stickers guide line relative to another stickers center
     */
    private void handleStickersGuideLinesDisplaying() {
        if (containers.size() != 0) {
            for (int i = 0; i < containers.size(); i++) {
                if (selected != containers.get(i)) {
                    // show vertical stickers guide line
                    if (selected.absoluteCenterX() >= containers.get(i).absoluteCenterX() - EPSILON
                            && selected.absoluteCenterX() <= containers.get(i).absoluteCenterX() + EPSILON) {
                        arrayCoordinatesStickerGuideLineV = getArrayCoordinatesGuideLine(containers.get(i).absoluteCenterX(), VERTICAL);
                        isShowStickerGuideLineV = true;
                        float offset = containers.get(i).absoluteCenterX() - selected.getWidth() / 2;
                        selected.setX(offset);
                    }
                    // show horizontal stickers guide line
                    if (selected.absoluteCenterY() >= containers.get(i).absoluteCenterY() - EPSILON
                            && selected.absoluteCenterY() <= containers.get(i).absoluteCenterY() + EPSILON) {
                        arrayCoordinatesStickerGuideLineH = getArrayCoordinatesGuideLine(containers.get(i).absoluteCenterY(), HORIZONTAL);
                        isShowStickerGuideLineH = true;
                        float offset = containers.get(i).absoluteCenterY() - selected.getHeight() / 2;
                        selected.setY(offset);
                    }
                }
            }
        }
    }

    private void handleGlobalGuideLinesDisplaying() {
        // show vertical global guide line
        showGuideLine(selected.absoluteCenterX(), guideLineRounding, VERTICAL);
        // show horizontal global guide line
        showGuideLine(selected.absoluteCenterY(), guideLineRounding, HORIZONTAL);
    }

    /**
     * Displaying global guide lines
     *
     * @param centerSticker  - center of sticker(x or y coordinates)
     * @param lineCoordinate - coordinate of guide line(x or y)
     * @param direction      - guide line direction
     */
    private void showGuideLine(float centerSticker, float lineCoordinate, String direction) {
        switch (direction) {
            case VERTICAL:
                float nearestGuideLineX = Math.round(centerSticker / lineCoordinate) * lineCoordinate;
                if (nearestGuideLineX == 0 || nearestGuideLineX == getWidth()) return;
                float distanceDifferenceX = nearestGuideLineX - centerSticker;
                if (distanceDifferenceX >= -EPSILON && distanceDifferenceX <= EPSILON) {
                    arrayCoordinatesGlobalGuideLineV = getArrayCoordinatesGuideLine(nearestGuideLineX, VERTICAL);
                    isShowGlobalGuideLineV = true;
                    selected.setX(nearestGuideLineX - selected.getWidth() / 2);
                }
                break;
            case HORIZONTAL:
                float nearestGuideLineY = Math.round(centerSticker / lineCoordinate) * lineCoordinate;
                if (nearestGuideLineY == 0 || nearestGuideLineY == getWidth()) return;
                float distanceDifferenceY = nearestGuideLineY - centerSticker;
                if (distanceDifferenceY >= -EPSILON && distanceDifferenceY <= EPSILON) {
                    arrayCoordinatesGlobalGuideLineH = getArrayCoordinatesGuideLine(nearestGuideLineY, HORIZONTAL);
                    isShowGlobalGuideLineH = true;
                    selected.setY(nearestGuideLineY - selected.getHeight() / 2);
                }
                break;
        }
    }

    /**
     * Filling an array of coordinates of guide line to indicate the direction of the center of the
     * sticker (horizontal or vertical)
     *
     * @param center    - center sticker at the level of which will be drawn guide line
     * @param direction - guide lines direction
     */
    private PointF[] getArrayCoordinatesGuideLine(float center, String direction) {
        PointF[] pointCenterStickerLine = new PointF[2];
        switch (direction) {
            case VERTICAL:
                pointCenterStickerLine = fillArrayByCoordinates(center, 0, center, getWidth());
                break;
            case HORIZONTAL:
                pointCenterStickerLine = fillArrayByCoordinates(0, center, getWidth(), center);
                break;
        }
        return pointCenterStickerLine;
    }

    /**
     * Filling an array of start and ends coordinate of guide line
     *
     * @param startX - x coordinate of start line
     * @param startY - y coordinate of start line
     * @param endX   - x coordinate of end line
     * @param endY   - y coordinate of end line
     * @return - array of coordinates
     */
    private PointF[] fillArrayByCoordinates(float startX, float startY, float endX, float endY) {
        PointF[] lineCoordinates = new PointF[2];
        PointF startLine = new PointF(startX, startY);
        PointF endLine = new PointF(endX, endY);
        lineCoordinates[0] = startLine;
        lineCoordinates[1] = endLine;
        return lineCoordinates;
    }

    /**
     * Stick sticker every 45 degrees
     */
    private void handleRotateSticker() {
        float angel = selected.getRotate();
        float nearestAngel = Math.round(angel / angleRounding) * angleRounding;
        float degreesDifference = nearestAngel - angel;
        if (degreesDifference >= -roundingBounder && degreesDifference <= roundingBounder) {
            selected.setRotate(nearestAngel);
        }
    }

    private void hideAllGuideLines() {
        isShowStickerGuideLineV = false;
        isShowStickerGuideLineH = false;
        isShowGlobalGuideLineV = false;
        isShowGlobalGuideLineH = false;
    }

    /**
     * Align the scale of the sticker based on the scale of the nearest sticker
     */
    private void selectionSizeSticker() {
        if (selected.getContent().getType() != ContentType.TEAM1LOGO &&
                selected.getContent().getType() != ContentType.TEAM2LOGO) return;
        for (int i = 0; i < containers.size(); i++) {
            if (selected != containers.get(i)) {
                if (containers.get(i).getContent().getType() == ContentType.TEAM1LOGO ||
                        containers.get(i).getContent().getType() == ContentType.TEAM2LOGO) {
                    if (Math.abs(selected.getScale() - containers.get(i).getScale()) <= selected.getScale() * 0.05f &&
                            selected.getScale() != containers.get(i).getScale()) {
                        selected.setScale(containers.get(i).getScale());
                        return;
                    }
                }
            }
        }
    }

    public void setActive(boolean active) {
        if (isActive == active) return;
        isActive = active;
        if (changeParentViewStatusCallback != null) {
            changeParentViewStatusCallback.onResult(active);
        }
        notifyView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Events.ChangeParentViewStatus parentViewStatus) {
        setActive(parentViewStatus.isStatus());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            controlSize = getWidth() / 16;
            controlLineWidth = getWidth() / 108;
            paintBorder.setStrokeWidth(getWidth() / 500);
            paintSelectedBorder.setStrokeWidth(getWidth() / 400);
            paintBorder.setAntiAlias(true);
            paintSelectedBorder.setAntiAlias(true);

            marginDott = getWidth() / 80;

            dottedArray[0] = marginDott;
            dottedArray[1] = marginDott;

            PathEffect pe1 = new DashPathEffect(dottedArray, 0);
            PathEffect pe2 = new CornerPathEffect(marginDott / 3);
            PathEffect pe3 = new ComposePathEffect(pe1, pe2);

            paintSelectedBorder.setPathEffect(pe2);
            paintBorder.setPathEffect(pe3);
            //paintSelectedBorder.setPathEffect(pe1);

            minContentWidth = getWidth() / 18;
            minContentHeight = getHeight() / 20;

            workSpace.left = left + onBoundsTouchMargin;
            workSpace.top = top + onBoundsTouchMargin;
            workSpace.right = right - onBoundsTouchMargin;
            workSpace.bottom = bottom - onBoundsTouchMargin;

            guideLineRounding = getWidth() * 0.25f;
        }
    }

    /**
     * The method checks whether the point
     *
     * @param touchX   - X coordinate of depression
     * @param touchY   - Y coordinate of depression
     * @param controlX - X coordinate of the control point
     * @param controlY - Y coordinate of the control point
     * @return - True if pressing in the side-points, otherwise false
     */
    private boolean isCircle(float touchX, float touchY, float controlX, float controlY) {
        float dX = touchX - controlX;
        float dY = touchY - controlY;
        return Math.sqrt(dX * dX + dY * dY) <= controlSize;
    }

    private boolean isCircle(PointF point) {
        /*
        pA x1,y1    pB x2, y2
        *--------*
        /        *
        /        *
        *--------*

*/        // "Нормализуем" положение точки относительно центра круга
        float dx = point.x - selected.getRectCoordinate().getX3();
        float dy = point.y - selected.getRectCoordinate().getY3();

        return Math.sqrt(dx * dx + dy * dy) <= controlSize;
    }

    /**
     * draws all entities on the canvas
     *
     * @param canvas Canvas where to draw all entities
     */
    private void drawAllStickers(Canvas canvas) {
        for (ContainerInterface containerInterface : containers) {
            if (selected == containerInterface) {
                if (!isPreview) {
                    containerInterface.draw(canvas, paintSelectedBorder);
                } else {
                    containerInterface.draw(canvas, null);
                }
            } else {
                if (!isPreview) {
                    containerInterface.draw(canvas, paintBorder);
                } else {
                    containerInterface.draw(canvas, null);
                }
            }
        }
    }

    public void setContentListener(ContentListenerInterface contentListener) {
        this.contentListener = contentListener;
    }

    public ContentListenerInterface getContentListener() {
        return contentListener;
    }

    private float getDegree(PointF rect, MotionEvent event) {
        return rect != null ? getDegree(rect.x, rect.y, event.getX(), event.getY()) : 0;
    }

    private float getDegree(float x1, float y1, float x2, float y2) {
        double delta_x = (x1 - x2);
        double delta_y = (y1 - y2);
        double radians = Math.atan2(delta_y, delta_x);//
        double degrees = Math.toDegrees(radians);
        return (float) degrees;
    }

    public Bitmap getOverlayBitmap() {
        return overlayBitmap;
    }

    public void setOverlayBitmap(Bitmap overlayBitmap) {
        this.overlayBitmap = overlayBitmap;
    }

    public void setChangeContentCallback(Callback<Integer> changeContentCallback) {
        this.changeContentCallback = changeContentCallback;
    }

    public void setChangeParentViewStatusCallback(Callback<Boolean> changeParentViewStatusCallback) {
        this.changeParentViewStatusCallback = changeParentViewStatusCallback;
    }
}
