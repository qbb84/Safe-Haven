package libgdx.safehaven.UI;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import libgdx.safehaven.Utility;

public class HelpUIScrollbar implements HelpUIComponent {

	private ScrollPane scrollPane;
	private HelpUI helpUI;

	public HelpUIScrollbar(HelpUI helpUI) {
		this.helpUI = helpUI;
		this.scrollPane = new ScrollPane(helpUI, Utility.HELPUI_SKIN);
	}

	@Override
	public Actor getActor() {
		return scrollPane;
	}

	@Override
	public void toggleVisibility() {
		getActor().setVisible(!getActor().isVisible());
	}
}
