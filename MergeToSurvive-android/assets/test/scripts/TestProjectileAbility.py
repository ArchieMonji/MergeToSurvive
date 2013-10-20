from com.vgdc.merge.entities.abilities import ProjectileAbility

requirements = { 'test_projectile' : 'EntityData' }

class TestProjectileAbility(ProjectileAbility):

	def __init__(self):
		ProjectileAbility.__init__(self, 'test_projectile')