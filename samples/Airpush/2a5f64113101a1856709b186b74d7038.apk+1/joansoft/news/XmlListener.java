package joansoft.news;

public interface XmlListener {
    void onEndTag(String str);

    void onFinish();

    void onStartTag(String str);

    void onStartTag(String str, String str2);

    void onText(String str, String str2);
}
