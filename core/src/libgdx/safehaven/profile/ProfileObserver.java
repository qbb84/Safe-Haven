package libgdx.safehaven.profile;

public interface ProfileObserver {
	void onNotify(final ProfileManager profileManager, ProfileEvent event);

	enum ProfileEvent {
		PROFILE_LOADED,
		SAVING_PROFILE,
		CLEAR_CURRENT_PROFILE
	}
}
