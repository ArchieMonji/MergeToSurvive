from com.vgdc.merge.entities.abilities import Ability
from com.badlogic.gdx.math import Vector2
from com.vgdc.merge.entities import Entity
from com.vgdc.merge.entities import EntityData
from com.vgdc.merge.entities import Projectile
from com.vgdc.merge.entities.rendering import BlankRenderer
	
class HitBoxAbility(Ability):
	
	def __init__(self, hitbox = "", blank = True):
		self.projectile = hitbox
		self.blank = blank

	def onUse(self, entity, retrievable = False):
		box = self.createProjectile(entity.getWorld().getHandler().getEntityData(self.projectile), entity, retrievable)
		if self.blank:
			box.setRenderer(BlankRenderer())
		box.getMovingBody().setAcceleration(Vector2(0, 0))
		
		
