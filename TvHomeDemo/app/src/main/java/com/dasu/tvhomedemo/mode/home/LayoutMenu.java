package com.dasu.tvhomedemo.mode.home;

import java.io.Serializable;
import java.util.List;

/**
 * Created by suxq on 2017/8/1.
 */

public class LayoutMenu implements Serializable {

    private long menuId;
    private String menuName;
    private List<Element> elementList;

    public long getMenuId() {
        return menuId;
    }

    public LayoutMenu setMenuId(long menuId) {
        this.menuId = menuId;
        return this;
    }

    public String getMenuName() {
        return menuName;
    }

    public LayoutMenu setMenuName(String menuName) {
        this.menuName = menuName;
        return this;
    }

    public List<Element> getElementList() {
        return elementList;
    }

    public LayoutMenu setElementList(List<Element> elementList) {
        this.elementList = elementList;
        return this;
    }

    public static class Element implements Serializable{
        private long elementId;
        private String elementName;

        public long getElementId() {
            return elementId;
        }

        public Element setElementId(long elementId) {
            this.elementId = elementId;
            return this;
        }

        public String getElementName() {
            return elementName;
        }

        public Element setElementName(String elementName) {
            this.elementName = elementName;
            return this;
        }
    }
}
