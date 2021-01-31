package food.restra.hungerbite.customer.feed.livedata;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import food.restra.hungerbite.customer.feed.model.PostModel;

public class PostListLiveData extends LiveData<List<PostModel>> implements OnCompleteListener<QuerySnapshot> {
  private Task<QuerySnapshot> querySnapshotTask;

  private List<PostModel> shoppingListTemp = new ArrayList<>();

  public MutableLiveData<List<PostModel>> postList = new MutableLiveData<List<PostModel>>();

  private ListenerRegistration listenerRegistration = () -> {};

  public PostListLiveData(Task<QuerySnapshot> querySnapshotTask) {
    this.querySnapshotTask = querySnapshotTask;
  }

  @Override
  protected void onActive() {
    querySnapshotTask.addOnCompleteListener(this);
    super.onActive();
  }

  @Override
  protected void onInactive() {
    super.onInactive();
  }

  @Override
  public void onComplete(@NonNull Task<QuerySnapshot> task) {
    if (task.isSuccessful()) {
      shoppingListTemp.clear();
      for (DocumentSnapshot document : task.getResult()) {
        if (document.exists()) {
          PostModel postModel = document.toObject(PostModel.class);
          shoppingListTemp.add(postModel);
        }
      }
      postList.setValue(shoppingListTemp);
    }
  }
}
