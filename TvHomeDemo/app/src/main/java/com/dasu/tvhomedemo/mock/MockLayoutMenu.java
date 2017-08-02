package com.dasu.tvhomedemo.mock;

import com.dasu.tvhomedemo.mode.home.LayoutMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by suxq on 2017/8/1.
 */

public class MockLayoutMenu {

    public static List<LayoutMenu> mock() {
        List<LayoutMenu> layoutMenuList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            String menu = "菜单" + i;
            LayoutMenu layoutMenu = new LayoutMenu().setMenuName(menu);
            Random random = new Random();
            List<LayoutMenu.Element> elementList1 = new ArrayList<>();
            int cnt = random.nextInt(30) + 1;
            for (int j = 0; j< cnt; j++) {
                String element = "模块" + i + j;
                LayoutMenu.Element e1 = new LayoutMenu.Element().setElementName(element);
                elementList1.add(e1);
            }
            layoutMenu.setElementList(elementList1);
            layoutMenuList.add(layoutMenu);
        }
        return layoutMenuList;
    }

}
