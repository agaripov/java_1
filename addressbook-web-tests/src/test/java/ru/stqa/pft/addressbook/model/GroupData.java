package ru.stqa.pft.addressbook.model;

import java.util.Objects;

public class GroupData {
    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private final String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupData groupData = (GroupData) o;
        return Objects.equals(name, groupData.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    private final String footer;
    private final String header;

    public GroupData(String name, String footer, String header) {
        this.id = Integer.MAX_VALUE;
        this.name = name;
        this.footer = footer;
        this.header = header;
    }

    public GroupData(int id, String name, String footer, String header) {
        this.id = id;
        this.name = name;
        this.footer = footer;
        this.header = header;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFooter() {
        return footer;
    }

    public String getHeader() {
        return header;
    }

    @Override
    public String toString() {
        return "GroupData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
