package com.aires.databasesource;

/**
 * Created by 10183966 on 2017/2/17.
 */
public class TDDL {
    private Integer id;

    private String name;

    private String province;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public String toString() {
        return "TDDL{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", province='" + province + '\'' +
                '}';
    }
}
