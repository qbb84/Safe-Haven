package libgdx.safehaven.UI;


public interface StatusObserver {
	void onNotify(final int value, StatusEvent event);

	enum StatusEvent {
		UPDATED_GP,
		UPDATED_LEVEL,
		UPDATED_HP,
		UPDATED_MP,
		UPDATED_XP,
		LEVELED_UP
	}
}
