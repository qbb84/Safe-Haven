package com.packtpub.libgdx.safehaven.UI;

public interface InventorySlotSubject {

    public void addObserver(InventorySlotObserver inventorySlotObserver);
    public void removeObserver(InventorySlotObserver inventorySlotObserver);
    public void removeAllObservers();
    public void notify(final InventorySlot slot, InventorySlotObserver.SlotEvent event);
}
