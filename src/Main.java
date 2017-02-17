import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import enums.MenuChoice;
import statistics.Parse;
import statistics.Statistics;
import utility.Utility;

public class Main {

	// Fields
	private static boolean willQuit = false;
	private static boolean willDisplayVersions = true;
	private static Map<String, String> arguments = new HashMap<>();
	private static String current_file;
	private static Parse parsed_file;
	private static Statistics stats;

	// Main
	public static void main(String[] args) {

		// Scanner
		Scanner scan = null;
		try {
			scan = new Scanner(System.in);

			// Read arguments
			for (String arg : args) {
				if (arg.contains("=")) {
					arguments.put(arg.substring(0, arg.indexOf('=')), arg.substring(arg.indexOf('=') + 1));
				}
			}
			String is_anon = arguments.get("is_anon");
			String file_path = arguments.get("file_path");
			String options = arguments.get("options");

			// Data anonymization
			if (is_anon != null) {
				Statistics.willHidePlayers = Utility.getBool(Integer.valueOf(is_anon));
			}

			// File Association
			if (file_path != null && !file_path.isEmpty() && options != null) {
				willDisplayVersions = false;
				int[] choices = options.chars().map(x -> x - '0').toArray();

				StringBuilder output = new StringBuilder("<START>");
				for (int i : choices) {
					MenuChoice c = MenuChoice.getEnum(i);
					if (c != null && c.canBeAssociated()) {
						String result = parsing(c, new File(file_path));
						if (result.startsWith("Warning")) {
							System.out.println(
									"Warning:\tThis log is outdated. Make sure the log is created by arcdps build 20170214 onwards.");
							scan.nextLine();
						} else {
							output.append(System.lineSeparator() + result);
						}
					}
				}
				output.append("<END>");
				System.out.println(output.toString());
				scan.nextLine();

				return;
			}

			// Menu
			else {

				// Create required directories
				new File("./logs").mkdir();
				new File("./graphs").mkdirs();
				new File("./tables").mkdirs();

				// Obtain list of .evtc files in /logs/
				List<File> logs = new ArrayList<File>();
				Utility.recursiveFileSearch("./logs", logs);

				// /logs/ must be non-empty
				if (logs.isEmpty()) {
					System.out.println("/logs/ contains no .evtc files.");
					System.out.println("Press Enter to exit.");
					scan.nextLine();
					return;
				}

				// Display menu
				else {
					while (!willQuit) {
						System.out.println("_______________" + System.lineSeparator());
						System.out.println("EVTC Log Parser");
						System.out.println("_______________" + System.lineSeparator());
						System.out.println("0. Dump EVTC");
						System.out.println("1. Final DPS");
						System.out.println("2. Phase DPS");
						System.out.println("3. Damage Distribution");
						System.out.println("4. Graph Total Damage");
						System.out.println("5. Misc. Combat Stats");
						System.out.println("6. Final Boons");
						System.out.println("7. Phase Boons");
						System.out.println("8. Dump Tables");
						System.out.println("9. Quit");
						System.out.println("_______________" + System.lineSeparator());
						System.out.println("Enter an option by number below:");

						// Read user input
						MenuChoice choice = null;
						try {
							choice = MenuChoice.getEnum(scan.nextInt());

						} catch (InputMismatchException e) {
							e.printStackTrace();
						}
						scan.nextLine();

						// Invalid choice
						if (choice == null) {
							System.out.println("Invalid option. Try again." + System.lineSeparator());
						}

						// Quitting
						else if (choice.equals(MenuChoice.QUIT)) {
							willQuit = true;
						}

						// Valid choice
						else {

							// Apply option to all .evtc files in /logs/
							for (File log : logs) {
								System.out.println(System.lineSeparator() + "Input file:\t" + log.getName());
								String output = parsing(choice, log);
								System.out.println(output);
							}
						}
					}
				}
			}
		}

		// Close scanner
		finally {
			if (scan != null) {
				scan.close();
			}
		}
		return;
	}

	private static String parsing(MenuChoice choice, File log) {

		// Parse the log
		if (current_file == null || !current_file.equals(log.getName().split("\\.(?=[^\\.]+$)")[0])) {
			try {
				parsed_file = new Parse(log);
				stats = new Statistics(parsed_file);
				current_file = log.getName().split("\\.(?=[^\\.]+$)")[0];
				if (willDisplayVersions) {
					System.out.println("Log version:\t" + parsed_file.getBossData().get_build_version());
				}
				if (Integer.valueOf(parsed_file.getBossData().get_build_version()) < 20170214) {
					return "Warning:\t\tThis log is outdated. Make sure the log is created by arcdps build 20170214 onwards.";
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Choice
		if (choice.equals(MenuChoice.FINAL_DPS)) {
			return stats.get_final_dps();
		} else if (choice.equals(MenuChoice.PHASE_DPS)) {
			return stats.get_phase_dps();
		} else if (choice.equals(MenuChoice.DMG_DIST)) {
			return stats.get_damage_distribution();
		} else if (choice.equals(MenuChoice.G_TOTAL_DMG)) {
			return "Output file:\t" + stats.get_total_damage_graph(current_file);
		} else if (choice.equals(MenuChoice.MISC_STATS)) {
			return stats.get_combat_stats();
		} else if (choice.equals(MenuChoice.FINAL_BOONS)) {
			return stats.get_final_boons();
		} else if (choice.equals(MenuChoice.PHASE_BOONS)) {
			return stats.get_phase_boons();
		} else if (choice.equals(MenuChoice.DUMP_EVTC)) {
			File evtc_dump = new File(
					"./tables/" + current_file + "_" + parsed_file.getBossData().get_name() + "_evtc-dump.txt");
			try {
				Utility.writeToFile(parsed_file.toString(), evtc_dump);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "Output file:\t" + evtc_dump.getName();
		} else if (choice.equals(MenuChoice.DUMP_TABLES)) {
			File text_dump = new File(
					"./tables/" + current_file + "_" + parsed_file.getBossData().get_name() + "_all-tables.txt");
			try {
				Utility.writeToFile(stats.get_final_dps() + System.lineSeparator() + stats.get_phase_dps()
						+ System.lineSeparator() + stats.get_combat_stats() + System.lineSeparator()
						+ stats.get_final_boons() + System.lineSeparator() + stats.get_phase_boons()
						+ System.lineSeparator() + stats.get_damage_distribution(), text_dump);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "Output file:\t" + text_dump.getName();
		}
		return "";
	}
}
