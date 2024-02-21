package libgdx.safehaven.UI;

public interface InventorySlotObserver {
	void onNotify(final InventorySlot slot, SlotEvent event);

	enum SlotEvent {
		ADDED_ITEM,
		REMOVED_ITEM
	}
}
