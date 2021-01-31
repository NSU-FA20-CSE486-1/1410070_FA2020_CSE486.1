package food.restra.hungerbite.customer.feed.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import food.restra.hungerbite.customer.feed.livedata.PostListLiveData;
import food.restra.hungerbite.customer.feed.model.PostModel;
import food.restra.hungerbite.customer.feed.repository.PostListRepository;

public class ShoppingListViewModel extends ViewModel {
  private PostListRepository repository = new PostListRepository();
  PostListLiveData liveData = null;

  public LiveData<List<PostModel>> getLunchListLiveData() {
    liveData = repository.getLunchPosts();
    return liveData;
  }

  public LiveData<List<PostModel>> getDinnerListLiveData() {
    liveData = repository.getDinnerPosts();
    return liveData;
  }

  public LiveData<List<PostModel>> getBreakfastListLiveData() {
    liveData = repository.getBreakfastPosts();
    return liveData;
  }

  public LiveData<List<PostModel>> getDessertLiveData() {
    liveData = repository.getDessertPosts();
    return liveData;
  }

  public LiveData<List<PostModel>> getShoppingList() {
    return liveData.postList;
  }
}
