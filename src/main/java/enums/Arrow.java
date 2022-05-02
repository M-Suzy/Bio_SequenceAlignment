package enums;

public enum Arrow {
    UP("/U+2191"), LEFT("/U+2190"), DIAGONAL("/U+2196");

    private final String unicode;

    Arrow(String unicode) {
        this.unicode = unicode;
    }

    public String getUnicode(){
        return unicode;
    }
}
