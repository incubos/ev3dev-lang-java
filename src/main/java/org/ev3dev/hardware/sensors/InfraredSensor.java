package org.ev3dev.hardware.sensors;

import org.ev3dev.exception.EV3LibraryException;
import org.ev3dev.exception.InvalidModeException;
import org.ev3dev.exception.InvalidPortException;
import org.ev3dev.exception.InvalidSensorException;
import org.ev3dev.hardware.ports.LegoPort;

/**
 * LEGO EV3 infrared sensor.
 * @author Anthony
 *
 */
public class InfraredSensor extends Sensor {
	
	/**
	 * Proximity required Sysfs mode
	 */
	public static final String SYSFS_PROXIMITY_REQUIRED_MODE = "IR-PROX";
	
	/**
	 * Proximity Sysfs value index
	 */
	public static final int SYSFS_PROXIMITY_VALUE_INDEX = 0;
	
	/**
	 * This device's default driver name
	 */
	public static final String DRIVER_NAME = "lego-ev3-ir";

	private boolean autoSwitchMode = true;
	
	/**
	 * Creates a new InfraredSensor instance.
	 * @param port LegoPort
	 * @throws InvalidPortException If the specified port wasn't valid
	 * @throws InvalidSensorException If the specified sensor wasn't a InfraredSensor
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public InfraredSensor(LegoPort port) throws EV3LibraryException, InvalidPortException, InvalidSensorException {
		super(port);
		if (!this.getDriverName().equals(DRIVER_NAME)){
			throw new InvalidSensorException("Can't create a InfraredSensor instance if the port isn't connected a infrared sensor!");
		}
	}

	/**
	 * A measurement of the distance between the sensor and the remote, as a percentage. 100% is approximately 70cm/27in.
	 * @return A measurement of the distance
	 * @throws EV3LibraryException If I/O goes wrong
	 * @throws InvalidModeException The mode selected wasn't valid, or <b>Auto Switch Mode</b> has disabled.
	 */
	public int getProximity() throws InvalidModeException, EV3LibraryException{
		if (!this.getMode().equals(SYSFS_PROXIMITY_REQUIRED_MODE)){
			if (autoSwitchMode){
				this.setMode(SYSFS_PROXIMITY_REQUIRED_MODE);
			} else {
				throw new InvalidModeException("[Auto-switch is off] You are not using a correct mode(" + SYSFS_PROXIMITY_REQUIRED_MODE + ")! Yours: " + this.getMode());
			}
		}
		String str = this.getAttribute("value" + SYSFS_PROXIMITY_VALUE_INDEX);
		return Integer.parseInt(str);
	}
	
	/**
	 * Set Auto Switch Mode to be enabled or disabled.<br>
	 * (Default: enabled)
	 * @param autoswitch A Boolean
	 */
	public void setAutoSwitchMode(boolean autoswitch){
		this.autoSwitchMode = autoswitch;
	}
	
	/**
	 * Get whether Auto Switch Mode is enabled or disabled.
	 * @return A Boolean
	 */
	public boolean getAutoSwitchMode(){
		return autoSwitchMode;
	}
}
