package libgdx.safehaven.dialog;

public interface ConversationGraphObserver {
	void onNotify(final ConversationGraph graph, ConversationCommandEvent event);

	enum ConversationCommandEvent {
		LOAD_STORE_INVENTORY,
		EXIT_CONVERSATION,
		ACCEPT_QUEST,
		ADD_ENTITY_TO_INVENTORY,
		RETURN_QUEST,
		NONE
	}
}
