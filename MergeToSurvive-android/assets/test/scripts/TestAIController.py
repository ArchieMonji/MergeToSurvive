from com.vgdc.merge.entities import Entity
from com.vgdc.merge.entities.controllers import UnitController

class TestAIController(UnitController):
	
	def __init__(self):
		self.time = 2
		self.movetime = 2
		self.left = False
	
	def copy(self):
		return TestAIController()
	
	def onUpdate(self, delta):
		self.movetime = self.movetime - delta
		if self.movetime < 0:
			self.movetime = self.movetime + self.time
			self.left = not self.left
		if self.left:
			self.moveLeft(delta)
		else:
			self.moveRight(delta);
		UnitController.onUpdate(self, delta);
		
	def onDamage(self, entity):
		UnitController.onDamage(self, entity)
		if self.getEntity().isDead():
			self.onDeath();


