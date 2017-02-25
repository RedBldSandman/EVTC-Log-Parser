package enums;

public enum MenuChoice {

	// Constants
	QUIT(0, false),
	FINAL_DPS(1, true),
	PHASE_DPS(2, true),
	DMG_DIST(3, true),
	G_TOTAL_DMG(4, false),
	MISC_STATS(5, true),
	FINAL_BOONS(6, true),
	PHASE_BOONS(7, true),
	COMPARE_BOON(8, true),
	DUMP_TABLES(9, false),
	DUMP_EVTC(10, false);

	// Fields
	private int ID;
	private boolean can_be_associated;

	// Constructor
	MenuChoice(int ID, boolean can_be_associated) {
		this.ID = ID;
		this.can_be_associated = can_be_associated;
	}

	// Public Methods
	public static MenuChoice getEnum(int ID) {
		for (MenuChoice c : values()) {
			if (c.getID() == ID) {
				return c;
			}
		}
		return null;
	}

	// Getters
	public int getID() {
		return ID;
	}

	public boolean canBeAssociated() {
		return can_be_associated;
	}

}
