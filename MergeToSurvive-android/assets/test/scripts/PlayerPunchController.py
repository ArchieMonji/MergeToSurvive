from com.vgdc.merge.entities.controllers import HitBoxController

class PlayerPunchController(HitBoxController):

	def __init__(self):
		HitBoxController.__init__(self, 0.5, 3, 3)