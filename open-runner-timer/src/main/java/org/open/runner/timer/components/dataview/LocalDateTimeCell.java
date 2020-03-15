package org.open.runner.timer.components.dataview;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.TableCell;


public class LocalDateTimeCell<T> extends TableCell<T, LocalDateTime> {
	
	private DateTimeFormatter formatter = null;

    public LocalDateTimeCell(final String format) {    
    	 formatter = DateTimeFormatter.ofPattern(format == null ? "dd-MM-yyyy HH:mm:ss" : format);
    }

    @Override
    protected void updateItem(LocalDateTime item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText("");            
        } else {
            final String formatDateTime = item.format(formatter);        	
            setText(formatDateTime);
        }
    }
}