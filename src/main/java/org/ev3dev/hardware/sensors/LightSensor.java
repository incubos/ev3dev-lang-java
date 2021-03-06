package org.ev3dev.hardware.sensors;

import org.ev3dev.exception.EV3LibraryException;
import org.ev3dev.exception.InvalidModeException;
import org.ev3dev.exception.InvalidPortException;
import org.ev3dev.exception.InvalidSensorException;
import org.ev3dev.hardware.ports.LegoPort;

/**
 * LEGO NXT Light Sensor
 * @author Anthony
 *
 */
public class LightSensor extends Sensor {

	/**
	 * Reflected light Sysfs required mode
	 */
	public static final String SYSFS_REFLECTED_REQUIRED_MODE = "REFLECT";

	/**
	 * Reflected light Sysfs value index
	 */
	public static final int SYSFS_REFLECTED_VALUE_INDEX = 0;
	
	/**
	 * Ambient light Sysfs required mode
	 */
	public static final String SYSFS_AMBIENT_REQUIRED_MODE = "AMBIENT";

	/**
	 * Ambient light Sysfs value index
	 */
	public static final int SYSFS_AMBIENT_VALUE_INDEX = 0;
	
	/**
	 * This device's default driver name
	 */
	public static final String DRIVER_NAME = "lego-nxt-light";
	
	public boolean autoSwitchMode = true;

	/**
	 * Creates a new LightSensor instance.
	 * @param port LegoPort
	 * @throws InvalidPortException If the specified port wasn't valid
	 * @throws InvalidSensorException If the specified sensor wasn't a LightSensor
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public LightSensor(LegoPort port) throws EV3LibraryException, InvalidPortException, InvalidSensorException {
		super(port);
		if(!this.getDriverName().equals(DRIVER_NAME)){
			throw new InvalidSensorException("Can't create a LightSensor instance if the port isn't connected to a light sensor!");
		}
	}
	
	/**
	 * A measurement of the reflected light intensity, as a percentage.
	 * @return A measurement of the reflected light intensity
	 * @throws EV3LibraryException If I/O goes wrong
	 * @throws InvalidModeException The mode selected wasn't valid, or <b>Auto Switch Mode</b> has disabled.
	 */
	public float getReflectedLightIntensity() throws EV3LibraryException, InvalidModeException{
		if (!this.getMode().equals(SYSFS_REFLECTED_REQUIRED_MODE)){
			if (autoSwitchMode){
				this.setMode(SYSFS_REFLECTED_REQUIRED_MODE);
			} else {
				throw new InvalidModeException("[Auto-switch is off] You are not using a correct mode(" + SYSFS_REFLECTED_REQUIRED_MODE + ")! Yours: " + this.getMode());
			}
		}
		String str = this.getAttribute("value" + SYSFS_REFLECTED_VALUE_INDEX);
		return Float.parseFloat(str);
	}
	
	/**
	 * A measurement of the ambient light intensity, as a percentage.
	 * @return A measurement of the ambient light intensity
	 * @throws EV3LibraryException If I/O goes wrong
	 * @throws InvalidModeException The mode selected wasn't valid, or <b>Auto Switch Mode</b> has disabled.
	 */
	public float getAmbientLightIntensity() throws EV3LibraryException, InvalidModeException{
		if (!this.getMode().equals(SYSFS_AMBIENT_REQUIRED_MODE)){
			if (autoSwitchMode){
				this.setMode(SYSFS_AMBIENT_REQUIRED_MODE);
			} else {
				throw new InvalidModeException("[Auto-switch is off] You are not using a correct mode(" + SYSFS_AMBIENT_REQUIRED_MODE + ")! Yours: " + this.getMode());
			}
		}
		String str = this.getAttribute("value" + SYSFS_AMBIENT_VALUE_INDEX);
		return Float.parseFloat(str);
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
	public boolean isAutoSwitchMode(){
		return autoSwitchMode;
	}

}
