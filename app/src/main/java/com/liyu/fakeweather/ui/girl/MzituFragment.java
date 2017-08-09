package com.liyu.fakeweather.ui.girl;

import com.liyu.fakeweather.model.Girl;
import com.liyu.fakeweather.ui.base.BaseGirlsListFragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liyu on 2016/12/13.
 */

public class MzituFragment extends BaseGirlsListFragment {

    @Override
    protected void getGirlFromServer() {
        showRefreshing(true);
        String url = getArguments().getString("url") + "/page/" + currentPage;
        final String fakeRefer = getArguments().getString("url") + "/"; //伪造 refer 破解防盗链
        final String realUrl = "http://api.caoliyu.cn/meizitu.php?url=%s&refer=%s";// 然后用自己的服务器进行转发
        subscription = Observable.just(url).subscribeOn(Schedulers.io()).map(new Func1<String, List<Girl>>() {
            @Override
            public List<Girl> call(String url) {
                List<Girl> girls = new ArrayList<>();
                try {
                    Document doc = Jsoup.connect(url).timeout(10000).get();
                    Element total = doc.select("div.postlist").first();
                    Elements items = total.select("li");
                    for (Element element : items) {
                        Girl girl = new Girl(String.format(realUrl, element.select("img").first().attr("data-original"), fakeRefer));
                        girl.setLink(element.select("a[href]").attr("href"));
                        girls.add(girl);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return girls;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Girl>>() {
            @Override
            public void onCompleted() {
                isLoading = false;
            }

            @Override
            public void onError(Throwable e) {
                isLoading = false;
                showRefreshing(false);
            }

            @Override
            public void onNext(List<Girl> girls) {
                currentPage++;
                showRefreshing(false);
                if (adapter.getData() == null || adapter.getData().size() == 0) {
                    adapter.setNewData(girls);
                } else {
                    adapter.addData(adapter.getData().size(), girls);
                }
            }
        });
    }
}
