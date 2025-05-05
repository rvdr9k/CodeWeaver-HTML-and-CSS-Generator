public class Span extends HtmlElement {
    private boolean bold = false;
    private boolean italic = false;
    private boolean underline = false;

    public Span(String content) {
        super("span");
        this.setText(content);
    }

    public void setBold(boolean bold) {
        this.bold = bold;
        updateFontWeight();
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
        updateFontStyle();
    }

    public void setUnderline(boolean underline) {
        this.underline = underline;
        updateTextDecoration();
    }

    private void updateFontWeight() {
        if (bold) {
            addStyle("font-weight", "bold");
        } else {
            // Remove font-weight if it exists
            style = style.replaceAll("font-weight:\\s*bold;?", "");
        }
    }

    private void updateFontStyle() {
        if (italic) {
            addStyle("font-style", "italic");
        } else {
            // Remove font-style if it exists
            style = style.replaceAll("font-style:\\s*italic;?", "");
        }
    }

    private void updateTextDecoration() {
        if (underline) {
            addStyle("text-decoration", "underline");
        } else {
            // Remove text-decoration if it exists
            style = style.replaceAll("text-decoration:\\s*underline;?", "");
        }
    }

    @Override
    public String render() {
        return "<" + tag + getAttributes() + ">" + text + "</" + tag + ">";
    }
}