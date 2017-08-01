package com.liyu.fakeweather.utils;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by liyu on 2017/7/30.
 */

public class SortUtil {

    public static void sort(List<? extends Sortable> list) {
        final Collator chineseCollator = Collator.getInstance(Locale.CHINA);
        Collections.sort(list, new Comparator<Sortable>() {
            @Override
            public int compare(Sortable o1, Sortable o2) {
                return chineseCollator.compare(o1.sortName(), o2.sortName());
            }
        });
    }

    public interface Sortable {
        String sortName();
    }
}


