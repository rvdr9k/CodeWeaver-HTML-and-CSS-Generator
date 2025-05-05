public class Paragraph extends HtmlElement {
    private String alignment = "left";

    public Paragraph(String content) {
        super("p");
        this.setText(content);
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
        this.addStyle("text-align", alignment);
    }

    @Override
    public String render() {
        return "<" + tag + getAttributes() + ">" + text + "</" + tag + ">";
    }
}