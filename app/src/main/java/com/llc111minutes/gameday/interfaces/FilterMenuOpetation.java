package com.llc111minutes.gameday.interfaces;

import android.graphics.Bitmap;
import android.widget.SeekBar;

import com.llc111minutes.gameday.model.Overlay;
import com.llc111minutes.gameday.model.Pack;
import com.llc111minutes.gameday.model.Template;
import com.llc111minutes.gameday.model.enums.FILTER;
import com.llc111minutes.gameday.sticks.team.universal.sticker.ParentView;
import com.llc111minutes.gameday.util.FilterUtil;

public interface FilterMenuOpetation {
    void appFilter(FilterUtil.FILTER filter);
    void addFilter(FILTER filter);
    boolean isFilterAdded(FILTER filter);
    FILTER getFilter(FILTER filter);
    void removeFilter(FILTER filter);
    void notifyFilters();
    void subscribeSeekBarChange(SeekBar seekBar, FILTER filter);
    void unsubscribeSeekBarChange();
    void setTemplate(Template template);
    Template getTemplate();
    Bitmap getSourceBitmap();
    Bitmap getBitmapWithBigFilter();
    void clearAll();
    void eventAddCustomContainer();
    Pack getPack();
    ParentView getParentView();
    void setTextToTitle(String text);
    int getOverlayId();
    void setOverlay(Overlay overlay);
    void setOverlayColorFilter(int color);
    int getOverlayColorFilter();
}