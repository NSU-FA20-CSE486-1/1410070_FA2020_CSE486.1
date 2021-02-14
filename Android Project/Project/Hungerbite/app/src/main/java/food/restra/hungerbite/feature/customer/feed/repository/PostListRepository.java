package food.restra.hungerbite.feature.customer.feed.repository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import food.restra.hungerbite.feature.customer.feed.livedata.PostListLiveData;


public class PostListRepository {
  private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

  public PostListLiveData getLunchPosts(String location) {
    Task<QuerySnapshot> querySnapshotTask;
    if(location.isEmpty()){
      querySnapshotTask = firebaseFirestore
              .collection("posts")
              .whereEqualTo("category","lunch")
              .get();
    }else{
      querySnapshotTask = firebaseFirestore
              .collection("posts")
              .whereEqualTo("category","lunch")
              .whereEqualTo("location",location)
              .get();
    }


    return new PostListLiveData(querySnapshotTask);
  }

  public PostListLiveData getDinnerPosts(String location) {
    Task<QuerySnapshot> querySnapshotTask;
    if(location.isEmpty()){
      querySnapshotTask = firebaseFirestore
              .collection("posts")
              .whereEqualTo("category","dinner")
              .get();
    }else{
      querySnapshotTask = firebaseFirestore
              .collection("posts")
              .whereEqualTo("category","dinner")
              .whereEqualTo("location",location)
              .get();
    }


    return new PostListLiveData(querySnapshotTask);
  }

  public PostListLiveData getBreakfastPosts(String location) {
    Task<QuerySnapshot> querySnapshotTask;
    if(location.isEmpty()){
      querySnapshotTask = firebaseFirestore
              .collection("posts")
              .whereEqualTo("category","breakfast")
              .get();
    }else{
      querySnapshotTask = firebaseFirestore
              .collection("posts")
              .whereEqualTo("category","breakfast")
              .whereEqualTo("location",location)
              .get();
    }


    return new PostListLiveData(querySnapshotTask);
  }

  public PostListLiveData getDessertPosts(String location) {

    Task<QuerySnapshot> querySnapshotTask;
    if(location.isEmpty()){
      querySnapshotTask = firebaseFirestore
              .collection("posts")
              .whereEqualTo("category","dessert")
              .get();
    }else{
      querySnapshotTask = firebaseFirestore
              .collection("posts")
              .whereEqualTo("category","dessert")
              .whereEqualTo("location",location)
              .get();
    }


    return new PostListLiveData(querySnapshotTask);
  }

  public PostListLiveData getSearchedData(String query) {
    Task<QuerySnapshot> querySnapshotTask = firebaseFirestore
            .collection("posts")
            .whereGreaterThanOrEqualTo("title",query)
            .limit(30)
            .get();

    return new PostListLiveData(querySnapshotTask);
  }
}