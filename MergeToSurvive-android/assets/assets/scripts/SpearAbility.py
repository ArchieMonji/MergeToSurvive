from HitBoxAbility import HitBoxAbility

class SpearAbility(HitBoxAbility):
		
	def __init__(self):
		HitBoxAbility.__init__(self, spear_p_e, False)
		self.itemName = spear_i_e