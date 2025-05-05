import java.util.*;

public class HtmlRoot {
    private List<HtmlElement> elements = new ArrayList<>();
    private List<List<HtmlElement>> history = new ArrayList<>();
    private int currentState = -1;

    public void addElement(HtmlElement e) {
        saveState();
        elements.add(e);
    }

    public void clearAll() {
        saveState();
        elements.clear();
        saveState();
    }

    public boolean undo() {
        if (currentState <= 0) return false;
        currentState--;
        elements = new ArrayList<>(history.get(currentState));
        return true;
    }

    public boolean redo() {
        if (currentState >= history.size() - 1) return false;
        currentState++;
        elements = new ArrayList<>(history.get(currentState));
        return true;
    }

    public boolean canUndo() {
        return currentState > 0;
    }

    public boolean canRedo() {
        return currentState < history.size() - 1;
    }

    private void saveState() {
        if (currentState < history.size() - 1) {
            history = history.subList(0, currentState + 1);
        }
        
        history.add(new ArrayList<>(elements));
        currentState++;
    }

    public String render() {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n<html>\n<head>\n");
        html.append("  <title>Generated HTML</title>\n");
        html.append("  <meta charset=\"UTF-8\">\n");
        html.append("  <style>body { margin: 20px; }</style>\n");
        html.append("</head>\n<body>\n");
        
        for (HtmlElement e : elements) {
            html.append("  ").append(e.render()).append("\n");
        }
        
        html.append("</body>\n</html>");
        return html.toString();
    }
}