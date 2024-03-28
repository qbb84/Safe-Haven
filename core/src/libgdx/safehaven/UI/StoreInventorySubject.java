package libgdx.safehaven.UI;


public interface StoreInventorySubject {
	void addObserver(StoreInventoryObserver storeObserver);

	void removeObserver(StoreInventoryObserver storeObserver);

	void removeAllObservers();

	void notify(String value, StoreInventoryObserver.StoreInventoryEvent event);
}
