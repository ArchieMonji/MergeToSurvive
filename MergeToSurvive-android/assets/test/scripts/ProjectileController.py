from AbilityController import AbilityController
from com.vgdc.merge.entities import Entity
from com.vgdc.merge.entities import Platform

class ProjectileController(AbilityController):
	
	def __init__(self, time = 0):
		self.timeAlive = time
		
	def copy(self):
		controller = ProjectileController(self.timeAlive)
		return controller
		
	def onUpdate(self, delta):
		self.timeAlive-=delta
		if self.timeAlive<=0:
			print("Death by Time-Out!")
			if not self.getEntity().isDead():
				self.onDeath()
				
	def onPlatformCollision(self, platform):
		if not self.getEntity().isDead():
			print("Platform Collision!")
			self.onDeath()	
			
	def onEntityCollision(self, entity):
		if entity.getTeam() != self.getEntity().getTeam():
			print("Entity Collision!")
			if not self.getEntity().isDead():
				self.onDeath()