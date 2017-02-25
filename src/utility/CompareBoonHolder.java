package utility;

import player.Player;

public class CompareBoonHolder {
    // Fields
    private Player player;
    private String[] rate;
    private double average_rate;

    // Constructor
    public CompareBoonHolder(Player p, String[] rate, double avg) {
        this.player = p;
        this.rate = rate;
        this.average_rate = avg;
    }

    // Getters
    public Player getPlayer() { return player; }

    public String[] getRate() { return rate; }

    public double getAverageRate() { return average_rate; }
}
