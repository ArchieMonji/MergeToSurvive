from ProjectileAbility import ProjectileAbility

requirements = { 'test_projectile' : 'EntityData' }

class TestProjectileAbility(ProjectileAbility):

	def __init__(self):
		ProjectileAbility.__init__(self, 'test_projectile')