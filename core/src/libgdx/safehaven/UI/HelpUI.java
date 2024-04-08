package libgdx.safehaven.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import javafx.stage.Screen;
import libgdx.safehaven.Utility;

public class HelpUI extends Window {

	private final Array<InventoryObserver> _observers;
	private final InventorySlotTooltip _inventorySlotTooltip;
	private Table contentTable;



	public HelpUI() {
		super("Help", Utility.HELPUI_SKIN, "default");

		_observers = new Array<InventoryObserver>();
		_inventorySlotTooltip = new InventorySlotTooltip(Utility.HELPUI_SKIN);

		initializeLayout();
	}

	private void initializeLayout() {
		contentTable = new Table();
		contentTable.setFillParent(true);


		Label howToPlayLabel = new Label("TIP:\n - Use arrow keys or WASD to move\n", Utility.HELPUI_SKIN);
		howToPlayLabel.setAlignment(Align.center);
		contentTable.add(howToPlayLabel).pad(100).row();

		Label story = new Label("Story:\n - Your task is to defeat the bosses, and battle enemies.\n " +
			"You can complete quests to earn coins to spend at the shops.\n" +
			"Additionally, the quests will also help you complete the game linearly", Utility.HELPUI_SKIN);
		story.setAlignment(Align.center);
		contentTable.add(story).pad(100).width(contentTable.getWidth() - 20).row();



		this.add(contentTable).fill().expand();
		this.pack();
		this.setMovable(false);

		this.setVisible(false);
	}

	public void toggleVisibility() {
		this.setVisible(!this.isVisible());
		System.out.println("HelpUI " + "Toggling visibility: now " + (this.isVisible() ? "visible" : "hidden"));
	}

}
