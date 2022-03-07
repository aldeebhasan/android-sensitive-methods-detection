package joansoft.news;

public class XmlStripper implements XmlListener {
    private static XmlStripper me = new XmlStripper();
    XmlParser parser = new XmlParser("", this);
    StringBuilder sb;

    public static String getText(String xml) {
        return me.strip(xml);
    }

    private XmlStripper() {
    }

    private String strip(String xml) {
        this.sb = new StringBuilder();
        this.parser.setData(xml);
        this.parser.start();
        String str = this.sb.toString();
        this.sb = null;
        return str;
    }

    public void onEndTag(String name) {
    }

    public void onFinish() {
    }

    public void onStartTag(String name) {
    }

    public void onText(String text, String name) {
        this.sb.append(text);
        this.sb.append(' ');
    }

    public void onStartTag(String name, String params) {
    }
}
