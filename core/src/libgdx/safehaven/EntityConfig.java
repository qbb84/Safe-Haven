package libgdx.safehaven;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class EntityConfig {

	private final Array<AnimationConfig> animationConfig;
	private Array<InventoryItem.ItemTypeID> inventory;
	private Entity.State state = Entity.State.IDLE;
	private Entity.Direction direction = Entity.Direction.DOWN;
	private String entityID;
	private String conversationConfigPath;
	private String questConfigPath;
	private String currentQuestID;
	private String itemTypeID;
	private ObjectMap<String, String> entityProperties;

	EntityConfig() {
		animationConfig = new Array<AnimationConfig>();
		inventory = new Array<InventoryItem.ItemTypeID>();
		entityProperties = new ObjectMap<String, String>();
	}

	EntityConfig(EntityConfig config) {
		state = config.getState();
		direction = config.getDirection();
		entityID = config.getEntityID();
		conversationConfigPath = config.getConversationConfigPath();
		questConfigPath = config.getQuestConfigPath();
		currentQuestID = config.getCurrentQuestID();
		itemTypeID = config.getItemTypeID();

		animationConfig = new Array<AnimationConfig>();
		animationConfig.addAll(config.getAnimationConfig());

		inventory = new Array<InventoryItem.ItemTypeID>();
		inventory.addAll(config.getInventory());

		entityProperties = new ObjectMap<String, String>();
		entityProperties.putAll(config.entityProperties);
	}

	public Entity.State getState() {
		return state;
	}

	public Entity.Direction getDirection() {
		return direction;
	}

	public String getEntityID() {
		return entityID;
	}

	public String getConversationConfigPath() {
		return conversationConfigPath;
	}

	public String getQuestConfigPath() {
		return questConfigPath;
	}

	public String getCurrentQuestID() {
		return currentQuestID;
	}

	public void setCurrentQuestID(String currentQuestID) {
		this.currentQuestID = currentQuestID;
	}

	public String getItemTypeID() {
		return itemTypeID;
	}

	public void setItemTypeID(String itemTypeID) {
		this.itemTypeID = itemTypeID;
	}

	public Array<AnimationConfig> getAnimationConfig() {
		return animationConfig;
	}

	public Array<InventoryItem.ItemTypeID> getInventory() {
		return inventory;
	}

	public void setInventory(Array<InventoryItem.ItemTypeID> inventory) {
		this.inventory = inventory;
	}

	public void setQuestConfigPath(String questConfigPath) {
		this.questConfigPath = questConfigPath;
	}

	public void setConversationConfigPath(String conversationConfigPath) {
		this.conversationConfigPath = conversationConfigPath;
	}

	public void setEntityID(String entityID) {
		this.entityID = entityID;
	}

	public void setDirection(Entity.Direction direction) {
		this.direction = direction;
	}

	public void setState(Entity.State state) {
		this.state = state;
	}

	public String getPropertyValue(String key) {
		Object propertyVal = entityProperties.get(key);
		if (propertyVal == null) return "";
		return propertyVal.toString();
	}

	public ObjectMap<String, String> getEntityProperties() {
		return entityProperties;
	}

	public void setEntityProperties(ObjectMap<String, String> entityProperties) {
		this.entityProperties = entityProperties;
	}

	public void setPropertyValue(String key, String value) {
		entityProperties.put(key, value);
	}

	public void addAnimationConfig(AnimationConfig animationConfig) {
		this.animationConfig.add(animationConfig);
	}

	public enum EntityProperties {
		ENTITY_HEALTH_POINTS,
		ENTITY_ATTACK_POINTS,
		ENTITY_DEFENSE_POINTS,
		ENTITY_HIT_DAMAGE_TOTAL,
		ENTITY_XP_REWARD,
		ENTITY_GP_REWARD,
		NONE
	}

	static public class AnimationConfig {
		private float frameDuration = 1.0f;
		private Entity.AnimationType animationType;
		private Array<String> texturePaths;
		private Array<GridPoint2> gridPoints;

		public AnimationConfig() {
			animationType = Entity.AnimationType.IDLE;
			texturePaths = new Array<String>();
			gridPoints = new Array<GridPoint2>();
		}

		public float getFrameDuration() {
			return frameDuration;
		}

		public void setFrameDuration(float frameDuration) {
			this.frameDuration = frameDuration;
		}

		public Array<String> getTexturePaths() {
			return texturePaths;
		}

		public void setTexturePaths(Array<String> texturePaths) {
			this.texturePaths = texturePaths;
		}

		public Array<GridPoint2> getGridPoints() {
			return gridPoints;
		}

		public void setGridPoints(Array<GridPoint2> gridPoints) {
			this.gridPoints = gridPoints;
		}

		public Entity.AnimationType getAnimationType() {
			return animationType;
		}

		public void setAnimationType(Entity.AnimationType animationType) {
			this.animationType = animationType;
		}
	}

}
