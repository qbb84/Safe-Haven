package libgdx.safehaven.UI;

import com.badlogic.gdx.Gdx;
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

		Label howToPlayLabel = new Label("How to Play:\n - Use arrow keys or WASD to move\n", Utility.HELPUI_SKIN);
		howToPlayLabel.setAlignment(Align.center);
		contentTable.add(howToPlayLabel).pad(10);


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
