from HitBoxAbility import HitBoxAbility

class PlayerPunchAbility(HitBoxAbility):

	def __init__(self):
		HitBoxAbility.__init__(self, 'player_punch')
		self.setOffset(35, 35)