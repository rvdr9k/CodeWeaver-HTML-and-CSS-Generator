public class Link extends HtmlElement {
    private String url;
    private String target = "_self";
    private String title = "";

    public Link(String url, String text) {
        super("a");
        this.url = url;
        this.setText(text);
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String render() {
        StringBuilder sb = new StringBuilder();
        sb.append("<a href=\"").append(url).append("\"");
        
        if (!target.isEmpty()) {
            sb.append(" target=\"").append(target).append("\"");
        }
        if (!title.isEmpty()) {
            sb.append(" title=\"").append(title).append("\"");
        }
        
        sb.append(getAttributes()).append(">")
          .append(text).append("</a>");
        return sb.toString();
    }
}