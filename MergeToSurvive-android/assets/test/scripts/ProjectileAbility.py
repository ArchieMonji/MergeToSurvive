from com.vgdc.merge.entities.abilities import Ability
from com.badlogic.gdx.math import Vector2
from com.vgdc.merge.entities import Entity
from com.vgdc.merge.entities import EntityData;

class ProjectileAbility(Ability):
	
	def __init__(self, projectile):
		self.projectile = projectile

	def onUse(self, entity, retrievable = False):
		data = entity.getWorld().getHandler().getEntityData(self.projectile)
		projectile = self.createProjectile(data, entity, retrievable)
		moveSpeed = data.moveSpeed
		if projectile.facingLeft():
			moveSpeed = -moveSpeed
		projectile.getMovingBody().setVelocity(Vector2(moveSpeed,data.jumpHeight))
