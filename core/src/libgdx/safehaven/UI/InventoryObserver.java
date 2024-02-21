package libgdx.safehaven.UI;

public interface InventoryObserver {
	void onNotify(final String value, InventoryEvent event);

	enum InventoryEvent {
		UPDATED_AP,
		UPDATED_DP,
		ITEM_CONSUMED,
		ADD_WAND_AP,
		REMOVE_WAND_AP,
		NONE
	}
}
