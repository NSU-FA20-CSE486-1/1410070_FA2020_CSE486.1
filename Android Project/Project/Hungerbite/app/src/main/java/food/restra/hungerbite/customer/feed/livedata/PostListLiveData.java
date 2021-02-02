package food.restra.hungerbite.customer.feed.livedata;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import food.restra.hungerbite.customer.feed.model.FoodItem;

public class PostListLiveData extends LiveData<List<FoodItem>> implements OnCompleteListener<QuerySnapshot> {
  private Task<QuerySnapshot> querySnapshotTask;

  private List<FoodItem> shoppingListTemp = new ArrayList<>();

  public MutableLiveData<List<FoodItem>> postList = new MutableLiveData<>();

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
          FoodItem foodItem = document.toObject(FoodItem.class);
          shoppingListTemp.add(foodItem);
        }
      }
      postList.setValue(shoppingListTemp);
    }
  }
}
