package org.ev3dev.hardware.motors;

import org.ev3dev.exception.EV3LibraryException;
import org.ev3dev.exception.InvalidMotorException;
import org.ev3dev.exception.InvalidPortException;
import org.ev3dev.hardware.Device;
import org.ev3dev.hardware.ports.LegoPort;
import org.ev3dev.io.Sysfs;

/**
 * The DC motor class provides a uniform interface for using regular DC motors
 *  with no fancy controls or feedback. This includes LEGO MINDSTORMS RCX motors and LEGO Power Functions motors.
 * @author Anthony
 *
 */
public class DCMotor extends Device{
	
	/**
	 * The Sysfs class's <code>address</code> property name
	 */
	public static final String SYSFS_PROPERTY_ADDRESS = "address";
	
	/**
	 * The Sysfs class's <code>command</code> property name
	 */
	public static final String SYSFS_PROPERTY_COMMAND = "command";
	
	/**
	 * The Sysfs class's <code>commands</code> property name
	 */
	public static final String SYSFS_PROPERTY_COMMANDS = "commands";
	
	/**
	 * The Sysfs class's <code>driver_name</code> property name
	 */
	public static final String SYSFS_PROPERTY_DRIVER_NAME = "driver_name";
	
	/**
	 * The Sysfs class's <code>duty_cycle</code> property name
	 */
	public static final String SYSFS_PROPERTY_DUTY_CYCLE = "duty_cycle";
	
	/**
	 * The Sysfs class's <code>duty_cycle_sp</code> property name
	 */
	public static final String SYSFS_PROPERTY_DUTY_CYCLE_SP = "duty_cycle_sp";
	
	/**
	 * The Sysfs class's <code>polarity</code> property name
	 */
	public static final String SYSFS_PROPERTY_POLARITY = "polarity";
	
	/**
	 * The Sysfs class's <code>ramp_up_sp</code> property name
	 */
	public static final String SYSFS_PROPERTY_RAMP_UP_SP = "ramp_up_sp";
	
	/**
	 * The Sysfs class's <code>ramp_down_sp</code> property name
	 */
	public static final String SYSFS_PROPERTY_RAMP_DOWN_SP = "ramp_down_sp";
	
	/**
	 * The Sysfs class's <code>state</code> property name
	 */
	public static final String SYSFS_PROPERTY_STATE = "state";
	
	/**
	 * The Sysfs class's <code>stop_action</code> property name
	 */
	public static final String SYSFS_PROPERTY_STOP_ACTION = "stop_action";
	
	/**
	 * The Sysfs class's <code>stop_actions</code> property name
	 */
	public static final String SYSFS_PROPERTY_STOP_ACTIONS = "stop_actions";
	
	/**
	 * The Sysfs class's <code>time_sp</code> property name
	 */
	public static final String SYSFS_PROPERTY_TIME_SP = "time_sp";
	
	/**
	 * The Sysfs class's <code>run-forever</code> command
	 */
	public static final String SYSFS_COMMAND_RUN_FOREVER = "run-forever";
	
	/**
	 * The Sysfs class's <code>run-timed</code> command
	 */
	public static final String SYSFS_COMMAND_RUN_TIMED = "run-timed";
	
	/**
	 * The Sysfs class's <code>run-direct</code> command
	 */
	public static final String SYSFS_COMMAND_RUN_DIRECT = "run-direct";
	
	/**
	 * The Sysfs class's <code>stop</code> command
	 */
	public static final String SYSFS_COMMAND_STOP = "stop";
	
	/**
	 * This Sysfs's class name (e.g. <code>/sys/class/lego-sensor</code>, and <code>lego-sensor</code> is the class name)
	 */
	public static final String CLASS_NAME = "dc-motor";
	
	/**
	 * This Sysfs's class name prefix (e.g. <code>/sys/class/lego-sensor/sensor0</code>, and <code>sensor</code> is the class name prefix without the [N] value.)
	 */
	public static final String CLASS_NAME_PREFIX = "motor";

	private String address;
	
