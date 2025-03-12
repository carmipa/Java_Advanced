package br.com.fiap.money_flow_api.model;

import java.util.Map;
import java.util.Random;

public class Category {

    private Long id;
    private String name;
    private String icon;

    public Category(Long id, String name, String icon) {
        this.id = (id == null) ? Math.abs(new Random().nextLong()) : id;
        this.name = name;
        this.icon = icon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }


}
