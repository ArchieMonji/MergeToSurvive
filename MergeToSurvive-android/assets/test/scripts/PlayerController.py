from com.badlogic.gdx import Gdx
from com.badlogic.gdx.graphics import Color
from com.vgdc.merge.entities import Entity
from com.vgdc.merge.entities.abilities import Ability
from com.vgdc.merge.entities.controllers import UnitController

class PlayerController(UnitController):
	
	up = 51
	down = 47
	left = 29
	right = 32
	ability = 0	
	
	def __init__(self):
		self.invulnerableTime = 1.0
		self.numFlashes = 6
		self.alpha = 0.5
		self.alphaToggle = 0
		self.num = 0
		self.fired = False
		self.toggled = False
		
	def onUpdate(self, delta):
		if Gdx.input.isKeyPressed(self.up):
			self.tryJump(delta)
		if Gdx.input.isKeyPressed(self.left):
			self.moveLeft(delta)
		if Gdx.input.isKeyPressed(self.right):
			self.moveRight(delta)
		if Gdx.input.isButtonPressed(self.ability):
			if not self.fired:
				if not self.useAbility(0, True):
					self.useDefault(0)
				self.fired = True
		elif self.fired:
			self.fired = False
		if self.alphaToggle > 0:
			self.alphaToggle -= delta
			if self.alphaToggle <= 0 and self.num > 0:
				self.alphaToggle += self.invulnerableTime/self.numFlashes/2
				color = self.getEntity().getRenderer().getColor()
				if color.a == 1.0:
					self.getEntity().getRenderer().setColor(color.r, color.g, color.b, self.alpha)
				else:
					self.getEntity().getRenderer().setColor(color.r, color.g, color.b, 1)
					self.num -= 1
		UnitController.onUpdate(self, delta)
		
	def copy(self):
		c = PlayerController()
		c.up = self.up
		c.down = self.down
		c.left = self.left;
		c.right = self.right
		c.ability = self.ability
		c.invulnerableTime = self.invulnerableTime
		c.numFlashes = self.numFlashes
		c.alpha = self.alpha
		return c
	
	def onDamage(self, entity):
		if self.alphaToggle <= 0:
			UnitController.onDamage(self, entity)
			self.alphaToggle = self.invulnerableTime/self.numFlashes/2
			self.num = self.numFlashes
			