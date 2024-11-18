package ru.iteco.fmhandroid.ui;
import androidx.test.espresso.IdlingResource;

public class SimpleIdlingResource implements IdlingResource {

    private ResourceCallback resourceCallback;
    private volatile boolean isIdle = true; // Начальное состояние - "свободен"

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }

    public void setIdleState(boolean isIdleNow) {
        isIdle = isIdleNow;
        if (isIdle && resourceCallback != null) {
            resourceCallback.onTransitionToIdle();
        }
    }
}