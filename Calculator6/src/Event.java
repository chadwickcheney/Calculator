import java.util.ArrayList;
import java.util.HashMap;

/**
 * Event class will receive inputs from Class Controller and kick off events
 * 
 * @author Chadwick Cheney
 *
 */
public class Event {

	private static final boolean DEBUG = true;
	private ArrayList<Keys> userInputs;
	private Display display;
	private ArrayList<Button> buttons;
	private Calculator calculator;
	private HashMap<Keys, Button> buttonsDragoman;

	/**
	 * Event constructor.
	 * 
	 * @param display is the display element of the program initialized in Class
	 *                Main and Instantiated in load.getDisplay().
	 */
	public Event(Display display, ArrayList<Button> buttons,
			HashMap<Keys, Button> buttonsDragoman) {
		userInputs = new ArrayList<Keys>();
		this.display = display;
		this.buttons = buttons;
		this.buttonsDragoman = buttonsDragoman;
		calculator = new Calculator();
	}

	/**
	 * Called from controller whenever an key event or mouse event is detected
	 * 
	 * @param key is the Class Key object
	 * @param b   is the button mapping to that Key
	 * @see #getLastOpenToggledButton(ArrayList)
	 * @see #deleteKey()
	 * @return no return value.
	 */
	public void interpreter(Button b) {
		Keys key = b.key;
		if (display.isOn) {
			b.pressed();
			try {
				System.out.println(key);
				switch (key) {
				case ENTER:
					calculate(userInputs);
					display.tick();
					break;
				case DELETEALL:
					deleteAllKeys();
					display.updateTextWithKeys(userInputs);
					break;
				case DELETE:
					Keys deletedKey = deleteKey();
					Button btnOfKeyDeleted = buttonsDragoman.get(deletedKey);
					display.updateTextWithKeys(userInputs);
					if (deletedKey == Keys.RIGHT_PARENTHESIS) {
						Button btn = getLastClosedToggleAsButton(userInputs);
						if (btn != null) {
							btn.pressed();
						}
					} else if (btnOfKeyDeleted.toggle) {
						btnOfKeyDeleted.changeToggleStatus();
					}
					break;
				case POWER:
					if (b.getToggleStatus()) {
						userInputs.add(key);
						userInputs.add(Keys.LEFT_PARENTHESIS);
					} else
						userInputs.add(Keys.RIGHT_PARENTHESIS);
					display.updateTextWithKeys(userInputs);
					break;
				case ROOT:
					if (b.getToggleStatus()) {
						userInputs.add(key);
						userInputs.add(Keys.LEFT_PARENTHESIS);
					} else
						userInputs.add(Keys.RIGHT_PARENTHESIS);
					display.updateTextWithKeys(userInputs);
					break;
				case NEGATE:
					ArrayList<Keys> temp4UserInputs = userInputs;
					int index = 0;
					Keys key4 = temp4UserInputs.get(index);

					if (key4 == Keys.SUBTRACTION) {
						break;
					}

					ArrayList<Keys> temp3UserInputs = new ArrayList<Keys>();
					temp3UserInputs.add(Keys.SUBTRACTION);
					for (Keys k : userInputs)
						temp3UserInputs.add(k);
					userInputs = temp3UserInputs;
					display.updateTextWithKeys(userInputs);
					break;
				case RIGHT_PARENTHESIS:
					// Check to see if you can close the next toggled queue
					Button btn = getLastOpenToggledButton(userInputs);
					if (btn != null) {
						if (btn.toggle) {
							btn.changeToggleStatus();
							b.changeToggleStatus();
						}
					}
				default:
					userInputs.add(key);
					display.updateTextWithKeys(userInputs);
				}
			} catch (NullPointerException e) {
				System.out.println(" invalid key press ");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Deletes all keys in user inputs
	 * 
	 * @return no return value
	 */
	public void deleteAllKeys() {
		userInputs = new ArrayList<Keys>();
		for (Button b : buttons) {
			if (b.isCurrentlyToggled) {
				b.changeToggleStatus();
			}
		}
	}

	/**
	 * Deletes the string mapping to the Key object of the most recent input.
	 * 
	 * @return the key was deleted.
	 */
	public Keys deleteKey() {
		Keys deletedKey = userInputs.remove(userInputs.size() - 1);
		return deletedKey;
	}

	/**
	 * Gets the last toggle that was opened and closed successfully to ensure
	 * when the user deletes a key, it deactivates or actives the toggle as
	 * expected.
	 * 
	 * @param tempUserInputs
	 * @return
	 */
	public Button getLastClosedToggleAsButton(ArrayList<Keys> tempUserInputs) {
		int pass = 0;
		for (int index = tempUserInputs.size() - 1; index > 0; --index) {
			if (tempUserInputs.get(index) == Keys.RIGHT_PARENTHESIS) {
				++pass;
			}

			else if (tempUserInputs.get(index) == Keys.LEFT_PARENTHESIS) {
				if (pass == 0) {
					try {
						int priorBtnToggleCounter = 0;
						try {
							if (buttonsDragoman.get(
									tempUserInputs.get(index - 1)).toggle) {
								priorBtnToggleCounter = 1;
							}
						} catch (Exception e) {
							continue;
						}
						return buttonsDragoman.get(tempUserInputs
								.get(index - priorBtnToggleCounter));
					} catch (Exception e) {
						break;
					}
				}
				--pass;
			}
		}
		return null;
	}

	/**
	 * Returns the last open toggled button for adding closing parenthesis.
	 * 
	 * @param userKeys is a temporary variable copy of user inputs.
	 * @return a button that maps to the last toggled key.
	 */
	public Button getLastOpenToggledButton(ArrayList<Keys> userKeys) {
		ArrayList<Keys> tempUserInputs = userKeys;
		ArrayList<Keys> temp2UserInputs = new ArrayList<Keys>();
		boolean foundOpenToggle;
		int foundAt = -1;
		while (true) {
			foundOpenToggle = false;
			for (int index = 0; index < tempUserInputs.size(); ++index) {
				Button btn = buttonsDragoman.get(tempUserInputs.get(index));
				System.out.println(
						"getlastopentoggledbutton: iscurrentlytoggled: "
								+ btn.isCurrentlyToggled);
				if (btn.isCurrentlyToggled) {
					foundOpenToggle = true;
					foundAt = index;
				} else if (!btn.isCurrentlyToggled && btn.toggle) {

				} else if (foundOpenToggle) {
					temp2UserInputs.add(tempUserInputs.get(index));
				}
			}
			if (!foundOpenToggle)
				break;
			else
				tempUserInputs = temp2UserInputs;
		}
		if (foundAt != -1) {
			Keys tempKey = userKeys.get(foundAt);
			Button btn = this.buttonsDragoman.get(tempKey);
			return btn;
		}
		return null;
	}

	/**
	 * Calls Class Calculator method run to validate and run the mathematical
	 * expression and saves the results for further calculations for the user.
	 * 
	 * @param expr is the array list of Key objects that user entered.
	 * @return no return value
	 */
	public void calculate(ArrayList<Keys> expr) {
		String set = display.keysMapString(userInputs);
		Double answer = calculator.run(set);

		// if the answer does not have decimal values,
		// then only an int will appear on the display
		if (answer != null) {
			ArrayList<Keys> keys = answer % 1 == 0
					? display.updateKeysWithString(
							Integer.toString(answer.intValue()))
					: display.updateKeysWithString(answer.toString());
			userInputs = keys;
			if (DEBUG)
				System.out.println("userInputs: " + userInputs);
		}
	}
}
