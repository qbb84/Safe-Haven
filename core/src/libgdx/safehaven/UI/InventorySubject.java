package libgdx.safehaven.UI;

public interface InventorySubject {
	void addObserver(InventoryObserver inventoryObserver);

	void removeObserver(InventoryObserver inventoryObserver);

	void removeAllObservers();

	void notify(final String value, InventoryObserver.InventoryEvent event);
}
