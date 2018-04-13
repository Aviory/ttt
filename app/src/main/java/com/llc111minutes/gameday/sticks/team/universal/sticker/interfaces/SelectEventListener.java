package com.llc111minutes.gameday.sticks.team.universal.sticker.interfaces;

/**
 * Created by Yurii on 2/14/17.
 */

public interface SelectEventListener {
    void onSelected(ContainerInterface container);
    void reSelected(ContainerInterface container);
    void unSelected(ContainerInterface container);
    void showDialog(ContainerInterface selected);

    void showDialogEdit(ContainerInterface selected);
}
