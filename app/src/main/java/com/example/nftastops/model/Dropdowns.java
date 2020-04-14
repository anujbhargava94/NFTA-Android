package com.example.nftastops.model;

public class Dropdowns {
    private Integer dropdown_id;
    private String dropdown_type;
    private Integer dropdown_value;
    private String display_name;

    public Integer getDropdown_id() {
        return dropdown_id;
    }

    public void setDropdown_id(Integer dropdown_id) {
        this.dropdown_id = dropdown_id;
    }

    public String getDropdown_type() {
        return dropdown_type;
    }

    public void setDropdown_type(String dropdown_type) {
        this.dropdown_type = dropdown_type;
    }

    public Integer getDropdown_value() {
        return dropdown_value;
    }

    public void setDropdown_value(Integer dropdown_value) {
        this.dropdown_value = dropdown_value;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }


    @Override
    public String toString() {

        if(dropdown_value!=null){
            return dropdown_value + ". " + display_name;
        }
        return display_name;
    }
}
