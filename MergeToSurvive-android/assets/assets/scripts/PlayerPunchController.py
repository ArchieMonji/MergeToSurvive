from HitBoxController import HitBoxController
from com.badlogic.gdx.math import Vector2

class PlayerPunchController(HitBoxController):

	def __init__(self):
		HitBoxController.__init__(self, 0.5, Vector2(3, 3))