package enums;

import java.util.ArrayList;
import java.util.List;

public enum Boon {

	// Constants
	MIGHT("Might", "MGHT", "intensity", 25, 1),
	QUICKNESS("Quickness", "QCKN", "duration", 5, 2),
	FURY("Fury", "FURY", "duration", 9, 3),
	PROTECTION("Protection", "PROT", "duration", 5, 4),
	ALACRITY("Alacrity", "ALAC", "duration", 9, 5),
	SPOTTER("Spotter", "SPOT", "duration", 1, 6),
	SPIRIT_OF_FROST("Spirit of Frost", "FRST", "duration", 1, 7),
	SUN_SPIRIT("Sun Spirit", "SUNS", "duration", 1, 8),
	GLYPH_OF_EMPOWERMENT("Glyph of Empowerment", "GoE", "duration", 1, 9),
	GRACE_OF_THE_LAND("Grace of the Land", "GoTL", "intensity", 5, 10),
	EMPOWER_ALLIES("Empower Allies", "EA", "duration", 1, 11),
	BANNER_OF_STRENGTH("Banner of Strength", "BoS", "duration", 1, 12),
	BANNER_OF_DISCIPLINE("Banner of Discipline", "BoD", "duration", 1, 13),
	SOOTHING_MIST("Soothing Mist", "MIST", "duration", 1, 14),
	ASSASSINS_PRESENCE("Assassin's Presence", "ASNP", "duration", 1, 15);

	// Fields
	private String name;
	private String abrv;
	private String type;
	private int capacity;
	private int ID;

	// Constructor
	private Boon(String name, String abrv, String type, int capacity, int ID) {
		this.name = name;
		this.abrv = abrv;
		this.type = type;
		this.capacity = capacity;
		this.ID = ID;
	}

	// Public Methods
	public static Boon getEnum(String name) {
		for (Boon b : values()) {
			if (b.getName() == name) {
				return b;
			}
		}
		return null;
	}

	public static Boon getEnum(int ID) {
		for (Boon b : values()) {
			if (b.getID() == ID) {
				return b;
			}
		}
		return null;
	}

	public static String[] getArray() {
		List<String> boonList = new ArrayList<String>();
		for (Boon b : values()) {
			boonList.add(b.getAbrv());
		}
		return boonList.toArray(new String[boonList.size()]);
	}

	public static List<String> getList() {
		List<String> boonList = new ArrayList<String>();
		for (Boon b : values()) {
			boonList.add(b.getName());
		}
		return boonList;
	}

	// Getters
	public String getName() {
		return this.name;
	}

	public String getAbrv() {
		return this.abrv;
	}

	public String getType() {
		return this.type;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public int getID() { return this.ID; }

}
