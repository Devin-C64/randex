package edu.udel.cisc675.randex;

public class PatternMatcher {
    private final char[] chars;

    public PatternMatcher(char[] chars) {
        this.chars = chars;
    }

    public boolean match(int off, char[] c) {
        int n = c.length;
        if (off + n > chars.length) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            if (c[i] != chars[off + i]) {
                return false;
            }
        }
        return true;
    }
}
