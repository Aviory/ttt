package com.llc111minutes.gameday.events;

import com.llc111minutes.gameday.model.Template;

/**
 * Created by Yurii on 3/27/17.
 */

public class Events {
    public static class ClearTemplate {}

    public static class ActionSetTemplate {
        private Template template;

        public ActionSetTemplate(Template template) {
            this.template = template;
        }

        public Template getTemplate() {
            return template;
        }

        public void setTemplate(Template template) {
            this.template = template;
        }
    }

    public static class ChangeContentEvent{
        private int size;

        public ChangeContentEvent(int size) {
            this.size = size;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

    public static class ChangeParentViewStatus {
        private boolean status;

        public ChangeParentViewStatus(boolean status) {
            this.status = status;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }
    }
}