package to.nate;

public class Colors {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public static final String INFO = ANSI_BLUE + "[ℹ] " + ANSI_RESET;
    public static final String SUCCESS = ANSI_GREEN + "✓" + ANSI_RESET;
    public static final String FAIL = ANSI_RED + "[X]" + ANSI_RESET;
}
