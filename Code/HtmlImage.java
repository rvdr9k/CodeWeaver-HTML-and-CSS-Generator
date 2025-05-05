public class HtmlImage extends HtmlElement {
    private String altText = "";
    private String width = "";
    private String height = "";

    public HtmlImage(String url) {
        super("img");
        this.setText(url);
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public void setDimensions(String width, String height) {
        this.width = width;
        this.height = height;
    }

    public void setAlignment(String alignment) {
        this.addStyle("display", "block");
        this.addStyle("margin-left", "auto");
        this.addStyle("margin-right", "auto");
        if (alignment.equals("left")) {
            this.addStyle("margin-left", "0");
        } else if (alignment.equals("right")) {
            this.addStyle("margin-right", "0");
        }
    }

    @Override
    public String render() {
        StringBuilder sb = new StringBuilder();
        sb.append("<img src=\"").append(text).append("\"");
        if (!altText.isEmpty()) sb.append(" alt=\"").append(altText).append("\"");
        if (!width.isEmpty()) sb.append(" width=\"").append(width).append("\"");
        if (!height.isEmpty()) sb.append(" height=\"").append(height).append("\"");
        sb.append(getAttributes()).append(">");
        return sb.toString();
    }
}