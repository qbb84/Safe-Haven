package libgdx.safehaven.UI;


public interface StatusSubject {
	void addObserver(StatusObserver statusObserver);

	void removeObserver(StatusObserver statusObserver);

	void removeAllObservers();

	void notify(final int value, StatusObserver.StatusEvent event);
}
