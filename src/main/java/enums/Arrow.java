package enums;

public enum Arrow{
    LEFT(0, "←"), DIAGONAL(1, "↖"), UP(2, "↑");

    private final String unicode;
    private final int position;

    Arrow(int position, String unicode) {
        this.unicode = unicode;
        this.position = position;
    }
    public String getUnicode(){
        return unicode;
    }

    public int getPosition() {
        return position;
    }

}
