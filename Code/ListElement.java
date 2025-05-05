import java.util.ArrayList;
import java.util.List;

public class ListElement extends HtmlElement {
    private List<String> items = new ArrayList<>();
    private boolean ordered;

    public ListElement(boolean ordered) {
        super(ordered ? "ol" : "ul");
        this.ordered = ordered;
    }

    public void addItem(String item) {
        items.add(item);
    }

    @Override
    public String render() {
        StringBuilder sb = new StringBuilder();
        sb.append("<").append(tag).append(getAttributes()).append(">\n");
        for (String item : items) {
            sb.append("  <li>").append(item).append("</li>\n");
        }
        sb.append("</").append(tag).append(">");
        return sb.toString();
    }
}