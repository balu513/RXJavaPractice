package com.balu.rxjavapractice.api;

import com.balu.rxjavapractice.model.posts.Album;
import com.balu.rxjavapractice.model.posts.Comment;
import com.balu.rxjavapractice.model.posts.Photo;
import com.balu.rxjavapractice.model.posts.Post;
import com.balu.rxjavapractice.model.posts.Todo;
import com.balu.rxjavapractice.model.posts.User;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PostsApiInterface {

    @GET("posts")
    Observable<List<Post>>  getAllPosts();

    @GET("comments")
    Observable<List<Comment>> getAllCommnets();

    @GET("albums")
    Observable<List<Album>> getAlbums();

    @GET("photos")
    Observable<List<Photo>> getAllPhotos();

    @GET("todos")
    Observable<List<Todo>> getAllTodos();

    @GET("users")
    Observable<List<User>> getAllUsers();




    @GET("posts/{id}")
    Observable<Post> getPostByPostId(@Path("id") int id);

    @GET("posts/{id}/comments")
    Observable<List<Comment>> getCommentsPostId(@Path("id") int id);

    @GET("comments")
    Observable<List<Comment>> getCommentsPostId2(@Query("postId") int id);

    @GET("posts")
    Observable<List<Post>> getPostsByUser(@Query("userId") int userId);




}
