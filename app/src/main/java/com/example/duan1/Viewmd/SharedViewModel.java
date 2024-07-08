package com.example.duan1.Viewmd;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashSet;
import java.util.Set;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<Integer> maspLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> addToCartClickedLiveData = new MutableLiveData<>();
    private Set<Integer> cartProductIds = new HashSet<>();
    private MutableLiveData<Integer> quantityToAddLiveData = new MutableLiveData<>();

    public void setQuantityToAdd(int quantityToAdd) {
        quantityToAddLiveData.setValue(quantityToAdd);
    }

    public MutableLiveData<Integer> getQuantityToAddLiveData() {
        return quantityToAddLiveData;
    }

    public void setMasp(int value) {
        maspLiveData.setValue(value);
    }

    public LiveData<Integer> getMasp() {
        return maspLiveData;
    }

    public LiveData<Boolean> getAddToCartClicked() {
        return addToCartClickedLiveData;
    }

    public void setAddToCartClicked(boolean clicked) {
        addToCartClickedLiveData.setValue(clicked);
    }

    public void addProductToCart(int masp) {
        cartProductIds.add(masp);
    }

    public boolean isProductInCart(int masp) {
        return cartProductIds.contains(masp);
    }

    public void removeProductFromCart(int masp) {
        cartProductIds.remove(masp);
    }
}
