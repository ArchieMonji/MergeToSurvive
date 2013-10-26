from com.vgdc.merge.entities.controllers import AbilityController
from com.vgdc.merge.entities import Entity
from com.vgdc.merge.entities import Platform

class ProjectileController(AbilityController):
	
	def __init__(self):
		self.timeAlive = 0
		
	