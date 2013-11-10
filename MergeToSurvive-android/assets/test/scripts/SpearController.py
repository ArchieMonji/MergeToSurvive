from HitBoxController import HitBoxController

class SpearController(HitBoxController):
	
	def __init__(self):
		HitBoxController.__init__(self, 0.5, Vector2(10, 10))