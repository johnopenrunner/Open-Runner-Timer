package org.open.runner.timer.api;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.Properties;

import org.open.runner.timer.api.exceptions.DataSourceException;
import org.open.runner.timer.api.listener.RFIDEventListener;
import org.open.runner.timer.api.model.EventType;
import org.open.runner.timer.api.model.TagField;

/**
 * 
 * RFID Reader Data Source Interface.
 *
 */
public interface RFIDDataSource {
	/**
	 * Connect to the RFID reader.
	 * 
	 * @param hostname the RFID Device hostname.
	 * @return true on success or false otherwise
	 */
	boolean connect(final InetAddress hostname) throws DataSourceException;

	/**
	 * Login to the RFID reader for management operations.
	 * 
	 * @param hostname the RFID reader hostname.
	 * @param username the admin user name.
	 * @param password the admin password.
	 * @return true on success or false otherwise
	 */
	boolean login(final InetAddress hostname, String username, final String password) throws DataSourceException;

	/**
	 * Disconnect from the reader.
	 * 
	 * @return true on success or false otherwise
	 */
	boolean disconnect() throws DataSourceException;

	/**
	 * Reconnect to the reader.
	 * 
	 * @return true on success or false otherwise
	 */
	boolean reconnect() throws DataSourceException;

	/**
	 * Logout from the reader.
	 * 
	 * @return true on success or false otherwise
	 */
	boolean logout() throws DataSourceException;

	/**
	 * Start sending events.
	 * 
	 * @throws DataSourceException
	 */
	void start() throws DataSourceException;

	/**
	 * Stop sending events.
	 * 
	 * @throws DataSourceException
	 */
	void stop() throws DataSourceException;

	/**
	 * Registers interest in being notified for the event type.
	 * 
	 * @param eventType the event type.
	 */
	void registerNotifyEventType(final EventType eventType);

	/**
	 * Synchronise the current date and time of the reader.
	 * 
	 * @param dateTime the current data and time.
	 */
	void synchroniseDateTime(LocalDateTime dateTime) throws DataSourceException;

	/**
	 * Gets the number of supported antennae.
	 * 
	 * @return the number of supported antennae.
	 */
	int getSupportedAntennae() throws DataSourceException;

	/**
	 * Checks to see if the anntenna is connected.
	 * 
	 * @return true if connected, otherwise false.
	 */
	boolean isAntennaConnected(final int antennaID) throws DataSourceException;

	/**
	 * gets the configuration for antenna identified by the antennaID.
	 * 
	 * @param antennaID the antenna ID.
	 * @return configuration of the antennae.
	 */
	void getAnntennaConfig(final short antennaID);

	/**
	 * Sets the reader properties.
	 * 
	 * @param properties the properties to update.
	 */
	void setReaderProperties(final Properties properties) throws DataSourceException;

	/**
	 * Get the tag fields to display
	 * 
	 * @return
	 */
	TagField[] getTagFields();

	/**
	 * Adds an event listener.
	 */
	void addEventListener(final RFIDEventListener listener);

}
