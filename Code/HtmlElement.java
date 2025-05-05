public abstract class HtmlElement {
    protected String tag;
    protected String text;
    protected String style = "";
    protected String classes = "";

    public HtmlElement(String tag) {
        this.tag = tag;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void addStyle(String property, String value) {
        if (!style.isEmpty()) {
            style += ";";
        }
        style += property + ":" + value;
    }

    public void addClass(String className) {
        if (!classes.isEmpty()) {
            classes += " ";
        }
        classes += className;
    }

    protected String getAttributes() {
        StringBuilder attrs = new StringBuilder();
        if (!classes.isEmpty()) {
            attrs.append(" class=\"").append(classes).append("\"");
        }
        if (!style.isEmpty()) {
            attrs.append(" style=\"").append(style).append("\"");
        }
        return attrs.toString();
    }

    public abstract String render();
}