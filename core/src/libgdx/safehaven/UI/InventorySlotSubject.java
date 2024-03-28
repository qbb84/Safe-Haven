package libgdx.safehaven.UI;

public interface InventorySlotSubject {

	void addObserver(InventorySlotObserver inventorySlotObserver);

	void removeObserver(InventorySlotObserver inventorySlotObserver);

	void removeAllObservers();

	void notify(final InventorySlot slot, InventorySlotObserver.SlotEvent event);
}
