package libgdx.safehaven.UI;

public interface StoreInventoryObserver {
	void onNotify(String value, StoreInventoryEvent event);

	enum StoreInventoryEvent {
		PLAYER_GP_TOTAL_UPDATED,
		PLAYER_INVENTORY_UPDATED
	}
}
