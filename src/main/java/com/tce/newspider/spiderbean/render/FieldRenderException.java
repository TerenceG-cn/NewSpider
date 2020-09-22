package com.tce.newspider.spiderbean.render;


import java.lang.reflect.Field;

/**
 * 注入某个FIELD异常
 */
public class FieldRenderException extends Exception{
        private Field field;

        public FieldRenderException(Field field, String message) {
            super(message);
            this.field = field;
        }

        public FieldRenderException(Field field, String message, Throwable cause) {
            super(message, cause);
            this.field = field;
        }

        public Field getField() {
            return field;
        }

        public void setField(Field field) {
            this.field = field;
        }
}
