public class Heading extends HtmlElement {
    private int level;

    public Heading(String text, int level) {
        super("h" + level);
        this.level = level;
        this.setText(text);
    }

    public void setAlignment(String alignment) {
        this.addStyle("text-align", alignment);
    }

    @Override
    public String render() {
        return "<" + tag + getAttributes() + ">" + text + "</" + tag + ">";
    }
}