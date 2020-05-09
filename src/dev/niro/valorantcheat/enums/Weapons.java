package dev.niro.valorantcheat.enums;

public enum Weapons {

	DWNED(0, 0, false),
	SHORTY(0, 0, false),
	FRENZY(0.04, 30, false),
	GHOST(0, 0, false),
	SHERIFF(0, 0, false),
	
	STINGER(0, 0, false),
	SPECTRE(0.04, 40, false),
	
	BUCKY(0, 0, false),
	JUDGE(0.05, 40, false),
	
	BULLDOG(0.035, 20, false),
	GUARDIAN(0.035, 45, false),
	PHANTOM(0.035, 42, false),
	VANDAL(0.033, 50, false),
	
	MARSHAL(0, 0, true),
	OPERATOR(0, 0, true),
	
	ARES(0.030, 92, false),
	ODIN(0.039, 90, false);
	
	private double recoilSpeed;
	private double maxRecoil;
	private boolean sniper;
	
	private Weapons(double recoilSpeed, double maxRecoil, boolean sniper) {
		this.recoilSpeed = recoilSpeed;
		this.maxRecoil = maxRecoil;
		this.sniper = sniper;
	}

	public double getRecoilSpeed() {
		return recoilSpeed;
	}

	public double getMaxRecoil() {
		return maxRecoil;
	}

	public boolean isSniper() {
		return sniper;
	}
		
}
