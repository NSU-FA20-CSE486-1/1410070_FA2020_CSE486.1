package food.restra.hungerbite.feature.customer.feed.repository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import food.restra.hungerbite.feature.customer.feed.livedata.PostListLiveData;


public class PostListRepository {
  private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

  public PostListLiveData getLunchPosts() {
    Task<QuerySnapshot> querySnapshotTask = firebaseFirestore
            .collection("posts")
            .whereEqualTo("category","lunch")
            .get();

    return new PostListLiveData(querySnapshotTask);
  }

  public PostListLiveData getDinnerPosts() {
    Task<QuerySnapshot> querySnapshotTask = firebaseFirestore
            .collection("posts")
            .whereEqualTo("category","dinner")
            .get();

    return new PostListLiveData(querySnapshotTask);
  }

  public PostListLiveData getBreakfastPosts() {
    Task<QuerySnapshot> querySnapshotTask = firebaseFirestore
            .collection("posts")
            .whereEqualTo("category","breakfast")
            .get();

    return new PostListLiveData(querySnapshotTask);
  }

  public PostListLiveData getDessertPosts() {
    Task<QuerySnapshot> querySnapshotTask = firebaseFirestore
            .collection("posts")
            .whereEqualTo("category","dessert")
            .get();

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