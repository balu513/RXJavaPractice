package com.balu.rxjavapractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.balu.rxjavapractice.api.api_client.PostsApiClient;
import com.balu.rxjavapractice.model.posts.Comment;
import com.balu.rxjavapractice.model.posts.Post;

import java.util.List;

public class FlatMapDemoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_map_demo);

        recyclerView = findViewById(R.id.recycler_view);

        initRecyclerView();
//        flatMapDemo();
        concatMapDemo();
    }
    private void initRecyclerView(){
        adapter = new RecyclerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    public void concatMapDemo(){
        PostsApiClient.getPostsApiClient().getAllPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap(new Function<List<Post>, ObservableSource<Post>>() {
                    @Override
                    public ObservableSource<Post> apply(List<Post> posts) throws Exception {
                        Log.d("Flat map: ","posts size: "+posts.size());
                        adapter.setPosts(posts);
                        return Observable.fromIterable(posts);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap(new Function<Post, ObservableSource<Post>>() {
                    @Override
                    public ObservableSource<Post> apply(final Post post) throws Exception {
                        return PostsApiClient.getPostsApiClient()
                                .getCommentsPostId(post.getId())
                                .map(new Function<List<Comment>, Post>() {
                                    @Override
                                    public Post apply(List<Comment> comments) throws Exception {
                                        post.setComments(comments);
                                        return post;
                                    }
                                }).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                }).subscribe(new Observer<Post>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
            }

            @Override
            public void onNext(final Post post) {
                Log.d("FlatMap onNext: ",post.toString()+" post ID:"+post.getId());
                adapter.updatePost(post);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


    public void flatMapDemo(){
     PostsApiClient.getPostsApiClient().getAllPosts()
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .flatMap(new Function<List<Post>, ObservableSource<Post>>() {
                 @Override
                 public ObservableSource<Post> apply(List<Post> posts) throws Exception {
                     Log.d("Flat map: ","posts size: "+posts.size());
                     adapter.setPosts(posts);
                     return Observable.fromIterable(posts);
                 }
             })
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .flatMap(new Function<Post, ObservableSource<Post>>() {
         @Override
         public ObservableSource<Post> apply(final Post post) throws Exception {
             return PostsApiClient.getPostsApiClient()
                     .getCommentsPostId(post.getId())
                     .map(new Function<List<Comment>, Post>() {
                 @Override
                 public Post apply(List<Comment> comments) throws Exception {
                     post.setComments(comments);
                     return post;
                 }
             }).subscribeOn(Schedulers.io())
                     .observeOn(AndroidSchedulers.mainThread());
         }
     }).subscribe(new Observer<Post>() {
         @Override
         public void onSubscribe(Disposable d) {
           disposables.add(d);
         }

         @Override
         public void onNext(final Post post) {
             Log.d("FlatMap onNext: ",post.toString()+" post ID:"+post.getId());
             adapter.updatePost(post);
         }

         @Override
         public void onError(Throwable e) {

         }

         @Override
         public void onComplete() {

         }
     });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
        disposables.dispose();
    }
}
