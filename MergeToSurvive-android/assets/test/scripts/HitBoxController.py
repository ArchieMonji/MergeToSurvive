from AbilityController import AbilityController
from com.badlogic.gdx.math import Vector2

class HitBoxController(AbilityController):
	
	def __init__(self, duration = 0, knockback = Vector2(0, 0)):
		self.duration = duration
		self.knockback = knockback
		
	def copy(self):
		controller = HitBoxController(self.duration, self.knockback)
		return controller
		
	def onUpdate(self, delta):
		self.duration-=delta
		if self.duration<=0:
			self.onDeath()
	
	def onEntityCollision(self, entity):
		AbilityController.onEntityCollision(self, entity)
		if entity.getTeam() != self.getEntity().getTeam():
			currentVelocity = entity.getMovingBody().getVelocity()
			kb = Vector2(self.knockback.x, self.knockback.y)
			if self.getEntity().facingLeft():
				kb.x = -kb.x
			entity.getMovingBody().setVelocity(Vector2(currentVelocity.x + kb.x, currentVelocity.y + kb.y))
			self.onDeath()