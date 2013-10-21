from com.vgdc.merge.entities.abilities import ProjectileAbility

requirements = { 'item_projectile' : 'EntityData' }

class ItemProjectileAbility(ProjectileAbility):

	def __init__(self):
		ProjectileAbility.__init__(self, 'item_projectile')