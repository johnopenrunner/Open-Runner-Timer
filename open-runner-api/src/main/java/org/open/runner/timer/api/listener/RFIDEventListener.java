package org.open.runner.timer.api.listener;

import org.open.runner.timer.api.model.RFIDEvent;
import org.open.runner.timer.api.model.RFIDTagData;
import org.open.runner.timer.api.model.TagEvent;

public interface RFIDEventListener {

	/**
	 * Process the read Tag event.
	 * @param eventType the type of event raised.
	 */
	void processTagEvent(final TagEvent tagEvent, final RFIDTagData tagData);
	/**
	 * Porcess status events.
	 * @param eventType
	 */
	void processStatusEvent(final RFIDEvent eventType);
}
