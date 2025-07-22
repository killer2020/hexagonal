package com.abhi.adapter.out.sms;

import java.util.Objects;

public class Sms {

    private String content;
    private String title;

    public Sms(String content, String title) {
        this.content = content;
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Sms{" +
                "content='" + content + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sms sms = (Sms) o;
        return Objects.equals(content, sms.content) && Objects.equals(title, sms.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, title);
    }
}
