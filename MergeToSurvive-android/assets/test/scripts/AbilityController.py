from com.badlogic.gdx.math import Vector2
from com.vgdc.merge.entities import Entity
from com.vgdc.merge.entities import EntityData
from com.vgdc.merge.entities import EntityType
from com.vgdc.merge.entities import Item
from com.vgdc.merge.entities.abilities import Ability
from com.vgdc.merge.entities.physics import MovingBody
from com.vgdc.merge.world import World
from com.vgdc.merge.entities.controllers import Controller

class AbilityController(Controller):
	
	def onDeath(self):
		entity = self.getEntity()
		world = entity.getWorld()
		Controller.onDeath(self)
		abilities = entity.getAbilities()
		if abilities is not None and abilities.size() > 0 and abilities.get(0) is not None:
			print("Drop it!")
			itemName = abilities.get(0).itemName
			if itemName is not None:
				itemData = world.getHandler().getEntityData(itemName)
				item = Item(itemData, world)
				world.getEntityManager().addEntity(item)
				body = item.getMovingBody()
				item.getPhysicsBody().setCollidableWith(EntityType.Item, False)
				item.getPhysicsBody().setCollidableWith(EntityType.Projectile, False)
				body.setPosition(entity.getPosition().cpy())
				body.setElasticity(0.5)
				if entity.facingLeft():
					body.setVelocity(Vector2(15, 25))
				else:
					body.setVelocity(Vector2(-15, 25))
				item.getController().setRelocating(True)