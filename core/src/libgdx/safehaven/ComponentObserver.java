package libgdx.safehaven;

public interface ComponentObserver {
	void onNotify(final String value, ComponentEvent event);

	enum ComponentEvent {
		LOAD_CONVERSATION,
		SHOW_CONVERSATION,
		HIDE_CONVERSATION,
		QUEST_LOCATION_DISCOVERED,
		ENEMY_SPAWN_LOCATION_CHANGED,
		PLAYER_HAS_MOVED
	}
}
