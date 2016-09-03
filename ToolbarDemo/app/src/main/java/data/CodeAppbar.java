package data;

/**
 * Created by sxq on 2016/5/31.
 */
public class CodeAppbar {
    private String code;

    public CodeAppbar(){
        this.code ="布局文件代码：\n" +
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<android.support.design.widget.CoordinatorLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                "    xmlns:app=\"http://schemas.android.com/apk/res-auto\"\n" +
                "    xmlns:tools=\"http://schemas.android.com/tools\"\n" +
                "    android:layout_width=\"match_parent\"\n" +
                "    android:layout_height=\"match_parent\"\n" +
                "    android:fitsSystemWindows=\"true\"\n" +
                "    tools:context=\"com.iwin.fragment01.MainActivity\">\n" +
                "\n" +
                "    <android.support.design.widget.AppBarLayout\n" +
                "        android:id=\"@+id/appbar\"\n" +
                "        android:layout_width=\"match_parent\"\n" +
                "        android:layout_height=\"200dp\"\n" +
                "        android:fitsSystemWindows=\"true\"\n" +
                "        android:theme=\"@style/AppTheme.AppBarOverlay\">\n" +
                "\n" +
                "        <android.support.design.widget.CollapsingToolbarLayout\n" +
                "            android:id=\"@+id/collapse\"\n" +
                "            android:layout_width=\"match_parent\"\n" +
                "            android:layout_height=\"match_parent\"\n" +
                "            app:expandedTitleGravity=\"bottom|center\"\n" +
                "            app:expandedTitleMarginStart=\"48dp\"\n" +
                "            app:layout_scrollFlags=\"scroll|exitUntilCollapsed|snap\">\n" +
                "\n" +
                "            <ImageView\n" +
                "                android:layout_width=\"match_parent\"\n" +
                "                android:layout_height=\"200dp\"\n" +
                "                android:scaleType=\"centerCrop\"\n" +
                "                android:src=\"@drawable/meinv1\"\n" +
                "                app:layout_collapseMode=\"parallax\"\n" +
                "                app:layout_collapseParallaxMultiplier=\"0.7\" />\n" +
                "            \n" +
                "            <android.support.v7.widget.Toolbar\n" +
                "                android:id=\"@+id/toolbar\"\n" +
                "                android:layout_width=\"match_parent\"\n" +
                "                android:layout_height=\"?attr/actionBarSize\"\n" +
                "                android:background=\"?attr/colorPrimary\"\n" +
                "                app:layout_collapseMode=\"pin\"\n" +
                "                app:popupTheme=\"@style/AppTheme.PopupOverlay\" />\n" +
                "        </android.support.design.widget.CollapsingToolbarLayout>\n" +
                "    </android.support.design.widget.AppBarLayout>\n" +
                "\n" +
                "    <include layout=\"@layout/content_main\" />\n" +
                "\n" +
                "    <android.support.design.widget.FloatingActionButton\n" +
                "        android:id=\"@+id/fab\"\n" +
                "        android:layout_width=\"wrap_content\"\n" +
                "        android:layout_height=\"wrap_content\"\n" +
                "        android:layout_margin=\"@dimen/fab_margin\"\n" +
                "        android:src=\"@android:drawable/ic_dialog_email\"\n" +
                "        app:layout_anchor=\"@id/appbar\"\n" +
                "        app:layout_anchorGravity=\"bottom|end\" />\n" +
                "\n" +
                "</android.support.design.widget.CoordinatorLayout>\n";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
