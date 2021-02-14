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

  public LiveData<List<FoodItem>> getLunchListLiveData(String location) {
    liveData = repository.getLunchPosts(location);
    return liveData;
  }

  public LiveData<List<FoodItem>> getDinnerListLiveData(String location) {
    liveData = repository.getDinnerPosts(location);
    return liveData;
  }

  public LiveData<List<FoodItem>> getBreakfastListLiveData(String location) {
    liveData = repository.getBreakfastPosts(location);
    return liveData;
  }

  public LiveData<List<FoodItem>> getDessertLiveData(String location) {
    liveData = repository.getDessertPosts(location);
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
