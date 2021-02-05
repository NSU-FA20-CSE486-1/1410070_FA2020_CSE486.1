package food.restra.hungerbite.feature.customer.feed.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import food.restra.hungerbite.feature.customer.feed.livedata.PostListLiveData;
import food.restra.hungerbite.feature.customer.feed.model.FoodItem;
import food.restra.hungerbite.feature.customer.feed.repository.PostListRepository;

public class ShoppingListViewModel extends ViewModel {
  private PostListRepository repository = new PostListRepository();
  PostListLiveData liveData = null;

  public LiveData<List<FoodItem>> getLunchListLiveData() {
    liveData = repository.getLunchPosts();
    return liveData;
  }

  public LiveData<List<FoodItem>> getDinnerListLiveData() {
    liveData = repository.getDinnerPosts();
    return liveData;
  }

  public LiveData<List<FoodItem>> getBreakfastListLiveData() {
    liveData = repository.getBreakfastPosts();
    return liveData;
  }

  public LiveData<List<FoodItem>> getDessertLiveData() {
    liveData = repository.getDessertPosts();
    return liveData;
  }

  public LiveData<List<FoodItem>> getSearchedLiveData(String query){
    liveData = repository.getSearchedData(query);
    return liveData;
  }

  public LiveData<List<FoodItem>> getShoppingList() {
    return liveData.postList;
  }
}
