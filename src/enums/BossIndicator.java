package enums;

public enum BossIndicator {

    // Constants
    INVULNERABILITY_VG(757, "Vale Guardian"), // Vale Guardian
    PROTECTIVE_SHADOW(31877, "Gorseval the Multifarious"), // Gorseval
    INVULNERABILITY_KC(757, "Keep Construct"), // Keep Construct
    XERAS_BOON(35025, "Keep Construct"), // Keep Construct
    XERA_SPAWN(35002, "Xera"), // Xera
    XERA_PHASE(34113, "Xera"); // Xera

    // Fields
    private int ID;
    private String name;

    // Constructors
    private BossIndicator(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    // Public Methods
    public static BossIndicator getEnum(int ID) {
        for (BossIndicator b : values()) {
            if (b.getID() == ID) {
                return b;
            }
        }
        return null;
    }

    public static BossIndicator getEnum(String name) {
        for (BossIndicator b : values()) {
            if (b.getName().equals(name)) {
                return b;
            }
        }
        return null;
    }

    // Getters
    public int getID() {
        return ID;
    }

    public String getName() { return name; }

}
