package joansoft.news;

public class XmlParser {
    private String data;
    private XmlListener listener;

    XmlParser(String data, XmlListener listener) {
        this.data = data;
        this.listener = listener;
    }

    public static void parse(String data, XmlListener listener) {
        new XmlParser(data, listener).start();
        listener.onFinish();
    }

    void setData(String data) {
        this.data = data;
    }

    void start() {
        try {
            this.data = this.data.replaceAll("\r", "");
            this.data = this.data.replaceAll("\n", "");
            int end = 0;
            String tag = null;
            boolean found = false;
            while (true) {
                int start = this.data.indexOf(60, end);
                if (start != -1 && end != -1) {
                    found = true;
                    if (start > end + 1) {
                        int i;
                        String str = this.data;
                        if (end > 0) {
                            i = end + 1;
                        } else {
                            i = 0;
                        }
                        String text = str.substring(i, start).trim();
                        if (text.length() > 0) {
                            this.listener.onText(text, tag);
                        }
                    }
                    end = this.data.indexOf(62, start);
                    if (end != -1) {
                        tag = this.data.substring(start + 1, end).trim();
                        String parms = "";
                        int spaceIndex = tag.indexOf(32);
                        if (spaceIndex != -1) {
                            parms = tag.substring(spaceIndex).trim();
                            tag = tag.substring(0, spaceIndex);
                        }
                        if (tag.charAt(0) == '/') {
                            this.listener.onEndTag(tag.substring(1));
                        } else {
                            this.listener.onStartTag(tag);
                            this.listener.onStartTag(tag, parms);
                        }
                    }
                } else if (!found) {
                    this.listener.onText(this.data, "");
                }
            }
            if (!found) {
                this.listener.onText(this.data, "");
            }
        } catch (Exception e) {
        }
    }
}
