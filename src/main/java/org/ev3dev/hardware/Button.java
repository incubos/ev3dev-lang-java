package org.ev3dev.hardware;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.ev3dev.exception.InvalidButtonException;

/***
 * Provides a generic button reading mechanism that can be adapted to platform specific implementations.
 *  Each platform's specific button capabilites are enumerated in the 'platforms' section of this specification.
 * @author Anthony
 *
 */
public class Button {
	
	public static final String SYSTEM_EVENT_PATH = "/dev/input/by-path/platform-gpio-keys.0-event";
	
	public static final int BUTTON_UP = 103;
	
	public static final int BUTTON_DOWN = 108;
	
	public static final int BUTTON_LEFT = 105;
	
	public static final int BUTTON_RIGHT = 106;
	
	public static final int BUTTON_ENTER = 28;
	
	public static final int BUTTON_BACKSPACE = 14;
	
	private int button;
	
	/**
	 * Creates a new Button instance with the Button specified.
	 * @param button The Integer field of the <code>Button</code> class
	 * @throws InvalidButtonException If the specified button isn't a valid button.
	 */
	public Button(int button) throws InvalidButtonException{
		if (button != BUTTON_UP && button != BUTTON_DOWN && button != BUTTON_LEFT &&
				button != BUTTON_RIGHT && button != BUTTON_ENTER && button != BUTTON_ENTER &&
				button != BUTTON_BACKSPACE){
			throw new InvalidButtonException("The button that you specified does not exist. Better use the integer fields like Button.BUTTON_UP");
		}
		this.button = button;
	}
	
	/**
	 * Returns whether the button is pressed.
	 * @return Boolean that the button is pressed.
	 */
	public boolean isPressed(){
		try {
			DataInputStream in = new DataInputStream(new FileInputStream(SYSTEM_EVENT_PATH));
			byte[] val = new byte[16];
			in.readFully(val);
			in.close();
			return test_bit(button, val);
		} catch (FileNotFoundException e){
			System.err.println("Error: Are you running this on your EV3? You must run it on your EV3.\n If you still have problems, report a issue to \"mob41/ev3dev-lang-java\".");
			e.printStackTrace();
			System.exit(-1);
			return false;
		} catch (IOException e){
			System.err.println("### ERROR MESSAGE ###\nError: Unexpected error! Report an issue to \"mob41/ev3dev-lang-java\" now, with logs!\n === STACK TRACE ===");
			e.printStackTrace();
			System.err.println("=== END STACK TRACE ===\nError: Unexpected error! Report an issue to \"mob41/ev3dev-lang-java\" now, with logs!\n ### END MESSAGE ###");
			System.exit(-1);
			return false;
		}
	}
	
	private static boolean test_bit(int bit, byte[] bytes){
	    return ((bytes[bit / 8] & (1 << (bit % 8))) == 1 ? false : true);
	}
}