	/***
	 * Creates a new DC motor object.
	 * @param port LegoPort
	 * @throws EV3LibraryException If the LegoPort specified goes wrong
	 */
	public DCMotor(LegoPort port) throws EV3LibraryException{
		super(port, CLASS_NAME, CLASS_NAME_PREFIX);
		
		address = port.getAddress();
		
		//Verify is the LegoPort connecting a motor / is a output
		if (!address.contains("out")){
			throw new InvalidPortException("The specified port (" + port.getAddress() + ") isn't a output.");
		} else if (!port.getStatus().equals(CLASS_NAME)){
			throw new InvalidMotorException("The specified port (" + port.getAddress() + ") isn't a motor (" + port.getStatus() + ")");
		}
	}
	
	/***
	 * Get the address of this motor.
	 * @return LegoPort address described in String
	 * @throws EV3LibraryException If the motor doesn't exist or IO ERROR
	 */
	public String getAddress() throws EV3LibraryException{
		return this.getAttribute(SYSFS_PROPERTY_ADDRESS);
	}
	
	/***
	 * Generic method to send commands to the motor controller.
	 * @param command Command that suits for the motor driver
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void sendCommand(String command) throws EV3LibraryException{
		this.setAttribute(SYSFS_PROPERTY_COMMAND, command);
	}
	
	/***
	 * Cause the motor to run until another command is sent
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void runForever() throws EV3LibraryException{
		sendCommand(SYSFS_COMMAND_RUN_FOREVER);
	}
	
	/***
	 * Run the motor for the amount of time specified in <b>time_sp</b>
	 *  and then stop the motor using the command specified by
	 *  <b>stop_command</b>
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void runTimed() throws EV3LibraryException{
		sendCommand(SYSFS_COMMAND_RUN_TIMED);
	}
	
	/**
	 * Runs the motor at the duty cycle specified by duty_cycle_sp.
	 *  Unlike other run commands, changing duty_cycle_sp while running
	 *   will take effect immediately.
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void runDirect() throws EV3LibraryException{
		sendCommand(SYSFS_COMMAND_RUN_DIRECT);
	}
	
	/**
	 * Stop any of the run commands before they are complete using the command specified by <b>stop_command</b>.
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void stop() throws EV3LibraryException{
		sendCommand(SYSFS_COMMAND_STOP);
	}
	
	/**
	 * Returns a list of commands that are supported by the motor controller.
	 *  Possible values are run-forever, run-to-abs-pos, run-to-rel-pos,
	 *   run-timed, run-direct, stop and reset. Not all commands may be supported.
	 * @return A String Arrays with all the supported commands
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public String[] getCommands() throws EV3LibraryException{
		String str = this.getAttribute(SYSFS_PROPERTY_COMMANDS);
		return Sysfs.separateSpace(str);
	}
	
	/**
	 * Returns the name of the driver that provides this tacho motor device.
	 * @return The name of the driver
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public String getDriverName() throws EV3LibraryException{
		return this.getAttribute(SYSFS_PROPERTY_DRIVER_NAME);
	}
	
	/**
	 * Returns the current duty cycle of the motor. Units are percent. Values are -100 to 100.
	 * @return Percentage
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public int getDutyCycle() throws EV3LibraryException{
		String dutycycle = this.getAttribute(SYSFS_PROPERTY_DUTY_CYCLE);
		return Integer.parseInt(dutycycle);
	}
	
	/**
	 * Writing sets the duty cycle setpoint. Reading returns the current value. Units are in percent.
	 *  Valid values are -100 to 100. A negative value causes the motor to rotate in reverse.
	 *   This value is only used when speed_regulation is off.
	 * @return Percentage
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public int getDutyCycleSP() throws EV3LibraryException{
		String dutycyclesp = this.getAttribute(SYSFS_PROPERTY_DUTY_CYCLE_SP);
		return Integer.parseInt(dutycyclesp);
	}
	
	/**
	 * Writing sets the duty cycle setpoint. Reading returns the current value. Units are in percent.
	 *  Valid values are -100 to 100. A negative value causes the motor to rotate in reverse.
	 *   This value is only used when speed_regulation is off.
	 * @param sp Percentage
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setDutyCycleSP(int sp) throws EV3LibraryException{
		this.setAttribute(SYSFS_PROPERTY_DUTY_CYCLE_SP, Integer.toString(sp));
	}
	
	/**
	 * Sets the polarity of the motor. With normal polarity, a positive duty cycle will cause the motor to rotate clockwise.
	 *  With inversed polarity, a positive duty cycle will cause the motor to rotate counter-clockwise. Valid values are normal and inversed.
	 * @return The polarity of the motor
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public String getPolarity() throws EV3LibraryException{
		return this.getAttribute(SYSFS_PROPERTY_POLARITY);
	}
	
	/**
	 * Sets the polarity of the motor. With normal polarity, a positive duty cycle will cause the motor to rotate clockwise. With inversed polarity,
	 *  a positive duty cycle will cause the motor to rotate counter-clockwise. Valid values are normal and inversed.
	 * @param polarity The polarity of the motor
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setPolarity(String polarity) throws EV3LibraryException{
		this.setAttribute(SYSFS_PROPERTY_POLARITY, polarity);
	}
	
	/**
	 * Writing sets the ramp up setpoint. Reading returns the current value. Units are in milliseconds.
	 *  When set to a value bigger than 0, the motor will ramp the power sent to the motor from 0 to 100% duty
	 *   cycle over the span of this setpoint when starting the motor. If the maximum duty cycle is
	 *    limited by duty_cycle_sp or speed regulation,
	 *  the actual ramp time duration will be less than the setpoint.
	 * @return The ramp-up set-point
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public int getRamp_Up_SP() throws EV3LibraryException{
		String str = this.getAttribute(SYSFS_PROPERTY_RAMP_UP_SP);
		return Integer.parseInt(str);
	}
	
	/**
	 * Writing sets the ramp up setpoint. Reading returns the current value. Units are in milliseconds.
	 *  When set to a value bigger than 0, the motor will ramp the power sent to the motor from 0 to 100% duty
	 *   cycle over the span of this setpoint when starting the motor. If the maximum duty cycle is
	 *    limited by duty_cycle_sp or speed regulation,
	 *  the actual ramp time duration will be less than the setpoint.
	 * @param ramp_up_sp The ramp-up set-point
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setRamp_Up_SP(int ramp_up_sp) throws EV3LibraryException{
		this.setAttribute(SYSFS_PROPERTY_RAMP_UP_SP, Integer.toString(ramp_up_sp));
	}
	
	/**
	 * Writing sets the ramp down setpoint. Reading returns the current value. Units are in milliseconds.
	 *  When set to a value smaller than 0, the motor will ramp the power sent to the motor from 100% duty cycle down
	 *   to 0 over the span of this setpoint when stopping the motor. If the starting
	 *  duty cycle is less than 100%, the ramp time duration will be less than the full span of the setpoint.
	 * @return The ramp-down set-point
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public int getRamp_Down_SP() throws EV3LibraryException{
		String str = this.getAttribute(SYSFS_PROPERTY_RAMP_DOWN_SP);
		return Integer.parseInt(str);
	}
	
	/**
	 * Writing sets the ramp down setpoint. Reading returns the current value. Units are in milliseconds.
	 *  When set to a value smaller than 0, the motor will ramp the power sent to the motor from 100% duty cycle down
	 *   to 0 over the span of this setpoint when stopping the motor. If the starting
	 *  duty cycle is less than 100%, the ramp time duration will be less than the full span of the setpoint.
	 * @param ramp_down_sp The ramp-down set-point
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setRamp_Down_SP(int ramp_down_sp) throws EV3LibraryException{
		this.setAttribute(SYSFS_PROPERTY_RAMP_DOWN_SP, Integer.toString(ramp_down_sp));
	}
	
	/**
	 * <b>This function returns a string that is likely a "spaced-array".</b><br>
	 * <b>Use this function to directly to return a String array:</b>
	 * <pre>
	 * getState()
	 * </pre>
	 * Reading returns a list of state flags. Possible flags are running, ramping holding and stalled.
	 * @return A list of state flags. String spaced-array
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public String getStateViaString() throws EV3LibraryException{
		return this.getAttribute(SYSFS_PROPERTY_STATE);
	}
	
	/**
	 * Reading returns a list of state flags. Possible flags are running, ramping holding and stalled.
	 * @return A list(String array) of state flags.
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public String[] getState() throws EV3LibraryException{
		String str = getStateViaString();
		return Sysfs.separateSpace(str);
	}
	
	/**
	 * Reading returns the current stop command. Writing sets the stop command. The value determines the motors behavior when command is set to stop.
	 *  Also, it determines the motors behavior when a run command completes. See stop_commands for a list of possible values.
	 * @return A stop command that listed using <code>getStopActions()</code>
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public String getStopAction() throws EV3LibraryException{
		return this.getAttribute(SYSFS_PROPERTY_STOP_ACTION);
	}
	
	/**
	 * Reading returns the current stop command. Writing sets the stop command. The value determines the motors behavior when command is set to stop.
	 *  Also, it determines the motors behavior when a run command completes. See stop_commands for a list of possible values.
	 * @param stop_command A stop command that listed using <code>getStopCommands()</code>
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setStopAction(String stop_command) throws EV3LibraryException{
		this.setAttribute(SYSFS_PROPERTY_STOP_ACTION, stop_command);
	}
	
	/**
	 * <b>This function returns a string that is likely a "spaced-array".</b><br>
	 * <b>Use this function to directly to return a String array:</b>
	 * <pre>
	 * getStopCommands()
	 * </pre>
	 * Returns a list of stop modes supported by the motor controller. Possible values are coast,
	 *  brake and hold. coast means that power will be removed from the motor and it will freely
	 *   coast to a stop. brake means that power will be removed from the motor and a passive
	 *    electrical load will be placed on the motor. This is usually done by shorting the motor
	 *     terminals together. This load will absorb the energy from the rotation of the motors
	 *      and cause the motor to stop more quickly than coasting. hold does not remove power from
	 *       the motor. Instead it actively try to hold the motor at the current position.
	 *  If an external force tries to turn the motor, the motor will 'push back' to maintain its position.
	 * @return A list of stop modes supported by the motor controller
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public String getStopActionsViaString() throws EV3LibraryException{
		return this.getAttribute(SYSFS_PROPERTY_STOP_ACTIONS);
	}
	
	/**
	 * Returns a list of stop modes supported by the motor controller. Possible values are coast,
	 *  brake and hold. coast means that power will be removed from the motor and it will freely
	 *   coast to a stop. brake means that power will be removed from the motor and a passive
	 *    electrical load will be placed on the motor. This is usually done by shorting the motor
	 *     terminals together. This load will absorb the energy from the rotation of the motors
	 *      and cause the motor to stop more quickly than coasting. hold does not remove power from
	 *       the motor. Instead it actively try to hold the motor at the current position.
	 *  If an external force tries to turn the motor, the motor will 'push back' to maintain its position.
	 * @return A list of stop modes supported by the motor controller
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public String[] getStopActions() throws EV3LibraryException{
		String str = getStopActionsViaString();
		return Sysfs.separateSpace(str);
	}
	
	/**
	 * Writing specifies the amount of time the motor will run when using the run-timed command. Reading returns the current value. Units are in milliseconds.
	 * @return Amount of time in ms
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public int getTime_SP() throws EV3LibraryException{
		String str = this.getAttribute(SYSFS_PROPERTY_TIME_SP);
		return Integer.parseInt(str);
	}
	
	/**
	 * Writing specifies the amount of time the motor will run when using the run-timed command. Reading returns the current value. Units are in milliseconds.
	 * @param time_sp Amount of time in ms
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setTime_SP(int time_sp) throws EV3LibraryException{
		this.setAttribute(SYSFS_PROPERTY_TIME_SP, Integer.toString(time_sp));
	}
}
