from com.vgdc.merge.entities.controllers import ProjectileController

class TestProjectileController(ProjectileController):

	def __init__(self):
		ProjectileController.__init__(self, 1.0)